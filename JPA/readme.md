





## JPA(Java Persistence API)

JPA는 인터페이스의 모음

JAP 2.1 표준 명세를 구현한 3가지 구현체가 존재

하이버네이트, EclipseLink, DataNucleus



### JPA 장점

SQL 중심적인 개발에서 객체 중심으로 개발

생산성 : 정말 간단하게 구현이 가능하다.

유지보수 : 한땀 한땀 수정할 필요 없이 일괄적용.

패러다임의 불일치 해결

성능

데이터 접근 추상화와 벤더 독립성

표준



### 패러다임 차이 해결



**JPA와 상속**

개발자가 할일

```java
class album extends item{ ... } // 상속 관계

jpa.persist(album);
```

JPA가 처리

```sql
INSERT INTO ITEM ...
INSERT INTO ALBUM ...
```



개발자가 할일

```java
class album extends item{ ... } // 상속 관계

Album album = jpa.find(Album.class, albumId);
```

JPA가 처리

```sql
SELECT I.*, A.*
	FROM ITEM I
	JOIN ALBUM A ON I.ITEM_ID = A.ITEM_ID
```





#### 연관 관계(객체 그래프 탐색)



개발자가 할일

```java
연관관계 저장
member.setTeam(team);
jpa.persist(member);
```



JPA가 처리

```java
Member member = jpa.find(Member.class, memberId);
Team team = member.getTeam();
```





#### 신뢰할 수 있는 엔티티, 계층

```
class Member{
	...
	Order order;
	Team team;
	...
}

class MemberService{
	...
	public void process(){
		Member member = memberDAO.find(memberId);
		member.getTeam(); // 자유로운 객체 그래프 탐색. 이후 지연로딩 공부해야됨.
		member.getORder().getDelivery();
	}

}
```



### 성능향상

같은 트랜잭션 안에서는 같은 엔티티를 반환 - 약간의 조회 성능 향상. (캐싱)

DB Isolation Level이 Read Commit이어도 애플리케이션에서 Repeatable Read 보장. (?????)

```
transaction.begin();

em.persist(memberA);
em.persist(memberB);
em.persist(memberC);
// 여기까지 Insert SQL을 데이터베이스에 보내지 않는다.

// 커밋하는 순간 데이터베이스에 insert sql을 모아서 보낸다.
transaction.commit();;
```



#### 지연 로딩

```java
Memrber member = memberDAO.find(memberId);
Team team = member.getTeam();
String teamName = team.getName();
```



```sql
SELECT * FROM MEMBER

SELECT * FROM TEAM

// 각각 쿼리가 따로 날아감. 1,2,3
```



#### 즉시 로딩

```java
Member member = memberDAO.find(memberId);
Team team = member.getTeam();
String teamName = team.getName();
```



```sql
SELECT M.*, T.*
FROM MEMBER
JOIN TEAM ...

// 쿼리가 첫 번째 라인에서 한 번에 날아감.
```



지연 로딩과 즉시 로딩은 상황에 따라서 다르게 사용한다.



### 방언

persistence.xml파일에서 설정.

- JPA는 특정 데이터베이스에 종속  X.
- 각각의 데이터베이스가 제공하는 SQL  문법과 함수는 조금씩 다름.
- SQL 표준을 지키지 않는 특정 데이터베이스만의 고유한 기능.



![img](https://media.vlpt.us/images/hyun6ik/post/c25121d2-4b85-429b-bfb2-735a2967d651/image.png)

###### JPA 구동 방식. 자바 ORM 표준 JPA 프로그래밍 - 기본편. 김영한.



```
public class JpaMain {

	public static void main(String[] args){ // jpa 시작
		EntityManagerFactory emf = Persistence.createEntitiyMAnagerFactory("hello");

		EntityManager em = emf.createEntityManager();
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Member member = new Member();
		member.setId(1L);
		member.setName("HelloA");
		
		em.persist(member);
		
		tx.commit();
		
		em.close();
		emf.close();
	}
}
```



##### @Entity

JPA에서 Entity로 사용한다는 어노테이션.

```
@Entity
public class Member{
	
	@Id
	private Long id;
	private String name;
	
	// getter
	// setter
	
}
```



#### JPQL

객체지향 쿼리.

- 나이가 18살 이상인 회원만 조회.

```java
List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5)
                    .setMaxResults(8)
                    .getResultList();
					
for(Member member : result){
    System.out.println("member.name = " + member.getName());
}
```

- 클래스를 대상으로 쿼리. 테이블 X.
- 기본적인 쿼리는 모두 지원.

- qureyDSL를 더하면 sql을 자바로 전부 바꿀 수 있다.

