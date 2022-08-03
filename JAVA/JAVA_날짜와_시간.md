# JAVA 날짜와 시간

## Calendar 클래스와 Date 클래스의 문제점
- JDK 1.7 버전까지 주로 사용했던 날짜 클래스.
- 윤초, 윤년등 고려해야 하는 것들이 많다.

### 불멸 객체가 아니다.
- Calendar 객체나 Date 객체가 여러 객체에서 공유되면 한 곳에서 바꾼 값이 다른 곳에 영향을 미치는 부작용이 생길 수 있다.
- 안전하게 사용하려면 다른 곳에 사용할때 깊은 복사로 사용해야된다. 

### int 상수 필드의 남용
- Calendar를 사용한 날짜 연산은 int 상수 필드를 사용한다.
    + 전혀 엉뚱한 상수가 들어가도 컴파일 시점에서 확인할 방법이 없다.
```java
calendar.add(Calendar.SECOND, 2); // 정상 코드
calendar.add(Calendar.JUNE, 2); // 비정상 코드 
```

### 헷갈리는 월 지정
- Calendar 클래스에서 1월을 표현하는 상수는 0이다
- Calendar 클래스의 1582 년 10월 4일 표현
    + Calendar.set(1582, 10, 4) 는 비정상적인 표현
  + Calendar.set(1582, 10-1, 4) 가 정상적인 표현
    
### Date와 Calendar의 불편한 역할 분담.
- 특정 시간대의 날짜를 생성한다거나, 년/월/일 같은 단위의 계산은 Date 클래스만으로는 수행하기 어렵다.
    + 날짜 연산을 위해서는 Calendar 객체를 생성하고, 다시 Date 객체를 생성한다.
    + Calendar 클래스는 생성 비용이 비싼 편이기 때문에 비효율적이기도 하다.

## Java의 개선된 날짜, 시간 API 
- 좋은 API의 기준은 오용하기 어려워야 하고, 문서가 없어도 쉽게 사용할 수 있어야한다. 하지만 기존에 있던 API들은 좋은 API기준에 부적합하다.

### Joda-Time
- LocalDate.DateTime 등으로 지역 시간과 시간대가 지정된 시간을 구분.
- plusDays, plusMinutes, plusSeconds 등 단위별로 날짜 연산 메서드를 LocalDate, DateTime 클래스에서 지원한다. 
    + 메서드가 호출되면 새로운 객체를 복사해서 반환한다.(깊은 복사)
- 월의 int 값과 명칭이 일치한다. 1월은 int 값 1이다
- 13월 같이 잘못 된 월이 넘어가면 객체 생성 시점에서 IllegalFieldValueException을 던진다.
- 요일 상수는 일괄되게 사용한다.
- 잘못 된 시간대 ID지정에는 IllegalFieldValueException을 던진다.