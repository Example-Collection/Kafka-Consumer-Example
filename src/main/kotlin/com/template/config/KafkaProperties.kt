package com.template.config

import org.springframework.context.annotation.Configuration

@Configuration
class KafkaProperties {

    private val bootStrapServer = "localhost:9092"
    private val consumer = mutableMapOf<String, String>()

    fun getConsumerProps(): Map<String, Any> {
        val properties = this.consumer
        properties.putIfAbsent("bootstrap.servers", this.bootStrapServer)
        properties.putIfAbsent("group.id", "sample_consumer")
        properties.putIfAbsent("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
        properties.putIfAbsent("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
        return properties
    }
}