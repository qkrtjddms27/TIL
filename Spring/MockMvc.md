# MockMvc

## MockMvc 란?
웹 어플리케이션을 애플리케이션 서버에 배포하지 않고 테스트용 MVC환경을 만들어 요청 및 전송, 응답기능을 제공해주는 유틸리티 클래스.
- 실제 서버에 구현한 애플리케이션을 실행시키지 않고 테스트용으로 시뮬레이션하여 MVC가 되도록 도와주는 클래스

```java
class ControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void testController() throws Exception {
        String jsonString = "{\"name\": \"테스트\"}";
        
        mockMvc.perform(
                get("test?query=food") // 요청 url
                .contentType(MediaType.APPLICATION_JSON) // Json 타입으로 지정
                .content(jsonString) // jjson으로 내용 등록
                        // 검증 
                .andExpect(status().isOk()) // 응답 status를 ok인지 확인
                        // 결과 출력
                .andDo(print()) // 응답값 print
        );
    }
}
```

### perform 
- perform 을 설정하면 요청 설정 메스드를 통해서 요청에 대한 설정을 할 수 있다.
- perform 의 Expect 메서드를 통해 테스트를 진행할 수 있다.

#### perform 요청 설정 메소드
- param / params : 쿼리 스트링 설정
- cookie : 쿠키 설정
- requestAttr : 요청 스코프 객체 설정
- sessionAttr : 세션 스크프 객체 설정
- content : 요청 본문 설정
- header / haders : 요청 헤더 설정
- contentType : 본문 타입 설정

#### perform 검증 설정 메소드
- status : 상태 코드 검증
  + isNotFound() : 404인지 확인
  + isMethodNotAllowed() : 405인지 확인
  + isInternalServerError() : 500인지 확인
  + is(int status) : 임의로 지정한 상태 코드인지 확인.
- header : 응답 header 검증
- content : 응답 본문 검증
- cookie : 쿠키 상태 검증
- view : 컨트롤로가 반환한 뷰 이름 검증
- redirectedUrl(Pattern) : 리다이렉트 대상의 경로 검증
- model : 스프링 MVC 모델 상태 검증
- request : 세션 스코프, 비동기 처리, 요청 스코프 상태 검증
- forwardedUrl : 이동대상의 경로 검증

#### perform 기타 메서드
- andDo : print, log를 사용할 수 있는 메소드
```java
.andDo(print())
.andDo(log())
```