package com.droid.newsapiclient.data.util.extensions


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.View.*
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.droid.newsapiclient.R
import com.google.android.material.snackbar.Snackbar


fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) VISIBLE else GONE
}

fun View.show() {
    visibility = VISIBLE
}

fun View.hide() {
    visibility = GONE
}

fun View.makeInvisible() {
    visibility = INVISIBLE
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun View.showSnackbar(message: String, length: Int) {
    val snackbar = Snackbar.make(this, message, length)
    snackbar.apply {
        setTextColor(ContextCompat.getColor(this.context, android.R.color.white))
        this.setBackgroundTint(ContextCompat.getColor(context, R.color.primaryColor))
        show()

    }
}


fun View.showErrorSnackbar(message: String, length: Int) {
    val snackbar = Snackbar.make(this, message, length)

    snackbar.apply {
        this.setBackgroundTint(ContextCompat.getColor(this.context, android.R.color.holo_red_light))
        this.setTextColor(ContextCompat.getColor(this.context, android.R.color.white))
        show()
    }
}

fun View.showSuccessSnackbar(message: String, length: Int) {
    val snackbar = Snackbar.make(this, message, length)

    snackbar.apply {
        this.setBackgroundTint(
            ContextCompat.getColor(view.context, R.color.primaryColor)
        )
        this.setTextColor(ContextCompat.getColor(this.context, android.R.color.white))
        show()
    }
}

fun Activity.hideSoftInput() {
    val inputmethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputmethodManager.hideSoftInputFromWindow(getView().windowToken, 0)
}

fun Activity.getView(): View {
    return window.decorView.rootView
}

fun View.showRetrySnackBar(message: String, action: ((View) -> Unit)?) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)

    snackbar.apply {
        this.setBackgroundTint(ContextCompat.getColor(this.context, android.R.color.holo_red_light))

        val colorWhite = ContextCompat.getColor(this.context, android.R.color.white)
        this.setTextColor(colorWhite)
        this.setActionTextColor(colorWhite)
        setAction("RETRY") {
            dismiss()
            action?.invoke(this@showRetrySnackBar)
        }
        show()

    }
}

internal inline fun <reified T> Activity.navigateTo(
    clearTask: Boolean = false,
    noinline intentExtras: ((Intent) -> Unit)? = null
) {

    val intent = Intent(this, T::class.java)

    intentExtras?.run {
        intentExtras(intent)
    }

    if (clearTask) {
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    startActivity(intent)
}