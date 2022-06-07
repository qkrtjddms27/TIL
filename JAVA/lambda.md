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
    

```