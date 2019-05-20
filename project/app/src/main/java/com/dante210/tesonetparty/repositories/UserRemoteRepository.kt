package com.dante210.tesonetparty.repositories

import com.dante210.tesonetparty.api.TesonetApi
import com.dante210.tesonetparty.data.Password
import com.dante210.tesonetparty.data.Username
import io.reactivex.Single
import org.koin.dsl.module

class UserRemoteRepository(private val tesonetApi: TesonetApi) {
  companion object {
    val module = module {
      single { UserRemoteRepository(get()) }
    }
  }

  // Todo Handle timeout, internet connection
  fun signIn(username: Username, password: Password) : Single<TesonetApi.Token> {
    return tesonetApi.signIn(username.value, password.value)
  }
}