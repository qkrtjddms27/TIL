# 비검사 경고를 제거하라
- 다양한 비검사 경고가 있고, 대부분의 비검사 경고는 쉽게 제거할 수 있다.
- 대부분 컴파일러가 해결방법을 알려준다.
- 비검사 경고는 Runtime 상황에서 TypeCastingException 상황을 방지하기위한 경고.

## 비검사 경고 예제
- 타입 매개변수를 명시하지 않아 생긴 비검사 경고
```
// 비검사 경고 발생
Set<Lark> exaltation = new HashSet();

// 비검사 경고 해결
Set<Lark> exaltation = new HashSet<>();
```

## @SuppressWarnings
- 경고에 대해 이미 인지하고 있으며, 해당 경고를 무시하고 싶을 때 사용.
- 해당 어노테이션은 항상 가능한 한 좁은 범위에 적용하는 것이 좋다.
  + 너무 넓게 설정하면 생각지도 못한 경고를 넘길 수도 있기 때문.
- 해당 어노테이션을 사용하면 경고를 무시해도 안전한 이유를 항상 주석으로 달아줘야한다.

```java
public class Test {
    public <T> T[] toAray(T[] a) {
        if (a.length < size){
            @SuppressWarnings("unchecked")
            T[] result = (T[]) Arrays.copyOf(elements, size, a.getClass());
            return result;
        }
            
        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }
}
```
