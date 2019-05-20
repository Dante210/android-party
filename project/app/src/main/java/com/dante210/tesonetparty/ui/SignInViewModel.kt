package com.dante210.tesonetparty.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dante210.tesonetparty.api.TesonetApi
import com.dante210.tesonetparty.data.*
import com.dante210.tesonetparty.repositories.UserRemoteRepository
import io.reactivex.Single
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class SignInViewModel(private val remoteRepository: UserRemoteRepository, private val context: Context) : ViewModel() {
  companion object {
    val module = module {
      viewModel { SignInViewModel(get(), get()) }
    }
  }

  val usernameRx = MutableLiveData<Username>()
  val passwordRx = MutableLiveData<Password>()

  fun onSignInClick(): Either<ErrorMsg, Single<TesonetApi.Token>> {
    val username = usernameRx.value
    val password = passwordRx.value

    if (username != null && password != null) {
      return when {
        username.value.isBlank() -> ErrorMsg("Please enter your username").toError()
        password.value.isBlank() -> ErrorMsg("Please enter your password").toError()
        else -> {
          Right(remoteRepository.signIn(username, password))
        }
      }
    }

    return ErrorMsg("Oops, something went wrong. Please try again").toError()
  }
}
