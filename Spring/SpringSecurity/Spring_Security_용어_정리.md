# Spring Security 관련 용어 정리
- Spring Security에서 JWT를 통해 인증과 인가를 구현하기 위해서는 Filter Chain의 추상클래스와 인터페이스를 구현해야한다.
- 필터체인에 대한 이해가 필요하다.

## 필터 체인
- Filter는 Dispatch Servlet에 도착하기전에 먼저 처리된다.
- 필터체인은 이름 그대로 스프링에 있는 필터를 체인처럼 엮어놓은 흐름을 뜻한다.
- 다양한 필터가 존재하고, 각각의 필터는 각기 다른 관심사를 맡아서 처리한다.
    + 원하는 방향으로 수정 및 확장할 수 있다.
- 정리하면 Dispatcher Servlet에 도달하기전에 먼저 처리되는 작업의 흐름이다.
- 필터 체인에 Spring SecurityFilterChain을 붙이고 filter를 이요하여 스프링 시큐리티의 인증 과정의 전체적인 동작을 관장하게됨.

### 필터체인의 종류
- HeaderWriterFilter : Request의 헤더를 검사하여 header를 추가하거나 삭제하는등의 역할을 맡는다.
- CorsFilter : 허가된 사이트나 클라이언트의 요청인지 검사하는 역할을 한다.
- CsrfFilter : CSRF의 공격과 관련된 필터, Post나 Put과 같이 리소스를 변경하는 요청의 경우 내가 내보냈던 리소스에서 올라온 요청인지 확인한다.
- LogoutFilter : logout과 관련된 URI인지 확인하는 필터.
- UsernamePasswordAuthenticationFilter : username / password 로 로그인을 하려고 하는지 체크하여 승인이 되면 Authentication을 부여하고 이동 할 페이지로 이동한다.
- ConcurrentSessionFilter : 동시 접속 허용여부를 체크한다.
- BearerTokenAuthenticationFilter : Authorization 헤더에 Bearer 토큰을 인증해주는 역할을 한다.
- RequestCacheAwareFilter : request한 내용을 성능을 위해서 Cache에 담아주는 역할을 한다. 다음에 같은 값으로 요청이 오면 이전 Cache값을 줄 수 있다.
- RememberMeAuthenticationFilter : 클라이언트의 인증 유무를 기억하는 기능 RememberMe 쿠키를 통해 인증을 처리할 수 있다.
- AnonymousAuthenticationFilter : Authentication의 Null을 방지하기 위해 Authentication을 Anonymous 정해주는 역할을 한다.
- SessionManagementFilter : 서버에서 지정한 세션정책에 맞게 사용자가 사용하고 있는지 검사하는 역할
- ExceptionTranslationFilter : 해당 필터 이후에 인증이나 권한 예외가 발생하면 해당 필터가 처리해준다.
- FilterSecurityInterceptor : 사용자가 요청한 request에 들어가고 결과를 리턴해도 되는 권한이 있는지 체크. 권한이 없다면 ExceptionTranslationFilter에서 Exception처리.

## SecurityContextHolder
- SecurityContextHolder는 Authentication을 담고 있는 Holder, 즉 인증된 사용자의 구체적인 정보를 보관한다.
- SecurityContext를 포함하고 있고, 현재 스레드를 SecurityContext와 연결해 주는 역할을 한다.
- ThreadLocal을 이용하여 인증 관련 정보를 저장.
- SecurityContextHolder 설정을 이용하여 병경 가능.
    + SecurityContextHolder.MODE_THREADLOCAL (default)
    + SecurityContextHolder.MODE_INHERITABLETHREADLOCAL
    + SecurityContextHolder.MODE_GLOBAL
    
## SecurityContext
- SecurityContextHodler를 통해 얻을 수 있다.
- Authentication에 대한 정보를 갖고 있다.

## Authentication
- Session에 저장되는 정보가 Authentication.
- 역할에 따라 principal, credentials, authorities, detail로 구성.
    + Principal : 식별된 사용자 정보를 보관, UserDetails의 인스턴스.
    + Credentials : 주체가 올바르다는 것을 증명, 일반적으로 비밀번호
    + Authorities : AuthenticationManager가 설정한 권한을 의미. Authorities 상태에 영향을 주지 않거나 수정할 수 없는 인스턴스를 사용.
    
## GrantedAuthority
- Authentication에 부여된 권한을 나타내며 getAuthorities()를 통해 얻을 수 있다.

## AuthenticationManager
- AuthenticationManager는 Spring Security의 filter가 인증을 수행하는 방법을 정의하는 API
- SecurityContextHolder에 설정
- SecurityContextHolder에 의해 반환된 Authentication은 AuthenticationManger에 의해 검증.
- ProviderManger를 구현체로 사용.
