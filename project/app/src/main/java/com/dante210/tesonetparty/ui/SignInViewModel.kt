package com.dante210.tesonetparty.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dante210.tesonetparty.data.*
import com.dante210.tesonetparty.repositories.UserRemoteRepository
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class SignInViewModel(private val remoteRepository: UserRemoteRepository) : ViewModel() {
  companion object {
    val module = module {
      viewModel { SignInViewModel(get()) }
    }
  }

  val usernameRx = MutableLiveData<Username>()
  val passwordRx = MutableLiveData<Password>()

  fun onSignInClick() {
    handleSignIn()
      .fold(
        onLeft = { tokenSingle ->
            tokenSingle
              .subscribeOn(Schedulers.io())
              .observeOn(Schedulers.single())
              .subscribeBy(
                onError = { throwable -> Log.e("TEST", throwable.message) },
                onSuccess = { token -> Log.d("TEST", "Token = $token") })
            Unit
        },
        onRight = { errorMsg ->
          Log.e("TEST", "error = $errorMsg")
          Unit
        }
      )
  }

  private fun handleSignIn(): Result<Single<Token>, ErrorMsg> {
    val username = usernameRx.value
    val password = passwordRx.value

    if (username != null && password != null) {
      return when {
        username.value.isBlank() -> ErrorMsg("Please enter your username").toError()
        password.value.isBlank() -> ErrorMsg("Please enter your password").toError()
        else -> {
          Success(remoteRepository.signIn(username, password))
        }
      }
    }

    return ErrorMsg("Oops, something went wrong. Please try again").toError()
  }
}
