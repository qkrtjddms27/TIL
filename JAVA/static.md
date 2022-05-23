# Static

## 정적 멤버
- 객체에 소속된 멤버가 아니라 클래스에 고정된 멤버.
- 클래스 로더가 클래스를 로딩해서 메소드 메모리 영역에 적재할때 클래스별로 관리.
- Static 변수(필드)와 Static 메소드.

### 정적 멤버 생성
- Static 키워드를 통해 생성된 정적멤버들은 Heap 영역이 아닌 Static 영역에 할당.
- G.C에 영향을 받지 않는다.
  + Static을 너무 남발하게 되면 만들고자 하는 시스템 성능에 악영향을 줄 수 있다.

### 정적 멤버 선언
- static 키워드를 사용하여 선언이 가능하다.
```java
static int num = 0;
public static void static_method(){}
```



