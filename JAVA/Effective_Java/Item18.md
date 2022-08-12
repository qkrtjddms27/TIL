# 상속보다는 컴포지션을 사용하라
- 상속은 코드를 재사용하는 강력한 수단이지만, 다른 방법도 있다는 것을 알아야한다.
- 아래와 같은 상황이 아니라면 상속을 사용하는 것에 대해서 다시 한 번 생각해봐야 한다,
  + 상위 클래스와 하위 클래스 모두 같은 프로그래머가 통제하는 패키지 안에서 관리.
  + 확장할 목적으로 설계되었고 문서화도 잘 된 클래스.
- 여기서 말하는 상속은 인터페이스 - 클래스 상속이 아닌, 클래스 - 클래스의 상속을 얘기한다.


## 메서드 호출과 달리 상속은 캡슐화를 깨뜨린다.
- 상위 클래스가 어떻게 구현되느냐에 따라 하위 클래스의 동작에 이상이 생길 수 있다.
- 그 여파로 코드 한 줄 건드리지 않은 하위 클래스가 오동작할 수 있다는 말이다.
  + 상위 클래스 설계자가 확장을 충분히 고려하고 문서화도 제대로 해두지 않으면 하위 클래스는 상위 클래스의 변화에 발맞춰 수정돼야만 한다.

### 예제
```java
public class InstrumentedHashSet<E> extends HashSet<E> {
    
    private int addCount = 0;
    
    public InstrumentedHashSet() {}
    
    public InstrumentedHashSet(int initCap, float loadFactor) {
        super(initCap, loadFactor);
    }
    
    @Override
    public boolean add(E a) {
        addCount++;
        return super.add(e);
    }
    
    @Override
    public boolean addAll(Collection<? extends E> c){
        addCount += c.size();
        return super.addAll(c);
    }
    
    public int getAddCount(){
        return addCount;
    }
}

class Test {
    public static void main(String[] args) {
        InstrumentedHashSet s = new InstrumentedHashSet();
        s.addAll(List.of("틱", "틱틱", "펑"));
    }  
}
```

- 해당 Test의 기대값은 3이지만 실제로 출력되는 값은 6이다.
  + InstrumentedHashSet에서 addAll은 addCount 3을 더한 후 HashSet의 addAll 구현을 호출.
  + HashSet의 addAll은 add()를 사용하여 구현되어 있기 때문에 추가로 addSize에 3이 중복으로 더해져 6이 반환된다.

- 해당 구현내용은 HashSet문서에는 존재하지 않는 방식이므로 코드를 세밀하게 분석하지 않으면 알 수 없다.


## 해결방법, 래퍼클래스
- 기존 클래스를 확장하지 않고, 새로운 클래스를 만들고 기존 클래스가 새로운 클래스의 구성요소로 사용한다. (컴포지션)
  + 새 클래스의 인스턴스 메서드들은 기존 클래스의 대응하는 인스턴스 메서들을 호출하여 결과를 반환한다. (전달)
  + 새로운 클래스는 기존 클래스의 내부 구현 방식의 영향에서 벗어나며 심지어 기존 클래스에 새로운 메서드가 추가되더라도 영향을 받지 않는다.
    
```java
public class ForwardingSet<E> implements Set<E> {
    private final Set<E> s;
    public ForwardingSet(Set<E> s){ this.s = s; }
    
    public void clear() {s.clear();}
    public boolean contains(Object o) { return s.contains(o); }
    public boolean isEmpty() {return s.size();}
    ...
}
```
    
## 정리
- 컴포지션을 써야 할 상황에서 상속을 사용하는 건 내부 구현을 불필요하게 노출하는 꼴이다. (캡슐화 X)
- 상속은 상위 클래스의 API를 결함까지도 승계한다.