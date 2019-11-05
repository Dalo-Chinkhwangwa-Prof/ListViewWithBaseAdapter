package com.example.myexternalapplication

import android.util.Log

object ErrorLogger {
    private const val ERROR_TAG = "TAG_ERROR"

    fun logThrowable(throwable: Throwable) {
        Log.e(ERROR_TAG, throwable.toString())
    }
}