package pdog18.com.core

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

private val baseUrl = "http://gank.io/api/"

private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(StethoInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

private val gson = GsonBuilder()
        .registerTypeAdapter(Double::class.java, JsonDeserializer { json, _, _ ->
            try {
                return@JsonDeserializer json.asDouble
            } catch (e: UnsupportedOperationException) {
                return@JsonDeserializer Double.NaN
            }
        })
        .registerTypeAdapter(Float::class.java, JsonDeserializer { json, _, _ ->
            try {
                return@JsonDeserializer json.asFloat
            } catch (e: UnsupportedOperationException) {
                return@JsonDeserializer Float.NaN
            }
        })
        .registerTypeAdapter(Long::class.java, JsonDeserializer { json, _, _ ->
            try {
                return@JsonDeserializer json.asLong
            } catch (e: UnsupportedOperationException) {
                return@JsonDeserializer 0L
            }
        })
        .registerTypeAdapter(Int::class.java, JsonDeserializer { json, _, _ ->
            try {
                return@JsonDeserializer json.asInt
            } catch (e: UnsupportedOperationException) {
                return@JsonDeserializer 0
            }
        })
        .create()

val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()!!

inline fun <reified T> createApi(): T {
    return retrofit.create(T::class.java)
}
