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
* 메모리 누수의 세 번째 주범은 바로 리스너(listener) 혹은 콜백(callback)이라 부르는 것이다. 클라이언트가 콜백을 등록만 하고 명확히 해지하지 않는다면,
  먼가 조치해주지 않는 한 콜백은 계속 쌓여갈 것이다. 이럴때 콜백을 약한 참조(weak reference)로 저장하면 가비지 컬렉터가 즉시 수거해간다. 예를 들어 WeakHashMap에 키로 저장하면 된다.

<pre>
<b>핵심 정리</b>  
메모리 누수는 겉으로 잘 드러 나지 않아 시스템에 수년간 잠복하는 사례도 있다. 이런 누수는 철저한 코드 리뷰나 힙 프로파일러 같은 디버깅 도구를 동원해야만 발견되기도 한다.
그래서 이런 종류의 문제는 예방법을 익혀두는 것이 매우 중요하다.  

APM으로 봐도 되고... 없으면 jmap 으로 하면 될 듯
</pre>

### item #08: finalizer와 cleaner 사용을 피하라

* 가비지컬렉터가 하게 해라. finalizer와 cleaner가 객체를 회수하는것을 보장하지 않음. 오히려 가비지 컬렉터를 방해한다.
* finalizer와 cleaner로는 제때 실행되어야 하는 작업은 절대 할 수 없다. 실행되는 타이밍뿐 아니라, 실행 여부조차도 보장 받지 못한다. 접근할 수 없는 일부 객체에 딸린 종료 작업을 전혀 수행하지
  못한 채 프로그램이 종료 될 수도 있다. 따라서 프로그램 생애주기와 상관없는, <b>상태를 영구적으로 수정하는 작업에는 절대 finalizer와 cleaner에 의존해서는 안 된다.</b>
  예를 들어, DB같은 공유자원의 영구 락(lock)해제를 finalizer와 cleaner에 맡겨 놓으면 분산 시스템 전체가 서서히 멈출 것아다.
* try-with-resources(item 09)보다 50배 더 느린 테스트 결과가 있었다.
* 걍 쓰지말고, 만약에 파일아니 쓰레드 등 종료해야할 자원을 담고 있는 객체의 클래스에서 finalize나 cleaner를 대신해줄 묘안은 <b>autoClosable을 구현해</b>주고, 클라이언트에서 인스턴스를
  다 쓰고 나면 close 메소드를 호출하면 된다(일반적으로 예외가 발생해도 제대로 종료되도록 try-with-resources)를 사용해야한다. 아이템 9). 각 인스턴스는 자신이 닫혔는지를 추적하는 것이 좋다.
  다시 말새, close메소드에서 이 객체가 더 이상 유효하지 않음을 필드에 기록하고, 다른 메소드는 이 필드를 검사해서 객체가 닫힌 후에 불렀다면 IllegalStateException을 던지는 것이다.
* 그렇다면 finalizer와 cleaner는 어디에 사용해야할까?
    * 하나는 자원의 소유자가 close메소드를 호출하지 않는 것에 대비한 안전망 역할이다. cleaner와 finalizer가 즉시 (혹은 끝까지) 호출되리라는 보장은 없지만, 클라이언트가 하지 않은 자원회수를
      늦게라도 해주는 것이 안 하는 것보다는 낫다. 이런 안전망 역할의 finalizer를 작성할 때는 그럴 만한 값어치가 있는지 심사숙고하자. 자바 라이브러리의 일부 클래스는 안전망 역할의 finalizer를
      제공한다.
      <b>FileInputStream, FileOutputStream, ThreadPoolExecutor</b>가 대표적이다.
    * 두번째는 네이티브 피어(native peer)와 연결된 객체에서다. 네이티브 피어란 일반 자바 객체가 네이티브 메서드를 통해 기능을 위임한 네이티브 객체를 말한다. 네이티브 피어는 자바 객체가 아니니
      가비지 컬렉터는 그 존재를 알지 못한다. 그 결과 자바 피어를 회수할 때 네이티브 객체까지 회수하지 못한다. cleaner와 finalizer가 처리하기에 적당한 작업니다. 단, 성능 저하를 감당할 수
      있고 네이티브 피어가 심각한 자원을 가지고 있지 않을 때만 해당된다. 성능 저하를 감당할 수 있고 네이티브 피어가 심각한 자원을 가지고 있지 않을 때에만 해당된다. 성능 저하를 감당할 수 없거나 네이티브
      피어가 사용하는 자원을 즉시 회수해야 한다면 앞서 설명한 close 메서드를 사용해야 한다.
    * cleaner는 사용하기 조금 까다롭다. 아래의 Room class를 보면, 방(room)자원을 수거하기 전에 반드시 청소(clean)해야 한다고 가정해보자. Room 클래스는 AutoCloseable을
      구현한다. 사실 방 자원을 수거할때 자동으로 cleaner를 사용할지 말지는 내부 구현 방식에 대한 문제다. 즉, finalizer와 달리 cleaner는 클래스의 public API에 나타나지 않는다는
      이야기이다.
  ```java
    public class Room implements AutoCloseable {
  
        private static final Cleaner cleaner = Cleaner.create();

        // 청소가 필요한 자원. 절대 Room을 참조해서는 안 된다!
        private static class State implements Runnable {
            int numJunkPiles;   // 방(Room)안의 쓰레기 수

            State(int numJunkPiles) {
                this.numJunkPiles = numJunkPiles;
            }

            // close 메서드나 cleaner가 호출한다.
            @Override
            public void run() {
                System.out.println("방 청소");
                numJunkPiles = 0;
            }
        }

        // 방의 상태. cleanable과 공유한다.
        private final State state;

        // cleanable 객체. 수거 대상이 되면 방을 청소한다.
        private final Cleaner.Cleanable cleanable;

        public Room(int numJunkPiles) {
            state = new State(numJunkPiles);
            cleanable = cleaner.register(this, state);
        }
  
        @Override
        public void close() throws Exception {
            cleanable.clean();
        }
    }
  ```
    * 위의 코드가 좀 더 현실적이으로 만들려면 numJunkPiles 필드가 네이티브 피어를 가리키는 포인터를 담는 final long 변수여야 한다. 아래는 Room 클래스를 사용하는 예시이다.
  ```java
    public class AdultMain {
        public static void main(String[] args) throws Exception {
            try (Room myRoom = new Room(7)) {
                System.out.println("안녕~");
            }
        }
    }
  ```

  ```java
    public class TeenagerMain {
        public static void main(String[] args) {
            new Room(99);
            System.out.println("아무렴");
            // System.gc();
        }
    }
  ```
    * AdultMain은 try-with-resources를 사용하여 잘 작성되었다. 프로그램이 끝날때(System.exit()), Room자원이 회수되면서 Room의 close() 메소드가 호출되고 있다.
      하지만, TeenagerMain은 다른 결과를 보이고 있다. 즉, 앞에서 cleaner의 동작이 예측할 수 없다는 상황이다. cleaner의 명세에는 아래와 같이 쓰여 있다.
  > System.exit을 호출할 때의 cleaner 동작은 구현하기 나름이다. 청소가 이뤄질지는 보장하지 않는다.
    * 명세에서는 명시하지 않았지만 일반적인 프로그램 종료에서도 마찮가지다. TeenagerMain의 main 메소드에 System.gc()를 추가하는 것으로 종료전에 Room의 close() 메소드 호출을 할
      수 있지만, 항상 호출되지 않을 수도 있다.

### item #9: try-finally 보다는 try-with-resources를 사용하라

* 자바 라이브러리에는 close 메소드를 호출해 직접 닫아줘야하는 자원이 많다. (eg. InputSteam, OutputStream, java.sql.Connection 등..)
* 자원 닫기는 클라이언트가 놓치기 쉬워서 성능 이슈로 이어지기 쉽다. 이런한 문제의 안전망으로 finalizer를 사용하기도 하지만 finalizer는 그리 믿을만하지 못하다(item #08).

```java
    static String firstLineOfFile(String path)throws IOException{
        BufferedReader br=new BufferedReader(new FileReader(path));
        try{
        return br.readLine();
        }finally{
        br.close();
        }
        }
```

* 그런데 만약 자원을 하나 더 사용 한다면?

```java
    static void copy(String src,String dst)throws IOException{
        InputStream in=new FileInputStream(src);
        try{
        OutputStream out=new FileOutputStream(dst);
        try{
        byte[]buf=new byte[BUFFER_SIZE];
        int n;
        while((n=in.read(buf))>=0)
        out.write(buf,0,n);
        }finally{
        out.close();
        }
        }finally{
        in.close();
        }
        }
```

* 너무 복잡하게 되었다.
* 여튼.. 첫번째 예시 코드를 보면 심각한 결점이 있다. try블록, finally 블록 모두에서 exception이 발생할 수 있다. 첫 번째 예시에서 br.readLine()에서 예외가 발생하고, 같은 이유로
  close()에서도 예외가 발생할 것이다. 이런 상황이라면, 두 번째 예외가 첫번째 예외를 집어삼켜 버린다. 이러면 stack trace에서 첫 번째 예외에 관한 정보는 남지 않아서 실제 시스템에서 디버깅을
  어렵게 한다(일반적으로 첫 번째 예외를 보고 싶을 것 이므로).
* 이러한 문제들은 자바7부터 지원하는 try-with-resources 덕에 모두 해결되었다. 이 구조를 사용하려면 해당 자원이 AutoCloseable 인터페이스를 구현해야한다. 단순히 void를 리턴하는
  close 메서드 하나만 정의한 인터페이스이다. 자바 라이브러리와 서드파티 라이브러리들의 수많은 클래스와 인터페이스가 이미 AutoCloseable을 구현하거나 확장해뒀다. 닫아야하는 자원을 뜻하는 클래스를
  작성한다면 AutoCloseable을 반드시 구현하기 바란다.
* 다음은 try-with-resources를 사용해서 위의 코드들을 개선한 예시이다.

```java
    static String firstLineOfFile(String path)throws IOException{
        try(BufferedReader br=new BufferedReader(new FileReader(path))){
        return br.readLine();
        }
        }
```

```java
    static void copy(String src,String dst)throws IOException{
        try(IntputStream in=new FileInputStream(src);
        OutputStream out=new FileOutputStream(dst)){
        byte[]buf=new byte[BUFFER_SIZE];
        int n;
        while((n=in.read(buf))>=0)
        out.write(buf,0,n);
        }
        }
```

* try-with-resources 버전이 짧고 읽기 쉬운 코드를 만들 수 있고, 예외를 진단하기도 훨씬 좋다. firstLineOfFile 메소드를 보면, readline과 close 양쪽에서 예외가 발생하면,
  close에서 발생한 예외는 숨겨지고 readline에서 발생한 예외가 기록된다. 이 처럼, 실전에서는 프로그래머에게 보여줄 예외 하나만 보존되고 여러 개의 다른 예외가 숨겨질 수도 있다. 이렇게 숨겨진 예외들도
  그냥 버려지지는 않고, stack trace 내역에 '숨겨졌다(suppressed)'는 꼬리표를 달고 출력된다. 또한, java7에서 Throwable에 추가된 getSuppressed 메서드를 이용하면 프로그램
  코드에서 suppressed된 stack trace 내역을 가져올 수 있다.
* try-with-resources에서도 try-finally 처럼 catch절을 사용할 수 있다. catch절 덕분에 try문을 더 중첩하지 않고도 다수의 예외를 처리할 수 있다. 다음 코드에서는
  firstLineOfFile 메소드를 살짝 수정하여 파일을 열거나 데이터를 읽지 못했을 때 예외를 던지는 대신 기본값을 반환하도록 해봤다.

```java
    static String firstLineOfFile(String path,String defaultVal){
        try(BufferedReader br=new BufferedReader(new FileReader(path))){
        return br.readLine();
        }catch(IOException ex){
        return defaultVal;
        }
        }
```

### item #10: equals는 일반 규약을 지켜 재정의하라.

* equals 메소드는 재정의하기 쉬워 보이나 곳곳에 함정이 있어서 자칫하면 끔찍한 결과를 초래한다. 끔찍한 결과를 회피하는 방법은 아예 재정의하지 않는 것이다. 그냥 두면 그 클래스의 인스턴스는 오직 자기
  자신과만 같게 된다. 아래의 상황중 하나에 해당된다면 재정의하지 않는 것이 최선이다.
    * <b>각 인스턴스가 본질적으로 고유하다.</b> 값을 표현하는게 아니라 동작하는 개체를 표현하는 클래스가 여기 해당한다. Thread가 좋은 예로, Object의 equals 메소드는 이러한 클래스에 딱
      맞게 구현되었다.
    * <b>인스턴스의 '논리적 동치성(logical equality)'을 검사할 일이 없다.</b> 예컨대 java.util.regex.Pattern는 equals를 재정의해서 두 Pattern의 인스턴스가
      같은 정규표현식을 나타내는지를 검사하는, 즉 논리적 동치성을 검사하는 방법도 있다. 하지만 설계자는 클라이언트가 이 방식을 원하지 않거나 애초에 필요하지 않다고 판단할 수도 있다. 설계자가 후자로
      판단했다면 Object의 기본 equals만으로 해결된다.
    * <b>상위 클래스에서 재정의한 equals가 하위 클래스에도 딱 들어맞는다.</b> 예컨대 대부분의 set 구현체는 AbstractSet이 구현한 equals를 상속받아 쓰고, List 구현체들은
      AbstractList로 부터, Map구현체들은 AbstractMap으로부터 상속받아 그대로 쓴다.
    * <b>클래스가 private이거나 package-private이고 equals 메서드를 호출할 일이 없다.</b> 여러분이 위험을 철저히 회피하는 스타일이라 equals가 실수라도 호출되는 걸 막고 싶다면
      다음처럼 구현해두자.
  ```java
    @Override
    public boolean equals(Object o) {
        throw new AssertionError(); // 호출 금지!
    }
  ```
* 그렇다면 equals를 재정의해야 할 때는 언제 일까? 객체 식별성(object identity: 두 객체가 물리적으로 같은가)이 아니라 논리적 동치성을 확인해야 하는데, 상위 클래스의 equals가 논리적
  동치성을 비교하도록 재정의되지 않았을 때다. 주로 값 클래스들이 여기에 해당된다. 값 클래스란 Integer와 String처럼 값을 표현하는 클래스를 말한다. 두 값 객체를 equals로 비교하는 프로그래머는
  객체가 같은지가 아니라 값이 같은지를 알고 싶어 할 것이다. equals가 논리적 동치성을 확인하도록 재정의해두면, 그 인스턴스는 값을 비교하길 원하는 프로그래머의 기대에 부응함은 물론 Map의 키와 Set의
  원소로 사용할 수 있게 된다.
* 값 클래스라 해도, 값이 같은 인스턴스가 둘 이상 만들어지지 않음을 보장하는 인스턴스 통제 클래스(item #01)라면 equals를 재정의하지 않아도 된다. Enum(item #34)도 여기에 해당된다. 이런
  클래스에서는 논리적으로 같은 인스턴스가 2개 이상 만들어지지 않으니 논리적 동치성과 객체 식별성이 사실상 똑같은 의미가 된다.
* equals 메서드를 재정의할 때는 반드시 일반 규약을 따라야 한다. 다음은 Object명세에 적힌 규약이다.
  > equals 메서드는 동치관계(equivalence relation)를 구현하며, 다음을 만족한다.
  > * <b>반사성(reflexivity)</b>: null이 아닌 모든 참조 값 x에 대해, x.equals(x)는 true이다.
  > * <b>대칭성(symmetry)</b>: null이 아닌 모든 참조 값 x, y에 대해, x.equals(y)가 true이면 y.equals(x)도 true이다.
  > * <b>추이성(transitivity)</b>: null이 아닌 모든 참조 값 x, y에 대해, x.equals(y)가 true이고, y.equals(z)도 true이면 x.equals(z)도 true다.
  > * <b>일관성(consistency)</b>: null이 아닌 모든 참조 값 x, y에 대해, x.equals(y)를 반복해서 호출하면 항상 true를 반환하거나 항상 false를 반환한다.
  > * <b>null-아님</b>: null이 아닌 모든 참조 값 x에 대해, x.equals(null)은 false다.
* Object 명세에서 말하는 동치관계란 무엇일까? 쉽게 말해, 집합을 서로 같은 원소들로 이뤄진 부분집합으로 나누는 연산이다. 이 부분집합을 동치류(equivalence class: 동치 클래스)라 한다.
  equals메소드가 쓸모 있으려면 모든 원소가 같은 동치류에 속한 어떤 원소와도 서로 교환할 수 있어야 한다.
* 이제 동치관계를 만족시키기 위한 다섯 요건을 하나씩 살펴보자.
    * <b>반사성</b>은 단순히 말하면 객체는 자기자신과 항상 같아야한다는 뜻이다.
    * <b>대칭성</b>은 두 객체는 서로에 대한 동치 여부에 똑같이 답해야한다는 뜻이다. 반사성 요건과 달리 대칭성 요건은 어기기 쉽다. 대소문자를 구별하지 않는 문자열을 구현한 아래의 클래스를 예로 보자.
      이 클래스에서 toString 메소드는 원본 문자열의 대소문자를 그대로 돌려주지만 equals에서는 대소문자를 무시한다.
      ```java
      public class CaseInsensitiveString {
          private final String s;
  
          public CaseInsensitiveString(String s) {
              this.s = Objects.requireNonNull(s);
          }
  
          // 대칭성 위배
          @Override
          public boolean equals(Object o) {
              if (o instanceof CaseInsensitiveString) {
                  return  s.equalsIgnoreCase(((CaseInsensitiveString) o).s);
              }
  
              if (o instanceof String) {  // 한방향으로 작동한다.
                  return s.equalsIgnoreCase((String) o);
              }
  
              return false;
          }
      }
      ```
        * CaseInsensitiveString의 equals는 순진하게 String에 대해서도 문자열 비교를 한다. 그래서 대칭성이 깨진 것이다. CaseInsensitiveString의 equals
          메소드는 String으로 전달되는 Object를 equalsIgnoreCase를 통해서 판단하겠지만, String의 equals method는 문자열 그대로 비교를 할 것이다.
      ```java
          CaseInsensitiveString cis = new CaseInsensitiveString("polish");
          String s = "polish";
          cis.equals(s); // true
          s.equals(cis); // false, 대칭성이 깨졌다.
      ```
        * 이번에는 CaseInsensitiveString을 컬렉션에 넣어보자.
      ```java
          List<CaseInsensitiveString> list = new ArrayList<>();
          list.add(cis);
          list.contains(s); // false, ArrayList에서 동치성 여부를 s.equals(cis)로 검사한다.
      ```
        * OpenJDK에서는 false를 리턴하지만, JDK 버전에 따라서는 true를 반환하거나 런타임 예외를 던질 수도 있다.
          <b>equals 규약을 어기면 그 객체를 사용한느 다른 객체들이 어떻게 반응할지 알 수 없다.</b>
        * 이 문제를 해결하려면 CaseInsensitiveString의 equals를 String과도 연동하겠다는 허황된 꿈을 버려야 한다. 그 결과 equals는 다음처럼 간단한 모습으로 바뀐다.
      ```java
          @Override
          public boolean equals(Object o) {
              return o instanceof CaseInsensitiveString &&
                  ((CaseInsensitiveString) o).s.equalsIgnoreCase(s);
          }
      ```
    * <b>추이성</b>은 첫 번째 개체와 두 번째 객체가 같고, 두 번째 객체와 세 번째 객체가 같다면, 첫 번째 객체와 세 번째 객체가 같아야 한다는 뜻이다. 이 요건도 쉽게 어길 수 있다. 상위 클래스에는
      없는 새로운 필드를 하위 클래스에 추가하는 상황을 생각해보자. equals에 영향을 주는 정보를 추가한 것이다. 간단한 2차원에서의 점을 표현하는 클래스를 예로 들어 보자.
      ```java
        public class Point {
            private final int x;
            private final int y;
    
            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }
    
            @Override
            public boolean equals(Object o) {
                if (!(o instanceof Point))
                    return false;
                Point p = (Point) o;
                return p.x == x && p.y == y;
            }
        }
      ```
        * 이제 이 클래스를 확장해서 점에 색상을 더해보자.
      ```java
      public class ColorPoint extends Point {
       private final Color color;
    
       public ColorPoint(int x, int y, Color color) {
           super(x, y);
           this.color = color;
       }
      }
      ```
        * equals 메소드는 어떻게 해야 할까? 그대로 둔다면 Point의 구현이 상속되어 색상 정보는 무시한 채 비교를 수행한다. equals 규약을 어긴 것은 아니지만, 중요한 정보를 빼고 비교를 하는
          상황이다. 다음 코드 처럼 비교대상이 다른 colorPoint이고 위치와 색상이 같을 때만 true를 반환하는 equals를 생각해보자.
      ```java
      @Override
      public boolean equals(Object o) {
       if (!(o instanceof ColorPoint))
           return false;
       return super.equals(o) && ((ColorPoint) o).color == color;
      }
      ```
        * 위의 코드는 대칭성이 깨진다. 그럼, ColorPoint.equals가 Point와 비교할 때는 생상을 무시하도록 하면 해결될까?
      ```java
      @Override
      public boolean equals(Object o) {
       if (!(o instanceof Point))
           return false;
    
       // o가 일반 Point면 색상을 무시하고 비교한다.
       if (!(o instanceof ColorPoint))
           return o.equals(this);
    
       return super.equals(o) && ((ColorPoint) o).color == color;
      }
      ```
        * 위의 코드는 대칭성은 지켜주지만 추이성이 깨져버린다.
      ```java
      ColorPoint p1 = new ColorPoint(1, 2, Color.RED);
      Point p2 = new Point(1, 2);
      ColorPoint p3 = new ColorPoint(1, 2, Color.BLUE);
      ```
        * 위의 코드로 선언된 객체들도 추이성에 위배된다(p1.equals(p3) -> false). 또한 이 방식은 무한 재귀에 빠질 위험도 있다. Point의 또 다른 하위 클래스로 smallPoint를
          만들고 equals는 같은 방식으로 구현된다면 stackOverflowError를 일으킨다.
        * 그렇다면 해결 방법은?  
          사실 이러한 현상은 모든 객체 지향 언어의 동치관계에서 나타나는 근본적인 문제다. <b>구체 클래스를 확장해 새로운 값을 추가하면 equals 규약을 만족시킬 방법은 존재하지 않는다.</b>
          객체 지향적 추상화의 이점을 포기하지 않는 한은 말이다.
        * 이 말을 얼핏, equals 안의 instanceof 검사를 getClass 검사로 바꾸면 규약도 지키고 값도 추가하면서 구체 클래스를 상속할 수 있다는 뜻으로 들린다.
      ```java
      @Override
      public boolean equals(Object o) {
          if (o == null || o.getClass() != getClass())
              return false;
          Point p = (Point) o;
          return p.x == x && p.y == y;
      } 
      ```
        * 위의 코드는 리스코프 치환 원칙(LSP)에 위배된다.
        * 위의 equals는 같은 구현 클래스의 객체와 비교할 때만 true를 반환한다. 괜찮아 보이지만 실제로 활용할 수는 없다. Point의 하위 클래스는 정의상 여전히 Point이므로 어디서든
          Point로써 활용될 수 있어야 한다. 그런데, 이 방식에서는 그렇지 못한다. 예를 들어 주어진 점이 (반지름이 1인) 단위 원 안에 있는지를 판별하는 메서드가 필요하다고 해보자. 다음은 이를
          구현한 코드다.
      ```java
      // 단위 원 안의 모든 점을 포함하도록 unitCircle을 초기화한다.
      private static final Set<Point> unitCircle = Set.of(
         new Point(1, 0), new Point(0, 1),
         new Point(-1, 0), new Point(0, -1));
      
      private static boolean onUnitCircle(Point p) {
          return unitCircle.contains(p);
      }
      ```
        * 좋은 코드는 아니지만, 어쨌든 동작하는 코드이다. 이제 값을 추가하지 않는 방식으로 Point를 확장하겠다. 만들어진 인스턴스의 개수를 생성자에서 세보도록 하자.
      ```java
      public class CounterPoint extends Point {
          private static final AtomicInteger counter = new AtomicInteger();
          
          public CounterPoint(int x, int y) {
              super(x, y);
              counter.incrementAndGet();
          }
      
          public static int numberCreated() { return counter.get(); }
      }
      ```
        * 리스코프 치환의 원칙(Liskov Substitution Principle)에 따르면, 어떤 타입에 중요한 속성이라면 그 하위 타입에서도 마찬가지로 중요하다. 따라서 그 타입의 모든 메소드가 하위
          타입에서도 똑같이 잘 작동해야 한다. 이는 앞서의 "Point의 하위 클래스는 정의상 여전히 Point이므로 어디서든 Point로써 활용될 수 있어야 한다"를 표현한 말이다. 그런데,
          CounterPoint의 인스턴스를 onUnitCircle 메서드에 넘기면 어떻게 될까? Point 클래스의 equals를 getClass를 사용해 작성했다면 onUnitCircle은 false를
          반환할 것이다. 반면, Pointer equals를 instanceof 기반으로 올바로 구현했다면 CounterPoint 인스턴스를 건네줘도 onUnitCircle 메소드가 제대로 동작할 것아다.
        * 구현 클래스의 하위 클래스에서 값을 추가할 방법은 없지만 괜찮은 우회 방법이 하나 있다. "상속 대신 컴포지션을 사용하라"는 아이템 #18의 조언을 따르면 된다. Point를 상속하는 대신
          Point를 ColorPoint의 private 필드로 두고, ColorPoint와 같은 위치의 일반 Point를 반환하는 뷰(view)메서드(item #6)를 public으로 추가하는 식이다.
      ```java
      public class ColorPoint {
          private final Point point;
          private final Color color;
    
          public ColorPoint(int x, int y, Color color) {
              this.point = new Point(x, y);
              this.color = Objects.requireNonNull(color);
          }
      
          /**
           * 이 ColorPoint의 Point뷰를 반환한다.
           */
          public Point asPoint() {
              return point;
          }
          
          @Override
          public boolean equals(Object o) {
              if (!(o instanceof ColorPoint)) 
                  return false;
              ColorPoint cp = (ColorPoint) o;
              return cp.point.equals(point) && cp.color.equals(color);
          }
      }
      ```
        * 자바 라이브러리에도 구체 클래스를 확장해 값을 추가한 클래스가 종종 있다. 한 가지 예로 java.sql.Timestamp는 java.util.Date를 확장한 후 nanoseconds필드를
          추가했다. 그 결과로 Timestamp의 equals는 대칭성을 위배하며, Date객체와 한 컬렉션에 넣거나 서로 섞어 사용하면 엉뚱하게 동작할 수 있다. 그래서 Timestamp의 API 설명에는
          Date와 섞어 쓸 때의 주의사항을 언급하고 있다. 둘을 명확하게 분리해 사용하는 한 문제될 것은 없지만, 섞이지 않도록 보장해줄 수단은 없다. 자칫 실수하면 디버깅하기 어려운 이상한 오류를 경험할
          수 있으니 주의하자. Timestamp를 이렇게 설계한 것은 실수 니 절대 따라 해서는 안 된다.
      > 추상 클래스의 하위 클래스에서라면 equals 규약을 지키면서도 값을 추가 할 수 있다. "태그 달린 클래스보다는 클래스 계층구조를 활용하라"는 아이템 #23의 조언을 따르는 클래스 계층구조에서는 아주 중요한 사실이다. 예컨대 아무런 값을 갖지 않는 추상 클래스인 Shape을 위에 두고, 이를 확장하여 radius 필드를 추가한 Circle 클래스와, length와 width 필드를 추가한 Rectangle 클래스를 만들 수 있다. 상위 클래스를 직접 인스턴스로 만드는 게 불가능하다면 지금까지 이야기한 문제들은 일어나지 않는다.
    * <b>일관성</b>은 두 객체가 같다면 (어느 하나 혹은 두 객체가 모두 수정되지 않는 한) 앞으로도 영원이 같아야 한다는 뜻이다. 가변 객체는 비교 시점에 따라서 서로 다를 수 있는 반면, 불변객체는
      한번 변함없이 같거다 다르다. 클래스를 작성할때는 불변 클래스로 만드는 게 나을지 심사숙고하자(item #17). 불변클래스로 만들기로 했다면 equals가 한번 같다고 한 객체와는 영원히 같다고
      해야한다.  
      클래스가 불변이든 가변인든 <b>equals의 판단에 신뢰할 수 없는 자원이 끼어들게 해서는 안 된다.</b> 이 제약을 어기면 일관성 조건을 만족시키기가 아주 어렵다. 예를 들면,
      java.net.URL의 equals는 주어진 URL과 매핑된 호스트의 IP 주소를 이용해서 비교한다. 호스트 명을 IP로 변경하려면 DNS lookup을 통해서 변환할텐데 그 결과가 항상 같다고 보장할
      수 없다. 이는 실무에서도 종종 문제를 발생 시킨다. 이런 문제를 피하려면 equals는 항시 메모리에 존재하는 객체만을 사용한 결정적(deterministic) 계산만 수행해야 한다.
    * <b>null-아님</b>으로 표현되는 마지막 요건은 이름처럼 모든 객체가 null이 아니어야 한다는 뜻이다. 수많은 클래스가 다음 코드처럼 입력이 null인지를 확인해 자신을 보호한다.
      ```java
      // 명시적 null 검사 - 필요 없다.
      @Override
      public boolean equals(Object o) {
          if (o == null)
              return false;
          ...
      }
      ```
      이러한 검사는 필요하지 않다. 동치성을 검사하려면 건너받은 객체를 적절히 형변환 후 필수 필드들의 값을 알아내야 한다. 그러기 위해서 형변환에 앞서서 instanceof 연산자로 입력 객체가 올바른
      타입인지 검사해야 한다.
      ```java
      // 묵시적 null 검사 - 이쪽이 낫다.
      @Override
      public boolean equals(Object o) {
          if (!(o instanceof MyType))
              return false;
          MyType mt = (MyType) o;
          ...
      }
      ```
      equals가 타입을 확인하지 않으면 잘못된 타입이 인수로 주어졌을 때 ClassCassException을 던저서 일반 규약을 위배하게 된다. 근데 instanceof는 피연산자가 null이면 false를
      반환한다. 따라서 입력이 null이면 타입 확인 단계에서 false를 반환하기 때문에 null 검사를 명시적으로 할 필요가 없다.
* 지금끼지의 내용을 종합해서 양질의 equals 메소드 구현 방법을 단계별로 정리해보자.

> 1. <b>== 연산자를 사용해 입력이 자기 자진의 참조인지 확인한다.</b> 자기 자신이면 true를 반환한다. 이는 단순한 성능 최적화용으로, 비교 작업이 복잡한 상황일 때 효과가 매우 크다.
> 2. <b>instanceof 연산자로 입력이 올바른 타입인지 확인한다.</b> 그렇지 않다면 false를 반환한다. 이때 올바른 타입은 equals가 정의된 클래스인 것이 보통이지만,
     > 가끔은 그 클래스가 구현한 특정 인터페이스가 될 수도 있다. 어떤 인터페이스는 자신을 구현한 (서로 다른) 클래스끼리도 비교할 수 있도록 equals 규약을 수정하기도 한다.
     > 이런 인터페이스를 구현한 클래스라면 equals규약을 수정하기도 한다. 이런 인터페이스를 구현한 클래스라면 equals에서 (클래스가 아닌) 해당 인터페이스를 사용해야 한다.
     > Set, List, Map, Map.Entry 등의 컬렉션 인터페이스들이 여기 해당한다.
> 3. <b>입력을 올바른 타입으로 형변환한다.</b> 앞서 2번에서 instanceof 검사를 했기 때문에 이 단계는 100% 만족 한다.
> 4. <b>입력 객체와 자기 자신의 대응되는 '핵심' 필드들이 모두 일치하는지 하나씩 검사한다.</b> 모들 필드가 일치하면 true를, 하나라도 다르면 false를 반환한다.
     > 2단계에서 인터페이스를 사용했다면 입력의 필드 값을 가져올 때도 그 인터페이스의 메서드를 사용해야 한다. 타입이 클래스라면 (접근 권한에 따라) 해당 필드에 직접 접근 할 수도 있다.

* float와 double을 제외한 기본 타입 필드는 == 연산자로 비교하고, 참조 타입 필드는 각각의 equals 메소드로, float와 double 필드는 각각 정적 메서드인 Float.compare(
  float, float)와 Double.compare(double, double)로 비교한다. float와 double을 특별 취급하는 이유는 Float.NaN, -0.0f, 특수한 부동소수 값등을 다뤄야 하기
  때문이다. 자세한 설명은 [JLS 15.21.1]이나 Float.equals의 API 문서를 참고하자. Float.equals와 Double.equals 메서드를 대신 사용할 수도 있지만, 이 메서드들은
  오토박싱을 수반할 수 있으니 성능상 좋지 않다. 배열 필드는 원소 각각을 앞서의 지침대로 비교한다. 배열의 모슨 원소가 핵심 필드라면 Arrays.equals 메소드들 중 하나를 사용하자.
* 때로는 null도 정상값으로 취급하는 참조 타입 필드도 있다. 이런 필드는 정적 메소드인 Objects.equals(Object, Object)로 비교해 NPE 발생을 예방하자.
* 앞서의 CaseInsensitiveString 예처럼 비교하기가 아주 복잡한 필드를 가진 클래스도 있다. 이럴 때는 그 필드의 표준형(canonical form)을 저장해둔 후 표준형 끼리 비교하면 훨씬
  경제적이다. 이 기법은 특히 불변 클래스(item #17)에 제격이다. 가변 객체라면 값이 바뀔 때마다 표준형을 최신 상태로 갱신해줘야 한다.
* 어떤 필드를 먼저 배교하느냐가 equals의 성능을 좌우하기도 한다. 최상의 성능을 바란다면 다를 가능성이 더 크거나 비교하는 비용이 싼 (혹은 둘 다 해당하는) 필드를 먼저 비교하자. 동기화용 lock 필드
  같이 객체의 논리적 상태와 관련 없는 필드는 비교하면 안된다. 핵심 필드로부터 계산해낼 수 있는 파생 필드 역시 굳이 비교할 필요는 없지만, 파생 필드를 비교하는 쪽이 더 빠를 때도 있다. 파생 필드가 객체
  전체의 상태를 대표하는 상황이 그렇다. 예컨대 자신의 영역을 캐시해두는 Polygon클래스가 있다고 해보자. 그렇다면 모든 변과 정점을 일일이 비교할 필요 없이 캐시해둔 영역만 비교하면 결과를 곧바로 알 수
  있다.
* <b>equals를 다 구현했다면 세가지만 자문해 보자. 대칭적인가 추이성이 있는가? 일관적인가?</b> 자문에서 끝내지 말고 단위 테스트를 작성해 돌려보자. 단, equals 메소드를 AutoValue를 이용해
  작성했다면 테스트를 생략해도 안심할 수 있다. 세 요건중 하나라도 실패한다면 원인을 찾아서 고치자. 물론 나머지 요건인 반사성과 null-아님 요건도 만족해야 하지만, 이 둘이 문제가 되는 경우는 별로 없다.
* 다음은 이상의 비법에 따라 작성해본 PhoneNumber 클래스용 equals 메서드다.
  ```java
    public final class PhoneNumber {
        private final short areaCode, prefix, lineNum;

        public PhoneNumber(short areaCode, short prefix, short lineNum) {
            this.areaCode = rangeCheck(areaCode, 999, "지역코드");
            this.prefix = rangeCheck(prefix, 999, "프리픽스");
            this.lineNum = rangeCheck(lineNum, 999, "가입자 번호");
        }

        private static short rangeCheck(int val, int max, String arg) {
            if (val < 0 || val > max) {
                throw new IllegalArgumentException(arg + ": " + val);
            }
            return (short) val;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof PhoneNumber))
                return false;
            PhoneNumber pn = (PhoneNumber) o;
            return pn.lineNum == this.lineNum && pn.prefix == this.prefix && pn.areaCode == this.areaCode;
        }
    }
  ```
* 드디어 마지막 주의 사항이다.
    * <b>equals를 재정의할 때는 hashCode도 반드시 재정의하자</b>(item #11).
    * <b>너무 복잡하게 해결하려 들지 말자.</b> 필드들의 동치성만 검사해도 equals 규약을 어렵지 않게 지킬 수 있다. File 객체의 symbolic link같은 alias를 비교하는 것은 과하다.
    * <b>Object</b> 외의 타입을 매개변수로 받는 equals 메소드는 절대 선언하지 말자. 많은 사람이 아래와 같이 equals 메소드를 작성하고 문제의 원인을 찾는 실수를 한다.
      ```java
      // 잘못된 예 - 입력 타입은 반드시 Object 여야 한다.
      public boolean equals(MyType o) {
          ...
      }
      ```
      위의 예시는 Object.equals를 Override한게 아니다. 매개변수가 다르므로 Overloading(다중 정의 - item #52)된 것이다. 이 메소드는 하위 클래스에서 @Override
      어노테이션이 긍정 오류(false positive: 거짓 양성)을 내게하고 보안 측면에서도 잘못된 정보를 준다. @Override 어노테이션을 일관되게 명시적으로 사용하게 되면 이런 실수를 예방 할 수
      있다(item #40). 예를 들어 위의 예시 메소드에 @Override 어노테이션을 사용했다면 컴파일 오류가 발생할 것아다.
    * equals(hashCode도 마찬가지)를 작성하고 테스트하는 일은 지루하고 이를 테스트하는 코드도 항상 뻔하다. 다행이 이러한 작업을 대신하는 오픈소스가 있다. 구글에서 만든 AutoValue가 이
      메소드들을 알아서 작성해주며, 직접 작성하는 것과 근본적으로 동일한 코드를 만들어 준다.

### item #11: equals를 재정의하려거든 hashCode도 재정의하라.

* equals를 재정의한 클래스 모두에서 hashcode도 재정의해야 한다.  
  재정의 하지 않으면 hashCode 일반 귀을 어기게 되어 해당 클래스의 인스턴으를 HashMap, HashSet 같은 컬렉션의 원소로 사용할 때 문제를 일으킬 것이다.
* 다음은 Object 명세에서 발췌한 규약이다.
  > * equals 비교에 사용되는 정보가 변경되지 않았다면, 애플리케이션이 실행되는 동안 그 객체의 hashCode 메서드는몇 번을 호출해도 일관되게 항상 같은 값을 반환해야 한다.  
      단, 애플리케이션을 다시 실행한다면 이 값이 달라져도 상관없다.
  > * equals(Object)가 두 객체를 같다고 판단했다면, 두 객체의 hashCode는 똑같은 값을 반환해야 한다.
  > * equals(Object)가 두 객체를 다르다고 판단했더라도, 두 객체의 hashCode가 서로 다른 값을 반환할 필요는 없다. 단, 다른 객체에 대해서는 다른 값을 반환해야 해시테이블의 성능이 좋아진다.
* <b>hashCode 재정의를 잘못했을 때 크게 문제가 되는 조항은 두 번째이다. 즉, 논리적으로 같은 객체는 같은 해시코드를 반환해야 한다.</b>  
  예를 들어 item10에서 사용했던 PhoneNumber 클래스의 인스턴스를 HashMap의 원소로 사용한다고 해보자.
    ```java
    Map<PhoneNumber, String> m = new HashMap<>();
    m.put(new PhoneNumber((short) 707, (short) 867, (short) 5309), "제니");
  
    m.get(new PhoneNumber((short) 707, (short) 867, (short) 5309));
    ```
  세번째 라인에서 "제니"가 반납될 듯 하지만, 실제로는 null을 반환한다. PhoneNumber 클래스는 hashCode를 재정의 하지 않았고, Object 클래스의 hashCode가 사용되었다.
  논리적인 동치인 두 객체가 서로 다른 해시코드를 가지게 되어 위의 두 번째 규약을 지키지 못하였다.
* 좋은 해시 함수(성능이 좋은)는 위의 3번째 규약에 맞게 서로 다른 인스턴스는 다른 해시코드를 반환해야한다. 그렇지 않으면, 모든 객체의 해시값은 해시테이블의 버킷하나에 linked list 형태로 담기게 되고,
  그 결과 복잡도 O(1)인 해시테이블의 O(n)으로 느려지게 되어 객체가 많아지면 도저히 사용 할 수가 없다.  
  이상적인 해시 함수는 주어진 (서로 다른) 인스턴스들을 32비트 정수 범위에 균일하게 분배해야 한다. 이를 완벽하게 실현하기는 어려울 수 있으나, 비슷하게 만들기는 그다지 어렵지 않다. 다음이 좋은
  hashCode를 작성하는 간단한 요령이다.

1. int 변수 result를 선언하고 값 c로 초기화 한다. 이때 c는 해당 객체의 첫번째 핵심 필드를 단계 2.a 방식으로 계산한 해시코드다 (여기서 핵심필드는 equals 비교에 사용되는 필드를 말한다.
   item10 참조).
2. 해당 객체의 나머지 핵심 필드 f 각각에 대해 다음 작업을 수행한다.  
   a. 해당 필드의 해시코드 c를 계산한다.  
   i. 기본 타입 필드라면, <i>Type.hashCode(f)</i>를 수행한다. 여기서 <i>Type</i>은 해당 기본 타입의 박싱 클래스다.  
   ii. 참조 타입 필드면서 이 클래스의 equals 메서드가 이 필드의 equals를 재귀적으로 호출해 비교한다면, 이 필드의 hashCode를 재귀적으로 호출한다.
   계산이 더 복잡해질 것 같으면, 이 필드의 표준형(canonical representation)을 만들어 그 표준형의 hashCode를 호출한다. 필드의 값이 null이면 0을 사용한다(다른 상수도 괜찮지만
   전통적으로 0을 사용한다).  
   iii. 필드가 배열이라면, 핵심 원소 각각을 별도 필드처럼 다룬다. 이상의 규칙을 재귀적으로 적용해 각 핵심 원소의 해시코드를 계산한 다음, 단계 2.b 방식으로 갱신한다. 배열에 핵심 원소가 하나도 없다면
   단순히 상수(0을 추천한다)를 사용한다.
   모든 원소가 핵심 원소라면 Arrays.hashCode를 사용한다.  
   b. 단계 2.a에서 계산한 해시코드 c로 result를 갱신한다. 코드로는 다음과 같다.  
   result = 31 * result + c;
3. result를 반환한다.

* hashCode를 다 구현했다면 이 메서드가 동치인 인스턴스에 대한 검증을 unit 테스트를 통해서 해보자 (equals, hashCode 메소드를 AutoValue로 생성했다면 건너뛰어도 좋다).
* 파생 필드는 해시코드 계산에서 제외해도 된다. 즉, 다른 필드로부터 계산해 낼 수 있는 필드는 모두 무시해도 된다. 즉, 다른 필드로부터 게산해 낼 수 있는 필드는 모두 무시해도 된다. 또한, equals
  메소드에서 사용하지 않는 필드는 <b>'반드시'</b> 제외해야 한다. 그렇게 하지 않으면 두번째 규약을 어길 가능성이 높다.
* 단계 2.b의 곱셈 31 * result는 곱하는 필드의 순서에 따라서 result 값이 달라지게 된다. String의 hashCode를 곱셈없이 구현한다면 모든 anagram(구성하는 알파벳이 동일하고 순서가
  다른)은 모두 같은 해시를 가진다.
  곱하는 숫자를 31로 정한 이유는 홀수이며 소수(prime)이기 때문이다. 소수를 곱하는건 명확하지 않지만 전통적으로 그리 해왔다. blah~ blah~
* 위의 방식을 PhoneNumber의 hashCode에 적용해보면 아래와 같다.
    ```java
    @Override
    public int hashCode() {
        int result = Short.hashCode(this.areaCode);
        result = 31 * result + Short.hashCode(this.prefix);
        result = 31 * result + Short.hashCode(this.lineNum);

        return result;
    }
    ```
* 위의 구현은 간단하 계산만 수행한다. 그 과정에 비결정적(undeterministic)인 요소가 없어서 동치인 PhoneNumber 인스턴스들은 같은 해시 코드를 가질 것이 확실하다. 또한 코드의 내용도
  단순하고, 빠르며 서로 다른 전화번호를 다른 해시 버킷으로 훌륭하게 분배해 준다.
  단순하지만, 완벽하고 훌륭한 코드이다. 단, 해시 충돌이 더욱 적은 방법을 꼭 사용해야 한다면 구아바(Guava)의 com.google.common.hash.Hashing을 참고하자.
* Object 클래스는 임의의 개수만큼의 객체를 받아 해시코드를 계산해주는 정적메소드인 hash를 제공한다. <b>하지만 속도가 느리기 때문에 성능이 민감하지 않은 상황에서만 사용하자.</b>
    ```java
    @Override
    public int hashCode() {
        return Objects.hash(this.areaCode, this.prefix, this.lineNum);
    }
    ```
* 클래스가 불변이고 해시코드를 계산하는 비용이 크다면, 매번 새로 계산하지 말고 캐싱하는 방식을 고려해야한다(member variable로 가지고 있음된다).
  인스턴스가 만들어질 때 해시코드를 계산할 수도 있지만, 해시코드가 늘 필요한 상황이 아니라면 hashCode가 처음 불릴때 계산하는 지연 초기화(lazy initialization) 전략도 사용 가능하다.
  지연 초기화를 사용하기 위해서는 클래스를 쓰레드 안전하게 만들도록 신경써야 한다(item 83).
    ```java
    private int hashCode;
  
    @Override
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            result = Short.hashCode(this.areaCode);
            result = 31 * result + Short.hashCode(this.prefix);
            result = 31 * result + Short.hashCode(this.lineNum);
            hashCode = result;
        }
        return result;
    }
    ```
* <b>성능을 높인답시고 해시코드를 계산할 때 핵심 필드를 생략해서는 안 된다.</b> 속도야 빨라지겠지만, 해시 품질이 나빠져 해시테이블의 성능을 심각하게 떨어뜨릴수 있다.
* <b>다시 말하지만, equals를 재정의 했다면, hashCode도 꼭 반드시 재정의 해야한다. 재정의하기 위해서는 위에서 얘기한 3가지 규칙을 준수해야한다.
  이러한 작업이 따분한 일이기도 하지만 item 10의 AutoValue 프레임워크를 사용하면 멋진 equals와 hashCode를 자동으로 만들어 준다. IDE들도 이런 기능을 일부 제공한다.</b>

### item #12: toString을 항상 재정의하라.

* Object의 toString 메소드는 직접 작성한 클래스의 원하는 문자열을 반환하는 경우는 드물다(PhoneNumber@adfaf 처럼 Object의 해시를 반환).
* toString의 일반 규약에 따라 '간결하면서 사람이 읽기 쉬운 형태의 유익한 정보'를 반환해야하고, '모든 하위 클래스에서 이 메서드를 재정의' 해야한다.
* equals와 hashCode 규약(item 10, 11)만큼 중요하지는 않지만, toString을 잘 구현한 클래스는 사용하기에 훨씬 즐겁고, 그 클래스를 사용한 시스템은 디버깅하기 쉽다.
* 실전에서 toString은 그 객체가 가진 주요 정보 모두를 반환하는게 좋다. 하지만, 객체가 너무 거대하거나 객체의 상태가 문자열로 표현하기에 적합하지 않다면 무리가 있다.
  이런 상황이라면 <b>"맨하튼 거주자 전화번호부(총 1487536개)"</b>나 <b>"Thread[main,5,main]"</b> 같은 요약 정보를 담아야 한다.
* 아래는 주요 정보가 toString으로 남기지 않는 경우의 적절한 예시이다.
    ```java
    Assertion failure: expected {abc, 123}, but was {abc, 123}.
    // 단언 실패: 예상값 {abc, 123}, 실제값 {abc, 123}.
    ```
* toString을 구현할때는 포맷을 정확하게 확정할지 말지 결정해야한다. toString의 반환값을 그대로 입출력에 사용하거나 CSV파일 처럼 사람이 읽을 수 있는 데이터 객체로 저장할 수도 있다.
  포맷을 명시하기로 했다면, 명시한 포멧에 맞는 문자열과 객체를 상호 전환할 수 있는 정적 팩토리나 생성자를 함께 제공해 주면 좋다. 자바의 기본 값 클래스가 따르는 방식이기도 하다(BigInteger,
  BigDecimal).
* 단점도 있다. 포맷을 한번 명시하면 (그 클래스가 많이 사용된다면) 평생 그 포맷에 얽매이게 된다. 향후 릴리즈에서 해당 포맷을 변경한다면 많은 부분에 수정이 필요하게 될 것이다.
  반대로 포맷을 명시하지 않았다면 향후 릴리즈에서 정보를 더 넣거나 모맷을 개선할 수 있는 유연성을 얻게 된다.
* 포맷을 명시하든 아니든 명확하게 의도를 밝혀야 한다. 포맷을 명시하려면 아주 정확하게 해야 한다. item #11의 PhoneNumber 클래스용 toString 메소드를 보자.
    ```java
        /**
         * 이 전화번호의 문자열 표현을 반환한다.
         * 이 문자열은 "XXX-YYY-ZZZZ" 형태의 12글자로 구성된다.
         * XXX는 지역 코드, YYY는 프리픽스, ZZZZ는 가입자 번호다.
         * 각각의 대문자는 10진수 문자 하나를 나타낸다.
         *
         * 전화번호의 각 부분의 값이 너무 작아서 자릿수를 채울 수 없다면,
         * 앞에서 부터 0을 채워 나간다. 예컨데 가입자 번호가 123이라면
         * 전화번호의 마자막 네 문자는 "0123" 이 된다.
         */
        @Override
        public String toString() {
            return String.format("%03d-%03d-%04d", areaCode, prefix, lineNum);
        }
    ```
  포맷을 명시하지 않기로 했다면 다음 처럼 작성할 수 있을 것이다.
    ```java
        /**
         * 이 약물에 대한 대략적인 설명을 반환한다.
         * 다음은 이 설명의 일반적인 형태이나,
         * 상세 형식은 정해지지 않았으며 향후 변경될 수 있다.
         * 
         * "[약물 #9: 유형=사람, 냄새=테레빈유, 겉모습=먹물]"
         */
        @Override
        public String toString() {....}
    ```
* 포맷 명시 여부와 상관없이 <b>toString이 반환한 값에 포함된 정보를 얻어올 수 있는 API를 제공하다.</b>
  예컨대, PhoneNumber 클래스는 지역 코드, 프리픽스, 가입자 번호용 접근자를 제공해야 한다. 그렇지 않으면 이 정보가 필요한 프로그래머는 toString 반환값을 파싱할 수 밖에 없다.
  성능이 나빠지고 필요하지도 않은 작업이며 게다가 향후 포맷을 바꾸면 시스템이 망가지는 결과를 초래할 수 있다.
* 정적 유틸리티 클래스(item #4)는 toString을 제공할 이유가 없다. 또한, 대부분의 열거 타입(item #34)도 자바가 이미 완벽한 toString을 제공하니 따로 재정의하지 않아도 된다.
  하지만, 하위 클래스들이 공유해야 할 문자열 표현이 있는 추상클래스라면 toString을 재정의해줘야 한다. 예컨대 대다수의 컬렉션 구현체는 추상 컬렉션 클래스들의 toString 메서드를 상속해 쓴다.
* item 10에서 소개한 구글의 AutoValue 프레임워크는 toString도 생성해준다. AutoValue는 각 필드의 내용을 멋지게 나타내 주기는 하지만 클래스의 '의미'까지 파악하지는 못한다.
  예컨대 앞서의 PhoneNumber 클래스 toString은 자동 생성에 적합하지 않고(전화번호는 표준 체계를 따라야 한다) Potion 클래스는 적합하다.
  비록 자동 생성에 적합하지는 않더라도 객체의 값에 관해 아무것도 알려주지 않는 Object의 toString 보다는 자동 생성된 toString이 훨씬 유용하다.
* <b>모든 구현 클래스에서 Object의 toString을 재정의 하자. 상위 클래스에서 이미 알맞게 재정의한 경우는 예외다. toString은 해당 객체에 관한 명확하고 유용한 정보를 읽기 좋은 형태로 반환해야
  한다.</b>

### item #13: clone 재정의는 주의해서 진행하라

* 가급적이면 하지 말자.
* 완벽하게 clone이 동작하는것은 배열뿐. 그외에는 대부분 직접 구현을 해야하는데 심혈을 기울여야 한다.
  그니깐.. 걍 하지 않는게....
* 어쩔 수 없이 꼭 필요하다면 복사생성자와 복사 팩터리라는 더 나은 객체 복사 방식을 제공할 수 있다.
    ```java
    // 복사 생성자
    public Yum(Yum yum) { .... };
    ```
* 아래의 복사 팩토리는 복사 생성자를 모방한 정적 팩토리(item 1)이다.
    ```java
    // 복사 팩터리
    public static Yum newInstance(Yum yum) { ... };
    // 
    ```
* 복사 생성자와 복사 팩토리는 해당 클래스가 구현한 '인터페이스'타입의 인스턴스를 인수로 받을 수 있다.  
  예컨대 관례상 모든 범용 컬렉션 구현체는 Coolection이나 Map 타입을 받는 생성자를 제공한다.

### item #14: Comparable을 구현할지 고려하라
