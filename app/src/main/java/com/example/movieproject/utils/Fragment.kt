package com.example.movieproject.utils

import androidx.fragment.app.Fragment
import com.example.movieproject.ui.BaseActivity

fun Fragment.showLoading() {
    when (activity) {
        is BaseActivity -> (activity as BaseActivity).showLoading()
    }
}

fun Fragment.hideLoading() {
    when (activity) {
        is BaseActivity -> (activity as BaseActivity).hideLoading()
    }
}