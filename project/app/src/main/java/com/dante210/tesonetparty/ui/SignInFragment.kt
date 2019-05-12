package com.dante210.tesonetparty.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dante210.tesonetparty.R
import com.dante210.tesonetparty.data.Password
import com.dante210.tesonetparty.data.Username
import com.jakewharton.rxbinding.view.RxView
import com.jakewharton.rxbinding.widget.RxTextView
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.sign_in_fragment.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SignInFragment : Fragment() {
  companion object {
    private val TAG: String = SignInFragment::class.java.simpleName
  }

  private val signInModel: SignInViewModel by viewModel()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.sign_in_fragment, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val username = signInModel.usernameRx.value
    val password = signInModel.passwordRx.value

    if (username != null) username_field.setText(username.value)
    if (password != null) password_field.setText(password.value)

    RxTextView
      .textChanges(username_field)
      .subscribe { signInModel.usernameRx.postValue(Username(it.toString())) }

    RxTextView
      .textChanges(password_field)
      .subscribe { signInModel.passwordRx.postValue(Password(it.toString())) }

    RxView
      .clicks(sign_in_button)
      .subscribe {
        signInModel.onSignInClick()
          .fold(
            onLeft = { errorMsg ->
              Toast.makeText(context, errorMsg.value, Toast.LENGTH_SHORT).show()
              Unit
            },
            onRight = { tokenSingle ->
              tokenSingle
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                  onError = { throwable ->
                    Log.e(TAG, throwable.message)
                    // Todo sort out how to respond to the errors.
//                    Toast.makeText(context, throwable.message, Toast.LENGTH_SHORT).show()
                  },
                  onSuccess = { token ->
                    Log.d(TAG, "Token = ${token.value}")
                    findNavController().navigate(R.id.loadingFragment)
                  }
                )
              Unit
            }
          )
      }
  }
}
