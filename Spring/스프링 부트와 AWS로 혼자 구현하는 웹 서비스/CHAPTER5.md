# 스프링 시큐리티와 OAuth 2.0으로 로그인 기능 구현하기

## 스프링 시큐리티

- 인증과 인가 기능을 가진 프레임워크.
- 인터셉터, 필터 기반의 보안 기능을 구현하는 것보다 스프링 시큐리티를 통해 구현하는 것을 적극 권장.

## OAuth2

- 소셜 로그인 기능
- 직접 구현하게되면 고려해야 할 것이 많다.
  + 로그인 시 보안, 회원가입 시 이메일 혹은 전화번호 인증, 비밀번호 찾기, 비밀번호 변경, 회원정보 변경

### User Entity
```java
@Getter
@NoArgsConstructor
@Entity
class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false)
  private String name;
  
  @Column(nullable = false)
  pivate String email;
  
  @Column
  private String picture;
  
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;
  
  @Builder
  public User(String name, STring email, String picture, Role role) {
      this.name = name;
      this.email = email;
      this.picutre = picutre;
      this.role = role;
  }
  
  public User update(String name, String picture) {
      this.name = name;
      this.picture = picture;
      
      return this;
  }
  
  public STring getRoleKey() {
      return this.role.getKey();
  }
}


```

### Enum 타입
```java
@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");
  
    private final String key;
    private final String title;
}
```

### Repository
```java

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
}
```

### SecurityConfig
```java
@RequiredArgsConstructor
@EnableWebSecurtiy
public class SecurityConfig extends WebSecurityConfigureAdapter {
    
    private final CustomOAuth2UserService customOAuth2UserService;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .header().frameOptions().disable()
                .and()
                    .authorizeRequest()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    .anyRequest().aauthenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
    }
}
```

#### @EnableWebSecurity
- Spring Security 설정들을 활성화 해주는 어노테이션

#### csrf().disable().headers().frameOptions().disable()
- h2-console 화명을 사용하기 위해 해당 옵션들을 disable.

#### authorizeRequests
- URL별 권한 관리를 설정하는 옵션의 시작점.
- authorizeRequests가 선언되어야만 antMatchers 옵션을 사용할 수 있다.

#### antMatchers
- 권한 관리 대상을 지정하는 옵션
- URL, HTTP 메소드별로 관리가 가능
- "/"등 지정된 URL들을 permitAll() 옵션을 통해 전체 열람 권한을 줌
- "/api/v1/**" 주소를 가진 API는 USER 권한을 가진 사람만 사용가능하도록

#### anyRequest
- 설정된 값들 이외의 나머지 URL들을 나타냄
- authenticated()을 추가하여 나머지 URL들은 모두 인증된 사용자들에게만 허용.

#### oauth2Login
- OAuth 2 로그인 기능에 대한 여러 설정의 진입점.

#### userInfoEndpoint
- OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당

#### userService
- 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록.
- 리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시.

### CustomOAuth2UserService
```java

public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2USer> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        
        OAuth2UserService<OAuth2UserRequest, OAuth2USer>
                delegate = new DefaultOAuth2UserService();
        
        Oauth2User oauth2User = delegate.loadUser(userRequeset);
        
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();
        
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNAmeAttributeName,
                oAuth2User.getAttributes());
        
        User user = savOrUpdate(attributes);
        
        httpSession.setAttribute("user", new SessionUser(user));
        
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNAmeAttributeKey());
    }
    
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getNAme(), attributes.getPicture()))
                .orElse(attributes.toEntiy());
        
        return userRepository.save(user);
    }
}
```

#### registrationId
- 현재 로그인 진행 중인 서비스를 구분하는 코드
  + 구글, 네이버, 카카오 서비를 이용한 로그인인지 구분.

#### userNameAttributeName
- OAuth2 로그인 진행 시 키가 되는 필드값.
- 구글은 기본 코드를 제공하지만, 네이버 카카오 등은 지원하지 않는다.

#### OAuthAttributes
- OAuth2UserService를 통해 가져온 OAuth2User의 attriubte를 담을 클래스.

#### SessionUser
- 세션에 사용자 정보를 저장하기 위한 Dto 클래스
  + User클래스를 쓰지 않고 따로 관리하는 것이 유리하다.