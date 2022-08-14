# 상속을 고려해 설계하고 문서화하고, 그러지 않았다면 상속을 금지하라
- 프로그래머의 통제를 벗어난 외부 클래스를 상속하는 것은 위험하다.
  + 상속용 클래스는 재정의할 수 있는 메서드들을 내부적으로 어떻게 이용하는지 문서로 남겨야 한다. 
  + 클래스의 API로 공개된 메서드에서 클래스 자신의 또 다른 메서드를 호출할 수도 있다.
  
## Implementation Requirements
- API 문서의 메서드 설명 끝에서 종종 "Implementation Requirements"로 시작하는 부분은 내부 동작 방식을 설명하는 곳이다.
- Implementation Requirements: 컬렉션을 순회하며 주어진 원소를 찾도록 구현되었다. 주어진 원소를 찾으면 반복자의 remove...

## 상속용 클래스를 설계할때 어떤 메서드를 protected로 노출할지 결정하는 방법
- 실제 하위 클래스를 만들어 시험해보는 것이 최선 상속용 클래스를 시험하는 방법은 직접 하위 클래스를 만들어보는 것이 '유일'
- 상속용으로 설계한 클래스는 배포 전에 반드시 하위 클래스를 직접 만들어 검증해야 한다.
  + 상속용 클래스에 protected 메서드와 필드를 구현하면서 선택한 결정에 계속 고민해야한다.

## 상속용 클래스의 생성자는 재정의 가능 메서드를 호출해서는 안 된다.
- 하위 클래스의 생성자가 하위 클래스의 생성자보다 먼저 실행
  + 재정의한 메서드가 하위 클래스의 생성자에서 초기화하는 값에 의존한다면 의도대로 동작하지 않는다.
- clone과 readObject 모두 직접적으로든 간접적으 로든 재정의 가능 메서드를 호출해서는 안 된다.

```java
public class Super {
    public Supuer() {
        overrideMe();
    }
    
    public void overrideMe() {}
}

public final class Sub extends Super {
    
    private final Instant instant;
    
    Sub() {
        instant = Instant.now();
    }
    
    @Override
    public void overrideMe() {
        System.out.println(instant);
    }
    
    public static void main(String[] args) {
        Sub sub = new Sub();
        sub.overrideMe();
    }
}
```

- 해당 테스트의 결과는 instant를 두 번 출력하는 것이 아니라 null, instant이다.
- Super의 생성자가 먼저 호출되고 하위 메서드가 호출되기때문에 예측하기 까다로워진다.

## 정리
- 상속용으로 구현클래스를 만드는 것은 많은 비용이 들어간다.
  + 특정 부분에서 예측 불가능하게 동작하는 부분으로 전부 주석으로 제공해야한다.
  + Override될 수 있는 메서드를 (super)생성자에서 호출하면 안된다.
  + 정말 필요한 부분에서 protect나 public으로 상속가능하게 해야한다. 하지만 너무 적다면 상속의 의미를 다시 생각해보자.

- 상속해야하는 명확한 의미가 없다면 상소을 금지하는 클래스를 만드는 것이 좋다.