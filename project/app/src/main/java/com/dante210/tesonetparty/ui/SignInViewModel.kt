package com.dante210.tesonetparty.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dante210.tesonetparty.data.*
import com.dante210.tesonetparty.repositories.UserRemoteRepository
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class SignInViewModel(private val remoteRepository: UserRemoteRepository, private val context: Context) : ViewModel() {
  companion object {
    val module = module {
      viewModel { SignInViewModel(get(), get()) }
    }

    private val TAG : String = SignInViewModel::class.java.simpleName
  }

  val usernameRx = MutableLiveData<Username>()
  val passwordRx = MutableLiveData<Password>()

  fun onSignInClick() {
    handleSignIn()
      .fold(
        onLeft= { errorMsg ->
          Toast.makeText(context, errorMsg.value, Toast.LENGTH_SHORT).show()
          Unit
        },
        onRight = { tokenSingle ->
            tokenSingle
              .subscribeOn(Schedulers.io())
              .observeOn(Schedulers.single())
              .subscribeBy(
                onError = { throwable ->
                  Log.e(TAG, throwable.message)
                  // Todo give feedback to the user
                },
                onSuccess = { token -> Log.d("TEST", "Token = ${token.value}") })
            Unit
        }
      )
  }

  private fun handleSignIn(): Either<ErrorMsg, Single<Token>> {
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
