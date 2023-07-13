package com.example.testapplication.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.interview.R
import com.example.interview.databinding.ActivityMainBinding
import com.example.testapplication.view.base.Action
import com.example.testapplication.view.base.ActionHandler
import com.example.testapplication.MyApplication
import com.example.testapplication.adapters.PhotoAction
import com.example.testapplication.adapters.PhotoAdapter
import com.example.testapplication.view.bas.ViewComponent
import com.example.testapplication.view.components.FlickrState
import com.example.testapplication.view.components.PhotoDialogFragment
import com.example.testapplication.viewmodel.PhotoViewModel
import com.example.testapplication.viewmodel.PhotoViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ActionHandler {

    @Inject
    lateinit var viewModelFactory: PhotoViewModelFactory

    private val viewModel: PhotoViewModel by viewModels { viewModelFactory }

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        (application as MyApplication).appComponent.inject(this)

        listOf<ViewComponent<*>?>(
            binding.photoList,
            binding.searchBar
        ).forEach {
            it?.actionHandler = this
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect {
                handleState(it)
            }
        }
    }

    private fun handleState(viewState: FlickrState) {
        viewState.run {
            error?.let { showError(it) }
            paginateLoading.let { binding.pageProgress.isVisible = paginateLoading }
            photoListComponentState?.let { binding.photoList.handleState(it) }
        }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.main, message, Snackbar.LENGTH_LONG).show()
    }

    override fun handleAction(action: Action) {
        viewModel.handleAction(action)
    }
}