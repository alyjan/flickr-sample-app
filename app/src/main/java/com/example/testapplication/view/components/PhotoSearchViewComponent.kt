package com.example.testapplication.view.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.interview.databinding.ViewComponentPhotoSearchBinding
import com.example.testapplication.view.bas.ViewComponent
import com.example.testapplication.view.base.Action
import com.example.testapplication.view.base.ActionHandler
import com.example.testapplication.view.base.ViewState

class PhotoSearchViewComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ViewComponent<ViewPhotoSearchComponentState> {

    override lateinit var actionHandler: ActionHandler

    private var binding: ViewComponentPhotoSearchBinding

    init {
        binding = ViewComponentPhotoSearchBinding.inflate(LayoutInflater.from(context), this, true)
        binding.container.setEndIconOnClickListener {
            val query = binding.search.text.toString()
            actionHandler.handleAction(
                ViewPhotoSearchComponentAction.Search(
                    query = query
                )
            )
        }
    }

    override fun handleState(viewState: ViewPhotoSearchComponentState) {}
}

sealed class ViewPhotoSearchComponentState: ViewState

sealed class ViewPhotoSearchComponentAction: Action {
    data class Search(
        val query: String
    ): ViewPhotoSearchComponentAction()
}