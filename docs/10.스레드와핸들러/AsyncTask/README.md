# AsyncTask

## 목차

- [AsyncTask 구조](#AsyncTask-구조)
- [AsyncTask 제약사항](#AsyncTask-제약사항)

## AsyncTask 구조

### Task란 무엇인가요?

Task란 작업을 의미하는 최소 단위로써 운영체제 입장에서는 프로세스, 프로세스 입장에서는 스레드가 Task를 처리하게 됩니다.

### Task 처리 모델

- 동기식 처리 모델 : 순차적으로 처리하는 모델입니다. 어떤 작업이 처리중이면 처리가 완료될때까지 기다립니다.
- 비동기식 처리 모델  : 병령적으로 작업을 처리하는 모델입니다. 어떤 작업이 처리중이면 대기하지 않고 즉시 다음 작업을
  처리합니다. 그리고 작업이 종료된 시점에 처리 결과를 받습니다.

### AsyncTask란 무엇인가요?

AsyncTask는 **비동기 처리를 할 수 있도록 스레드와 핸들러 기능을 하나의 클래스에 합쳐 놓은 것**입니다.

### AsyncTask 클래스가 구현해야 하는 3개의 인터페이스

- doInBackground() : 백그라운드 스레드 역할을 합니다. 이 블럭안에 코드만 백그라운드에서 실행됩니다.
- onProgressUpdater() : doInBackground() 블럭에서 publishProgress()가 호출될 때마다 실행됩니다.
  파일 다운로드 시에 현재 진행률을 보여주는 형태로 많이 사용됩니다.
- onPostExecute() : doInBackground()의 코드가 완료된 후에 호출됩니다. 파일 다운로드를 하는 로직이라면
  다운로드 완료 처리를 이 메서드에서 하면 됩니다.

```kotlin
        buttonDownload.setOnClickListener {
    val asyncTask = object : AsyncTask<String, Void, Bitmap?>() {   // 1
        override fun doInBackground(vararg params: String?): Bitmap? { // 2
            val urlString = params[0]!!
            try {
                val url = URL(urlString)
                val stream = url.openStream()
                return BitmapFactory.decodeStream(stream)
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }


        override fun onProgressUpdate(vararg values: Void?) { // 3
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(result: Bitmap?) { // 4
            super.onPostExecute(result)
            if (result != null) {
                imagePreview.setImageBitmap(result)
            } else {
                Toast.makeText(this@MainActivity, "다운로드 오류", Toast.LENGTH_SHORT).show()
            }
        }

    }
    asyncTask.execute(editUrl.text.toString()) // 5
}
```

1. AsyncTask 클래스의 제너릭의 세가지는 3가지 구현 메서드의 인자 타입입니다.
2. execute 메서드로 전달된 인자를 받을 수 있습니다. publishProgress() 메서드로 onProgressUpdate() 메서드에
   데이터를 전달할 수 있습니다. 작업 종료시 onPostExecute() 메서드에 Result 타입의 값을 반환할 수 있습니다.
3. onProgressUpdate 메서드는 파라미터를 이용하여 UI를 업데이트할 수 있습니다.
4. Result 타입의 값을 반환받으면 호출되는 메소드이며, 메인 스레드에서 동작합니다. 작업 결과를 UI에 업데이트 할수 있습니다.
5. 스레드의 start와 같은 역할입니다.

## AsyncTask 제약사항

- 한번 실행한 AsyncTask는 재사용할 수 없고 다시 생성해야 합니다.
- AsyncTask를 사용해서 스케줄링 할 수 있는 작업수의 한계를 가지고 있습니다.
- 안드로이드 버전 별 병렬 처리 동작이 다르므로 허니콤 이후 버전에서 멀티 스레드 작업을 원한다면
  AysyncTask를 실행할때 AsyncTask.THREAD_POOL_EXECUTOR 스케줄러를 지정해야 합니다.

## 미니 퀴즈

1. 순차적으로 작업을 처리하는 작업 모델은 무엇인가요? : 동기식 처리 모델
2. AsyncTask가 작업을 시작하면 호출되고 백그라운드 스레드에서 스레드에서 동작하는 메서드는 무엇인가요?
    - doInBackground() 메서드
3. 개수를 정해지지 않았으며 여러 개를 전달할 수 있는 인자는 무엇일까요?
    - 가변인자(vararg)
