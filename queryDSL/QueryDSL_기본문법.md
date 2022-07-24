# queryDSL 사용방법

## JPQL VS queryDSL

```java
public void startJPQL() {
  // member1을 찾아라
  em.createQuery("select m from Member m 
                  where m.username = :username",
                  Member.class)
        .setParameter("username", "member1")
        .setSingleResult();
        
  Assertions.assertThat(findMember.getUsername()).isEqualTo("member1");
}
```

```java
public void startQueryDSL() {
  JPAQueryFactory queryFactory = new JPAQueryFactory(em);
  qMemeber m = new QMember("m");
  
  Member findMember = queryFacotry
          .select(m)
          .from(m)
          .where(m.username.eq("member1"))
          .fetchOne();
          
  assertThat(findMember.getUsername()).isEqualTo("Member1");
}
```

- 문자 더하기로 작성하는 경우 sql injection공격 대상이 될 수 있다.
- 오타를 잡기 쉽다. (runtime error -> compile error)
- JPAQueryFactory는 필드(클래스 변수, 전역 변수)에 선언해도된다.
  + 동시성문제 고려 안해도됨.

## Q클래스 사용법.
- QueryDsl를 빌드하면 생성됨.

```java
QUser qUser = new QUser("u"); // 직접 별칭 지정.
QUser qUser = QUser.user; // 기본 인스턴스 사용.
```

```java
import static study.querydsl.entity.Quser.*;

public void startQuerydsl() {
  User findUser = queryFactory
        .select(user)
        .from(user)
        .where(user.username.eq("user1"))
        .fetchOne();
}

public void startQuerydsl2() {
  QUser u1 = new QUser("u1");
  
  User FindUser = queryFactory
          .select(u1)
          .from(u1)
          .where(u1.username.eq("user1"))
          .fetchOne();
          
  assertThat(findUser.getUsername()).isEqualTo("user1");
}

public void search() {
  queryFactory
      .selectFrom(user)
      .where(user.username.eq("user1")
                .and(member.age.eq(10)))
      .fetchOne();
      
  assertThat(finduser.getUsername()).isEqualTo("user1");
}
```

## 조건문
```java
public void searchAndParam() {
  User findUser = queryFactory
                  .selectFrom(user)
                  .where(
                    user.username.eq("user1").and(member.age.eq(10))
                  )
<!--                
                    .where(
                    user.username.eq("member1"),
                    user.age.eq(10)
                  ) 
-->
                  .fetchOne();
                  
  assertThat(findUser.getUsername()).isEqualTo("user1");
}
```

## 결과 조회
- **fetch()** : 리스트 조회, 데이터 없으면 빈 리스트 반환.
- **fetchOne()** : 단 건 조회
  + 결과가 없으면 : null
  + 결과가 둘 이상이면 : `com.querydsl.core.NonUniqueResultException` 발생
- **fetchFirst()** : limit(1).fetchOne()
- **fetchCount()** : count 쿼리로 변경해서 count 수 조회

```java
public void resultFetch() {
  List<Member> fetch = queryFactory
          .selectFrom(member)
          .fetch();
          
  Member fetchOne = queryFactory
          .selectFrom(member)
          .fetchOne();
  
  Member fetchFirst = queryFactory
          .limit(1)
          .fetchFirst();
          
  QueryResult<Member> results = queryFactory
          .selectFrom(member)
          .fetchResults();
          
  results.getTotal(); // 페이징을 위한 db에서 count값을 가져온다.
  List<Member> content = results.getResults(); 
          
}
```

## 정렬
- 회원 나이 내림차순
- 회원 이름 오름차순
- 이름이 없으면 마지막에 출력(null last)
```java
public void sort() {
  em.persist(new Member(null, 100));
  em.persist(new Member("member5", 100));
  em.persist(new Member("member6", 100));
  
  queryFactory
          .selectFrom(member)
          .where(member.age.eq(100))
          .orderBy(member.age.desc(), member.username.asc().nullsLast()) // nullsFirst
          .fetch();
          
   Member member5 = result.get(0);
   Member member6 = result.get(1);
   Member memberNull = result.get(2);
   assertThat(member5.getusername()).isEqualTo("member5");
   assertThat(member6.getusername()).isEqualTo("member6");
   assertThat(memberNull.getUsername()).isNull();
}

publi void paging() {
  queryFactory
    .selectFrom(member)
    .orderBy(member)
    .orderBy(member.username.dsec())
    .offset(1)
    .limit(2)
    .fetch();
    
  assertThat(result.size()).isEqualTo(2);
}
```

## 페이징

```java
@Test
public void paging1() {
    QueryResults<Member> queryResults = queryFactory
        .selecFrom(member)
        .orderBy(member.username.desc())
        .offest(1)
        .limit(2)
        .fetchResults();
    
    assertThat(queryResults.getTotal()).isEqualTo(4);
    assertThat(queryResults.getLimit()).isEqualTo(2);
    assertThat(queryResults.getOffset()).isEqualsTo(1);
    assertThat(queryResults.getResults()).isEqualTo(2);
    
}
```

## 집합
- queryDSL 이 제공하는 튜플로 반환된다.
- DTO로 뽑아오는 방법이 있다.
```java
class Test {
    @Test
    public void aggregation() {
        List<Tuple> result = queryFactory
                .select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .fetch();
        
        Tuple tupleA = result.get(0);
        Tuple tupleB = result.get(1);
        
        assertThat(tupleA.get(team.name)).isEqualTo("teamA");
        assertThat(tupleA.get(member.age.avg())).isEqualTo(15);
        
        assertThat(tupleA.get(team.name)).isEqualTo("teamB");
        assertThat(tupleA.get(member.age.avg())).isEqualTo(35);
    }
}
```

## 조인
- 조인의 기본 문법은 첫 번째 파라미터에 조인 대상을 지정하고, 두 번째 파라미터에 별칭으로 사용할 Q타입을 지정.

```java
class Test {
    @Test
    public void join() {
        List<Tuple> result = queryFactory
                .selectFrom(member)
                .join(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch();
        
        assertThat(result)
                .extracting("username")
                .containExactly("member1", "member2");
                        
                
}
```

## 세타 조인
- 회원의 이름이 팀 이름과 같은 회원 조회.
- outter 조인이 불가능.
```java
class Test {
    @Test
    public void join() {
        List<Member> result = queryFactory
                .select(member)
                .from(member, team)
                .where(member.username.eq(team.name))
                .fetch();
        
        assertThat(result)
                .extracting("username")
                .containExactly("teamA", "teamB");
                
}
```