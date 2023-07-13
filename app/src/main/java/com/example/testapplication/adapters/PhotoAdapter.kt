package com.example.testapplication.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.interview.R
import com.example.testapplication.view.base.Action
import com.example.testapplication.view.base.ActionHandler
import com.example.testapplication.data.Photo
import com.example.testapplication.view.components.PhotoDialogFragment

open class PhotoAdapter(private val actionHandler: ActionHandler) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    private val photos: MutableList<Photo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position])
        if (position == photos.size - 1) {
            loadMoreItems()
        }
    }

    override fun getItemCount(): Int = photos.size

    fun updatePhotos(newPhotos: List<Photo>, resetData: Boolean) {
        if (resetData) {
            photos.clear()
        }
        photos.addAll(newPhotos)
        notifyDataSetChanged()
    }

    private fun loadMoreItems() {
        actionHandler.handleAction(PhotoAction.FetchNextPage)
    }

    inner class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.imageView)
        private val progressBar: ProgressBar = view.findViewById(R.id.image_loading_progress)

        fun bind(photo: Photo) {
            // This is how the URL is formatted according to the documentation
            val imageUrl = "https://live.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg"
            Glide.with(itemView)
                .load(imageUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(imageView)

            itemView.setOnClickListener {
                val photoDialog = PhotoDialogFragment.newInstance(photo)
                photoDialog.show((itemView.context as AppCompatActivity).supportFragmentManager, "photoDialog")
            }
        }
    }
}

sealed class PhotoAction {
    object FetchNextPage: Action
}