

[TOC]

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

```java
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

```java
transaction.begin();

em.persist(memberA);
em.persist(memberB);
em.persist(memberC);
// 여기까지 Insert SQL을 데이터베이스에 보내지 않는다.

// 커밋하는 순간 데이터베이스에 insert sql을 모아서 보낸다.
transaction.commit();
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

---



persistence.xml파일에서 설정.

- JPA는 특정 데이터베이스에 종속  X.
- 각각의 데이터베이스가 제공하는 SQL  문법과 함수는 조금씩 다름.
- SQL 표준을 지키지 않는 특정 데이터베이스만의 고유한 기능.



![img](https://media.vlpt.us/images/hyun6ik/post/c25121d2-4b85-429b-bfb2-735a2967d651/image.png)

JPA 구동 방식. 자바 ORM 표준 JPA 프로그래밍 - 기본편. 김영한.



```java
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



#### entity



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



###  영속성 컨텍스트

---



엔티티를 영구 저장하는 환경

```java
EntityManager.persist(entity);
```

눈에 보이지 않는 공간이 생성된다.



#### 영속성 컨테스트 엔티티의 생명주기

**비영속(new/transient)** : 영속성 컨테스트와 전혜 관계가 없는 새로운 상태

```java
Member member = new Member();
member.setId("member1");
memeber.setUsername("회원");
```



**영속(managed)** : 영속성 컨테스트에 관리되는 상태

```java
//객체를 생성한 상태(비영속)
Member member = new Member();
member.setId("member1");
memeber.setUsername("회원");

EntityManager em = emf.createEntityManager();
em.getTransaction().begin();

// 객체를 저장한 상태(영속)
em.persist(member); // 영속상태 시작.
```



**준영속(detached)** : 영속성 컨테스트에 저장되었다가 분리된 상태

```java
em.detach(memeber);
```



**삭제(removed)** : 삭제된 상태

```java
em.remove(member);
```



#### 영속성 컨텍스트의 이점

---



**1차 캐시** : 조회할때  영속성 컨텍스트에 값이 존재하는지 먼저 확인. 영속성 컨테스트에 없으면 DB에서 조회.

영속성 컨텍스트는 보통 트랜잭션 단위로 생성된다.

한 애플리케이션 단위로 사용되는 캐시는 2차 캐시, 2차 캐시에 비해 작은 단위(트랜잭션 단위)로 사용되는 것은 1차 캐시.



**영속 엔티티의 동일성 보장** : 1차 캐시로 반복 가능한 읽기등급의 트랜잭션 격리 수준을 데이터베이스가 아닌 애플리케이션에서 제공.

```java
Member findMember1 = em.find(Member.class, 101L);
Member findMember2 = em.find(Member.class, 101L);

System.out.println("result = " + (findMember1 == findMember2)); // result = true;
```



**트랜잭션을 지원하는 쓰기 지연** : 한 번에 쿼리를 날린다.

```java
EntityManager em = emf.createEntitiyManager();
EntityTransaction transaction = em.getTransaction();

transaction.begin();

em.persist(memberA);
em.persist(memberB);
// 여기까지 INSERT SQL을 데이터베이스에 보내지 않는다.
// 1차 캐시에 저장.

// 커밋하는 순간 데이터베이스에 INSERT SQL을 보낸다.
transaction.commit();
```



batch_size : 한 방에 모아서 보내는 단위를 설정할 수 있다.

```xml
<property name = "hibernate.jdbc.batch_size" value="10"/>
```



**변경 감지** : JPA가 1차캐시에 스냅샷을 떠놓고 스냅샷과 Entity가 다르면 쿼리를 날려준다.

```java
Member member = em.find(Member.class, 150L);
member.setName("ZZZZ"); // JPA가 DB에 쿼리 쏨.

System.out.println("=========");
```



​	![TIL(D+1). [JPA] 영속성 컨텍스트(persistence context) - 박기창님의 블로그 - 인프런 | 커뮤니티](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRsXIIUkpbsrupFzYoW-xEA7zkueShP6ntYpg&usqp=CAU)



**엔티티 삭제** : 엔티티 삭제 변경 감지와 같은 메커니즘.

```java
Member memberA = em.find(Member.class, "memberA");

em.remove(memberA);
```



### 플러시

---

쓰기 지연 SQL 저장소에 담겨있는 SQL을 DB에 반영하는 과정.

영속성 컨텍스트를 비우지 않음.

영속성 컨텍스트의 변경내용을 데이터베이스에 **동기화**

트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화하면 됨.

- 변경 감지
- 수정된 엔티티 쓰기 지연 SQL 저장소에 등록
- 쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송(등록, 수정, 삭제).

- em.flush() - 직접 호출
- 트랜잭션 커밋 - 플러시 자동 호출
- JPQL 쿼리 실행 - 플러시 자동 호출





### 준영속 상태

---

- 영속 상태의 엔티티가 영속성 컨테스트에서 분리.

- 영속성 컨테스트가 제공하는 기능을 사용 못함. (변경 감지)

```
em.detach(member);
em.clear();
```



### 엔티티 매핑

---



#### 객체와 테이블 매핑 : @Entity

- JPA가 관리
- JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 필수
- 주의사항
  - 기본 생성자 필수(파라미터가 없는 생성자)
  - final 클래스, enum, interface, inner 클래스 사용 X
  - 저장할 필드에 final 사용 X

```java
// @Table(name = "Member") defalut값 사용하는 것을 권장
@Entity // defalut
class Member {
	
	@Id
	private Long id;
    
    	@Column(unique = true, length = 10) // DDL 생성 기능.
	private String name;
	
	public Member() {} // 기본 생성자
	public Member(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	// getter
	// setter
}
```



#### 데이터베이스 스키마 자동 생성

- 설정에 따라 DDL을 애플리케이션 실행 시점에 자동 생성
  - create : 기존테이블 삭제 후 다시 생성 (DROP + CREATE)
  - create-drop : create와 동일 끝날떄 DROP (DROP + CREATE + DROP) 
  - update : 변경분만 반영
  - validate : 엔티티와 테이블이 정상 매핑되었는지만 확인
  - none : 사용하지 않음
- 방언별로 알아서 적절한 sql.
- 운영에서 create, create-drop, update는 사용하면 안됨.
- 실행에서는 영향을 주지 않는다.

```xml
<property name="hibernate.hbm2ddl.auto" value="create-drop" /`>
```



```java
@Entity // defalut
class Member {
	
	@Id
	private Long id;
    
    	@Column(unique = true, length = 10) // DDL 생성 기능.
	private String name;
	
	// 기본 생성자
	// getter
	// setter
}
```



### 필드와 컬럼 매핑

---



```java
@Entity
class Member {
	
	@Id
	private Long id;
    
    	@Column(name = "member_name", columnDefinition = "varchar(100) default `EMPTY`")
	private String name;
	
	@Enumerated(EnumType.STRING)
	private RoleType roleType;
	
	@Temporal(temporalType.TIMESTAMP)
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;
	
	// 기본 생성자
	// getter
	// setter
}
```



- @Column : 컬럼 매핑
  - name :  필드와 매핑할 테이블 이름을 지정, defalut = 객체의 필드 이름
  - insertable, updateable : 등록, 변경 가능 여부 defalut = True
  - nullable**(DDL)** : null 값의 허용 여부를 설정한다. false로 설정하면 DDL 생성 시에 not null 제약조건이 붙는다.
  - columnDefinition**(DDL)** : 데이터베이스 컬럼 정보를 직접 줄 수있다.
  - length**(DDL)** : 문자 길이 제약조건
  - precision, scale**(DDL)** : 아주 큰 숫자, 소수점이 필요할때.
- @Enumerated : enum 타입을 매핑할 떄 사용. **enumType.STRING (사용 권장)** ORDINAL 사용시 큰 장애가 될 수 있다.

- @Temporal : 날짜 타입 매핑
- @Enumerated : enum 타입 매핑
- @Lob : BLOB, CLOB 매핑
- @Transient : 특정 필드를 컬럼에 추가시키지 않음.





### 기본 키 매핑

---

```java
@Entity
// @SequenceGenerator(name = "member_seq_generator", sequenceName = "member_seq")
// @TableGenerator(
//			name = "MEMBER_SEQ_GENERATOR",
//			table = "MY_SEQUENCES",
//			pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
             
public class Member {

	@Id
    //@GeneratedValue(strategy = GeneratoionType.TABLE)
    //@GeneratedValue(strategy = GeneratoionType.SEQUENCE)
    @GeneratedValue(strategy = GeneratoionType.IDENTITY)
	private String id;
	
	@Column(name = "name", nullable = false)
	private String username;
	
	// 기본 생성자
	// getter
	// setter
}
```



- @Id만 사용
- @GeneratedValue : 자동생성
  - IDENTITY : 데이터베이스에 위임.
  - SEQUENCE : 데이터베이스 시퀀스 오브젝트 사용, ORACLE
    - @SequenceGenerator 필요
  - TABLE : 키 생성용 테이블 사용, 모든 DB에서 사용
    - @TableGenerator 필요
  -  AUTO: 방언에 따라 자동 지정, 기본값

- allocationSize : DB의 seq값을 미리 size만큼 가져와서 사용. defalut 값 50.



### 연관관계 매핑 기초

DB에 맞춰서 설계하는게 아닌 자바스럽게 설계를 도와준다.

**방향** : 단방향, 양방향

**다중성** : 다대일, 일대다, 일대일, 다대다



```java
public class Team {
	long teamId;
	...
}

public class Member {
	long teamId;
	...
}
// Team과 Member 다대일 관계로 설계되어있다.

Member findMember = em.find(Member.class, member.getId()); // Member를 객체를 가져온다.

Long findTeamId = findMember.getTeamId();  // 찾은 Member객체의 teamId를 가져온다.
Team findTeam = em.find(Team.class, findTeamId); // 가져온 teamId를 통해서 Team객체를 가져온다.
// 객체 탐색이 어렵다...
```



- 테이블은 외래 키로 조인을 사용해서 연관된 테이블을 찾는다.
- 객체는 참조를 사용해서 연관된 객체를 찾는다.

- 객체 그래프 탐색이 가능해짐.

  

#### 단방향 연관관계

```java
public class Team {
	private long teamId;
	...
}

public class Member {
//  long teamId; // 개편 전.
    
    @ManyToOne
    @JoinColumn(name = "team_id")
	private Team team;  // 개편 후.
	...
}

Member findMember = em.find(Member.class, member.getId());
// Long findTeamId = findMember.getTeamId();
// Team findTeam = em.find(Team.class, findTeamId);
Team findTeam = findMember.getTeam();

```



#### 양방향 연관관계

```java
public class Team {
	private long teamId;
    
    @OneToMany(mappedBy = "team")
	private List<Member> members = new ArrayList<>(); // 추가
	...
}

public class Member {
    @ManyToOne
    @JoinColumn(name = "team_id")
	private Team team;
	...
}
```



#### 연관관계의 주인과 mappedBy

- 객체와 테이블 관계의 차이를 이해해야됨.
- 객체를 양방향으로 참조하려면 단방향 연관관계를 2개 만들어야된다.

- 값을 **같이** 갱신해줘야 됨.
- 주인이 아닌쪽은 수정은 불가능하고 읽기만 가능.
- 외래키가 있는 곳을 연관관계의 주인으로 설정.
- 위 내용과 별개로 양방향 관계일때는 변경 시 순수 객체를 고려하여 양방향에 값을 셋팅해야된다.



양방향 연관관계에서 한 번에 양방향에 셋팅해주기위한 편의 메소드.

```java
public class Member{
	public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
```

- 양방향 매핑시 무한 루프를 조심해야된다.
  - toString(), lombok, JSON 생성 라이브러리
  - JSON : 컨트롤러에서 엔티티를 반환하지 말자. 단순한 DTO로 바꿔서 반환하자.



#### 양방향 매핑 정리

- 단방향 매핑만으로도 이미 연관관계 매핑은 완료하는게 좋다.
- 양방향 매핑은 반대 방향으로 조회 기능이 추가된 것 뿐.
  - 단방향 매핑만으로 충분하지만 굳이 양방향으로 만들지 말자.
- 단방향 매핑을 잘 하고 양방향매핑은 필요할때 추가해주면됨.
  - 설계상의 큰 변화를 주진않음.



#### 연관관계의 주인을 정하는 기준.

- 비즈니스 로직을 기준으로 연관관계의 주인을 선택하면 안됨.
- 연관관계의 주인은 외래 키의 위치를 기준으로 정해야함.



#### 연관관계 고려사항

- 다중성
- 단방향, 양방향
  - 테이블은 외래 키 하나로 양쪽 조인 가능. 그래서 방향이라는 개념이 없음.
  - 객체는 참조용 필드가 있는 쪽으로만 참조 가능. (단방향, 양방향)
- 연관관계의 주인



### 다중성

- 다대일
- 일대다
- 일대일
- 다대다



#### 다대일 

- 다(**외래키 포함**)대일

##### 다대일 단방향

- 가장 많이 사용하는 방법

  ![다대일단방향](readme.assets/다대일단방향-1642237899772.png)

##### 다대일 양방향

![다대일양방향](readme.assets/다대일양방향.png)

#### 일대다

- 일(**외래키 포함**)대다
- 디비설계상 외래키가 존재하는 방향은 **다**쪽으로 정해져있다.(다른 패러다임)
- 프로그래밍하는 단계에서는 괜찮으나 쿼리나 소스를 이해하는데 복잡도가 올라감.(다른 패러다임)



#### 일대일

- 주 테이블이나나 대상 테이블 중에 외래 키 선택 가능
  - 주 테이블에 외래 키
  - 대상 테이블에 외래 키
- 외래 키에 데이터베이스 유니크 제약조건 추가.
  - 관리가 편해짐
- 다대일 연관관계랑 유사한 설계.
- JoinColumn(name = "테이블 명")은 넣는게 좋다.
  - 디폴트 값으로 사용해도되지만 만들어지는 이름이 지저분함.



#### 다대다

- 관계형 데이터베이스는 **정규화된 테이블** 2개로 다대다 관계를 표현할 수 없음.
- 연결 테이블을 추가해서 일대다, 다대일 관계로 풀어내야함.
  - 중간 테이블이 숨겨져있고 컬럼이 fk말고 추가되지 않음.
- 운영상에서는 아무 의미없는 값을 PK로 두는 것이 좋다.(아직 안겪어봐서 모르겠음.)

