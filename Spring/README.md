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
