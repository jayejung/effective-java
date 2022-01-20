# effective-java
### item #1: 생성자 대신 정적 팩토리 메소드를 고려하라.

<code>com.ijys.effectivejava.item01.Item01Main</code>

#### 장점 5가지
1. 이름을 가질수 있다.
2. 호출될 때마다 인스턴스를 새로 생성하지 않아도 된다.
3. 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다.
4. 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.
5. 정적 팩토리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.
#### 단점
1. 상속을 하려면 public이나 protected 생성자가 필요하니 정적 팩토리 메서드만 제공하면 하위 클래스를 만들 수 없다. (상속보다는 컴포지션을 사용하게 유도하고, immutable하기 위해서 이 제약을 따라야한다는 점에서 장점이 될 수도 있다.)
2. 정적 팩토리 메서드는 프로그래머가 찾기 어럽다. 생성자와 같이 명확하지 않으니 정적 팩토리 메소드 방식 클래스를 인스턴스화할 방법을 찾아야 한다. (API 문서를 잘 정리하거나, 메소드명을 알려진 규약을 따라 짓는 방식 등으로 문제를 해결해야한다.)
   <pre>
    from: 매개변수 1개를 받아서 해당 타입의 인스턴스를 반환하는 형변환 메서드
      <code>Date d = Date.from(instant);</code>
    
    of: 여러 매개변수를 받아 적합한 타입의 인스턴스를 반환하는 집계 메소드  
      <code>Set&lt;Rank&gt; faceCards = EnumSet.of(JACK, QUEEN, KING);</code>
    
    valueOf: from과 of의 더 자세한 버전  
      <code>BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);</code>
    
    instance or getInstance: (매개변수를 받는다면) 매개변수로 명시한 인스턴스를 반환하지만, 같은 인스턴스임을 보장하지는 않는다.  
      <code>StackWalker luke = StackWalker.getInstance(options);</code>
    
    create or newInstance: instance 혹은 getInstance와 같지만, 매번 새로운 인스턴스를 생성해 반환함을 보장한다.  
      <code>Object newArray = Array.newInstance(classObject, arrayLen);</code>
    
    get{Type}: getInstance와 같으나, 생성할 클래스(FileStore)가 아닌 다른 클래스(Files)에 팩터리 메서드를 정의할 때 쓴다. "Type"은 팩토리 메소드가 반환할 객체의 타입이다.  
      <code>FileStore fs = Files.getFileStore(path);</code>
    
    new{Type}: newInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩토리 메소드를 정의할 때 쓴다. "Type"은 팩토리 메소드가 반환할 객체의 타입이다.  
      <code>BufferedReader br = Files.newBufferedReader(path);</code>
    
    type: getType과 newType의 간결한 버전  
      <code>List&lt;Compliant&gt; litany = Collections.list(legacyLitany);</code>
   </pre>
### item #2: 생성자에 매개변수가 많다면 빌더를 고려하라.

<code>com.ijys.effectivejava.item02.Item02Main</code>

1. telescoping constructor pattern (점층적 생성자 패턴)  
   <code> com.ijys.effectivejava.item02.NutritionFactsTelescoping</code>
   * 필요한 경우의 수에 해당하는 constructor를 생성. 모든 생성자에서 멤버 셋팅을 직접하지 않고, 파라메터가 많은 생성자를 chaining 하여 호출.
   * 매개변수 개수가 많아지면 클라이언트 코드를 작성하거나 일기 어려움.
   * 매개변수 순서가 바뀌어도 컴파일러가 인지 못함.
   
2. javaBeans Pattern  
   <code> com.ijys.effectivejava.item02.NutritionFactsBeans</code>
   * 매개변수가 없는 생성자로 객체를 만든 후, setter로 값을 설정.
   * 객체 생성을 위해서 메소드를 여러개 호출해야함.
   * 객체가 완전하게 생성되기 전까지 consistency가 무너진 상태.
   * 객체를 immutable로 만들 수 없음.
   * 위의 담점(consistency/immutable 관련)을 해결하기 위해서 객체가 완성된 후에 객체를 수동으로 freezing하여, freezing되기 전에는 사용하지 못하게 하기도 하지만, 
   다루기 어려운 방법이고 컴파일러가 보증할 수 없어 unchecked exception에 약하다.

3. Builder pattern #1  
   <code> com.ijys.effectivejava.item02.NutritionFacts</code>
   * NutritionFactsBuilder는 immutalbe하고, 모든 매개변수의 기본값들을 한곳에 모아두었음.
   * 세터들은 빌더 자신을 반환하므로 연쇄적으로 호출가능함.(fluent API or method chaining)
   * _NutritionFacts nutritionFactsWithCalories = new NutritionFacts.NutritionFactsBuilder(100, 2).calories(0).build();_ 와 같은 클라이언트 코드는 쓰기 쉽고 읽기도 쉬움.
   * 빌더 패턴은 (파이썬, 스칼라에 있는) 명명된 선택적 매개변수(named optional parameters)를 흉내 낸 것이다.
   * (유효성 검사 코드는 생략되었지만) 잘못된 매개변수를 최대한 일찍 발견하려면 빌더의 생성자와 메서드에서 입력 매개변수를 검사하고,
   build 메소드가 호출하는 생성자에서 여러 매개변수에 걸친 불변식(invariant)를 검사해야 하고 잘 못된 점이 발견되면 어땐 매개변수가 잘못 되었는지를 자세히 알려주는 메세지를 담아
   IllegalArgumentException을 던지면 됨.  

4. Builder pattern #2 (계층적으로 설계된 클래스)   
<code> com.ijys.effectivejava.item02.pasta.*</code>
   * 각 계층의 클래스에 관련 빌더를 멤버로 정의해야함. 추상 클래스는 추상 빌더를, 구체 클래스(concrete class)는 구체 빌더를 갖게 해야함. Pizza class는  피자의 다양한 종류를 표현하는 계층구조의 루트에 놓으니 추상 클래스다.
   * 각 하위 클래스의 빌더가 정의한 build 메서드는 해당하는 구체 하위 클래스를 반환하도록 선언한다. NyPizza.Builder는 NyPizza를 반환하고, Calzone.Builder는 Calzone를 반환.
   (하위 클래스의 메소드가 상위 클래스 메서드가 정의한 반환 타입이 아닌, 그 하위 타입을 반환하는 기능을 공변 반환 타이핑(covariant return typing)이라고 함.) 이 기능을 이용하면 클라이언트가 형변환에 신경 쓰지 않고도 빌더를 사용할 수 있음.
#### 단점
   * 객체를 만들려면, 그에 앞서 빌더부터 만들어야함. 빌더 생성 비용이 크지는 않지만, 성능에 민감한 상황이라면 문제가 될 수 있음.
   * Telescoping Constructor Pattern 보다는 코드가 장황해서 매개변수가 4개 이상되어야 값어치를 함.
   * 하지만, API는 시간이 지날 수록 매개변수가 많아지는 경향이 있음.

### item #3: private 생성자나 열거 타입으로 싱글턴임을 보증하라.

<code>com.ijys.effectivejava.item03.Item03Main</code>
* stateless 상태의 객체, 설계상 유일해야하는 객체의 경우에는 싱글톤으로...  
* 싱글톤을 만드는 방법은 일반적으로 2가지.  
  * 2가지 모두 생성자는 private으로 감춰두고 유일한 instance에 접근할 수 있는 수단으로 public static 멤버를 하나 마련함.
  1. public static 멤버가 final인 경우.  
     <code>com.ijys.effectivejava.item03.Elvis01</code>  
  public, protected 생성자가 없으므로, Elvis01 class가 초기화 될때 만들어진 인스턴스가 전체 시스템에서 하나뿐임을 보장함.  
  AccessibleObject.setAccessible을 사용해 private method를 호출할 수 있으나, 이런 공격을 방어하려면 생성자에서 두 번째 객체가 생성하려고 할때 예외를 던지면 됨.  

  2. 정적 팩토리 메소드를 public static 멤버로 제공.  
     <code>com.ijys.effectivejava.item03.Elvis02</code>  
  Elvis02.getInstance는 항상 같은 객체의 참조를 반환하므로 제2의 Elvis02 인스턴스를 만들지 않음.(reflection을 이용한 예외는 똑같이 발생)  
  Elvis01 방식(1. public static멤버가 final인 경우)의 장점은 해당 클래스 싱글톤임이 API에 명백하게 드러남. public static 필드가 final이니 절대로 다른 객체를 참조 할 수 없음. 두 번째 장점은 간결함.  
  한편, 두 번째 방식(Elvis02: 2. 정적 팩토리 메소드를 public static 멤버로 제공)의 첫 번째 장점은 (마음이 바뀌면) API를 바꾸지 않고 싱글톤이 아니게 변경할 수 있음. 유일한 인스턴스를 반환하던 팩토리 메소드가 호출하는 쓰레드 별로 다른 인스턴스를 생성하여 반환하게 변경할 수 있음.  
  두 번째 장점은 원한다면 정적 팩토리를 제네릭 싱글톤 팩토리로 만들 수 있다는 점임(item30). 세 번째 장점은 정적 팩토리의 메소드 참조를 공급자(supplier)로 사용할 수 있다는 점이다. 가령 Elvis02.getInstance를 Supplier<Elvis02>로 사용하는 식이다(item 43, 44). 이러한 장점들이 굳이 필요하지 않다면 public 필드 방식이 좋다.
  
  * 위의 2가지 방법중 하나로 생성된 싱글톤이 보장된 객체를  serialize하고 unserializa하면 새로운 객체가 생성되어서 싱글톤 상황이 깨짐. 
  멤버 변수를 모두 transient로 선언하고, readResolve 메소드를 구현해서 싱글톤을 유지할 수 할 수 있음. (item 89)  
  <code>com.ijys.effectivejava.item03.SerializableMember</code>
* 위의 2가지와 다른 방법은 원소가 하나인 열거형으로 선언하게 되면 싱글톤이 유지됨.  
   <code>com.ijys.effectivejava.item03.Elvis03</code>
   * public 필드 방식과 비슷하지만, 더 간결하고, 추가 노력없이 직렬화 할 수 있고, 복잡한 직렬화 상황이나 리플렉션 공격에도 제2의 인스턴스가 생기는 일을 완벽하게 막아준다.  
   * 조금 부자연스럽게 보일수 있으나, 대부분의 상황에서 원소가 하나뿐인 열거 타입이 싱글톤을 만드는 가장 좋은 방법이다. 단, 만들려는 싱글톤이 Enum외의 클래스를 상속해야 한다면 이 방법은 사용할 수 없음(Enum이 다른 인터페이스를 구현하도록 선언할 수는 있음).

### item #4: 인스턴스화를 막으려거든 private 생성자를 사용하라
<code>com.ijys.effectivejava.item04.ImpossibleInstantiationUtils</code>
* private 생성자와 생성자에서 AssertionError을 thrown하므로, 인스턴스화를 막는다.
* 주석을 꼭 포함해서 생성자를 만들지 못함을 noti한다.

### item #5: 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라
<code>com.ijys.effectivejava.item05.SpellChecker</code>
* 많은 클래스가 하나 이상의 자원에 의존한다. 가령 맞춤법 검사기는 사전(dictionary)에 의존하는데, 이런 클래스를 정적 유틸리티 클래스(아이템4)로 구현한 모습을 쉽게 볼수 있다.
```java
public class SpellChecker {
    private static final Lexicon dictionary = ...;
    private SpellChecker() {} // 객체 생성 방지
    ...
}
```
* 비슷하게 아래와 같이 싱글턴(item03)으로 구현하는 경우도 흔하다.
```java
public class SpellChecker {
    private static final Lexicon dictionary = ...;
    private SpellChecker(...) {}
    public static SpellChecker INSTANCE = new SpellChecker(...);
    ...
}
```
* 두 방식 모두 사전을 하나만 사용한다고 가정한다는 점에서 그리 훌륭하지 않음. SpellChecker가 여러 사전을 사용할 수 있도록 만들어보자. 간단하게 dictionary 필드에서 final 한정자를 제거하고 다른 사전으로 교체하는 메서드를 추가할 수도 있지만,
아쉽게도 이 방식은 어색하고 오류를 내기 쉬우며 멀티스레드 환경에서는 쓸 수 없다. <b>사용하는 자원에 따라 동작이 달라지는 클래스에는 정적 유틸리티 클래스나 싱글턴 방식이 적합하지 않다.</b>
* 대신 클래스(SpellChecker)가 여러 자원 인스턴스를 지원해야 하며, 클라이언트가 원하는 자원(dictionary)을 사용해야한다. 이 조건을 만족한느 간단한 패턴이 있으니
바로 <b>인스턴스를 생성할 때 생성자에 필요한 자원을 넘겨주는 방식</b>이다. 이는 의존 관계 주입의 한 형태로, 맞춤법 검사기를 생성할 때 의존객체인 사전을 주입하면 된다.

```java
public class SpellChecker {
    private final Lexicon dictionary;

    public SpellChecker(Lexicon dictionary) {
        this.dictionary = Objects.requireNonNull(dictionary);
    }
    ...
}
```
* 이 패턴의 쓸만한 변형으로 생성자에 자원 팩토리를 넘겨주는 방식이 있다. 팩토리란 호출할때마다 특정 타입의 인스턴스를 반복해서 만들어 주는 객체를 말한다. 즉, 팩토리 메소드 패턴을 구현한 것이다.
java 8에서 등장한 Supplier<T> 인터페이스가 팩토리를 표현한 완벽한 예다. Supplier<T>를 입력으로 받는 메소드는 일반거으로 한정적 와일드 카드 타입(bounded wildcard type, 아이템 31)을 사용해 팩토리의 타입 매개 변수를 제한해야한다.
```java
public class SpellCheck {
    private final Lexicon dictionary;
    
    public SpellChecker(Supplier<? extends Lexicon> dictFactory) {
        this.dictionary = dictFactory.get();
    }
}
```
<pre style="white-space: pre-wrap;">
<b>핵심정리</b>
클래스가 내부적으로 하나 이상의 자원에 의존하고, 그 자원이 클래스의 동작에 영향을 준다면 싱글턴과 정적 유틸리티 클래스는 사용하지 않는 것이 좋다.
이 자원들을 클래스가 직접 만들게 해서도 안 된다. 대신 필요한 자원을 (혹은 그 자원을 만들어주는 팩토리를) 생성자에 (혹은 정적 팩토리나 빌더에) 넘겨주자. 
의존 관계 주입이라 하는 이 기법은 클래스의 유연성, 재사용성, 테스트 용이성을 기막하게 개선해준다. 
</pre>

### item #6: 불필요한 객체 생성을 피하라
* 똑같은 기능의 객체를 매번 생성하지 말고 재사용해라. 재상용은 빠르고 세련된다. 특히, 불변 객체(item 17)는 언제든지 재사용 할 수 있다.  
다음은 절대 하지 말아야할 예이다.  
<code>String s = new String("bikini");</code>  
실행될 때마다 String 인스턴스를 새로 만든다. 개선된 버전은 아래와 같다.  
<code>String s = "bikini";</code>  
위의 코드는 새로운 객채를 생성하지 않고 하나의 String 인스턴스를 사용한다.  
* 생성자 대신 정적 팩토리 메소드(item 1)을 제공하는 불변 클래스에서는 정적 팩토리 메서드를 사용해 불필요한 객체 생성을 피할 수 있다. 
예컨대 Boolean(String) 생성자 대신 Boolean.valueOf(String) 팩토리 메소드를 사용하는게 좋다.(이 생성자는 java9에서 deprecated 되었다). 생성자는 호출할 때마다 새로운 객체를 만들지만, 팩터리 메서드는 그렇지 않다.
* 생성 비용이 비싼 객체도 있다. 비싼 객체가 반복적으로 필요하다면 캐싱하여 재사용하기를 권한다.(생성비용이 비싼지는 매번 명확하게 알수는 없다). 
아래의 코드는 주어진 문자열이 유효한 로마숫자인지를 확인하는 정규표현식을 사용한 메소드이다.  
```java
static boolean isRomanNumeral(String s) {
    return s.matches("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
}
```
* 위의 방식의 문제점은 String.matches 메소드를 사용한다는 데 있다. <b>String.matches는 정규표현식으로 문자열 형태를 확인한느 가장 쉬운 방법이지만, 성능이 중요한 상황에서 반복해 사용하기엔 적합하지 않다.</b>
이 메소드가 내부에서 만드는 정규표현식용 Pattern 인스턴스는, 한 번 쓰고 버려져서 곧바로 가비지 컬렉션 대상이 된다. Pattern은 입력받은 정규표현식에 해당하는 유한 상태 머신(finite state machine)을 만들기 때문에 인스턴스 생성 비용이 높다.  
성능을 개선하려면 필요한 정규표현식을 표현하는 (불변인) Pattern인스턴스를 클래스 초기화(정적 초기화) 과정에서 직접 생성해 캐싱해두고, 나중에 isRomanNumeral 메서드가 호출될 때마다 이 인스턴스를 재사용 한다.
```java
public class RomanNumerals {
    private static final Pattern ROMAN = Pattern.compile("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
    
    static boolean isRomanNumeral(String s) {
        return ROMAN.matcher(s).matches();
    }
}
```
* 위의 개선된 방식으로 8자리 숫자로 구성된 문자 126개를 사용해서 테스트해보니 약간 빠른 성능르 보였다.  
<code>com.ijys.effectivejava.item06.Item06Main</code>
* 성능만 좋아진 것이 아니라 코드도 명확해졌다. 개선 전에서는 존재조파 몰랐던 Pattern 인스턴스를 static final 필드로 꺼내어 이름을 지어주어 코드의 의미가 훨씬 잘 드러난다.
* 개선된 isRamonNumeral 방식의 클래스가 초기회된 후 이 메서드를 한 번도 호출하지 않는다면 ROMAN 필드는 쓸데 없이 초기화되는 것이다.
isRomanNumeral 메소드가 처음 호출될 때 필드를 초기화하는 지연 초기화(lazy initialization, item 83)로 불필요한 초기화를 없앨 수는 있지만, 권하지는 않는다. 코드를 복잡하게 만들고 성능은 크게 향상되지 않을 때가 많기 때문이다(item 67).
* 오토박싱(auto boxing)도 불필요한 객체를 만들어내는 다른 예이다. 오토박싱은 프로그래머가 기본 타입과 박싱된 기본 타입을 섞어 쓸때 자동으로 상호 변환해주는 기술이다.
<b>오토박싱은 기본 타입과 그에 대응하는 박싱된 기본 타입의 구분을 흐려주지만, 완전히 없애주는 것은 아니다.</b> 
의미상으로는 특별한 것이 없어보이지만, 성능에서는 큰 차이를 보인다(아이템 61). 다음은 모든 양의 정수의 총합을 구하는 메소드이다.
```java
private static long sum() {
    Long sum = 0;
    for (long i = 0; i <= Integer.MAX_VALUE; i++)
        sum += i;
    
    return sum;
}
```
* sum 변수를 long이 아닌, Long으로 선언되어 있기 때문에 불필요한 Long 인스턴스가 2^31개가 만들어진다.
단순히 sum을 long으로 선언해주면 거의 10배의 성능 향상을 확인할 수 있다. <b>박싱된 기본 타입보다는 기본 타입을 사용하고, 의도치 않은 오토박싱이 숨어들지 않도록 주의하자.</b>
* 이번 아이템을 "객체 생성은 비싸니 피해야 한다"로 오해하면 안된다. 특이 요즘 JVM에서의 garbage collector의 상당히 최적화 되어 있어서 크게 부담이 없다.
만약 프로그램의 명확성, 간결성, 기능을 위해서 객체를 추가로 생성한느 것이라면 일반적로 좋은 일이다.

### item #7: 다 쓴 객체 참조를 해제하라
* 가비지컬랙터가 있다하더라도 메모리 관리를 전혀 하지 않아도 된다는 의미는 아니다. 아래의 코드를 보자.
```java
public class Stack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if (size == 0)
            throw new EmptyStackException();
        return elements[--size];
    }

    /**
     * 원소를 위한 공간을 적어도 하나 이상 확보한다.
     * 배열 크기를 늘려야 할 때마다 대략 두 배씩 늘린다.
     */
    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}
```
* 특별한 문제가 없어 보이는 코드처럼 보이지만, pop을 하고 size를 줄이는데 actual size를 줄이는게 아니라 index와 같은 역할을 한다.
즉, stack의 요소를 pop하고 size가 줄어들때, pop한 요소는 여전히 다 쓴 참조(obsolete reference)를 가지고 있다. 
여기서 다 쓴 참조는 문자 그대로 다시 쓰지 않을 참조를 뜻 한다. (활성 영역의 외부에 위치한 모든 요소에 대한 참조가 해당된다.)
활성 영역은 index가 size보다 작은 원소들로 구성된다.
* 가비지 컬렉션 언어에서는 (의도치 않게 객체를 살려두는) 메모리 누수를 찾기가 아주 까다롭다. 객체 참조 하나를 살려두면 가비지 컬렉터는 그 객체뿐 아니라 그 객체가 참조하는 모든 객체(그리고 또 그 객체들이 참조하는 모든 객체..)를 회수해가지 못한다.
그래서, 단 몇 개의 객체가 매우 많은 객체를 회수되지 못하게 할 수 있고 잠재적으로 성능에 악영향을 줄 수 있다.
* 해법은 간단하다. 해당 참조를 다 썼을 때 null 처리(참조 해제)하면 된다. 예시의 stack 클래스에서는 각 원소의 참조가 더 이상 필요 없어지는 시점은 stack에서 pop할때다.
아래는 개선된 pop 메소드이다.
```java
    public Object pop() {
        if (size == 0)
            throw new EmptyStackException();
        Object result = elements[--size];
        elements[size] = null; // 다 쓴 참조 해제
        return result;
    }
```
* 다 쓴 참조를 null 처리하면 다른 이점도 찾아온다. 만약 null 처리한 참조를 실수로 사용하려 하면 프로그램은 즉시 NullPointerException을 던지며 종료된다(미리 null 처리하지 않았다면 아무 내색 없이 무언가 잘못된 일을 수행할 것이다.)
프로그램 오류는 가능한 한 조기에 발견하는게 좋다.
* 이런 문제를 해결하기 위해서 모든 객체를 다 쓰자마자 일일이 null 처리하는 데 혈안이 되기도 한다. 하지만 그럴 필요도 없고 바람직하지도 않다. 프로그램을 필요 이상으로 지저분하게 만들 뿐이다.
<b>객체 참조를 null 처리하는 일은 예외적인 경우여야 한다.</b> 다 쓴 참조를 해제하는 가장 좋은 방법은 그 참조를 담은 변수를 유효 범위(scope)밖으로 밀어내는 것이다.
변수의 범위를 최소가 되게 정의 했다면(아이템 57)이 일은 자연스럽게 이뤄진다.
* 그러면, null 처리는 언제 해야 할까? Stack 클래스는 왜 메모리 누수에 취약한 걸까? 바로 스택이 자기 메모리를 직접 관리하기 때문이다.
이 스택은 (객체 자체가 아니라 객체 참조를 담는) elements 배열로 저장소 풀을 만들어 원소들을 관리한다.
* 배열의 활성 영역에 속한 원소들이 사용되고 비활성 영역은 쓰이지 않는다. 문제는 가비지 컬렉터는 이 사실을 알 길이 없다는 데 있다.
가비지 컬렉터가 보기에는 비활성 영역에서 참조하는 객체도 똑같이 유효한 객체를 더는 쓰지 않을 것임을 가비지 컬렉터에 알려야 한다.
* 일반적으로 <b>자기 메모리를 직접 관리하는 클래스라면 프로그래머는 항시 메모리 누수에 주의해야한다.</b> 원소를 다 사용한 즉시 그 원소가 참조한 객체들을 다 null 처리 해줘야한다.
* 캐시 역시 메모리 누수를 일으키는 주범이다. 객체 참조를 캐시에 넣고 나서, 이 사실을 까맣게 잊은 채 그 객체를 다 쓴 뒤로도 한참을 그냥 놔두는 일을 자주 접할 수 있다.
해법은 여러가지다. WeakHashMap을 사용하면 다 쓴 엔트리는 그 즉시 자동으로 제거 될 것이다. 단 WeakHashMap은 이러한 상황에서만 유용하다.
* 캐시를 만들때 보통은 캐시 엔트리의 유효기간을 정확히 정의하기 어렵기 때문에 시간이 지날수록 엔트리를 삭제 대상으로 만드는 방식을 흔히 사용한다.
* 이런 방식에서는 삭제 대상 엔트리를 이따금 청소해줘야 한다. (ScheduledThreadPoolExecutor 같은) 백그라운드 스레드를 활용하거나 캐시에 새 엔트리를 추가할때 부수작업으로 수행하는 방법이 있다.
LinkedHashMap은 removeEldestEntry 메서드를 사용해서 후자의 방식으로 처리한다. 더 복잡한 캐시를 만들고 싶다면 java.lang.ref 패키지를 직접 활용해야 할 것이다.
* 메모리 누수의 세 번째 주범은  바로 리스너(listener) 혹은 콜백(callback)이라 부르는 것이다. 클라이언트가 콜백을 등록만 하고 명확히 해지하지 않는다면, 
먼가 조치해주지 않는 한 콜백은 계속 쌓여갈 것이다. 이럴때 콜백을 약한 참조(weak reference)로 저장하면 가비지 컬렉터가 즉시 수거해간다. 예를 들어 WeakHashMap에 키로 저장하면 된다.
<pre>
<b>핵심 정리</b>  
메모리 누수는 겉으로 잘 드러 나지 않아 시스템에 수년간 잠복하는 사례도 있다. 이런 누수는 철저한 코드 리뷰나 힙 프로파일러 같은 디버깅 도구를 동원해야만 발견되기도 한다.
그래서 이런 종류의 문제는 예방법을 익혀두는 것이 매우 중요하다.  

APM으로 봐도 되고... 없으면 jmap 으로 하면 될 듯
</pre>

### item #08: finalizer와 cleaner 사용을 피하라
