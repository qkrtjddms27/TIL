# Web 확장

## 도메인 클래스 컨버터
> HTTP 파라미터로 넘어온 엔티티의 아이디로 엔티티 객체를 찾아서 바인딩

- HTTP 요청은 회원 id로 받지마 도메인 컨버터가 중간에 동작해서 회원 엔티티 객체를 반환
- 도메인 클래스 컨버터도 리파지터리를 사용해서 엔티티를 찾는다.
- 도메인 클래스 컨버터로 엔티티르르 파라미터로 받으면, 이 엔티티는 단순 조회용으로만 사용해야 한다.
---

- 도메인 클래스 컨버터 사용 전
```java
@GetMapping("/members/{id}")
public String findMember(@PathVariable("id") Long id) {
    Member member = memberRepository.findById(id).get();
    return member.getUsername()
}
```

- 도메인 클래스 컨버터 사용 후
```java
@GetMapping("/members/{id}")
public String findMember(@PAthVariable("id") Memnber member) {
    return member.getUsername()
}
```

## 페이징과 정렬 예제
```java
@GetMapping("/members")
public Page<Member> list(Pageable pageable) {
    Page<Member> page = memberRepository.findAll(pageable);
    return page;    
}
```

- 파라미터로 Pagealbe 을 받을 수 있다.
  + 실제는 org.springframework.data.domain.PageRequest 객체 생성

- 파라미터
  + /members?page=0&size=3&sort=id,desc&sort=username,desc
  + page : 현재 페이지, 0부터 시작.
  + size : 한 페이지에 노출할 데이터 건수
  + sort : 정렬 조건을 정의, 정렬 속성, ASC | DESC
    * 정렬 기준을 추가하고 싶으면 sort 파라미터 추가.
  + 파라미터 기본 값 설정
    * spring.data.web.pageable.default-page-size=20, 기본 페이지 사이즈
    * spring.data.web.pageable.max-page-size=2000, 최대 페이지 사이즈
  + 글로벌 설정외에도 @PageableDefault 어노테이션을 사용해서 만들 수 있다.
  
```java
@RequestMapping(value = "/members_page", method = RequestMethod.GET)
public String list(@PageableDefault(size = 12, sort = "username",
                        direction = Sorg.Direction.DESC) Pageable pageable) {
    ...
}
```

### 페이징 정보가 둘 이상이면 접두사로 구분, 접두사
- 정렬정보가 둘 이상이면 접두사를 이용해서 구분할 수 있다.
- `@Qualifier`에 접두사명 추가, "{접두사명}_xxx"
- 예제: /members?member_page=0&order_page=1
```java
public String list(
        @Qualifier("member") Pageable memberPagealbe,
        @Qualifier("order") Pageable orderPageable, ...
) 
```

### Page 내용을 DTO로 변환하기
- 엔티티를 API로 노출하면 다양한 문제가 발생한다. 그래서 엔티티를 꼭 DTO로 변환해서 반환해야 한다.
- Page는 map()을 지원해서 내부 데이터를 다른 것으로 변경할 수 있다.

```java
public Page<MemberDto> list(Pageable pageable) {
    Page<Member> page = memberRepository.findAll(pageable);
    Page<MemberDto> pageDto = page.map(MemberDto::new);
    return pageDto;
}

public Page<MemberDto> list(Pageable pageable) {
    reutrn memberRepository.findAll(pageable).map(MemberDto::new);
}
```

### Page를 1부터 시작하기
- 스프링 데이터는 Page를 0부터 시작한다.
- 만약 1부터 시작이라면?
  + Pageable, Page를 파라미터와 응답 값으로 사용하지 않고, 직접 클래스를 만들어서 처리한다. PageRequest(Pageable 구현체)를 생성해서 리포지토리에 넘긴다.
  + `spring.data.web.pageable.one-indexed-parameters`를 `true`로 설정한다.
    * 이 방법은 web에서 page 파라미터를 -1 처리 할 뿐이다.
    * 응답값인 `Page`에 모두 0페이지 인덱스를 사용하는 한계가 있다.