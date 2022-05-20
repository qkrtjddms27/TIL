# enum

## enum 타입의 문법
```java
enum Scale {
    DO, RE, MI, FA, SO, RA, TI
}

public static void main(String[] args) {
    scale sc = Scale.DO;
    System.out.println(sc);
    
    switch (sc) {
        case DO:
            System.out.println("도~");
            break;
        case RE:
            System.out.println("레~");
            break;
        case MI:
            System.out.println("미~");
            break;
        case FA:
            System.out.println("파~");
            break;
        default:
            System.out.println("솔~ 라~ 시~");
    }
}
```

## 이전 방식의 문제점

```java
interface Animal {
    int DOG = 1;
    int CAT = 2;
}

interface Person {
    int MAN = 1;
    int WOMAN = 2;
}

class NonSafeConst {
    public static void main(String[] args) {
        who(Person.MAN); // 정상적인 메소드 호출
        who(Animal.DOG); // 비정상적인 메소드 호출
    }
    
    public static void who(int man) {
        switch (man) {
            case Person.MAN:
                System.out.println("남성 손님입니다.");
                break;
            case Person.WOMAN:
                System.out.println("여성 손님입니다.");
                break;
        }
    }
}
```
위의 예제를 보면 Animal.DOG 변수와 Person.MAN 변수가 같은 타입과 값을 가진다. 해당 문제를 막기위해 enum을 사용한다.
eunm은 열거형이라고 부르며, 타입 정확한 값 뿐만 아니라 **타입을 체크 할 수 있다.**

## enum 타입의 장점
- 문자열과 비교해 IDE 의 지원. (자동완성, 오타검증, 텍스트 리팩토링)
- 허용 가능한 값들을 제한할 수 있다.
- 리팩토링시 변경 범위가 최소화 된다.
    + 내용의 추가가 필요하더라도 enum 코드외에 수정할 필요가 없다.
- **Java의 enum은 완전한 기능을 갖춘 클래스 이다.**

### 데이터들 간의 연관관계 표현
하나의 테이블(테이블명:origin)에 있는 내용을 2개의 테이블(테이블명:table1,table2)에 등록하는 기능이 있습니다.</br>
origin 테이블의 값은 "Y", "N"인데, table1, table2는 "1"/"0", true/false 형태 발생
```java
public class LegacyCase {
    public String toTable1Value(String originValue) {
        if("Y".equals(originValue)) {
            return "1";
        } else {
            return "2";
        }
    }
    
    public boolean toTable2Value(String originValue) {
        if("Y".equals(originValue)) {
            return "1";
        } else {
            return "2";
        }
    }
}
```
- 현재 상황에서는 "Y", "1", true 는 모두 같은 의미라는 코드에서 알 수 없다.
    + Y란 값은 "1"이 될 수도 있고, true가 될 수도 있다는 것을 확인하려면 항상 위에서 선언된 클래스와 메소드를 찾아야만 한다.
- 불필요한 코드량이 많다.
    + Y, N 외에 R, S 등의 추가 값이 필요한 경우 if 문을 포함한 메소드 단위로 코드가 증가하게 된다.
    + 동일한 타입의 값이 추가되는것에 비해 너무 많은 반복성 코드가 발생.
    
```java
public enum TableStatus {
    Y("1", true),
    N("2", false);
    
    private String table1Value;
    private boolean table2Value;
    
    TableStatus(String table1Value, boolean table2Value {
        this.table1Value = table1Value;
        this.table2Value = table2Value;
    }
    
    public String getTable1Value() {
        return table1Value;
    }
    
    public boolean isTable2Value() {
        return table2Value;
    }
}


@Test
public void origin테이블에서_조회한_데이터를_다른_2테이블에_등록한다() throws Exception {
  TableStatus origin = selectFromOriginTable();

  String table1Value = origin.table1Valu();
  boolean table2Value = origin.isTable2Value();

  assertThat(origin, is(TableStatus.Y));
  assertThat(table1Value, is("1"));
  assertThat(table2Value, is(true));
  }
```

### 상태와 행위를 한곳에서 관리

#### 문제가 있는 코드
```java
public class LegacyCalculator {
    
    public static long calculate(String code, long originValue) {
        if("CALC_A".equals(code)) {
            return originValue;
        } else if("CALC_B".equals(code)) {
            return originValue*3;
        } else if("CALC_C".equals(code)) {
            return originValue*10;
        } else {
            return 0;
      } 
    }
}


@Test
public void 코드에_따라_서로다른_계산하기_기존방식 () throws Exception {
  String code = selectCode();
  long originValue = 10000L;
  long result = LegacyCalculator.calculate(code, originValue);

  assertThat(result, is(10000L));
}
```
- 현재 상황의 문제점
  + 똑같은 기능을 하는 메소드를 중복 생성할 수 있다.
    * 메소드가 있다는 것을 알지 못하면 다시 만드는 경우가 생긴다.
    * 기존의 계산 로직이 변경 될 경우, 다른 사람은 2개의 메소드의 로직을 다 변경해야하는지 해당 부분만 변경하면되는지 알기 어렵다
    * **관리 포인트가 증가한다.**
  
#### 개선된 코드
```java
public enum CalculatorType {
    CALC_A(value -> value),
    CALC_A(value -> value * 10),
    CALC_A(value -> value * 3),
    CALC_ETC(value -> 0L);
    
    private Function<Long, Long> expression;
    
    CalculatorType(Function<Long, Long> expression) {
        this.expression = expression;
    }
    
    public long calculate(long value) {
        return expression.apply(value);
    }
}
@Test
public void 코드에_따라_서로다른_계산하기_enum () throws Exception {
    CalculatorType code = selectType();
    long originValue = 10000L;
    long result = code.calculate(originValue);
    
    assertThat(result, is(10000L));
}
```
