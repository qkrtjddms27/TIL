# Builder

## 점층적 생성자 패턴의 단점
생성자와 정적 팩터리를 사용하여 객체를 생성했을때의 문제는 선택적 매개변수가 많을때 확정이 용이하지 않다는 문제가 있다.
- 코드를 읽을 때 각 값의 의미가 무엇인지 헷갈림.
- 실수로 매개변수의 순서를 바꿔도 컴파일러단계에서는 알아차리기 힘들다.

### 점층적 생성자 패턴 코드
```java
public class NutritionFacts {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;
    
    public NutritionFacts(int servingsSize, int servings) {
        this(servingSize, servings, 0);
    }
    
    public NutritionFacts(int servingSize, int servings, int calories) {
        this(servingSize, servings, calories, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat) {
        this(servingSize, servings, calories, fat, 0);
    }
    
    public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium) {
        this(servingSize, servings, calories, fat, sodium, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }
}
```

## 자바빈즈 패턴(Setter를 이용한 생성)
```java
public class NutritionFacts {
    private int servingSize;
    private int servings;
    private int calories;
    private int fat;
    private int sodium;
    private int carbohydrate;
    
    // setter
}
```

### 자바빈즈 패턴의 문제점
- 객체 하나를 만들려면 메서드를 여러 개 호출해야함.
- 객체가 생성되기 전까지는 일관성이 무너진 상태에 놓이게 된다.
    + 생성자로 생성하면 매개변수들이 유효한지를 생성자에서만 확인하면 일관성을 유지할 수 있었다.
- **자바빈즈 패턴에서는 클래스를 불변으로 만들 수 없다.**

## Builder 패턴
- lombok의 @Builder 을 활용해도된다.
```java
public class NutritionFacts {
    private int servingSize;
    private int servings;
    private int calories;
    private int fat;
    private int sodium;
    private int carbohydrate;

    public static class Builder {
        private final int servingSize;
        private final int servings;
        
        private int calories        = 0;
        private int fat             = 0;
        private int sodium          = 0;
        private int carbohydrate    = 0;
    }
    
    public Builder(int servingSize, int servings) {
        this.servingsSize   = servingSize;
        this.servings       = servings;
    }
    
    public Bilder calories(int val) {calories = val; return this;}
    ...
    public NutritionFacts build() {
        return new NutritionFacts(this);
    }
    
    private NutritionFacts(Builder builder) {
        servingSize = builder.servings;
        servings    = builder.servings;
        calories    = builder.calories;
        fat         = builder.fat;
        sodium      = builder.sodium;
        carbohydrate= builder.carbohydrate;
    }
}
```
- 빌더 패턴은 명명된 선택적 매개변수를 흉내 낸 것이다.
- 불변성을 지킬 수 있고, 확장성이 열려있다.
- 앞선 두 패턴의 장점을 가져왔다.

