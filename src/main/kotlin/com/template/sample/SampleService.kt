package com.template.sample

import org.springframework.stereotype.Service

@Service
class SampleService {

    fun onMessage(messageOne: String, messageTwo: String) {
        try {
            Thread.sleep(10000)
        } catch(e: InterruptedException) {
            e.printStackTrace()
        }
        println("===================================================")
        println("MESSAGE ONE : $messageOne")
        println("MESSAGE TWO : $messageTwo")
        println("===================================================")
    }
}