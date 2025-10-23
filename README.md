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
if (savedInstanceState == null) {      // or use android:name in FragmentContainerView
  supportFragmentManager.commit {
      setReorderingAllowed(true)
      add<Fragment>(R.id.main)
  }
}
```

Когда работаем от имени деятельности (Activity), то всегда используем SupportFragmentManager. В текущем исполнении в деятельности тем или иным образом указывается только начальный фрагмент.

## Замена фрагментов
Когда заменяем фрагмент (replace), верхний в стеке FragmentManager-а фрагмент удаляется, а на его место помещается новый. При обратном вызове, если стек пустой, приложение закроется:
```
parentFragmentManager.commit {
    replace<AnotherFragment>(R.id.fragment_container_view)    // Kill this fragment and open new
}
```

Когда добавляем фрагмент (add), он ложится в стеке поверх предыдущего фрагмента. В случае свайпа, нажатие системной кнопки "назад" добавленный фрагмент будет удален из стека, а тот который находился под ним, снова выйдет на передний план. 
```
parentFragmentManager.commit {
    add<AnotherFragment>(R.id.fragment_container_view)   // Show new fragment on top of this (need background)
    addToBackStack("tag")
}
```

Функция addToBackStack() добавляет скрываемый фрагмент в стек. Можно добавить имя, по которому будет осуществляться поиск фрагмента в стеке для упрощения поиска, выборки или удаления из стека. Методы add и addToBackStack() должны идти в паре с методом popBackStack(), который возвращается к предыдущему фрагменту, или remove(), который удаляет определенный фрагмент из стека. Иначе количество "добавленных" фрагментов в стек окажется больше, чем количество самих созданных экранов:  
```
getParentFragmentManager().popBackStack()
```

Также можно использовать методы show() и hide(), чтобы возвращаться к фрагментам, которые уже были добавлены в стек FragmentManager-а.

У фрагмента есть доступ к ParentFragmentManager и ChildFragmentManager. Первый обращается к FragmentManager-у родительского компонента (породившего фрагмента или самой деятельности), а второй к стеку дочерних фрагментов.

## Запрос данных через дочерний фрагмент
Аналогично механизму startActivityForResult можно запустить фрагмент и "прослушивать" момент появление от него данных
```
parentFragmentManager.setFragmentResultListener("TAG", this) { key, bundle ->
    // TODO 
}
```

Дочерний фрагмент должен соответствующие данные вернуть:
```
val args = Bundle()
args.putString("KEY", "value");
getParentFragmentManager().setFragmentResult("TAG", args)
```
