package com.devopsexam.gameofthrones.util


import com.devopsexam.gameofthrones.models.dto.GameOfThronesDto
import com.devopsexam.gameofthrones.models.entity.GameOfThrones

class GameOfThronesConverter {

    companion object {

        fun convertFromDto(gameOfThronesDto: GameOfThronesDto): GameOfThrones {
            return GameOfThrones(
                    gameOfThronesDto.characterName!!, gameOfThronesDto.houseName, gameOfThronesDto.royal,
                    gameOfThronesDto.parents, gameOfThronesDto.killedBy, gameOfThronesDto.characterImageThumb, gameOfThronesDto.characterImageFull,
                    gameOfThronesDto.killed, gameOfThronesDto.parentOf, gameOfThronesDto.siblings
            )
        }

        fun convertFromDtoMap(gameOfThronesDto: Iterable<GameOfThronesDto>): List<GameOfThrones> {
            return gameOfThronesDto.map { convertFromDto(it) }
        }

        fun convertToDto(gameOfThrones: GameOfThrones) : GameOfThronesDto {
            return GameOfThronesDto(
                    gameOfThrones.id.toString(),
                    gameOfThrones.characterName,
                    gameOfThrones.houseName,
                    gameOfThrones.royal,
                    gameOfThrones.parents,
                    gameOfThrones.killedBy,
                    gameOfThrones.characterImageThumb,
                    gameOfThrones.characterImageFull,
                    gameOfThrones.killed,
                    gameOfThrones.parentOf,
                    gameOfThrones.siblings
            )
        }

        fun convertToDtoMap(gameOfThrones: Iterable<GameOfThrones>) : List<GameOfThronesDto> {
            return gameOfThrones.map { convertToDto(it) }
        }
    }

}