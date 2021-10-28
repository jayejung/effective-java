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
    > from: 매개변수 1개를 받아서 해당 타입의 인스턴스를 반환하는 형변환 메서드  
   > <code>Date d = Date.from(instant);</code>
   > 
   > of: 여러 매개변수를 받아 적합한 타입의 인스턴스를 반환하는 집계 메소드  
   > <code> Set<Rank> faceCards = EnumSet.of(JACK, QUEEN, KING);</code>
   > 
   > valueOf: from과 of의 더 자세한 버전  
   > <code>BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);</code>
   > 
   > instance or getInstance: (매개변수를 받는다면) 매개변수로 명시한 인스턴스를 반환하지만, 같은 인스턴스임을 보장하지는 않는다.  
   > <code>StackWalker luke = StackWalker.getInstance(options);</code>
   > 
   > create or newInstance: instance 혹은 getInstance와 같지만, 매번 새로운 인스턴스를 생성해 반환함을 보장한다.  
   > <code>Object newArray = Array.newInstance(classObject, arrayLen);</code>
   > 
   > get{Type}: getInstance와 같으나, 생성할 클래스(FileStore)가 아닌 다른 클래스(Files)에 팩터리 메서드를 정의할 때 쓴다. "Type"은 팩토리 메소드가 반환할 객체의 타입이다.  
   > <code>FileStore fs = Files.getFileStore(path);</code>
   > 
   > new{Type}: newInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩토리 메소드를 정의할 때 쓴다. "Type"은 팩토리 메소드가 반환할 객체의 타입이다.  
   > <code>BufferedReader br = Files.newBufferedReader(path);</code>
   > 
   > type: getType과 newType의 간결한 버전  
   > <code>List<Compliant> litany = Collections.list(legacyLitany);</code>

### item #2: 생성자에 매개변수가 많다면 빌더를 고려하라.


