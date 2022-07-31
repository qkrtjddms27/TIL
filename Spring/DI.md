# DI

## 생성자 주입, Setter 주입

```java
public class Controller {
    private Service service;
    
    public void setService(Service service) {
        this.service = service;
    }
    
    public void callService() {
        service.doSomething();
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        
        controller.setService(new serviceImpl1());
        controller.setService(new serviceImpl2());
        
        controller.setService(new Service() {
            @Override
            public void domSomething(){
                System.out.println("Anonymous clas is doing something");
            }
        });
        
        Controller.setService(
                () -> System.out.println("do Something")
        );
        
        controller.callService();
    }
}
```

- Controller 클래스의 callService 기능은 Service 타입의 객체에 의존하고 있다.
- main 함수에서 Controller 클래스를 사용하는 것은 수정자 메소드인 setService() 에 Service 인터페이스의 구현체만 넘겨주면 된다.
- 어떤 구현체든, 구현체가 어떤방법으로 구현되든, SErvice 인터페이스를 구현하기만 하면 된다.
- **Service 의 구현체를 주입해주지 않아도 Controller 객체는 사용이 가능하다.**
    + NPE를 발생한다.
    
## 생성자 주입

```java
public class Controller {
    private Service service;
    
    public Controller(Service service) {
        this.service = service;
    }
    
    public void callService() {
        service.doSomething();
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        Controller controller1 = new Controller(new ServiceImpl());
        
        controller.callService();
    }
}
```

- null을 주입하지 않는 한 NPE를 발생시키지 않는다.
- 의존관계 주입을 하지 않는 경우에는 Controller 객체를 생성할 수 없다.
    + 의존관계에 대한 내용을 외부로 노출시킴으로써 컴파일 타입에 오류를 잡을 수 있다.
- final 선언이 가능하다.
- 순환참조 시 컴파일단계에서 에러를 알 수 있다.