# JPQL

- SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어
  + JPQL은 엔티티 클래스를 대상으로 쿼리.
  + SQL은 데이터베이스 테이블을 대상으로 쿼리.
- 테이블이 아닌 엔티티 객체를 대상으로 쿼리를 짜야한다
- 모든 쿼리를 100% JPQL만으로 사용할 수는 없다.

## QueryDSL

- JPQL 빌더 역할.
- 컴파일 시점에 문법 오류를 찾을 수 있음.
- 동적쿼리 작성이 편리함.
- 문자가 아닌 자바코드로 JPQL을 작성할 수 있음.

## JDBC, SpringJdbcTemplate

- JPA를 사용하면서 JDBC 커넥션을 직접 사용가능, Spring JdbcTemplate, 마이바티스등을 함꼐 사용가능.
- 영속성 컨텍스트를 적절한 시점에 강제로 플러시 필요.

## JPQL 기본 문법
```
select m from Member m where m.age > 18
```
- 별칭 필수, as는 생략가능
- JPQL 키워드는 대소문자 구분 X
- 엔티티 이름 사용, 테이블이름 X
- 엔티티와 속성은 대소문자 구분해서 사용.

```
select
    count(m),
    SUM(m.age),
    AVG(m.age),
    MAX(m.age),
    MIN(m.age)
from Member m
```

## TypeQuery, Query
- TypeQuery는 반환 타입이 명확할때 사용.
- Query는 반환 타입이 명확하지 않을떄 사용.
  + 
```
TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class); // TypeQuery
Query query = em.createQuery("select m.username, m.age from Member m"); // Query
```

## 결과 조회 API
- query.getResultList() : 결과가 하나 이상일 때, 리스트 반환
- query.getSingleResult() : 경과가 정확히 하나, 단일 객체 반환
    + 결과가 없으면 : javax.persistence.NoResultException
    + 둘 이상이면 : javax.persistence.NonUniqueResultException
    
## 파라미터 바인딩 - 이름 기준(권장), 위치 기준
순서관리 문제때문에 이름 기준으로 바인딩이 권장사항이다.

- 이름 기준
```
SELECT m FROM Member m where m.username=:username

query.setParameter("username", usernameParam);
```

- 위치 기준
```
SELECT m FROM Member m where m.username=:?1

query.setParameter(1, usernameParam);
```

## 프로젝션, SELECT
- SELECT 절에 조회할 대상을 지정하는 것
- 대상 : 엔티티, 임베디드 타입, 스칼라 타입(숫자, 문자 등 기본 데이터 타입)
  + 엔티티 : SELECT m FROM Member m, SELECT m.team FROM Member m
  + 임베디드 타입 : SELECT m.address FROM Member m
  + 스칼라 타입 : SELECT m.username, m.age FROM Member m
- SELECT m.team FROM Member m (묵시적 조인)
  + 해당 JPQL은 join으로 자동으로 번역되서 나감
  + 하지만 예측이 쉽지않음.
  + join은 튜닝할 수 있는 건덕지가 많기때문에 확실히 명시해서 작성하는 것이 좋다.
- SELECT m.username, m.age FROM Member m, 여러타입 한 번에 조회하기.
  + select new jpql.MemberDTO(m.username, m.age) from Member m
  
## 페이징 API
- 해당 코드를 사용하면, 방언별로 최적화된 쿼리가 나감.
- 극한의 최적하는 아니지만 어느정도는 되어있는 쿼리.
```
em.createQuery("select m from Member m order by m.age desc", Member.class)
  .setFirstResult(0)
  .setMaxResults(10)
  .getREsultList();
```

## JOIN
- 내부 조인 : SELECT m FROM Member m JOIN m.team t
- 외부 조인 : SELECT m FROM Member m LEFT JOIN m.team t
- 세타 조인 : SELECT count FROM Member m, Team t WHERE m.username = t.name

### JOIN ON
- 조인 대상 필터링
- 연관관계가 없는 세타 조인을 외부 조인이 가능
  + 5.1이후 부터는 내부 조인말고 외부 조인이 가능.
  
- JPQL : SELECT m, t FROM Member m LEFT JOIN m.team t on t.name = 'A'
- SQL : SELECT m.*, t.* FROM Member m LEFT JOIN Team t ON m.TEAM_ID=t.id and t.name = 'A'

## 서브 쿼리
- SELECT, WHERE절에 사용가능, FROM절은 불가능.
  + 조인으로 풀어서 해결
- 팀 A 소속인 회원
  + SELECT m FROM Member m WHERE exists (select t from m.team t where t.name = '팀A')
- 전체 상품 각각의 재고보다 주문량이 많은 주문들
  + SELECT o FROM Order o where o.orderAmount > ALL(select p.stockAmount from Product p)
- 어떤 팀이든 팀에 소속된 회원
  + SELECT m FROM Member m where m.team = ANY (select t from Team t)
  
## 페치 조인
- SQL 조인 종류 X
- JPQL 에서 성능 최적화를 위해 제공하는 기능
- 연관된 엔티티나 컬렉션을 SQL 한 번에 함께 조회하는 기능
- join fetch 명령어 사용
  + JPQL : select m form Member m join fetch m.team
  + SQL : select M.*, T.* FROM MEMBER M INNER JOIN TEAM T ON M.TEAM_ID = T.ID
- 위 예제에서 알 수 있듯 Member의 데이터를 로딩 시점에 한 번에 채워넣는다.

### 한계점
- 페치 조인의 대상에는 별칭을 줄 수 없다.
- 연관관계를 찾아가는 것은 특정 갯수만 뽑아오는게 아니라 모두 가져오는 것으로 설계되어있음.
  + 특정 갯수만 뽑아오는 부분은 따로 조회하는 것이 맞다.
- 둘 이상의 컬렉션은 페치 조인 할 수 없다.
- 컬렉션을 페치 조인하면 페이징 API를 사용할 수 없다.


### 벌크 연산
- 재고가 10개 미만인 모든 상품의 가격을 10% 상승하려면?
- JPA 변경 감지 기능으로 실행하려면 너무 많은 SQL 실행
  + 재고가 10개 미만인 상품을 리스트로 조회
  + 상품 엔티티의 가격을 10% 증가
  + 트랜잭션 커밋 시점에 변경감지가 동작한다.
- 변경된 데이터가 100건이라면 100번의 UPDATE SQL 실행

#### 주의
- 벌크 연산은 영속성 컨텍스트를 무시하고 데이터베이스에 직접 쿼리를 날린다.
  + 벌크 연산을 먼저 실행. (영속성 컨텍스트로 아무런 작업을 하지 않고)
  + 벌크 연산 수행 후 영속성 컨텍스트 초기화
  + 플러쉬는 단방향 초기화 컨텍스트에서 DB로 셋팅. 반대는 아님. 데이터 정합성 주의해야됨.
    * 컨텍스트에 있는 내용을 find 명령어로 다시 가져오면 초기화는 되지않는다. 