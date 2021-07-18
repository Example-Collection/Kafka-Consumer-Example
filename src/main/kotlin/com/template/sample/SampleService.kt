package com.template.sample

import org.springframework.stereotype.Service

@Service
class SampleService {

    fun onMessage(messageOne: String, messageTwo: String) {
        println("===================================================")
        println("MESSAGE ONE : $messageOne")
        println("MESSAGE TWO : $messageTwo")
        println("===================================================")
    }
}