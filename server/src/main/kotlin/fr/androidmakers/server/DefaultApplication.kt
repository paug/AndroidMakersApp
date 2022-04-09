package fr.androidmakers.server

import org.springframework.boot.ConfigurableBootstrapContext
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext

@SpringBootApplication
class DefaultApplication {
}

fun runServer(): ConfigurableApplicationContext {
  CachedData.initialize()
  return runApplication<DefaultApplication>()
}



