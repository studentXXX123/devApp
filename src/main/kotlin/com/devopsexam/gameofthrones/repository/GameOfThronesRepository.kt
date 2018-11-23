package com.devopsexam.gameofthrones.repository

import com.devopsexam.gameofthrones.models.entity.GameOfThrones
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface GameOfThronesRepository : CrudRepository<GameOfThrones, Long> {

    fun findByCharacterName(characterName: String): Iterable<GameOfThrones>

    fun findAllByCharacterNameContainingIgnoreCase(characterName: String): Iterable<GameOfThrones>
}