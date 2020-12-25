# chapter 3 - 08 null 값에 대한 안정적인 처리 : Null Safety

코틀린은 null 값 처리에 많은 공을 들인 언어이다. null은 프로그래밍을 하면서 항상 이슈의 중심에 있는데, null로 인해 프로그램 전체, 혹은 앱 전체가 멈출 수 있기 때문이다.

프로그램이 멈출 수 있는 상황을 한번 코드로 만들어 보자. 먼저 다음과 같은 클래스를 선언하자.

```kotlin
class One {
	fun print() {
		Log.d("null_safety", "can you call me?")
	}
}
```

그리고 one 변수를 하나 선언하고 타입으로 내가 만든 클래스를 지정한다. 그리고 특정 조건이 만족할 때만 선언한 변수에 생성자를 호출해서 저장해두는 if문을 만든다. 긜고 변수를 통해 해당 클래스의 함수를 하나 호출한다.

```kotlin
var one: One

if (1 > 2) {
	one = One()
}

one.print()
```

조건문의 조건식이 false이기 때문에 one 변수는 아무것도 없는 null 상태가 된다. 이 때 print()를 호출하면 null 포인터 예외가 발생하면서 프로그램이 다운된다. 물론 이 정도의 코드는 안드로이드 스튜디오에서 오류를 발생시켜 컴파일되지 않게 막아준다. 하지만 코드의 양이 많아지면 이런 상황이 언제든 발생할 수 있는데, 코틀린은 그런 상황을 방지하기 위해 다양한 안전 장치를 만들어 두었다. 그 결과물이 Null Safety이다.

1. null 값 허용하기 : Nullable

    코틀린에서 지정하는 기본 변수는 모두 null이 입력되지 않는다. null 값을 입력하기 위해서는 변수를 선언할 때 타입 뒤에 ?(Nullable, 물음표) 를 입력한다.

    ```kotlin
    var variable: String?
    ```

    변수의 타입 뒤에 물음표를 붙이지 않으면 null 값을 입력할 수 없다. null 예외를 발생시키고 싶지 않다면 기본형으로 선언한다.

    - 함수 파라미터에 null 허용 설정하기

        안드로이드의 onCreate() 메서드의 Bundle 파라미터처럼 함수의 파라미터에도 null 허용 여부를 설정할 수 있다. 함수의 파라미터가 null을 허용할 경우는 코드 내부에서 해당 파라미터에 대해서 null 체크를 먼저 해야만 사용할 수 있다.

        ```kotlin
        fun nullParameter(str: String?) {
        	if (str != null) {
        		var length2 = str.length
        	}
        }
        ```

        파라미터 str에 null이 허용되었기 때문에 함수 내부에서 null 체크를 하기 전에는 str을 사용할 수 없다.

    - 함수의 리턴 타입에 null 허용 설정하기

        함수의 리턴 타입에도 물음표를 붙여 null 허용 여부를 설정할 수 있다.

        ```kotlin
        fun nullReturn() : String? {
        	return null
        }
        ```

        함수의 리턴 타입에 Nullabale이 지정되어 있지 않으면 null 값을 리턴할 수 없다.

2. 안전한 호출 : ?.

    ?. (Safe Call, 물음표와 온점) 을 사용하면 null 체크를 좀 더 간결하게 할 수 있다. Nullable인 변수 다음에 ?. 을 사용하면 해당 변수가 null일 경우 ?. 다음의 메서드나 프로퍼티를 호출하지 않는다. 아래 코드처럼 문자열의 길이를 반환하는 length 프로퍼티를 호출했는데 str 변수 자체가 null일 경우는 length 프로퍼티를 호출하지 않고 바로 null을 반환한다.

    ```kotlin
    fun testSafeCall(str: String?) : Int? {
    	var resultNull : Int? = str?.length
    	return resultNull;
    }
    ```

    만약 Safe Call을 사용하지 않았는데 str 변수가 null이면 프로그램은 두운된다.

3. null 값 대체하기 : ?:

    ?: (Elvis Operator, 물음표와 콜론)을 사용해서 원본 변수가 null일 때 넘겨줄 기본값을 설정해보자. 다음 코드에서 Safe call 다음에 호출되는 프로퍼티 뒤에 다시 ?:을 붙였다. 그리고 0이라는 값을 표시했다. 이렇게 호출하면 str 변수가 null일 경우 가장 뒤에 표시한 0을 반환한다.

    ```kotlin
    fun testElvis(str: String?): Int {
    	var resultNonNull: Int = str?.result?:0
    	return resultNonNull;
    }
    ```