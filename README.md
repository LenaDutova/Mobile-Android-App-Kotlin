Реализация на основе языка Kotlin трех-оконного приложения для входа, регистрации и отображения данных пользователю

Вестка
Верстка экранов реализована с использованием XML. См. res/layout

Добавлены константы для цветов, размеров и строк (colors, dimens and strings). См. res/values (-night, -ru). Строковые ресурсы содержат две локализации (RU, EN по умолчанию). Измените язык операционной системы на устройстве или эмуляторе для просмотра отличий. Описаны тема и некоторые стили элементов (styles, themes) в двух исполнениях - ночное оформление в дополнение к обычному (default, night)

Добавлено изображение для логотипа приложения, отражающее основной язык написания.

Деятельности (Activity)
Каждая деятельность (StartActivity, ReturningActivity, FinalActivity) прописана в файле AndroidManifest.xml. Одна из них определена как стартовая (загрузочная):

<intent-filter>
    <action android:name="android.intent.action.MAIN" />
    <category android:name="android.intent.category.LAUNCHER" />
</intent-filter>
Жизненный цикл и логировании
Создана родительская деятельность (DebuggingActivity) от которой унаследованы (extends) остальные деятельности. Что позволяет переиспользовать некоторые методы, например вывод сообщений при разработке в Logcat

Взаимодействие
В дяетельностях находим визуальные компоненты (views), определенные в XML, с помощью метода findViewById(). После чего можно изменить их отображение или привязать действия через соответствующие слушатели событий (listeners):

Button btn = (Button) findViewById(R.id.btn_id);
btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        // Click to button with ID
    }
});
Некоторые действия порождают запуск иных деятельностей, через механизм "явных" намерений (intent), в которых указывается требуемый компонент. В представленном ниже примере, метод finish() закрывает деятельность, с которой пользователь уходит:

Intent intent = new Intent(this, AnotherActivity.class);
startActivity(intent);
finish();
Некоторые действия порождают запуск иных деятельностей для предоставления дополнительных данных. В этом случае "порождающая" деятельность уходит из поля видимости, но не завершается, а ждет ответа от "порождаемой" деятельности:

Intent intent = new Intent(this, AnotherActivity.class);
resultLauncher.launch(intent);                  // New way
// startActivityForResult(intent, REQUEST);     // @Deprecated way
Затем необходимо прочитать данные из объекта Bundle, прикрепленного в возвращаемом намерении (Intent), сохраненных в виде пар "ключ-значение". "Порождаемая" деятельность в свою очередь должна отправить требуемые данные и завершиться:

Intent intent = new Intent();
intent.putExtra("KEY", "value");
setResult(RESULT_OK, intent);
finish();
