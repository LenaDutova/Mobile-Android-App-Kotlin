# Построение графа навигации

## Зависимости
Для использования навигационного графа требуется добавить зависимости в build.gradle.kts уровня Модуля:
```
dependencies {
    implementation ("androidx.navigation:navigation-fragment:2.9.5")
    implementation ("androidx.navigation:navigation-ui:2.9.5")    
}
```

Для использования плагина Safe args в build.gradle.kts уровня Проекта после перечисления плагинов требуется добавить:
```
buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.9.5")
    }
}
```

И добавить cам плагин в build.gradle.kts уровня Модуля:
```
plugins {
    id("androidx.navigation.safeargs.kotlin")
}
```

Для использования механизма привязки компонентов (ViewBinding) вместо поиска через findViewByID() требуется добавить разрешение в build.gradle.kts уровня Модуля:
```
android {
    buildFeatures {
        viewBinding = true
    }
}
```

## Построение навигационного графа
В ресурсах необходимо добавить директорию "navigation" или выбрать ее при создании файла навигационного графа. В файле графа необходимо прописать все фрагменты и действия переходов между ними. Допустим создан файл res/navigation/gpaph.xml

```
<?xml version="1.0" encoding="utf-8"?>
<navigation
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"

    android:id="@+id/navigation"
    app:startDestination="@+id/screen_start">

    <fragment
        android:id="@+id/screen_a"
        android:name="your.packages.AFragment"
        tools:layout="@layout/fragment_a">

        <action
            android:id="@+id/action_a_to_b"
            app:destination="@id/screen_b"/>
    </fragment>

    <fragment
        android:id="@+id/screen_b"
        android:name="your.packages.BFragment"
        tools:layout="@layout/fragment_b"/>
</navigation>
```

В макете деятельности необходимо прописать значение "androidx.navigation.fragment.NavHostFragment" для атрибута "name", указать идентификатор созданного навигационного графа в атрибуте "navGraph":
```
<androidx.fragment.app.FragmentContainerView
    ndroid:id="@+id/fragment_container_view"
    android:name="androidx.navigation.fragment.NavHostFragment"
    app:navGraph="@navigation/navigation"
/>
```

Более деятельность не будет отвечать за осуществление навигации. 

## Осуществление перехода между фрагментами
Навигация между фрагментами будет осуществляться с помощью объекта androidx.navigation.Navigation. Метод navigate() по своей сути осуществляет замену фрагментов. Чтобы создать новый фрагмент и перейти к нему, следует указать целевой идентификатор из навигационного графа:
```
Fragment.findNavController().navigate(R.id.action_a_to_b)
```

## Передача данных при переходе между фрагментами
Данные можно передать при переходе, но в этом случае стоит выделить объект действия, а не производить переход по идентификатору. При сборке проекта появится объект, созвучный классу фрагмента с постфиксом "Directions", на котором возможен вызов перехода одноименного тому, который задан в навигационном графе:
```
val action = AFragmentDirections.actionAToB(DATA = DataClass(0, true, "Hello world"));
Fragment.findNavController().navigate(action);
```

Но и принимающий данные фрагмент должен о них знать. Для этого в навигационном графе прописываем аргументы с указанием базового (примитивного) типа данных или вашего, реализующего интерфейсы Serializable или Parcelable, задаем значения по умолчанию или разрешаем NULL-значения:  
```
<fragment
        android:id="@+id/screen_b"
        ...>
    <argument
        android:name="DATA"
        android:defaultValue="@null"
        app:argType="your.packages.DataClass"
        app:nullable="true"/>
</fragment>
```

Чтобы прочитать данные из действия-перехода необходимо на объекте, созвучному классу фрагмента с постфиксом "Args", получить набор данных, следующим образом:
```
val args: BFragmentArgs by navArgs()
if (args.DATA != null) // do something
```

## Привязки компонентов (ViewBinding)
Все компоненты, которым вы указали идентификаторы, можно получить через связанный объект, созвучный с именами макетов (не классов фрагментов, а именно layout-файлов) с постфиксом "Binding". Допустим в качестве файла макета выбран "view.xml", тогда получается объект "ViewBinding".

Компонентам этого макета можно будет обратиться как к публичным свойствам (параметрам) класса, созвучным их идентификатам в XML-файле. Допустим нам нужна кнопка с идентификатором "clickable_btn", то и параметр будет "clickableBtn":
```
<Button
    android:id="@+id/clickable_btn" 
    .../>
    
binding.clickableBtn;
```

Ссылку на объект привязки стоит открывать в onCreate-методах и сбрасывать в onDestroy:
```
private var _binding: FragmentStartBinding? = null
    private val binding: FragmentStartBinding
        get() = _binding ?: throw RuntimeException()

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
    this.binding = FragmentBinding.inflate(inflater, container, false);
    return binding.getRoot();
}

@Override
public void onDestroyView() {
    super.onDestroyView();
    _binding = null;
}
```
