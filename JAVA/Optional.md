# Optional
- Optional은 Java 의 모든 것들을 담아 둘 수 있는 객체이다. 
- Optional내부의 값은 Null일 수 있다.
- Optional 권장사항 중 하나는 리턴 타입으로만 사용하는 것이다.
- Optional은 NPE와 관련된 처리를 하기 위한 것이다.

## Optional 주의사항

### 리턴 타입으로만 사용하자
- 클라이언트측에서 Null을 주면 결국 Null로 셋팅된다.
    + null이 된다.
- Map 의 Key 는 사용하면 안된다
    + Map 의 Key 는 Null 은 안된다.
**잘못된 예제**
```java
public class boo{
    public Progress progress;
    
    public void setProgress(Optional<Progress> progress) {
        this.progress = progress.get();
    }
}
```

### primitive 타입용 Optional은 따로 존재한다
- Boxing과 UnBoxing을 하고 많은 오버헤드를 발생시킨다.

```java
public class boo {
    public static void main(String[] args) {
        OptionalInt.of(10);
    }
}
```

### Null을 리턴하지 말자.
```java
public class boo{
    public Progress progress;
        
//    ....
    public Optional<Progress> getProgress() {
        return null; // Optional.empty();
    }
}
```

### Container 성격의 객체들을 Optional로 감싸지 않는다.
- 비어있는 것을 표현할 수 있는 타입들을 굳이 Optional 에 넣지 말자.
    + collection, Map, Stream, Optional
  
## Optional

### Optional 생성
- Optional.of()
- Optional.ofNullable()
- Optional.empty()

### Optional 값이 있는지 없는지 확인
- isPresent()
- isEmpty()

### Optional에 있는 값 가져오기
- get()
  + 그냥 꺼내면 Null이 리턴될 수 있다.
  
- ifPresent(Consumer)
  + Optional 에 값이 있을 경우 그 값을 가지고 ( ~~~ ) 리턴하라.
  + return 타입은 boolean
  
- orElse(T)
  + Optional에 값이 있으면 가져오고 없는 경우에 ( ~~~ ) 를 리턴하라.
  
- orElseGet(Supplier)
  + Optional에 값이 있으면 가져오고 없는 경우에 ~~를 하라
  
- orElseThrow(Supplier)
  + Optional에 값이 있으면 가져오고 없는 경우에 에러 던지기
  
- Optional filter(Predicate)
  - Optional에 들어있는 값 걸러내기

```java
public class Boo {
    
    public static void main(String[] args) {
        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "optional boot", true));
        springClasses.add(new OnlineClass(5, "rest api development", false));
        
        Optional<OnlineClass> optional = springClasses.stream()
                .filter(oc -> oc.getTitle().startWith("spring"))
                .findFirst();
        
        OnlineClass onlineClass = optional.get();
        System.out.println(onlineClass.getTitle());
    }
    
} 
```