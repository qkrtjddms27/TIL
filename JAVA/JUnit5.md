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

## assertion 확인하기.
```java
class StudyTest {
    void create_new_study() {
        Study study = new Study();
        assertNotNull(study);
        // 뒤바뀌어도 상관없지만 기대값, 상태순으로 생각해야된다.
        // 문자열 대신 Supplier 적용 가능하다.
        // 람다식으로 만들면 필요한 시점에만 사용함
        //   일반 문자열을 사용하면 성공하든 실패하든 문자열을 생성한다.
        //   람다식을 사용하면 실패할때만 문자열을 생성한다.
        //   비용 절감.
        assertAll (
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다."),
                () -> assertEquels(StudyStatus.DRAFT, () -> "스터디를 처음 만들면 상태값이 DRAFT여야 한다."),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석가능 인원은 0보다 커야한다.")
        );
        
    }
}

@Getter
class Study {
    private StudyStatus status = StudyStatus.DRAFT;
    private int limit;
    
    public Study(int limit) {
        this.limit = limit;
    }
}
```

### assertion 주의 사항
spring에서 lcoalThread 환경으로 접근 할 수 없으므로 롤백이 되지않고 DB에 그대로 저장되는 경우가 있다.

### 조건에 따라 테스트 실행

```java
class Test {
    void create_new_study() {
        String test_env = System.getenv("TEST_ENV");
        System.out.println(test_env);
        
        // 아래 조건을 만족하지 못하면 뒤에 테스트는 실행되지 않는다.
        assumeTrue("LOCAL".equalsIgnoreCase(test_env));
        
        Study actual = new Study(10);
        assertThat(actual.getLimit()).isGreaterThan(0);
        
        // if else과 같진않지만 특정 조건이 만족하면 그 조건에 해당하는 테스트 진행
        
        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
            System.out.println("local");
            Study actual = new Study(100);
            assertThat(actual.getLimit()).isGreaterThan(0);
        });

        assumingThat("keesun".equalsIgnoreCase(test_env), () -> {
            System.out.println("keesun");
            Study actual = new Study(10);
            assertThat(actual.getLimit()).isGreaterThan(0);
        });
             
        // @EnabledOnOs(OS.MAC) -> 실행되는 OS가 MAC일 경우 실행.
        // @DisabledOnOS(OS.MAC) -> 실행되는 OS가 MAC이 아닐 경우 실행하지 않음.
        // @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "keesun") -> 환경 변수 TEST_ENV의 값이 keesun인 경우 실행
    }
}
```

## 태깅과 필터링
- 테스트 그룹을 만들고 원하느 테스트 그룹만 테스트를 실행할 수 있는 기능.
    + 모듈별, 단위테스트, 통합테스트, 테스트 시간 조건을 기준으로 원하는 태그를 붙여서 그룹화 할 수 있다.
    + 태깅은 여러개 붙일 수 있다.
    
```xml
<profile>
  <id>ci</id>
  <build>
    <plugin>
      <artifactld>maven-surefire-plugin</artifactld>
      <configuration>
        <groups>fast | slow</groups>
      </configuration>
    </plugin>
  </build>>
</profile>
```

### 커스텀 어노테이션
```java
@Taget(ElementType.METHOD) // 메소드에 붙일 수 있다.
@Retention(RetentionPolicy.RUNTIME) // 런타임까지 이 속성을 유지하고 싶다.
@Test // 테스트용이다
@Tag("fast") // 이 @이 붙으면 fast 속성을 유지할 것 이다.
public @interface FastTest {
    
}
```

## 테스트 반복하기
```java
class Test{
    @DisplayName("스터디 만들기")
    // 파라미터이름 현재 반보횟수 / 전체 진행해야하는 반복 횟수
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetition}")
    void repeatTest(RepetitionInfo repetitionInfo) {
      System.out.println("test " + repetitionInfo.getCurrentRepetition() + "/" +
                repetitionInfo.getTotalRepetitions());
    }
}

/**
 * 출력 결과
 * 스터디 만들기, 1/10
 * 스터디 만들기, 2/10
 * ...
 * 스터디 만들기, 9/10
 * 스터디 만들기, 10/10
 */


class Test{
    // 특정 파라미터를 가지고 테스트.
    @ParameterizedTest
    @ValueSource(Strings = {"날씨가", "많이", "추워지고", "있네요."})
    void repeatTest(RepetitionInfo repetitionInfo) {
      System.out.println(message);
    }
}
```

## 테스트 인스턴스
- 테스트의 순서는 정해져있지 않다. (기본 전략)
  + 테스트마다 새로운 인스턴스가 생성된다.
  
- TestInstance.Lifecycle.PER_CLASS 옵션을 주면, 같은 인스턴스에서 사용이 가능하다.
  + 인스턴스를 여러개 만들지 않으므로, 성능적인 측면에서 유리함.
```java
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Test {
    int value = 1;
    
    @test
    void test1() {
      System.out.println(value++);
    }
    
    @test
    void test2() {
      System.out.println(value++);
    }
}
```

## 테스트 순서
- 테스트 메서드 순서는 언제든 바뀔 수 있다.
- 기본적으로는 순서에 의존해서 테스트를 작성하면 안된다.
- 순차적인 케이스를 실행할 수 있다.
  + 테스트 인스턴스를 계속 생성하는 것이 아닌 하나만 만들어서 사용해야한다
  + @TestMethodOrder(OrderAnnotation.class)
  + Junit이 사용하는 Order를 사용해야 한다.
  + 우선순위가 같으면 예측하지 못한 결과가 발생할 수 있다.
- 단위테스트 같은 경우는 굳이 순서를 정하지 않을 것 같다.
```java
@DisplayNameGeneration(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class Test {
    int value = 1;
    
    @test
    @Order(1)
    void test1() {
      System.out.println(value++);
    }
    
    @test
    @Order(2)
    void test2() {
      System.out.println(value++);
    }
}
```

## Junit 기능 커스텀마이징
- JUnit 4 : RunWith(Runner), TestRule, MethodRule
- Junit 5 : Extension.

### 등록 방법
- 선언적 등록
  + @Extension, 선언적 등록
  + @RegisterExtension, 프로그래밍 등록
  + ServiceLoader, 자동 등록

```java

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
        
public class FindSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    
    private static final long THRESHOLD = 1000L;
    
    @Override
    public void beforeTestExecution(Extension context) throws Exception {
        String testClassName = context.getRequiredTestClass().getName();
        String testMethodName = context.getReuqiredTestClass().getName();
        ExtensionContext.Store store = context.getStore(ExtensionContext.Namespace.create());
        store.put("START_TIME", System.currentTimeMillis());
    }
    
    @Override
    public void afterTestExecution(Extension context) throws Exception {
        String testClassName = context.getRequiredTestClass().getName();
        String testMethodName = context.getReuqiredTestClass().getName();
        SlowTest annotation = requiredTestMethod.getAnnotation(SlowTest.class);
        ExtensionContext.Store store = context.getStore(ExtensionContext.Namespace.create());
        store.remove("START_TIME", long.class);
        long duration = System.currentTimeMillis() - start_time;
        if (duration > THRESHOLD && annotation != null) {
            System.out.printf("Please consider mark method [%s] with @SlowTest.\n", testMethodName);
        }
    }
}
```