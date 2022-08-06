# JpaRepository

- 인터페이스로 생성이 가능하다
- 간단한 쿼리문을 메서드명으로 명명규칙을 통해 작성이 가능하다.
- @Repository 어노테이션을 갖고 있다.

## 사용방법
- JpaRepository<Entity 클래스, 해당 Entity 클래스의 Key 값> 형태로 extends 가능하다.

```java
public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByAuthId(String authId);
}

@Entity
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_idx")
    private Long authIdx;

    @Column(nullable = false, unique = true, length=128, name = "auth_id")
    private String authId;

    @Column(nullable = false, length=128, name = "password")
    private String password;
    
    ....
}
```

### 메서드 명명 규칙
- findByXX : findBy 이후에 엔티티의 속성으로 값을 찾을 수 있다.
    ```
    Optional<Auth> findByAuthId(String authId);
    ```
- Like, NotLike : Like를 붙이면 해당 엔티티의 해당 인수가 포함된 결과와 포함하지 않은 결과 값을 찾을 수 있다. 
    ```
    Optional<Auth> findByNameLike(String param);
    ```
- StartingWith, EndingWith : 텍스트 값에서 인수에 지정된 텍스트로 시작하거나 끝나는 것을 검색하기 위한 것. 
    ```
    Optional<Auth> findByNameStartingWith("start");
    Optional<Auth> findByNameEndingWith("end");
    ```  
- IsNull, IsNotNull : 값이 null이거나, 혹은 null이 아닌 값을 검색한다.
    ```
    Optional<Auth> findByNameIsNull();
    Optional<Auth> findByNameIsNotNull();
    ``` 
- True, False : Boolean 값으로 ture이거나 false인 것을 검색한다.
    ```
    Optional<Auth> findByIsDeleteTrue();
    Optional<Auth> findByIsDeleteTrue();
    ``` 
- Before, After : 시간 값을 이용하여 항목의 값이 현재보다 이전이거나 이후의 값을 구한다.
    ```
    Optional<Auth> findByRegDtBefore(LocalDateTime.now());
    Optional<Auth> findByRegDtAfter(LocalDateTime.now());
    ```
- LessThan / GreaterThan : 숫자 값을 사용하며, 그 항목의 값이 인수보다 작거나 큰 것을 검색한다.
    ```
    Optional<Auth> findByRegDtBefore(LocalDateTime.now());
    Optional<Auth> findByRegDtAfter(LocalDateTime.now());
    ```