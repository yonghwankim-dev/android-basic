# 스레드와 루퍼

## 목차

- [메인 스레드](#메인-스레드란-무엇인가?)
    - [메인 스레드란 무엇인가?](#메인-스레드란-무엇인가?)
    - [메인 스레드 특징](#메인-스레드-특징)
    - [메인 스레드의 제약사항](#메인-스레드의-제약사항)
- [백그라운드 스레드](#백그라운드-스레드)
    - [Thread 객체를 상속받아서 스레드 생성](#Thread-객체를-상속받아서-스레드-생성)
    - [Runnable 인터페이스](#Runnable-인터페이스)
    - [람다식으로 Runnable 익명 객체 구현](#람다식으로-Runnable-익명-객체-구현)
    - [코틀린에서 제공하는 thread() 구현](#코틀린에서-제공하는-thread-구현)

## 메인 스레드(UI 스레드)

### 메인 스레드란 무엇인가?

안드로이드에서 메인 스레드는 UI 스레드로, 애플리케이션에서 사용자 입력이나 UI 업데이트를 담당하는 스레드입니ㅏㄷ.

### 메인 스레드 특징

- 화면의 UI를 그립니다.
- 안드로이드 UI 툴킷의 구성요소(android,widget, android,view, ...)와 상호작용합니다.
- UI 이벤트를 사용자에게 응답해줍니다.

### 메인 스레드의 제약사항

- UI 이벤트나 작업이 몇 초 내에 응답하지 않으면 안드로이드 시스템은 ANR(응용 프로그램이 응답하지 않음)이 표시됩니다.
  따라서 시간이 오래 걸리는 작업은 새로운 스레드를 생성해서 처리해야 합니다.
- 즉, UI 이벤트나 작업이 오래 걸리면 프로그램이 죽기 때문에 스레드를 새로 만들어서 처리해야 프로그램이 죽지 않습니다.

## 백그라운드 스레드

메인 스레드가 아닌 뒤쪽에서 별도로 실행되는 스레드입니다.

백그라운드 스레드는 네트워크 작업이나 오래 걸리는 계산, 파일 다운로드, 업로드와 같이 시간을 계산할 수 없는

오래 걸리는 작업에 대해서 처리하는 것을 권장합니다. 만약 메인 스레드가 이러한 오래 걸리는 작업을 수행하면

ANR이 발생할 것입니다.

### Thread 객체를 상속받아서 스레드 생성

다음과 같이 Thread 클래스를 상속받아서 스레드를 생성할 수 있습니다.

```kotlin
class WorkerThread : Thread() {
    override fun run() {
        var i = 0
        while (i < 10) {
            i += 1
            Log.i("WorkerThread", "$i")
        }
    }
}
```

다음과 같이 스레드 객체인 WorkerThread 객체를 생성하여 시작할 수 있습니다.

```kotlin
ovveride fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    var thread = WorkerThread()
    thread.start()
}
```

### Runnable 인터페이스

Runnable 인터페이스를 구현하여 스레드를 생성할 수 있습니다.

Runnable 인터페이스를 지원하는 이유는 코틀린에서는 다중 상속이 불가능하기 때문에 상속 관계에 있는 클래스에

인터페이스를 구현하여 스레드를 생성하기 위함입니다.

```kotlin
class WorkerRunnable : Runnable {
    override fun run() {
        var i = 0
        while (i < 10) {
            i += 1
            Log.i("WorkerThread", "$i")
        }
    }
}
```

```kotlin
ovveride fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    var thread = Thread(WorkerRunnable())
    thread.start()
}
```

### 람다식으로 Runnable 익명 객체 구현

인터페이스 내부에 메서드가 하나만 있는 경우 람다식으로 변환이 가능합니다.

```kotlin
Thread {
    var i = 0
    while (i < 10) {
        i += 1
        Log.i("WorkerThread", "$i")
    }
}.start()
```

### 코틀린에서 제공하는 thread 구현

코틀린에서는 백그라운드를 사용할 수 있습니다.

thread() 안에 파라미터로 start=true를 전달하면 thread() 안의 코드 블럭이 실행됩니다.

```kotlin
thread(start = true) {
    var i = 0
    while (i < 10) {
        i += 1
        Log.i("WorkerThread", "$i")
    }
}
```
