# public 클래스에서는 public 필드가 아닌 접근자 메서드를 사용하라

- 데이터 필드에 직접 접근할 수 있어 캡슐화의 이점을 제공하지 못한다.
```java
class Point {
    public double x;
    public double y;
}
```

- API를 수정하지 않고는 내부 표현을 바꿀 수 없고, 불변식을 보장할 수 없으며, 외부에서 필드에 접근할 때 부수 작업을 수행할 수도 없다.
- 모두 private으로 바꾸고 public 접근자를 추가한다.
- 단, private inner 클래스라면 수정 범위가 더 좁아져서 이 클래스를 포함하는 외부 클래스까지로 제한된다.
```java
class Point{
    private double x;
    private double y;
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getX() {return x;}
    public double getY() {return y;}
    
    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}
}
```

## 정리
public 클래스는 절대 가변 필드를 직접 노출해서는 안 된다. 불변 필드라면 노출해도 덜 위험하지만 완전히 안심할 수는 없다. 하지만 package-private 클래스나 private 중첩 클래스(inner class)에서는 종종 필드를 노출하는 편이 나을 수도 있다.
