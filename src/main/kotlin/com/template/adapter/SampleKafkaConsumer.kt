package com.template.adapter

import com.fasterxml.jackson.databind.ObjectMapper
import com.template.config.KafkaProperties
import com.template.domain.event.SampleEvent
import com.template.sample.SampleService
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.errors.WakeupException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Service
class SampleKafkaConsumer(
    kafkaProperties: KafkaProperties,
    private val sampleService: SampleService
    ) {

    private val logger = LoggerFactory.getLogger(SampleKafkaConsumer::class.java)

    companion object {
        private const val TOPIC_MESSAGE = "TOPIC_MESSAGE"
    }


    private val isClosed = AtomicBoolean(false)
    private var consumer = KafkaConsumer<String, String>(kafkaProperties.getConsumerProps())
    private val executorService = Executors.newCachedThreadPool()

    @PostConstruct
    fun start() {
        logger.info("Kafka consumer starting..")
        Runtime.getRuntime().addShutdownHook(Thread(this::shutdown))
        consumer.subscribe(Collections.singleton(TOPIC_MESSAGE))
        logger.info("Kafka consumer started.")

        executorService.execute {
            try {
                while(!isClosed.get()) {
                    val records: ConsumerRecords<String, String> = consumer.poll(Duration.ofSeconds(3))
                    for(record in records) {
                        logger.info("Consumed message in $TOPIC_MESSAGE: ${record.value()}")
                        val objectMapper = ObjectMapper()
                        val sampleEvent = objectMapper.readValue(record.value(), SampleEvent::class.java)
                        sampleService.onMessage(sampleEvent.messageOne, sampleEvent.messageTwo)
                    }
                }
                consumer.commitSync()
            } catch(e: WakeupException) {
                if(!isClosed.get()) throw e
            } catch(e: Exception) {
                logger.error(e.message, e)
            } finally {
                logger.info("Closing Kafka Consumer")
                consumer.close()
            }
        }
    }

    @PreDestroy
    fun shutdown() {
        logger.info("Shutting down Kafka Consumer")
        isClosed.set(true)
        consumer.wakeup()
    }

}