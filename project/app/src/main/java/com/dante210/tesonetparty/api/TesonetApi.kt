package com.dante210.tesonetparty.api

import com.dante210.tesonetparty.MyApplication
import com.dante210.tesonetparty.data.Password
import com.dante210.tesonetparty.data.Token
import com.dante210.tesonetparty.data.Username
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.dsl.module
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TesonetApi {
  companion object {
    const val TOKENS_URL = "/v1/tokens"
    const val SERVERS_URL = "/v1/servers"

    val module = module {
      single { MyApplication.retrofit.create(TesonetApi::class.java) }
    }
  }

  @POST(TOKENS_URL) @FormUrlEncoded
  fun signIn(@Field("username") username : String, @Field("password") password : String) : Single<Token>
}

class TesonetInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()

    val builder = request.newBuilder()
      .addHeader("Accept", "application/json")
      .addHeader("Content-Type", "application/json")

    val isSignInRequest = request.url().toString().contains(TesonetApi.TOKENS_URL)
    if (isSignInRequest) builder.addHeader("Authorization", "Bearer $request")

    return chain.proceed(builder.build())
  }
}