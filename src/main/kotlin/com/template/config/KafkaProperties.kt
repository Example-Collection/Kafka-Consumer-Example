package com.template.config

class KafkaProperties {

    private val bootStrapServer = "localhost:8081"
    private val consumer = mutableMapOf<String, String>()

    public fun getConsumerProps(): Map<String, Any> {
        val properties = this.consumer
        properties.putIfAbsent("bootstrap.server", this.bootStrapServer)
        return properties
    }
}