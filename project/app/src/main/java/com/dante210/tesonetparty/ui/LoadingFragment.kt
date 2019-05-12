package com.dante210.tesonetparty.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dante210.tesonetparty.R

class LoadingFragment : Fragment() {

  companion object {
    fun newInstance() = LoadingFragment()
  }

  private lateinit var viewModel: LoadingViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.loading_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(LoadingViewModel::class.java)
    // TODO: Use the ViewModel
  }

}
