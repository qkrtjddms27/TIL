## Spring


### 학습 환경 설정

JAVA : 1.8 <br/>
IDE : Intellij <br/>
Project : Gradle <br/>
SpringBoot : 2.6.2 <br/>

최근 Gradle, Maven 둘 다 /src에 /main, / test 가 나눠져있다. <br/>
스프링이 자체적으로 Tomcat 서버를 띄워준다.
#### 스프링이 시작되는 부분
```
@SpringBootApplication
public class HelloSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloSpringApplication.class, args);
	}

}
```

### 라이브러리

#### Gradle
```
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```
Gradle은 빌드툴이며 유사한 툴로 maven이 있으머, 현재는 Gradle을 많이 쓰는 추세이다. 관련된 의존 관계를 대신 설정해준다. <br/>
Spring-boot-start-thymeleaf -> Spring-boot-autoconfigure -> Spring-boot <br/>
내가 필요한 라이브러리를 설정하면 추가로 필요로하는 라이브러리를 추가로 내려받는다.

#### 로깅
실무에서는 System.out.println으로 출력하면 안된다.
로그로 따로 남겨야 로그파일로 관리가 되고, 심각한 오류를 따로 모아서 볼 수 있다.
slf4j, logback을 많이 쓴다.
```
spring-boot-starter-logging
 > logback
 > slf4j
```

##### log4j

아래로 내려갈수록 심각한 오류.
1. trace : debug보다 아래단계, 덜 중요하지만 변수를 쫓는 정도의 로그를 찍는데 사용함.
2. debug : debug를 위해서 사용하는 logging level.
3. info : 진행정보, 상태 정보를 찍는데 사용함.
4. warn : 잠재적 오류, 경고성 정보를 로깅하는데 사용함.
5. error : 오류가 발생했을 경우 사용함.

### 테스트 라이브러리

juonit : 테스트 프레임워크
mockito : 목 라이브러리
assertj : 테스트 코드를 좀 더 편하게 작성하게 도와주는 라이브러리
spring-test : 스프링 통합 테스트 지원

### View 환경설정

resources/static/index.html 에서 시작

#### thymelef

동적 컨텐츠를 생성할때 사용된다.<br/>
JPS와 유사하지만 JSP는 현재 사용하지 않는 추세이다. <br/>


#### 빌드

```
./gradlew clean
clean을 추가하면 완전히 지우고 다시 빌드함.
```

#### MVC와 템플릿 엔진

##### Controller
```
@controller
public class Hellocontroller {

	@GetMapping("hello")
	public String hello(Model model){
		model.addAttribute("name", "hello!!");
		return "hello";
	}
	
	@GetMapping("hello-mvc")
	public String helloMvc(@RequestParam("name") String name, Model model){
		model.addAttribute("name", name);
		return "hello-template";
	}
}
```

##### View (templates/hello-template.html)

```
<html xmlns:th="http://www.thymeleaf.org">
<body>
<p th:text="'hello ' + ${name}">hello! empty</p>
</body>
</html>
```

##### API 예제

```
@GetMapping("hello-string")
@ResponseBody
public String helloString(@RequestParam("name") String name) {
	return "hello " + name;
}

@GetMapping("hello-api")
@ResponseBody
public String helloString(@RequestParam("name") String name) {
	Hello hello = new Hello();
	hello.setName(name);
	return hello;
}

static class Hello {
	private String name;

	public String getName() {
		return name;
	}

	public void setName() {
		this.name = name;
	}
}
```

객체가 반환이 될 때는 기본 값이 JSON방식으로 데이터를 만들어서 반환되는 것이 기본 설정이다.
@ResponseBody 를 사용하게 되면, viewResolver 대신 HttpMessageConverter가 동작. <br/>
기본 문자처리는  **StringHttpMessageConverter**, 객체는 **MappingJackson2HttpMessageConverter**<br/>
Jackson은 객체를 JSON으로 만들어주는 유명한 라이브러리 중 하나. <br/>
byte 처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록되어 있음.<br/>

#### Getter, Setter

자바 빈 규약. 메서드를 통해서 접근하게 됨.
```
static class Hello {
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName() {
		this.name = name;
	}
}
```

#### Optional

NPE(Null Pointer Exception)을 극복하기 위한 Wrapper클래스.

```
Optional<String> optional = Optional.ofNullable(getName());
String name = optional.orElse("anonymous") // 값이 없다면 "anonymous" 반환.

List<String> names = getNames();
List<String> tempNames = list != null > list : new ArrayList<>();

List<String> nameList = Optional.ofNullAble(getList()).orElseGet(() -> new ArrayList<>());
```

### Junit5 / Test

spring 테스트에서 순서는 보장되지 않는다.<br/>
Junit4와 Junit5의 가장 큰 차이점은 람다를 사용할 수 있고 없고의 차이가 있다.<br/>
빌드하게되면 Test영역은 포함되지 않는다.

### Assertions 

#### 예제
```
junit.jupiter.api.Assertions

assertEquals(2, calculator.add(1,1));
assertEquals(4, calculator.multiply(2,2)
	"The optional failure message is now the last parameter");
assertTrue('a'<'b', () -> "Assertion messages can be lazily evaluated --"
	+ "to avoid constructing complex messages unnecessarily.");
```

#### 병행처리 assertAll
```
assertAll("person",
	() -> assertEquals("Jane", person.getFirstName()),
	() -> assertEquals("Doe", person.getlastName))
);
```
각각의 테스트 결과에 대해서 출력되며, 하나의 테스트만 실패한다면 하나의 테스트의 출력만 출력하게됨.

#### Junit의 어노테이션

@BeforeEach : 각각의 함수가 실행되기전에 실행되는 함수. <br/>
@BeforeAll : 모든 함수가 실행되기전 제일 먼저 실행되는 함수. <br/>
@AfterEach : 각각의 함수가 실행된후 실행되는 함수. <br/>
@AfterAll : 모든 함수가 실행된후 마지막에 실행되는 함수. <br/>

### 의존성 주입(DI)

@Service
@Controller
@Repository


