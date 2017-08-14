---
layout: post
title:  "JUnit4 테스트 케이스 작성"
categories: Java
tags: JUnit4 Java Testcase 단위테스트 TDD
comments: true
---

## Intro
**테스트 주도 개발**(TDD : Test Driven Development)은 코드 자체를 작성하기 전에 **코드에 대한 테스트**를 작성하는 것이다.  
이러한 테스트는 단순히 사용자에게서 결함을 예방하는 데 머무르지 않고,  
팀 차원에서 사용자가 필요로 하는 기능을 이해하고, 이러한 기능을 믿을 수 있고 예측 가능하게 전달하는 데 도움을 준다.


그럼 이제 테스트 주도 개발 의 기본이 되는 **단위테스트**를 배워보자.  

### 단위 테스트란?
**단위 테스트**(Unit test)는 컴퓨터 프로그래밍에서 소스 코드의 특정 모듈이 의도된 대로 정확히 작동하는지 검증하는 절차다.  
즉, 모든 함수와 메소드에 대한 **테스트 케이스**(Test case)를 작성하는 절차를 말한다. 


다시말해, 내가 만든 **메소드에 대한 테스트 케이스**를 작성하여 잘 동작하는지 검사하는 과정을 단위테스트라 하고,   
이러한 테스트와 개발을 병행하는 것을 TDD라 한다.  


그렇다면 메소드에 대한 테스트 케이스는 어떻게 만드는 것일까?

### JUnit4
자바에서 제공하는 테스트 프레임워크로 **JUnit4**를 이용하여 테스트 케이스를 작성할 수 있다.
기본적으로 JUnit은 reflection을 통해 클래스 구조를 파악한 후 해당 클래스 내에서 테스트를 나타내는 것을 모두 실행한다.  


이제 Eclipse IDE에서 JUnit을 사용하여 단위테스트를 작성하는 법을 알아보자.


## Usage
테스트를 위한 새로운 프로젝트를 하나 만든다.  
![ newProject ]({{ site.url }}/assets/junit/newProject.png)  


프로젝트 이름을 적고 Next를 누른다.
![ 1 ]({{ site.url }}/assets/junit/1.png)  


화살표로 표시된 소스폴더 모양 아이콘을 눌러 test 디렉토리를 추가시킨다.
![ 2 ]({{ site.url }}/assets/junit/2.png)  
![ 3 ]({{ site.url }}/assets/junit/3.png)  


다음으로 Library 탭을 눌러 JUnit4 라이브러리를 추가시킨다.  
JUnit은 여러 자바 IDE에 기본적으로 탑재돼 있고, 없을 경우 **[다운로드](http://bit.ly/My9IXz)**받을 수 있다.  
![ 4 ]({{ site.url }}/assets/junit/4.png)  
![ 5 ]({{ site.url }}/assets/junit/5.png)  
![ 6 ]({{ site.url }}/assets/junit/6.png)  


자, 이제 JUnit으로 개발을 병행할 기본적인 준비가 되었다.  
간단한 코드를 테스트 해보자.  
src 디렉토리 내 패키지를 간단하게 만들고 `MyClass.java`를 추가하였다.  


{% highlight java %}
package xyz.nakzzi.junit;

/**
 * MyClass
 * 
 * @version 1.0 [2017. 8. 14.]
 * @author Choi
 */
public class MyClass {

	public int myNumber = 1234;
	public String myString = "test string";
	public char[] myCharArray = {'t', 'e', 's', 't'};
	public SubClass mySubClass = null;
	
	public int sum(int x, int y) {
		return x + y;
	}
	
	public void initInstance() {
		this.mySubClass = new SubClass();
	}
	
	class SubClass {
	}
}
{% endhighlight %}


다음은 `MyClass`에 대한 테스트 케이스를 만드는 방법이다.  
위에서 만든 test 디렉토리를 우클릭하고 New - JUnit Test Case를 눌러 생성한다.  
![ 7 ]({{ site.url }}/assets/junit/7.png)  


JUnit Test Case가 없을 경우 Other에서 JUnit을 검색하여 추가시킨다.
![ 8 ]({{ site.url }}/assets/junit/8.png)  


간단하게 테스트 케이스 이름을 작성하고 아래에 Browse를 누른다.  
![ 9 ]({{ site.url }}/assets/junit/9.png)  


테스트를 진행할 `MyClass`를 검색하고, 테스트할 메소드를 선택한다.
![ 10 ]({{ site.url }}/assets/junit/10.png)  
![ 11 ]({{ site.url }}/assets/junit/11.png)  


아래와 같은 테스트 코드가 작성되었을 것이다.  


{% highlight java %}
package xyz.nakzzi.junit;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * MyClassTest
 * 
 * @version 1.0 [2017. 8. 14.]
 * @author Choi
 */
public class MyClassTest {


	/**
	 * Test method for {@link xyz.nakzzi.junit.MyClass#sum(int, int)}.
	 */
	@Test
	public void testSum() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link xyz.nakzzi.junit.MyClass#initInstance()}.
	 */
	@Test
	public void testInitInstance() {
		fail("Not yet implemented");
	}

}
{% endhighlight %}


cf) `@Test` 테스트 어노테이션은 아래 [Annotation](#annotation)에서 기술하도록 하겠다.  
간단하게 '기술된 코드가 테스트 케이스로 실행될 수 있음'을 나타낸다.  


해당 테스트 케이스를 실행하는 방법은 테스트 케이스 클래스 우클릭 - Run As - JUnit Test를 누른다.  
![ 12 ]({{ site.url }}/assets/junit/12.png)  


`fail()` 메소드는 JUnit에서 제공하는 메소드로 무조건실패를 반환한다.  
따라서 결과가 아래와 같이 `Failures: 2` 로 나왔을 것이다.  


cf) **TDD 황금률** : 실패하는 테스트 없이는 새 기능을 작성하지 말라.  
즉 코드를 작성할 때 코드가 실패하는 것을 가정한 테스트를 우선 작성하는 것이 중요하다.  
이러한 테스트를 통과시키고 리팩터링하는 것이 TDD주기의 핵심이다.  


![ 13 ]({{ site.url }}/assets/junit/13.png)  


우선 `MyClass`의 Field값에 대한 테스트 코드를 작성해보자.

{% highlight java %}
package xyz.nakzzi.junit;

import org.junit.Assert;
import org.junit.Test;

/**
 * MyClassTest
 * 
 * @version 1.0 [2017. 8. 14.]
 * @author Choi
 */
public class MyClassTest {

	private final MyClass myclass = new MyClass();
	
	@Test
	public void testField() {
		// myNumber
		log(""+myclass.myNumber);
		Assert.assertEquals(1234, myclass.myNumber);
		
		// myString
		log(myclass.myString);
		Assert.assertEquals("test string", myclass.myNumber);

		// myCharArray
		char[] cArray = {'t', 'e', 's', 't'};
		Assert.assertEquals(cArray, myclass.myCharArray);
		
		// mySubClass
		Assert.assertNull(myclass.mySubClass);
	}
	
	private void log(String text) {
		System.out.println(text);
	}
}

{% endhighlight %}


위 코드에 사용 된 assert 메소드는 아래 [Assertion Method](#assertion-method-검증-메소드)를 참고한다.  
간단하게 인자로 받은 두 값을 비교하여 성공과 실패를 반환하는 메소드이다.  
ex) `assertEquals(300, 300);`  
이 메소드는 `Integer` 값으로 받은 300과 300을 비교하여 같은지를 반환한다. 두 값은 같으므로 Test 성공으로 이어진다.  
하지만 `assertEquals(300, 400);`  
위와 같은 경우는 두 값이 다르므로 Failure를 반환하며 Test 실패로 이어진다.  


위 `MyClassTest` 를 실행하게 되면 성공을 반환할 것 같지만 테스트 실패가 뜨게된다.  
실패한 이유를 살펴보자.  


Eclipse에서는 어느곳에서 실패를 하게 되었는지 알려주는 Failure Trace를 제공한다.  
![ 14 ]({{ site.url }}/assets/junit/14.png) 


자세히 살펴보면 `MyClassTest.java:30`에서 AssertionError가 발생한 것을 알 수 있다.  
바로 아래줄인 at으로 시작하는 `at xyz.nakzzi.junit.MyClassTest.testField(MyClassTest.java:30)`을 더블 클릭 하면 해당 메소드로 찾아간다.  
![ 15 ]({{ site.url }}/assets/junit/15.png) 


JUnit에서 제공하는 Assertion Method 중 assertEquals는 값에 대한 비교이므로 array의 참조값을 비교한다.  
따라서 array의 값을 비교하려면 `assertArrayEquals(cArray, myclass.myCharArray);`로 바꿔줘야 한다.  


다음 테스트를 진행하기전 위 `testField()` 메소드의 `@Test` 어노테이션 옆에 `@Ignore`를 붙이고 `org.junit.Ignore;`를 import 시켜보자.  
이렇게 하면 `testField()`메소드는 해당 테스트 케이스의 **테스트 대상에서 제외**될 것이다.


다음은 `sum` 메소드에 대한 테스트 코드이다.  

{% highlight java %}
@Test
public void testSum() {
	assertEquals(3, myclass.sum(2, 2));
}
{% endhighlight %}
`sum(2, 2)`의 결과값은 4가 예측되며 일부러 3을 assert 해보았다.  
![ 16 ]({{ site.url }}/assets/junit/16.png)  
역시 Failure Trace를 보면 4가 기대되는 가운데 3이 결과로 왔다고 찍힌다.  


마지막으로 null 값에 대한 테스트 코트이다.
{% highlight java %}
@Test
public void testInitInstance() {
	Assert.assertNull(myclass.mySubClass);
	myclass.initInstance();
	Assert.assertNotNull(myclass.mySubClass);
}
{% endhighlight %}


아직 객체 생성이 되지않은 `mySubClass`는 null값을 갖으므로 첫번째 `assertNull`을 통과하였다.  
`initInstance()`메소드를 통해 객체를 생성하였고, 두번째 `assertNotNull`울 통과하였다.



### Assertion Method (검증 메소드)
위 코드에 import된 `org.junit.Assert.*;`은  
테스트 작성에 유용하게 사용되는 **Assertion Method**이다.  
Assert에 실패하거나 Exception이 발생하면 Failure 상태가 되며 테스트가 종료된다.  
각 Method들은 정적으로 선언되어 있으므로 Assert.assertEquals(…)와 같이 참조할 수도 있다.


{% highlight java %}
assertArrayEquals(byte[] expecteds, byte[] actuals) 
assertArrayEquals(char[] expecteds, char[] actuals) 
assertArrayEquals(int[] expecteds, int[] actuals) 
assertArrayEquals(long[] expecteds, long[] actuals) 
assertArrayEquals(java.lang.Object[] expecteds, java.lang.Object[] actuals) 
assertArrayEquals(short[] expecteds, short[] actuals) 
{% endhighlight %}
	: 인자로 받은 두 개의 자료 형 배열이 같은지를 확인한다.  
	(배열 노드 값의 비교)  
{% highlight java %}
assertArrayEquals(java.lang.String message, byte[] expecteds, byte[] actuals) 
assertArrayEquals(java.lang.String message, char[] expecteds, char[] actuals) 
assertArrayEquals(java.lang.String message, int[] expecteds, int[] actuals)
assertArrayEquals(java.lang.String message, long[] expecteds, long[] actuals)
assertArrayEquals(java.lang.String message, java.lang.Object[] expecteds, java.lang.Object[] actuals) 
assertArrayEquals(java.lang.String message, short[] expecteds, short[] actuals)
{% endhighlight %}
	: 인자로 받은 두 개의 자료 형 배열이 같은지를 확인한다.  
	두 객체가 같지 않은 경우(failure), 첫 번째 인자로 받은 message 가 출력된다.  
	(배열 노드 값의 비교)
```
assertEquals(double expected, double actual) 
assertEquals(double expected, double actual, double delta)
assertEquals(long expected, long actual)
assertEquals(java.lang.Object[] expecteds, java.lang.Object[] actuals) 
assertEquals(java.lang.Object expected, java.lang.Object actual) 
```
	: 인자로 받은 두 개의 자료 형 값이 같은지를 확인한다.  
**(배열의 경우 노드 값의 비교가 아닌, 가리키고 있는 참조 값의 비교임에 유의)**
{% highlight java %}
int[] array1 = {1, 2, 3};
int[] array2 = {1, 2, 3};
assertEquals(array1, array2); // failure
{% endhighlight %}
```
assertEquals(java.lang.String message, double expected, double actual) 
assertEquals(java.lang.String message, double expected, double actual, double delta) 
assertEquals(java.lang.String message, long expected, long actual)
assertEquals(java.lang.String message, java.lang.Object[] expecteds, java.lang.Object[] actuals) 
assertEquals(java.lang.String message, java.lang.Object expected, java.lang.Object actual) 
```
	: 인자로 받은 두 개의 자료 형 값이 같은지를 확인한다.  
두 값이 같지 않은 경우(failure), 첫 번째 인자로 받은 message 가 출력된다.  
```
assertFalse(boolean condition) 
assertFalse(java.lang.String message, boolean condition) 
```
	: 인자로 받은 값의 진위가 거짓인지 확인한다.
```
assertNotNull(java.lang.Object object)
assertNotNull(java.lang.String message, java.lang.Object object) 
```
	: 인자로 받은 값이 Null이 아닌지 확인한다.
```
assertNotSame(java.lang.Object unexpected, java.lang.Object actual) 
assertNotSame(java.lang.String message, java.lang.Object unexpected, java.lang.Object actual) 
```
	: 인자로 받은 두 객체가 동일한 객체를 참조하지 않는 것을 확인한다.
```
assertNull(java.lang.Object object) 
assertNull(java.lang.String message, java.lang.Object object) 
```
	: 인자로 받은 값이 Null 인지 확인한다.
```
assertSame(java.lang.Object expected, java.lang.Object actual) 
assertSame(java.lang.String message, java.lang.Object expected, java.lang.Object actual) 
```
	: 인자로 받은 두 객체가 동일한 객체를 참조하는 것을 확인한다.
```
static <T> void assertThat(java.lang.String reason, T actual, org.hamcrest.Matcher<T> matcher)
static <T> void assertThat(T actual, org.hamcrest.Matcher<T> matcher) 
```
	: actual 인자가 matcher에서 지정된 조건을 만족하는지 확인한다.
```
assertTrue(boolean condition)
assertTrue(java.lang.String message, boolean condition) 
```
	: 인자로 받은 값의 진위가 참인지 확인한다.
```
fail()
fail(java.lang.String message) 
```
	: 테스트를 무조건 실패하게 한다.


### Annotation

`@Test`
**Test Annotation**은 public void method로 기술된 코드가 테스트 케이스로 실행될 수 있음을 나타낸다.
JUnit 실행 시 테스트 클래스의 Instance를 생성한 후 Test Annotation을 찾아 실행하며, 테스트 중 발생한 예외에 대해서는 JUnit에 의해 실패로 보고된다.  
예외 없이 테스트를 끝냈다면 테스트가 성공한 것으로 간주된다.  
```
@Test
public void methodTest() {
	…
}
```
`@Before`
**Before Annotation**은 Test method가 실행되기 이전에 먼저 실행되는 코드를 나타낸다.  
만약 해당 클래스의 슈퍼클래스가 있다면 슈퍼클래스의 @Before method가 먼저 실행되게 된다.  
```
@Before
public void initialize() {
	…
}
```	
`@BeforeClass`
**BeforeClass Annotation**은 DB Login과 같이 초기 계산 비용이 많이 드는 설정을 공유할 때 사용한다.  
해당 Annotation은 public static void (no-arguments) method 로 기술된 코드를 나타낸다.  
```
@BeforeClass
public static void onlyOnce() {
	…
}
```

`@After`
**After Annotation**은 Test method가 모두 실행되고 종료된 이후에 실행되는 코드를 나타낸다.  
해당 코드는 Before 또는 Test method에서 예외를 발생한다 하더라도 실행되는 것이 보장된다.  
주로 테스트 중 할당된 외부 리소스를 해제할 때 사용된다.
```
@After
public void finalize() {
	…
}
```
`@AfterClass`
***AfterClass Annotation**은 주로 @BeforeClass method에서 값 비싼 외부 리소스를 할당하는 경우 이것을 해제할 때 사용된다.  
이 역시 public static void (no-arguments) method로 기술된 코드를 나타낸다.  
```
@ BeforeClass
public static void login() {
	…
}
@ AfterClass
public static void logout() {
	…
}
```
`@Ignore`
**Ignore Annotation** 은 해당 method또는 Class를 일시적으로 비활성화 시킨다.  
```
@Ignore @Test
public void doSomething () {
	…
}
```
```
@Ignore
public class classTest() {
	@Test
	public void test1() {…}
	@Test
	public void test2() {…}
}
```

### 실행 순서
**실행 순서**는 다음과 같다.  
test1()과 test2()가 있다고 가정하면..  


`@BeforeClass` 메소드 -> `@Before 메소드` -> `@Test` **test1 메소드** -> `@After` 메소드 -> `@Before 메소드` -> `@Test` **test2** 메소드 -> `@After` 메소드 -> `@AfterClass` 메소드


**cf) Annotation과 Assertion Method는 [JUnit javadoc](http://junit.sourceforge.net/javadoc/org/junit/package-summary.html)에 자세히 요약되어 있다.**
