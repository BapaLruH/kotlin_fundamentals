package ru.movie.app.data.service

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.movie.app.BuildConfig
import java.util.concurrent.TimeUnit

object RetrofitModule {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("", BuildConfig.API_KEY).build()
            return@addInterceptor chain.proceed(request)
        }
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val retrofit = Retrofit.Builder().client(client).baseUrl(BuildConfig.BASE_URL)
        .addCallAdapterFactory(ResultAdapterFactory())
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val configurationApi = retrofit.create(ConfigurationApi::class.java)
    val movieApi = retrofit.create(MovieApi::class.java)
    val genresApi = retrofit.create(GenresApi::class.java)
    val personApi = retrofit.create(PersonApi::class.java)
    val searchApi = retrofit.create(SearchApi::class.java)
}