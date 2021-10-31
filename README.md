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
      <code>Set<Rank> faceCards = EnumSet.of(JACK, QUEEN, KING);</code>
    
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
      <code>List<Compliant> litany = Collections.list(legacyLitany);</code>
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
   * 세터들은 빌더 자신을 반환하므로 연쇄적으로 호출가능함.(fluent AaPI or method chaining)
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



