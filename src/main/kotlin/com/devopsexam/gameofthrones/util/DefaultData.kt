package com.devopsexam.gameofthrones.util

import com.devopsexam.gameofthrones.models.dto.GameOfThronesDto
import com.devopsexam.gameofthrones.service.GameOfThronesService
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class DefaultData {

    @Autowired
    private lateinit var gameOfThronesService: GameOfThronesService

    @PostConstruct
    fun initializeDefault() {

        val fileContent = this::class.java.classLoader.getResource("game-of-thrones.json").readText()

        val gameofthronesCharacters: List<GameOfThronesDto> = Gson().fromJson(fileContent, object : TypeToken<List<GameOfThronesDto>>() {}.type)

        gameOfThronesService.createCharactersFromJson(gameofthronesCharacters)
    }
}