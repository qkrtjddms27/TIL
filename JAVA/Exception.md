## Exception

### 예외처리의 목적
- 프로그램의 비정상 종료를 막고, 정상적인 실행 상태를 유지하는 것.

### Checked Exception
- try-catch 로 잡아서 예외처리를 하거나 상위 메소드로 넘겨줘야함.
- 컴파일 단계에서 체크

### Unchecked Exception (RuntimeException)
- 예외 처리 필수 아님
    + 컴파일 단계에서 찾을 수 없음.
    + 런타임 단계에서 체크
    + 프로그래머의 실수에 의해서 발생할 수 있는 예외.

### 예외 발생시키기

- 예외처리가 되어야 할 부분에 예외처리가 되어 있지 않아 컴파일 단계에서 에러가 발생.
```java
class ExceptionEx {
    public static void main(String[] args) {
        throw new Exception(); // 예외 발생
    }
}
```

### 예외처리
```java
class ExceptionEx {
    public static void main(String[] args) {
        try {
            throw new Exception();
        } catch (Exception e) {
            System.out.print("Exception 발생");
        }
        System.out.print("여기도달");
    }
}
```
- 결과
> Exception 발생
> 여기도달

### RuntimeException 주의사항
- 컴파일 단계에서 Exception이 발생하지 않는다.
```java
class ExceptionEx {
    public static void main(String[] args) {
        throw new RuntimeException(); // 예외 발생 
    }
}
```

### 사용자 정의 예외 만들기

#### 사용자 정의 Exception 클래스
```java
class MyException extends Exception {
    private final int ERR_CODE;
    
    MyException(String msg, int errCode){
        super(msg);
        ERR_CODE=errCode;
    }
    
    MyException(String msg) { // 생성자
        this(msg, 100);
    }
    
    public int getERR_CODE() {
        return ERR_CODE;
    }
}
```
#### 사용자 정의 Sample main 메서드
        
```java
class NewExceptionTest {
  public static void main(String args[]) {
      try {
          startInstall();
          copyFiles();
      } catch (SpaceException e)	{
          System.out.println("에러 메시지 : " + e.getMessage());
          e.printStackTrace();
          System.out.println("공간을 확보한 후에 다시 설치하시기 바랍니다.");
      } catch (MemoryException me)	{
          System.out.println("에러 메시지 : " + me.getMessage());
          me.printStackTrace();
          System.gc(); //  Garbage Collection을 수행하여 메모리를 늘려준다.
        // System.out.println("다시 설치를 시도하세요.");
      } finally {
          deleteTempFiles(); // 프로그램 설치에 사용된 임시파일들을 삭제한다.
      } // try의 끝
  } // main의 끝
  
  static void startInstall() throws SpaceException, MemoryException {
      if(!enoughSpace()) 
          throw new SpaceException("설치할 공간이 부족합니다.");
      if (!enoughMemory()) 
          throw new MemoryException("메모리가 부족합니다.");
  } // startInstall메서드의 끝

  static void copyFiles() { /* 파일들을 복사하는 코드를 적는다. */ }
  static void deleteTempFiles() { /* 임시파일들을 삭제하는 코드를 적는다.*/}
  static boolean enoughSpace()   {
    // 설치하는데 필요한 공간이 있는지 확인하는 코드를 적는다.
    return false;
  }
  static boolean enoughMemory() {
    // 설치하는데 필요한 메모리공간이 있는지 확인하는 코드를 적는다.
    return true;
  }
} // ExceptionTest클래스의 끝

class SpaceException extends Exception { 
    SpaceException(String msg) {
        super(msg);
    }
}

class MemoryException extends Exception {
    MemoryException(String msg) {
        super(msg);
    }
}

```

### 예외던지기

---
## Spring Exception
### ExceptionHandler

#### @ControllerAdvice
- 컨트롤러를 보조하는 클래스.
- 컨트롤러에서 클래스에서 Exception이 발생되면 처리.
- @ControllerAdvice(com.freeboard01.api.BoardApi)와 같이 특정한 클래스만 명시하는 것도 가능.

#### @ResponseBody
- ExceptionHandler 는 컨트롤러 혹은 restController 가 아니다.
- 응답값은 컨트롤러처럼 String 혹인 ModelAndView만 가능.
- String이나 ModelAndView를 이용해 에러 코드 혹은 메세지만 반환하거나 Map으로 반환하는 것.

#### @ExceptionHandler
- @ControllerAdvice 이 명시된 클래스 내부 메소드에 사용한다.
