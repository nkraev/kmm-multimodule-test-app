package com.example.wbdtestapp.android.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

fun <T> Flow<T>.handleError(action: (Throwable) -> Unit) = catch { action(it) }