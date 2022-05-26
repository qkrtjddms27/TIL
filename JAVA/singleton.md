# Singleton

- 오직 하나의 인스턴스만 생성하는 클래스.
- 무상태 객체나 설계상 유일해야 하는 시스템 컴포넌트.
- 클래스를 실글턴으로 만들면 이를 사용하는 클라이언트를 테스트하기가 어려워질 수 있다.
- 모두 생성자는 private 으로 감춰두고, 유일한 인스턴스에 접근할 수 있는 수단으로 public static method 를 만들어둔다.


## public static final 필드 방식의 싱글턴
```java
public class Elvis {
    public static final Elvis INSTANCE = new Elvis();
    private Elvis(){ ... }
    
    public void leaveTheBuilding() {}
}
```
- public static final 필드인 Elvis.INSTANCE 를 초기화할 떄 한 번만 호출.
- public 이나 protected 생성자가 없으므로 Elvis 클래스가 초기화될 때 인스턴스가 전체에서 하나임을 보장.


## 정적 팩토리 메서드 방식의 싱글턴
```java
public class Elvis {
    private static final Elvis INSTANCE = new Elvis();
    private Elvis(){ ... }
    public static Elvis getInstance() { return INSTANCE; }
    public void leaveTheBuilding() { ... }
}
```

## 열거 타입 방식의 싱글턴

```java
public enum Elvis {
    INSTANCE;
    public void leaveTheBuilding() { ... }
}
```
- public 필등 방식과 비슷하지만, 더 간결하고, 추가 노력 없이 직렬화 할 수 있다.
- 리플렉션 공격에서도 제2의 인스턴스가 생기는 일을 완벽히 막아준다.
- **대부분 상황에서는 원소가 하나뿐인 열거 타입이 싱글턴을 만드는 가장 좋은 방법이다.**