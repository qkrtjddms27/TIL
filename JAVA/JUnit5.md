# Junit5

## 설정
- 스프링 부트로 만들면 기본적으로 설정됨.
- dependency에 추가하면 사용가능함.

## JUnit4 -> JUnit 5
JUnit 4로 작성된 코드도 5로 실행 가능하다.
```java
class Test {
    
    @Test
    void create() {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("create");
    }
    
    @BeforeAll // 모든 메소드가 호출되기전.
    static void beforeAll() {
        System.out.println("before all");
    } 
    
    @AfterAll // 모든 메소드가 호출된 후.
    static void afterAll() {
        System.out.println("after all");
    }
    
    @BeforeEach // 각각의 메소드가 호출되기 전
    void beforeEach() {
        System.out.println("before each");
    }
    
    @AfterEach // 각각의 메소드가 호출된 후
    void afterEach() {
        System.out.println("After each");
    }
    
    @Test
    @Disable // 무시하고 지나감.
    void disable() {
        System.out.println("여긴 실행 안되요~");
    }
    
}
```

## 테스트 이름 표기

### @DisplayNameGeneration
- Method 와 Class 레퍼런스를 사용해서 테스트 이름을 표기하는 방법 설정.
- 기본 구현체로 ReplaceUnderscores 제공
```java
@DisplayNameGeneration(DisplayNAmeGenerator.ReplaceUnderscores.class)
class StudyTest {
    // 테스트 이름 표기가 바뀜 -> create new study
    @Test
    void create_new_study() {
        
    }
}
```
### @DisplayName
- 어떤 테스트인지 테스트 이름을 보다 쉽게 표현할 수 있는 방법을 제공하는 애노테이션.
- @DisplayNameGeneration 보다 우선 순위가 높다.
- 이모지 사용 가능.
```java
class StudyTest {
    @Test
    @DisplayName("스터디 만들기")
    void create_new_study() {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("create");
    }
}
```