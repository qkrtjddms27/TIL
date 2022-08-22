# 인텔리제이로 스프링 부트 시작하기

## Gradle
```groovy
buildscript {
    ext {
        springBootVersion = '2.1.7.RELEASE'
    }
    
    repositories {
        mavenCentral()
        jcenter()
    }
    
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
    }
}
```
- 스프링 이니셜라이저(부트)로 자동으로 생성할 수 있다.
- ext 키워드로 build.gradle에서 springBootVersion의 값을 '2.1.7.RELEASE'
- spring-boot-gradle-plugin라는 스프링 부트 그레이들 플로그인의 2.1.7.RELEASE

```groovy
apply plugin : 'java'
apply plugin : 'eclipse'
apply plugin : 'org.springframework.boot'
apply plugin : 'io.spring.dependency-management'
```
- io.spring.dependency-management 플러그인은스프링 부트의 의존성들을 관리해 주는 플러그인이라 꼭 추가.
- 앞 4개의 플로그인은 자바와 스프링 부트를 사용하기 위해서는 필수 플러그인들.


```groovy
repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
}
```
- repositories는 각종 의존성들을 어떤 원격 저장소에서 받을지를 지정.
- mavenCentral을 많이 사용하지만, 최근에는 라이브러리 업로드 난이도 관련 문제때문에 jcente도 많이 사용.
  + 본인이 만들어서 업로드하기 위한 과정이 많이 간편화 되어있음
    

