package com.example.subscription_dagger

import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

inline fun ComponentActivity.launchInLifecycle(
    dispatchers: CoroutineContext = Dispatchers.Main,
    crossinline block: suspend CoroutineScope.() -> Unit
) = lifecycleScope.launch(dispatchers) { block() }