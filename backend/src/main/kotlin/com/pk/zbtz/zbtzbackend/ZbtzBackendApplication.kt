package com.pk.zbtz.zbtzbackend

import com.pk.zbtz.zbtzbackend.databases.mondodb.services.DummyMongoDataService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST","PUT", "DELETE")
    }
}

@SpringBootApplication
class ZbtzBackendApplication {
    @Bean
    fun init(args: ApplicationArguments, dummyMongoDataService: DummyMongoDataService) = CommandLineRunner {
        if (args.sourceArgs.contains("generateMongoData")) {
            dummyMongoDataService.clearAndGenerateDummyData()
        }
        dummyMongoDataService.addDummyPlatformsAndGenres()
    }
}

fun main(args: Array<String>) {
    runApplication<ZbtzBackendApplication>(*args)
}
