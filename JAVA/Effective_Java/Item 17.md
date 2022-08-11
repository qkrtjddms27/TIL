# 변경 가능성을 최소화하라
- 불변 클래스를 만들자.
- 변경할 수 있는 부분을 최소한으로 줄이자.
- 특별한 이유가 없다면 필드는 private final로 선언하자

## 불변 클래스
- 불변 클래스란 그 인스턴스의 내부 값을 수정할 수 없는 클래스.
- 불변 인스턴스에 간직된 정보는 고정되어 객체가 파괴되는 순간까지 절대 달라지지않는다.
- String, 기본 타입의 Wrapper 형태, BigInteger, BigDecimal
- 한 번 만든 불변객체는 다양한 곳에서 공유되도 안전하기 때문에 안심하고 사용할 수 있다.
- 객체를 만들 때 다른 불변 객체들을 구성요소로 사용하면 불변성을 유지하기 편하다.
  + 맵이나 집합은 안에 담긴 값이 바뀌면 불변성이 허물어지는데, 불변 객체를 사용하면 그런 걱정은 하지않아도 된다.
- 불변 객체는 그 자체로 실패 원자성을 제공한다.
  + 실패 원자성 : 메서드에서 예외가 발생하여도 그 상태는 그대로 유지되어야 한다.
    
### 불변 클래스를 만드는 5가지 규칙
- 객체의 상태를 변경하는 메서드를 제공하지 않는다.
- 클래스를 확장할 수 없도록 한다.
  + 클래스를 final로 선언하여 상속을 막는다.
  + 생성자를 private으로 선언한다.
- 모든 필드를 final로 선언한다.
  + 인스턴스를 동기화 없이 다른 스레드로 건네도 문제없이 동작할 수 있게 한다.
- 모든 필드를 private으로 선언한다.
  + final만 사용하고 public하게 만들어도 되지만, 다음 릴리스(변경)이 있을 경우 내부 표현을 바꿀 수 없으므로 private으로 선언하는 것이 좋다.
- 자신 외에는 내부의 가변 컴포넌트에 접근할 수 없도록 한다.
  + 생성자, 접근자를 이용하여 방어복사를 활용하자.

    
### 불변 클래스 구현 예시
- plus, minus, times, divideBy는 사칙연산 메서드 인스턴스를 수정하지 않고 새로운 생성자를 통해 인스턴스를 만들어 반환한다.
- add(동사)가 아닌 plus(전치사)
  + 객체의 값을 변경하지 않는다는 의미를 내포.
```java
public final class Complex {
    private final double re;
    private final double im;
    
    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }
    
    public double realPart()      { return re; }
    public double imaginaryPart() { return im; }
    
    public Complex plus(Complex c) {
        return new Complex(re + c.re, im + c.im);
    }
    
    public Complex minus(Complex c) {
        return new Complex(re - c.re, im - c.im);
    }
    
    public Complex times(Complex c) {
        return new Complex(re * c.re - im* c.im,
                           re * c.im + im * c.re);
    }
    
    public Complex dividedBy (Complex c) {
        double tmp = c.re * c.re + c.im;
        
        return new Complex((re * c.re + im * c.im) / tmp,
                           (im * c.re - re * c.im) / tmp);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Complex))
            return false;
        
        return Double.compare(c.re, re) == 0
                && Double.compare(c.im, im) == 0;
    }
    
    @Override
    public public int hashCode() {
        return 31 * Double.hashCode(re) + Double.hashCode(im);
    }
    
    @Override
    public String toString() {
        return "(" + re + " + " + im + "i)";
    }
}
```

