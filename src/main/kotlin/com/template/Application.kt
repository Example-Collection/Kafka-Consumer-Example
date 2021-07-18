package com.template

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration

//@SpringBootApplication(exclude=[DataSourceAutoConfiguration::class, HibernateJpaAutoConfiguration::class])
@SpringBootApplication(/*exclude=[DataSourceAutoConfiguration::class, HibernateJpaAutoConfiguration::class]*/)
//@Configuration
//@EnableAutoConfiguration(exclude=[DataSourceAutoConfiguration::class, HibernateJpaAutoConfiguration::class])
class Application {
}
fun main(args: Array<String>) {
    runApplication<Application>(*args)
}