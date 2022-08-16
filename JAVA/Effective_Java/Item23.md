# 태그 달린 클래스보다는 클래스 계층구조를 활용하라

## 예제 & 문제점
```java
class Figure {
    enum Shape { RECTANGLE, CIRCLE };
    
    // 태그 필드 - 현재 모양이 무엇인지, 현재 역할이 무엇인지 나타내는 필드
    final Shape shape;
    
    
    // 모양이 사각형일때만 사용하는 필드
    double length;
    double width;
    
    // 모양이 원일때만 사용하는 필드
    double radius;
    
    // 원 생성자
    Figure(double radius) {
        shape = Shpae.CIRLE;
        this.radius = radius;
    }
    
    Figure(double length, double width) {
        shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
    }
    
    double area() {
        switch (sahpe) {
            case RECTANGLE:
                return length * width;
            case CIRLE:
                return Math.PI * (radius * radius);
            default:
                throw new AssertionError(shape);
        }
    }
}
```
- 다른 의미를 위한 코드도 언제나 함께 하니 메모리도 많이 사용한다.
- 태그 달린 클래스는 클래스 계층구조를 어설프게 흉내낸 아류일 뿐이다.
- 장황하고 오류를 내기 쉽고, 비효율적이다.

## 해결책
- 클래스 계층구조로 정리한다.
- Root 클래스는 추상클래스로 정의한다.

```java
abstract class Figure {
    abstract double area();
}

class Circle extends Figure {
    final double radius;
    
    Circle(double radius) { this.radius = radius; }
    
    @Override
    double area() { return Math.PI * (radius * radius); }
}

class Rectangle extends Figure {
    final double length;
    final double width;
    
    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
    
    @Override
    double area() { return length * width; }
}
```

- 각 의미를 독립된 클래스에 담아 관련 없던 데이터 필드들을 모두 제거.
- 루트 클래스의 코드를 건드리지 않고도 다른 사람들이 독립적으로 계층구조를 확장하고 함께 사용할 수 있다.

## 정리

- 태그 달린 필드를 사용할 상황은 거의 없다. 
- 새로운 클래스를 작성하는 데 태그 필드가 등장한다면 태그를 없애고 계층구조로 대체하는 방법을 생각해보자.
- 기존 클래스가 태그 필드를 사용하고 있다면 계층구조로 리팩터링을 고려해보자.