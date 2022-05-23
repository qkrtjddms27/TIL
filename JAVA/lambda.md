# Lambda

## Lambda 특징
- 함수를 간단한 식으로 표현하는 방법
- 자바의 람다는 익명 함수(객체)이다.
    + 익명 함수는 이름이 없는 함수.
        * 메서드는 클래스에 종속적인 것, 함수는 클래스에 독립적인 것.
        * 메서드에서 반환 타입과 이름을 지운다.
```java
int max(int a, int b) {
    return a > b ?  a : b;
}

(a, b) -> a > b ? a : b
```

```java
int printVar(String name, int i) {
    System.out.println(name+"="+i);
}

(name, i) -> System.out.println(name+"="+i);
```

```java
int square (int x) {
    return x * x;
}

x -> x*x
```

```java
int roll() {
    return (int) (Math.random() * 6);
}

() -> (int)(Math.random() *6)
```
    
- 람다의 장점
    + 코드의 간결성 - 복잡한 식을 단순하게 표현할 수 있다.
    + 지연 연산 수행 - 불필요한 연산을 최소화 할 수 있다.
        * 값을 저장하지 않는다.
        * 원본을 수정하지 않는다.
        * 함수형 언어의 장점을 갖고 있다.
    
## Stream

**Stream API를 사용하기 위해서는 먼저 Stream 을 생성해주어야 한다.**

### Stream 의 구조
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