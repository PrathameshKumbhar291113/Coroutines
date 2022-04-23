package com.example.coroutinesapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutinesapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val TAG ="MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        GlobalScope.launch{
//            delay(3000)
//                Log.d(TAG,"Hello From Coroutine and ${Thread.currentThread().name}")
            val networkCallAns =  doNetworkCall()
            val networkCallAns2 =  doNetworkCall2()

            Log.d(TAG,networkCallAns)
            Log.d(TAG,networkCallAns2)


        }
        GlobalScope.launch(Dispatchers.Main) {

            Log.d(TAG,"Hello From Main Activity Thread and ${Thread.currentThread().name}")

        }


        GlobalScope.launch(Dispatchers.IO) {

            Log.d(TAG,"This is the starting thread ${Thread.currentThread().name}")

            val answerTheIncomingCall = doNetworkCall()
            withContext(Dispatchers.Main){
                Log.d(TAG,"This is the setting text thread ${Thread.currentThread().name}")
                binding.tvDummy.text = answerTheIncomingCall
            }


        }


        GlobalScope.launch(Dispatchers.Default) {

            Log.d(TAG,"Hello From Main Activity Thread and ${Thread.currentThread().name}")

        }


        GlobalScope.launch(Dispatchers.Unconfined) {

            Log.d(TAG,"Hello From Main Activity Thread and ${Thread.currentThread().name}")

        }


        GlobalScope.launch { newSingleThreadContext("MyThread")

            Log.d(TAG,"Hello From Main Activity Thread and ${Thread.currentThread().name}")

        }
//        Run blocking starts the new coroutine in the main thread
//        Run blockin will block the main thread

        Log.d(TAG,"Before of the run blocking")
        runBlocking {
            launch {
//          Will lauch the other co routine in the background
            }
            Log.d(TAG,"Start of the run blocking")
            delay(300L)
            Log.d(TAG,"End of the run blocking")
        }

        Log.d(TAG,"After of the run blocking")
    }

    suspend fun  doNetworkCall() : String {
        delay(3000L)
        return "This is a answer"
    }

    suspend fun  doNetworkCall2() : String {
        delay(3000L)
        return "This is a answer2"
    }
}
