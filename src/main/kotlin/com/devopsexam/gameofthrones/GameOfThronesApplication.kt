package com.devopsexam.gameofthrones

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackages = ["com.devopsexam.gameofthrones.models.entity"])
class GameofthronesApplication{}

fun main(args: Array<String>) {
    SpringApplication.run(GameofthronesApplication::class.java, *args)
}
