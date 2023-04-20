package com.example.samplecroutine

import android.os.Bundle
import androidx.activity.ComponentActivity
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * launch{}パターン
     */
    fun main() {
        val time = measureTimeMillis {
            // runBlockingはsuspend関数ではないため、suspendはつけなくて良い
            // runBlocking{}は同期的なので、ブロック内の処理は順次呼び出され、全ての処理が終了したら戻る。
            runBlocking {
                println("step1")
                // launch{}でコルーチン起動。(launch{}ごとにコルーチンがある)
                // launch{}はブロック内の処理が終了するのを待たずに戻るため、すぐに次の処理に移行する。
                // 非同期関数(launch{})は、関数が戻っても、タスクはまだ終了していない。
                launch { printForecast() }
                launch { printTemperature() }
                println("step2")
            }
        }
        println("Execution time: ${time / 1000.0} seconds")
    }

    // suspend関数は全ての処理が終わってから戻されるので、runBlocking{}内では順次実行されるようになる。
    private suspend fun printForecast() {
        // delay()はsuspend関数
        delay(1000)
        println("Sunny")
    }

    private suspend fun printTemperature() {
        delay(1000)
        println("30\\u00b0C")
    }

    /**
     * async{}パターン
     */
    fun main2() {
        val time = measureTimeMillis {
            runBlocking {
                println("step1")
                // コルーチンからの戻り値が必要な場合はasync{}を使用する。
                // async関数はDeferred型のオブジェクト(準備ができたらそこに結果が入るお約束のようなもの)を返す。
                // await()を使用してDeferredオブジェクトの結果にアクセスできる。
                val forecast: Deferred<String> = async { getForecast() }
                val temperature: Deferred<String> = async { getTemperature() }
                println("step2")
                println("${forecast.await()}, ${temperature.await()}")
                println("step3")
            }
        }
        println("Execution time: ${time / 1000.0} seconds")
    }

    // suspend関数は全ての処理が終わってから戻されるので、runBlocking{}内では順次実行されるようになる。
    private suspend fun getForecast(): String {
        // delay()はsuspend関数
        delay(1000)
        return "Sunny"
    }

    private suspend fun getTemperature(): String {
        delay(1000)
        return "30\\u00b0C"
    }
}