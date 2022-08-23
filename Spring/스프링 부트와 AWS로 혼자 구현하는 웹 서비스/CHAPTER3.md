# 스피링 부트에서 JPA로 데이터베이스 다뤄보자

## JPA 사용하는 이유

- 현대의 웹 애플리케이션에서 DB는 빠질 수 없는 요소.
  + 객체를 관계형 데이터 베이스에서 관리하는 것은 웹에서 중요한 부분이다.

- 관계형 데이터베이스가 계속해서 웹 서비스의 중심이 되면서 모든 코드는 SQL 중심, 객체 중심으로 코드 작성이 가능해진다.
  + 관계형 데이터베이스는 SQL만 인식이 가능하다.
  + 각 테이블마다 기본적인 CRUD를 매번 생성해야 한다.
  + 반복되는 코드 작업 단순화

- 패러다임의 불일치 문제 해소
  + 객체지향에서 DB의 데이터 모델링을 추가하면 조금 더 코드가 정신없어진다.
  + 알아보기 쉬운 User에서 Group을 가져오는 코드
    ```
    User user = findUser();
    Group group = user.getGroup();
    ```
    
  + DB와 관련하여 User 따로, Group 따로 조회하게 됨.
    ```
    User user = userDao.findUser();
    Group group = groupDao.findGroup(user.getGroupId());
    ```

- 결과적으로 SQL에 종속적이지 않은 개발이 가능해진다.

## Spring Data JPA
- JPA는 인터페이스, 자바 표준명세서.
- JPA의 구현체는 Hibernate, Eclipse Link 등이 있다.
- Hibernate를 사용하는 것 보다 Spring Data JPA를 사용하는 것이 더 좋다.
  + 구현체 교체의 용이성
    * Hibernate외에 다른 구현체로 쉽게 교체하기 위함.
  + 저장소 교체의 용이성
    * 관계형 데이터베이스 외에 다른 저장소로 쉽게 교체하기 위함.
    * Spring Data JPA에서 Spring Data MongoDB로 의존성만 교체하면됨.
    * CRUD의 인터페이스가 같기 때문에 이것이 가능하다.

## 실무에서 JPA
- 객체지향 프로그래밍과 관계형 데이터베이스 모두 잘 알아야하기 때문에 높은 러닝 커브를 가지고 있다.
- JPA에서는 여러 성능 이슈 해결을 위한 가이드(구글링)를 찾아보면 있다.

## 수정의 올바른 예시
- 롬복의 Setter는 사용하지 않는 것이 좋다.
- 해당 필드의 값 변경이 필요하면 명확히 그 목적과 의도를 나탈낼 수 있는 메서드를 추가해야한다.

```java
public class Order {
    public void setStatus(boolean status) {
        this.status = status;
    }
}

public void 주문서비스의_취소이벤트() {
    order.setStatus(false);
}
```

## Spring Data JPA 테스트 코드 작성

```java
@RunWith(SpringRunner.class) // Junit4 버전에서는 사용 5버전이후부터는 필요하지 않다.
@SpringBootTest
public class PostsRepositoryTest {
    
    @Autowired
    PostsRepository postsRepository;
    
    @After
    public void cleanup() {
        postsRepository.deleteAll(); // 테스트용 디비라서 사용.
    }
    
    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";
        
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("jojoldu@gamil.com")
                .build());
        
        //when
        List<Posts> postsList = postsRepository.findAll();
        
        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}
```

## Spring 웹 계층
- 사실 Service에서 비지니스 로직을 처리해야 하는 것은 오해다
  + Service는 트랜색션, 도메인 간 순서 보장의 역할만 한다.

- Web Layer
  + 흔히 사용하는 컨트롤러의 영역(@Controller)와 JSP/Freemarker 등의 뷰 템플릿 영역
  + 필터, 인터셉터, 컨틀롤러 어드바이스등 외부 요청과 응답에 대한 전반적인 영역.

- Service Layer
  + @Service에 사용되는 서비스영역.
  + 일반적으로 Controller와 Dao의 중간 영역에서 사용
  + @Transactional이 사용되어야 하는 영역.
  
- Repository Layer
  + Database와 같이 데이터 저장소에 접근하는 영역.
  
- Dtos
  + 계층 간에 데이터 교환을 위한 객체
  + 뷰 템플릿 엔진에서 사용될 객체나 Repository Layer에서 결과로 넘겨준 객체 등.
  
- Domain Model
  + 도메인이라 불리는 개발 대상을 모든 사람이 동일한 관점에서 이해할 수 있고 공유할 수 있도록 단수화시킨 것을 도메인 모델이라고 합니다.
  + @Entity도 여기에 속한다.
  
### 비즈니스 처리를 담당하는 곳, Domain

#### Service Layer에서 처리
```java
@Transactional
public Order cancelOrder(int orderId) {
    
    OrderDto order = orderDao.selectOrders(orderId);
    BillingDto billing = billingDao.selectBilling(orderId);
    DeliveryDto delivery = deliveryDao.selectDelivery(orderId);
    
    String deliveryStatus = delivery.getStatus();
    
    if("IN_PROGRESS".equals(deliveryStatus)) {
        delivery.setStatus("CANCEL");
        deliveryDao.update(delivery);
    }
    
    order.setStatus("CANCEL");
    deliveryDao.update(billing);
    
    reutrn order;
}
```
- 모든 로직이 서비스 클래스 내부에서 처리됨.
- 서비스 계층이 무의미하며, 객체란 단수히 데이터 덩어리 역할만 하게 됨.


#### Domain 모델에서 처리할 경우
```java
@Transacional
public Order cancelOrder(int orderId) {
    
    Orders order = ordersRepository.findById(orderId);
    Billing billing = billingRepository.findByOrderId(orderId);
    Delivery delivery = deliveryRepository.findByOrderId(orderId);
    
    delivery.cancel();
    order.cancel();
    billing.cancel();
    
    
    reutrn order;
}
```

- 해당 코드에서는 서비스 메서드에서는 트랜잭션과 도메인 간의 순서만 보장.
- 도메인에서 삭제를 처리하게 유도.

## @RequiredArgsConstructor 
- 생성자 주입을 사용하는 상황에서 생성자를 계속해서 바꿔줄 필요가 없어짐.