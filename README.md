# Работа с сетью с помощью Ktor клиента

Ktor — это асинхронная платформа для создания микросервисов, веб-приложений и многого другого, написанная на Kotlin. В том числе его можно использовать в качестве HTTP-клиента для Android приложений. https://ktor.io/docs/client-engines.html

## Зависимости

Само по себе мобильное приложение не имеет прав на использование сети. Добавим это разрешение и разрешение на проверку состояния сети в файле AndroidManifest.xml:
```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

Для использования библиотеки Ktor требуется добавить зависимости в build.gradle.kts уровня Модуля:
```
implementation("io.ktor:ktor-client-core:3.3.1")
```

По мимо этого требуется подключение различных вспомогательных движков, как то например, для сетевого подключения, логирования, сериализации данных в заданный формат (json):
```
// Ktor client to Android
implementation("io.ktor:ktor-client-android:3.3.1")
implementation("io.ktor:ktor-client-logging:3.3.1")
// Json serialization in Ktor
implementation("io.ktor:ktor-client-serialization:3.3.1")
implementation("io.ktor:ktor-client-content-negotiation:3.3.1")
implementation("io.ktor:ktor-serialization-kotlinx-json:3.3.1")
```

## Описание данных (models)

Структура документов, которые будут читаться из сети должны быть описана в "data class" дополненном аннотацией @Serializable.

Чаще всего библиотеки для конвертирования, могут собирать созвучные имена пропуская символы-разделители, например "user_name" из json будет записан в параметр "userName". Но в случае сильного расхождения, можно указать соответствие названий через аннотацию @SerialName:
```
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DenoJoke(val id: Int, @SerialName("punchline") val delivery: String) {}
```

Значения примитивных типов будут записаны, а вот для иных значений стоит убедится в наличии соответствующего сериализуемого-класса в вашем проекте.

## Описание API (service)

Следующий шаг заключается в описании контракта (интерфейса) всех возможных запросов к серверу, с указанием возвращаемых данных, входных параметров, с возможной перегрузкой методов или передаваемыми параметрами по умолчанию. Данные методы должны спровождаться ключевым словом suspend 

Фактически для Ktor-клиента данный этап можно пропускать

## Создание клиента

Для того чтобы начать взаимодействие с сетью необходимо создать Ktor-клиент, в котором будет прописан формат сериализации данных:
```
HttpClient(Android){
    install(ContentNegotiation) {
        json(Json {
            isLenient = true
            prettyPrint = true
        })
    }
}
```

При создании клиента можно прописать хост-адрес сервера данных, формат логирования запросов, поведение при отказе сети и пр.:
```
HttpClient(Android){
    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
            host = "v2.jokeapi.dev"
            path("joke/")
        }
    }
    
    // install (){}
}
```

Старайтесь переиспользовать ранее созданный клиент, а не пересоздавать его под каждый запрос. Для этого стоит использовать механизм доступа c "ленивой" инициализацией:
```
private val client: HttpClient by lazy {}
```

Данный клиент должен реализовать описанный ранее интерфейс. В случае Ktor-клиента каждый запрос требует индивидуальной настройки: пути, параметров запроса, заголовков запроса (по требованию):
```
override suspend fun getJokes(): List<ApiJoke> {
    return withContext(Dispatchers.IO) {
        val response: HttpResponse = client.request { // client.get ("Programming?amount=5")
            method = HttpMethod.Get
            url {
                path("Programming")
                parameter("amount", 5)
            }
        }

        Log.d("TAG_ApiKtorClient", "loading in ${Thread.currentThread().name} request $response")
        Log.d("TAG_ApiKtorClient", "data ${response.bodyAsText()}")

        (response.body() as ApiJokesList).jokes
    }
}
```

