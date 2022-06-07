# Stream

- Stream API를 사용하기 위해서는 먼저 Stream 을 생성해주어야 한다.
- Stream 은 기존의 있는 데이터로 작업하는 것이 아닌 새로운 데이터로 만들어서 작업한다.

## Stream 의 구조
- stream()
    + 스트림 생성

- filter
    + 중간 연산 (스트림 변환) - 연속에서 수행 가능합니다.

- count
    + 최종 연산 (스트림 사용) - 마지막에 단 한 번만 사용 가능합니다.

### Collection Stream 생성
```java
List<String> list = Arrays.asList("a", "b", "c");
Stream<String> listStream = list.stream();
```

### 배열의 Stream 생성
```java
Stream<String> stream = Stream.of("a", "b", "c");
Stream<String> stream = Stream.of(new String[] {"a", "b", "c"});
Stream<String> stream = Arrays.stream(new String[] {"a", "b", "c"});
```

### Filter

Filter 는 Stream 에서 조건에 맞는 데이터만을 정제하여 더 작은 컬렉션을 만들어내는 연산.

### 결과 만들기

**최대값/최소값/총합/평균/갯수 - Max/Min/Sum/Average/Count**
```java
OptionalInt min = IntStream.of(1, 3, 5, 7, 9).min();

int max = IntStream.of().max().orElse(0);

IntStream.of(1 ,3 ,5 ,7 ,9).average().ifPresent(System.out::println);

```

## Stream Example
```java
public class App { 
    public static void main(String[] args) { 
        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(2, "spring data jpa", true));
        springClasses.add(new OnlineClass(3, "spring mvc", false));
        springClasses.add(new OnlineClass(4, "spring core", false));
        springClasses.add(new OnlineClass(5, "spring api development", false));

        System.out.println("spring 으로 시작하는 수업");
        springClasses.stream()
                .filter(oc -> oc.getTitle().startWith("spring"))
                .foreach(oc -> System.out.println(oc.getId()));
      
        System.out.println("close 되지 않은 수업");
        springClasses.stream()
//                .filter(oc -> !oc.isClosed())
                .filter(Predicate.not(OnlineClass::isClosed))
                .foreach(oc -> System.out.println(oc.getId()));
      
        System.out.println("수업 이름만 모아서 스트림 만들기");
        Stream stream = springClasses.stream()
                                .map(oc -> oc.getTitle())
                                .foreach(System.out::println);
        
        List<OnlineClass> javaClasses = new ArrayList<>();
        javaClasses.add(new OnlineClass(6, "The Java, Test", true));
        javaClasses.add(new OnlineClass(7, "The Java, Code Manipulation", true));
        javaClasses.add(new OnlineClass(8, "The Java, 8 to 11", false));
        
        List<List<OnlineClass>> events = new ArrayList<>();
        events.add(springClasses);
        events.add(javaClasses);

        System.out.println("두 수업 목록에 들어있는 모든 수업 아이디 출력");
        events.stream()
                .flatMap(Collection::stream)
                .foreach(oc -> System.out.println(oc.getId()));

        System.out.println("10부터 1씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개 까지만");
        Stream.iterate(10, i -> i + 1)
                .skip(10)
                .limit(10)
                .forEach(System.out::println);

        System.out.println("자바 수업 중에 Test가 들어있는 수업이 있는지 확인");
        javaClasses.steram().anyMatch(oc -> oc.getTitle().contains("Test"));
        System.out.println(test);

        System.out.println("스프링 수업 중에 제목에 spring이 들어간 것만 모아서 List로 만들기");
        List<String> spring = javaClasses.stream()
                .filter(oc -> oc.getTitle().contains("spring"))
                .map(OnlineClass::getTitle)
                .collect(Collectors::toList);
        spring.forEach(System.out::println);
    }
    
    
    private class OnlineClass{
        private int id;
        private String title;
        private boolean closed;
        
        OnlineClass(int id, String title, boolean closed) {
            this.id = id;
            this.title = title;
            this.closed = closed;
        }
        
        public int getId() {
            return this.id;
        } 
        
        public String getTitle() {
            return this.title;
        }
        
        public boolean isClosed() {
            return this.closed;
        }
        
        public void setClosed(boolean closed) {
            this.closed = closed;
        }
    }
}
```