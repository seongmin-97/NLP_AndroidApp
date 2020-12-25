# chapter 3 - 07 클래스와 설계

우리가 지금 아는 선에서 클래스를 정리하면 클래스는 단지 변수와 함수의 모음이라고 할 수 있다. 그룹화할 수 있는 함수와 변수를 한군데 모아놓고 사용하기 쉽게 이름을 붙여놓은 것을 클래스라고 이해하자.

1. 클래스의 기본 구조

    코틀린에서 사용되는 클래스의 기본 구조는 아래와 같다.

    ```kotlin
    class "클래스명" {
    	var "변수"
    	fun "함수"() {
    		// 코드
    	}
    }
    ```

    다음은 Log 클래스의 코드를 함축해서 보여주는 것이다.

    ```kotlin
    class Log {
    	var status: Int
    	companion object {
    		static fun d(tag: String, msg: String) {
    			// 문자열을 출력하는 코드
    		}
    		static fun e(tag: String, msg: String) {
    			// 문자열을 출력하는 코드
    		}
    	}
    }
    ```

    이렇게 Log 클래스 안에 사용할 코드를 미리 정의해 놓고 Log.d() 함수를 호출해서 사용할 수 있다. companion object라는 생소한 코드가 있는데 이는 조금 있다가 알아보자.

2. 클래스의 생성

    코틀린은 객체를 사용하기 위해 두 가지 형태의 생성자(함수)를 제공한다. 앞의 함수에서 설명했듯이 코드를 실행한다는 것은 함수를 호출한다는 것이다. 클래스를 사용한다는 것은 곧 클래스라는 그룹으로 묶여있는 코드를 실행하는 것이기 때문에 함수 형태로 제공되는 생성자를 호출해야지만 클래스가 실행된다.

    생성자를 사용하지 않을 경우에는 생성 시 자동으로 기본 생성자가 호출되는데 기본 생성자는 파라미터가 아무것도 없는 빈 코드 블록이다.

    ```kotlin
    class Kotlin{
    	init {
    		//생상자가 없으면 아무것도 없는 init 블록이 실행되는 것과 같다.
    	}
    }
    ```

    - 프라이머리 생성자

        프라이머리(Primary) 생성자는 마치 클래스의 헤더처럼 사용할 수 있으며 constructor 키워드를 사용해서 정의하는데 조건에 따라 생략할 수 있다. 프라이머리 생성자도 결국은 함수이기 때문에 파라미터를 사용할 수 있다.

        ```kotlin
        class KotlinOne constructor(value: String) {
        	// Code
        }
        ```

        생성자에 접근 제한자나 특정 옵션이 없다면 constructor 키워드를 생략할 수 있다.

        ```kotlin
        class KotlinOne (value: String) {
        	// 코드
        }
        ```

        프라이머리 생성자는 마치 헤더처럼 class 키워드와 같은 위치에 작성되기 때문에 생성자 옆에 코드 블록을 작성할 수는 없지만, 이는 init 블록으로 대체할 수 있다.

        ```kotlin
        class KotlineTwo(value : string) {
        	init{
        		Log.d("class", "생성자로부터 전달받은 값은 ${value} 입니다.")
        }
        ```

    - 세컨더리 생성자

        세컨더리(Secondary) 생성자는 constructor 키워드를 마치 함수처럼 사용해서 작성할 수 있다.

        ```kotlin
        class KotlinTwo {
        	constructor (value: String) {
        		Log.d("class", "생성자로부터 전달받은 값은 ${value}입니다.")
        	}
        }
        ```

3. 클래스의 사용

    클래스의 이름에 괄호를 붙여서 클래스의 생성자를 호출한다. constructor 키워드를 직접 호출하지는 않는다.

    ```kotlin
    "클래스명"()
    ```

    아무런 파라미터 없이 클래스명 괄호를 붙여서 호출하면 init 블록이 있는 생성자가 호출되면서 블록 안의 코드가 자동으로 실행된다. 세컨더리 생성자의 경우 constructor 블록 안의 코드가 실행된다.

    다음과 같이 Kotlin 클래스의 생성자를 호출한 후 생성되는 것을 인스턴스(Instance)라고 하는데, 생성된 인스턴스는 변수에 담아둘 수 있다.

    ```kotlin
    var kotlin = Kotlin()
    ```

    생성자에 파라미터가 있으면 값을 입력해야 한다.

    ```kotlin
    var one = KotlinOne("value")
    ```

    - 클래스 안에 정의된 함수와 변수 사용하기

        클래스를 사용한다는 것은 사실상 클래스 내부에 정의된 함수와 변수를 사용한다는 것이다. 생성자를 통해 변수에 저장된 클래스의 인스턴스는 내부에 정의된 변수와 함수를 도트 연산자(.)로 접근할 수 있다.

    - 클래스를 인스턴스화 하지 않고 사용하기 : companion object

        companion object 코드 블록을 사용하면 클래스를 생성자로 인스턴스화하지 않아도 블록 안의 프로퍼티와 메서드를 호출해서 사용할 수 있다.

        ```kotlin
        class KotlinFour {
        	companion object {
        		var one: String = "None"
        		fun printOne() {
        			Log.d("class", "one에 입력된 값은 ${one}입니다.")
        		}
        	}
        }
        ```

        companion object 코드 블록 안의 프로퍼티와 메서드는 클래스명에 도트 연산자를 붙여서 생성자 없이 직접 호출할 수 있다. 주의할 점은 클래스명을 그대로 사용하기 때문에 호출하는 클래스명의 첫 글자가 대문자이다.

        ```kotlin
        KotlinFour.one = "new value"
        KotlinFour.printOne()
        ```

        우리가 지금까지 사용한 Log 클래스의 d(), e() 메소드가 모두 companion object 코드 블록 안에 만들어져 있기 때문에 생성자 없이 바로 호출해서 사용할 수 있었던 것이다.

4. 데이터 클래스

    코틀린은 간단한 값의 저장 용도로 데이터 클래스(data class)를 제공한다. data 예약어를 class 앞에 붙여서 만들고 클래스명 다음에 생성자처럼 파라미터를 정의할 수 있는데, 생성자와 다르게 파라미터 앞에 var 또는 val을 명시해서 변수인지 상수인지를 구분해야 한다.

    ```kotlin
    data class "클래스명" (val "파라미터1": "타입", var "파라미터2": "타입")  
    ```

    - 데이터 클래스의 정의와 생성

        데이터 클래스를 정의할 때 파라미터 앞에 있는 var(또는 val) 키워드는 생성할 수 없다. 데이터 클래스는 일반 클래스와 동등하게 생성한다. 일반 클래스에서 toString() 메서드를 호출하면 인스턴스의 주소값을 반환하지만, 데이터 클래스는 값을 반환하기 때문에 실제 값을 모니터링할 때 좋다.

        ```kotlin
        data class DataUser (var name: String, var age: Int)

        var dataUser = DataUser("Michael", 21)
        Log.d("DataClass", "DataUser is ${dataUser.toString()}")
        ```

    - copy() 메서드 지원

        copy() 메서드로 간단하게 값을 복사할 수 있다.

        ```kotlin
        var newData = dataUser.copy()
        ```

5. 클래스의 상속과 확장

    코틀린은 클래스의 재사용을 위해 상속을 지원한다. 상속은 개념적으로는 어렵지만, 사용 측면에서 바라보면 그렇게 어렵지 않다.

    상속은 클래스를 생성한 후 도트 연산자(.)를 통해 메서드와 프로퍼티를 사용하는 것처럼 클래스의 자원을 사용하는 또 다른 방법이다. 상속을 사용하면 메서드와 프로퍼티를 마치 내 클래스의 일부처럼 사용할 수 있다.

    이제 상속이 필요한 이유에 대해 알아보자. 앞으로 우리가 코딩할 안드로이드에는 Activity라는 클래스가 미리 만들어져 있고, Activity 클래스 내부에는 글자를 쓰는 기능, 그림을 그리는 기능, 화면에 새로운 창을 보여주는 기능들이 미리 정의되어 있다. 상속이 있기 때문에 우리는 이 Activity 클래스를 상속받아 약간의 코드만 추가하면 간단한 앱 하나를 만들 수 있다. 만약 상속이 없다면 위 기능을 우리가 모두 작성해야 할 것이므로 효율이 떨어진다.

    - 클래스의 상속

        상속 대상이 되는 부모 클래스는 open 키워드로 만들어야만 자식 클래스에서 사용할 수 있다. 만약 open 키워드로 열려 있지 않으면 상속할 수 없다. 상속을 받은 자식 클래스에서는 콜론을 이용해서 상속할 부모 클래스를 지정한다. 마지막으로 클래스 상속은 부모의 생성자를 호출해서 생성된 인스턴스를 자식이 갖는 과정이기 때문에 부모 클래스명 다음에 괄호를 입력해서 생성자를 호출한다.

        ```kotlin
        open class "상속될 부모 클래스" {
        	//code
        }
        class "자식 클래스" (value: String) : "부모 클래스"(value) {
        	//code
        }
        ```

    - 생성자 파라미터가 있는 클래스의 상속

        상속될 부모 클래스의 생성자에 파라미터가 있다면 자식 클래스의 생상자를 통해 값을 전달할 수 있다. 부모 클래스에 세컨더리 생성자가 있다면, 역시 자식 클래스의 세컨더리 생성자에서 super 키워드로 부모 클래스에 전달할 수 있다. 다음은 안드로이드의 View 클래스를 상속받는 예제이다. 부모 클래스의 세컨더리 생성자를 이용하는 경우는 부모 클래스명 다음에 오는 괄호를 생략한다.

        ```kotlin
        class CustomView : View ("생략") {
        	constructor(ctx: Context): super(ctx)
        	constructor(ctx: Context, attrs: AttributeSet): super(ctx, attrs)
        }
        ```

    - 부모 클래스의 프로퍼티와 메서드 사용하기

        부모 클래스에서 정의된 프로퍼티와 메서드를 내 것처럼 사용할 수 있다.

        ```kotlin
        open class Parent{
        	var hello: String = "Hello"
        	fun sayHello() {
        		Log.d("inheritance", "${hello}")
        	}
        }

        class Child : Parent() {
        	fun myHello() {
        		hello = "Hello everyone"
        		sayHello()
        	}
        }
        ```

        child 에는 sayHello와 hello가 없지만 문제없이 로그가 잘 출력된다.

    - 프로퍼티와 메서드의 재정의 : 오버라이드

        상속받은 부모 클래스의 프로퍼티와 메서드 중에 자식 클래스에서는 다른 용도로 사용해야 하는 경우가 있다. 부모 클래스와 동일한 이르므이 메서드나 프로퍼티를 사용할 필요가 있을 경우에 override 키워드를 사용해서 재정의할 수 있다. 오버라이드할 때는 프로퍼티나 메서드도 클래스처럼 앞에 open을 붙여서 상속할 준비가 되어 있어야 한다.

    - 메서드 오버라이드

        상속할 메서드 앞에 open 키워드를 붙이면 오버라이드할 수 있지만, open 키워드가 없는 메서드는 오버라이드할 수 없다.

        ```kotlin
        open class BaseClass {
        	open fun opened() {
        	}
        	fun notOpend() {
        	}
        }

        class ChildClass : BaseClass {
        	override fun opened() {
        	}
        	override fun notOpend() {
        	} // open키워드가 없으므로 잘못된 사용이다.
        }
        ```

    - 프로퍼티 오버라이드

        메서드 오버라이드처럼 프로퍼티 역시 open으로 열려 있어야만 오버라이드를 할 수 있다.

        ```kotlin
        open class BaseClass2 {
        	open var opened : String = "I am"
        }

        class ChildClass2 : BaseClass2() {
        	override var opened : String = "You are"
        }
        ```

    - 익스텐션

        코틀린은 클래스, 메서드, 프로퍼티에 대해 익스텐션(extentions)을 지원한다. 이미 만들어져 있는 클래스에 다음과 같은 형태로 메서드를 추가할 수 있다.

        ```kotlin
        fun "클래스.확장할 함수"() {
        	// code
        }
        ```

        상속이 미리 만들어져 있는 클래스를 가져다 쓰는 개념이라면 익스텐션은 미리 만들어져 있는 클래스에 메서드를 붙여넣는 개념이다. 확장을 한다고 해서 실제 클래스의 코드가 변경되는 것은 아니다. 단지 실행 시 도트 연산자로 호출해서 사용할 수 있을 뿐이다.

6. 설계 도구

    객체지향 프로그래밍은 구현(실제 로직을 갖는 코딩)과 설계(껍데기만 있는 코딩)으로 구분할 수 있는데 지금까지는 모두 구현에 중점을 둔 기법을 살펴보았다. 이번에는 프로그래밍 설계에 사용하는 설계 도구에 대해 알아보자.

    우리가 집중적으로 공부할 영역은 하니지만, 구현을 함에 있어 그 내용을 이해하는 것과 그렇지 않은 것의 차이는 크기 때문에 기본 사항은 이해하고 넘어갈 필요가 있다. 방대한 설계 기법의 내용 중 꼭 필요한 몇가지만 알아보자

    - 패키지(Package)

        설계라는 개념이 아직 막연할 수 있지만, 우리가 코딩을 하며 파일을 분류하고, 이름을 짓고, 특정 디렉터리에 모아 놓는 것이 모두 설계이다.

        패키지(Package)는 클래스와 소스 파일을 관리하기 위한 디렉터리 구조의 저장 공간이다. 다음과 같이 현재 클래스가 어떤 패키지(디렉터리)에 있는지 표시한다. 디렉터리가 계층 구조로 만들어져 있으면 온점으로 구분해서 각 디렉터리를 모두 나열해준다.

        ```kotlin
        package main directory.sub directory

        class "Class" {
        }
        ```

        하나의 패키지에 여러 개의 파일을 생성할 수 있기 때문에 서로 관계가 있는 파일을 동일한 패키지에 만들어두면 관리가 용이하다.

    - 추상화

        프로그래밍을 하기 전 개념 설계를 하는 단계에서 클래스의 이름과 클래스 안에 있음 직한 기능들을 유추해서 메서드 이름으로 먼저 나열한다.

        이때 명확한 코드는 설계 단계에서 메서드 블록 안에 직접 코드를 작성하는데, 그렇지 않은 경우에는 구현 단계에서 코드를 작성하도록 메서드의 이름만 작성한다. 이것을 추상화(abstract)라고 한고, abstract 키워드를 사용해서 명시한다.

        구현 단계에서 이 추상화된 클래스를 상속받아서 아직 구현되지 않은 부분을 마저 구현한다.

        ```kotlin
        abstract class Animal {
        	fun walk() {
        		Log.d("abstract", "걷습니다.")
        	}
        	abstract fun move()
        }

        class Bired : Animal() {
        	override fun move() {
        		Log.d("abstract", "날아서 이동합니다.")
        	}
        }
        ```

    - 인터페이스

        인터페이스(interface)는 실행 코드 없이 메서드 이름만 가진 추상 클래스라고 생각해도 무방하다. 즉, 누군가 설계해 놓은 개념 클래스 중에 실행 코드가 한 줄이라도 있으면 추상화, 코드 없이 메서드 이름만 나열되어 있으면 인터페이스이다.

        인터페이스는 상속 관계의 설계보다는 외부 모듈에서 내가 만든 모듈을 사용할 수 있도록 메서드의 이름을 나열해둔 일종의 명세서로 제공된다.

        인터페이스는 interface 예약어를 사용해서 정의할 수 있고 인터페이스에 정의된 메서드를 오버라이드해서 구현할 수 있다. 코틀린은 프로퍼티도 인터페이스 내부에 정의할 수 있는데, 대부분의 객체지향 언어에서는 지원하지 않는다. 추상 클래스와 다르게 class 키워드는 사용되지 않는다.

        ```kotlin
        interface "인터페이스명" {
        	var value: String
        	fun function1()
        	fun function2()
        }
        ```

    - 인터페이스 만들기

        interface 예약어로 인터페이스를 저으이한다. 코틀린은 인터페이스 내부에 프로퍼티도 정의할 수 있다. 메서드는 코드 블록 없이 이름만 작성해 놓는다. 인터페이스의 프로퍼티와 메서드 앞에는 abstract 키워드가 생략된 형태이다.

        ```kotlin
        interface InterfaceKotrin {
        	var variable: String
        	fun get()
        	fun set()
        }
        ```

    - 클래스에서 구현하기

        인터페이스를 클래스에서 구현할 때는 상속과는 다르게 생성자를 호출하지 않고 인터페이스 이름만 지정해주면 된다.

        ```kotlin
        class KotlinImpl : InterfaceKotlin {
        	override var variable: String = "init value"
        	override fun get() {
        		//code
        	}
        	override fun set() {
        		//code
        	}
        }
        ```

        인터페이스를 클래스의 상속 형태가 아닌 소스 코드에서 직접 구현할 때도 있는데, object 키워드를 사용해서 구현해야 한다. 실제로 안드로이드 프로젝트를 시작하면 자주 사용하는 형태이다.

        ```kotlin
        var kotlinImpl = object : InterfaceKotlin{
        	override var variable: String = "init value"
        	override fun get() {
        		//code
        	}
        	override fun set() {
        		//code
        	}
        }
        ```

    - 인터페이스의 효율적인 사용

        인터페이스는 외부의 다른 모듈을 위한 의사소통 방식을 정의하는 것이다. 혼자 개발하거나 소수의 인원이 하나의 모듈 단위를 개발할 때는 인터페이스를 사용하지 않는 것이 좋다. 인터페이스를 남용하면 코드의 가독성과 구현 효율성이 떨어지기 때문이다.

    - 접근 제한자

        코틀린에서 정의되는 클래스, 인터페이스, 함수, 변수는 모두 접근 제한자(Visibility Modifiers)를 가질 수 있다.

        함수형 언어라는 특성 때문에 코틀린은 기존 객체지향에서 접근 제한자의 기준으로 삼았든 패키지 대신에 모듈 개념이 도입되었다. internal 접근 제한자로 모듈 간의 접근을 제한할 수 있다.

    - 접근 제한자의 종류

        접근 제한자는 서로 다른 파일에게 자신에 대한 접근 권한을 제공하는 것인데 각 변수나 클래스 이름 앞에 아무런 예약어를 붙이지 않았을 때는 기본적으로 public 접근 제한자가 적용된다.

        1. private : 다른 파일에서 접근할 수 없다.
        2. internal : 같은 모듈에 있는 파일만 접근할 수 있다.
        3. protected : private와 같으나 상속 관계에서 자식 클래스가 접근할 수 있다.
        4. public : 제한 없이 모든 파일에서 접근할 수 있다.

    - 접근 제한자의 적용

        접근 제한자를 붙이면 해당 클래스, 멤버 프로퍼티 또는 메서드에 대한 사용이 제한된다.

        다음 코드를 통해서 접근 제한자가 어떻게 적용하는지 알아보자.

        ```kotlin
        open class Parent {
        	private val privateVal = 1
        	protected open val protectedVal = 2
        	internal val internalVal = 3
        	val defaultVal = 4
        }
        ```

        위 클래스를 상속한 자식 클래스에서 Log 출력을 시도하면 1번은 접근할 수 없고, 2번은 자식 클래스이므로 (상속 관계이므로) 접근 가능, 3번은 동일 모듈이므로 접근 가능, 4번은 접근 제한이 없기에 접근 가능하다.