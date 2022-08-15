# 인터페이스를 구현하는 쪽을 생각해 설계하라

## 디폴트 메서드의 등장
- 자바 8이전에는 기존 구현체를 깨뜨리지 않고는 인터페이스에 메서드를 추가 할 방법이 없었다. 
- 디폴트 메서드를 선언하면, 메서드를 추가할때 구현체에서 메서드를 추가로 오버라이딩하지 않더라도 코드가 깨지지 않는다.
- 하지만, 자바 8이후에는 디폴트 메서드의 등장으로 완화되었다. (완벽하게 대처되진 않았다.)
  + 생각할 수 있는 모든 상황에서 불변성을 깨지 않고 완벽하게 디폴트 메서드를 작성하는 것은 쉽지않기 때문이다.

## 디폴트 메서드의 등장으로 발생한 오류
- Collection인터페이스에 추가된 removeIf 메서드.
  + 이 메서드는 주어진 불리언 함수가 true를 반환하는 모든 원소를 제거한다.
  + 디폴트 구현은 반복자를 이용해 순회화면서 각 원소를 인수로 넣어 프레디키트(Predicate)를 호출하고, 프레디키트가 TRUE를 반환하면 반복자의 remove 메서드를 호출해 그 원소를 제거한다.
    * Predicate : 변수를 받아 boolean 형을 반환하는 함수형 인터페이스.

- SynchronizedCollection 클래스는 많이 사용되었지만 removeIf 메서드를 재정의하고 있지 않다. 
- Java 8이후에 SynchronizedColleciton에 removeIf가 디폴트 메서드로 자동으로 삽입되었지만, 예전에는 removeIf가 Override 되어 있지 않았었다.
  + 현재는 override되어 있음.
_ 
```java
public interface Example {
    default boolean removeIf(Predicate<? super E> filter) {
        Object.requireNotNull(filter);
        boolean result = false;
        for(Iterator<E> it = iterator(); it.hasNext(); ) {
            remove();
            result = true;
        }
        return result;
    }
}
```

## 정리
- 디폴트 메서드는 기존 구현체에 런타임 오류를 일으킬 수 있다.
- 인터페이스를 설계 할 때는 여전히 세심한 주의를 기울여야 한다.
- 인터페이스를 릴리스한 후라도 결함을 수정하는 게 가능한 경우도 있겠지만, 절대 그 가능성에 기대서는 안 된다.