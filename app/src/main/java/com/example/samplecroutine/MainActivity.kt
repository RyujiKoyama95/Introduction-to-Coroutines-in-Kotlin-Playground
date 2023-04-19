package com.example.samplecroutine

import android.os.Bundle
import androidx.activity.ComponentActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // runBlockingはsuspend関数ではないため、suspendはつけなくて良い
        // runBlocking{}は同期的。
        runBlocking {
            println("Weather forecast")
            // runBlocking{}に処理を渡しているので、runBlocking{}にsuspendはつけなくて良い
            printForecast()
        }
    }

    private suspend fun printForecast() {
        // delay()はsuspend関数
        delay(1000)
        println("Sunny")
    }
}