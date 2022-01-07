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
