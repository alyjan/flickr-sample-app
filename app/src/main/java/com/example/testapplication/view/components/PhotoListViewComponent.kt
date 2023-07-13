package com.example.testapplication.view.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.interview.databinding.ViewComponentPhotoListBinding
import com.example.testapplication.adapters.PhotoAdapter
import com.example.testapplication.data.Photo
import com.example.testapplication.view.bas.ViewComponent
import com.example.testapplication.view.base.ActionHandler
import com.example.testapplication.view.base.ViewState

class PhotoListViewComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ViewComponent<ViewPhotoListComponentState> {

    override lateinit var actionHandler: ActionHandler

    var photoAdapter: PhotoAdapter? = null
    var binding: ViewComponentPhotoListBinding

    init {
        binding = ViewComponentPhotoListBinding.inflate(LayoutInflater.from(context), this, true)
    }

    override fun handleState(viewState: ViewPhotoListComponentState) {
        when (viewState) {
            is ViewPhotoListComponentState.DisplayPhotos -> {
                viewState.apply {
                    binding.photoGrid.isVisible = photoGridVisible
                    binding.loading.isVisible = loadingVisible
                    photoAdapter?.updatePhotos(photos, resetData)
                }
            }
            is ViewPhotoListComponentState.Loading -> {
                photoAdapter = PhotoAdapter(actionHandler)
                binding.photoGrid.adapter = photoAdapter
            }
        }
    }
}

sealed class ViewPhotoListComponentState: ViewState {
    object Loading: ViewPhotoListComponentState()
    data class DisplayPhotos(
        val photos: List<Photo>,
        val resetData: Boolean,
        val photoGridVisible: Boolean = true,
        val loadingVisible: Boolean = false
    ): ViewPhotoListComponentState()
}