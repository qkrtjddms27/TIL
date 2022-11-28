# 스프링 데이터 JPA 분석

## SimpleJpaRepository
- org.springframework.data.jpa.repository.support.SimpleJpaRepository

```java
@Repository
@Transactional(readonly = true)
public class SimpleJpaRepository<T, ID>{
    
    @Transactional(readOnly = true)
    public <S extends T> S save(S entity) {
        if(entityInformation.isNew(entity)) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    }
    
    ...
}
```

- @Repository 적용 : JPA 예외를 스프링이 추상화한 예외로 벼환
- @Transactional : 트랜잭션 적용
  + JPA 의 모든 변경은 트랜잭션 안에서 동작
  + 스프링 데이터 JPA는 변경 메서드를 트랜잭션 처리
  + 서비스 계층에서 트랜잭션을 시작하지 않으면 리퍼지토리에서 트랜잭션 시작
  + 시비스 계층에서 트랜잭션을 시작하면 리파지토리는 해당 트랜잭션을 전파 받아서 사용
  + 스프링 데이터 JPA를 사용할 때 트랜잭션이 없어도 데이터 등록, 변경이 가능
- @Transactional(readOnly = true)
  + 데이터를 단순히 조회만 하고 변경하지 않는 트랜잭션에서 `readOnly = ture` 옵션을 사용하면 플러시를 생략해서 약간의 성능 향상을 얻을 수 있음.
- `save()` 메서드
  + 새로운 엔티티면 저장(persist)
  + 새로운 엔티티가 아니면 병합(merge)
  + 새로운 엔티티를 판단하는 기본 전략
    * 식별자가 객체일 때 null로 판단
    * 식별자가 자바 기본 타입일 때 0으로 판단
    * `Persistable` 인터페이스를 구현해서 판단 로직 변경 가능
  + Persistable 상속 받아서 직접 구현할 수 있다.

---

```java
public interface Persistable<ID> {
    ID getID();
    boolean isNew();
}
```

- 생성일자로 구분할 수 있다.
```java
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoAgrgsConstructor(access = AccessLevel.PROTECTED)
public class Item implements Persistable<String> {
    
    @Id
    private String id;
    
    @createDate
    private LocalDateTime createdDate;
    
    public Item(String id) {
        this.id = id;
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public boolean isNew() {
        return createdDate == null;
    }
}
```