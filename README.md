# Замена множественных деятельностей на фрагменты
## Зависимости
В build.gradle.kts уровня Модуля для использования фрагментов требуется добавить зависимость:
```
dependencies {
    implementation ("androidx.fragment:fragment-ktx:1.8.9")
}
```

## Удаление лишних деятельностей
В файле AndroidManifest.xml остается только одна стартовая деятельность (SingleActivity). Также можно перенести весь вспомогательный функционал (логировании, вывод предупреждений в Toast, Snackbar и иное) в одну деяетельность, так как прочие будут упразднены

## Верстка
В XML-макете для деятельности должен появится визуальный компонент FragmentContainerView для размещения в нем фрагментов интерфейса, содержащий атрибут "name" стартового фрагмента:
```
<androidx.fragment.app.FragmentContainerView
    android:name="your.packages.Fragment"
    android:id="@+id/fragment_container_view"
>
```

Либо стартовый фрагмент добавляется программно в методе onCreate() деятельности:
```
if (savedInstanceState == null) {
    supportFragmentManager.commit {
        setReorderingAllowed(true)
        add<Fragment>(R.id.fragment_container_view)
    }
}
```

Когда работаем от имени деятельности (Activity), то всегда используем SupportFragmentManager

## Замена фрагментов
Когда заменяем (replace()) фрагмент, верхний в стеке FragmentManager-а фрагмент удаляется, а на его место помещается новый. При обратном вызове, если стек пустой, приложение закроется:
```
supportFragmentManager.commit {
    replace(R.id.fragment_container_view, AnotherFragment::class.java, args)
    setReorderingAllowed(true)
}
```

Можно явно задать фрагмент, который будет добавлен, а также передать ему некоторый набор данных как набор пар "ключа-значения":
```
val args = Bundle()
args.putString ("KEY", "value")

val fragment = AnotherFragment()
fragment.arguments = args;

supportFragmentManager.commit {
    replace(R.id.fragment_container_view, fragment) // fragment with nested arguments
    setReorderingAllowed(true)
}
```

Функция addToBackStack() добавляет скрываемый фрагмент в стек. В случае свайпа, нажатие системной кнопки "назад" добавленный фрагмент будет удален из стека, а тот который находился под ним, снова выйдет на передний план. Можно добавить имя, по которому будет осуществляться поиск фрагмента в стеке для упрощения поиска, выборки или удаления из стека.
```
supportFragmentManager.commit {
    replace(R.id.fragment_container_view, AnotherFragment::class.java, args)
    setReorderingAllowed(true)
    addToBackStack("start")
}
```

Методы add и addToBackStack() должны идти в паре с методом popBackStack(), который возвращается к предыдущему фрагменту, или remove(), который удаляет определенный фрагмент из стека. Иначе количество "добавленных" фрагментов в стек окажется больше, чем количество самих созданных экранов:
```
supportFragmentManager.popBackStack()
```

Также можно использовать методы show() и hide(), чтобы возвращаться к фрагментам, которые уже были добавлены в стек FragmentManager-а.
