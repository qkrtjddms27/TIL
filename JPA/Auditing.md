# Auditing
> 엔티티를 생성, 변경할 때 변경한 사람과 시간을 추적하고 싶을때 사용할 수 있는 JPA 자동으로 등록, 변경할 수 있는 기능.
- 주로 사용하는 컬럼 속성
  + 등록일
  + 수정일
  + 등록자
  + 수정자

```java
@MappedSuperclass
@Getter
public class JpaBaseEntity {
    
    @Cloumn(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocaldateTime.now();
        createdDate = now;
        updatedDate = now;
    }
    
    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDateTime.now();    
    }
}

@Test
public void JpaEventBaseEntity() throws Exception {
    Member member = new Member("member1");
    memberRepository.save(member);
    
    Thread.sleep(100);
    member.setUsername("member2");
    
    em.flush();
    em.clear;
 
    Member findMember = memberRepository.findById(member.getId()).get();

    System.out.println("findMember.createdDate = " + findMember.getCreatedDate());
    System.out.println("findMEmber.updatedDate = " + findMember.getUpdatedDate());
}
```
> JPA 엔티티 라이프 싸이클의 콜백을 조종할 수 있게하는 어노테이션들이 있다.
- @PrePersist
  + JPA 엔티티가 비영속 상태에서 영속 상태가 되는 시점 이전에 실행.
  + @CreatedDate
  + @CreatedBy
- @PreUpdate
  + 영속 상태의 엔티티를 이용하여 데이터 업데이트를 수행하기 이전에 실행
  + @LastModifiedDate
  + @LastModifiedBy
- @MappedSuperclass
  + @createdAt, lastUpdatedAt

```java
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseTimeEntity {
    
    @CreatedDate
    @Column(updateable = false)
    private LocalDateTime createdDate;
    
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    
}

public class BaseEntity extends BaseTimeEntity {
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;
    
    @LastModifiedBy
    private String lastModifiedBy;
}
```