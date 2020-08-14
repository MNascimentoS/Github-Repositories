package br.com.mateus.githubrepositories.utils

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

inline fun <T> LifecycleOwner.observe(liveData: LiveData<T>, crossinline onChanged: (T) -> Unit) {
    liveData.observe(this, androidx.lifecycle.Observer { onChanged(it) })
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}