package com.devopsexam.gameofthrones.controller

import com.devopsexam.gameofthrones.models.WrappedResponse
import com.devopsexam.gameofthrones.models.dto.GameOfThronesDto
import com.devopsexam.gameofthrones.models.hal.PageDto
import com.devopsexam.gameofthrones.service.GameOfThronesService
import com.codahale.metrics.MetricRegistry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

const val BASE_JSON = "application/json;charset=UTF-8"

@RequestMapping(
        path = ["/gameofthrones"],
        produces = [BASE_JSON]
)

@RestController
class GameOfThronesController {

    @Value("\${server.servlet.context-path}")
    private lateinit var contextPath : String

    @Autowired
    private lateinit var gameOfThronesService: GameOfThronesService

    @Autowired
    private lateinit var metrics : MetricRegistry

    @GetMapping
    fun getAll(
               @RequestParam("characterName", required = false)
               characterName : String?,

               @RequestParam("search", required = false)
               search : String?,

               @RequestParam("offset", defaultValue = "0")
               offset: Int,

               @RequestParam("limit", defaultValue = "10")
               limit: Int
    ): ResponseEntity<WrappedResponse<PageDto<GameOfThronesDto>>> {
        val counter = metrics.counter("Number of times for this request")
        counter.inc()

        val timer = metrics.timer("Time to GET character with limit $limit and offset $offset")
        val context = timer.time()

        try {
            return gameOfThronesService.findBy(characterName, search, offset, limit)
        } finally {
            context.stop()
        }
    }

    @PostMapping
    fun post(@RequestBody gameOfThronesDto: GameOfThronesDto) : ResponseEntity<WrappedResponse<PageDto<GameOfThronesDto>>> {
        return gameOfThronesService.createCharacter(gameOfThronesDto)
    }

    @GetMapping(path = ["/{id}"])
    fun get(
        @PathVariable("id")
        id: String?) : ResponseEntity<WrappedResponse<PageDto<GameOfThronesDto>>> {
        metrics.meter("Get character by id $id").mark()
        return gameOfThronesService.findById(id)
    }

    @PutMapping(path = ["/{id}"])
    fun update(
        @PathVariable("id")
        id: String?,
               @RequestBody gameOfThronesDto: GameOfThronesDto) : ResponseEntity<WrappedResponse<PageDto<GameOfThronesDto>>> {
        metrics.meter("Update a character with id $id").mark()
        return gameOfThronesService.update(id, gameOfThronesDto)
    }

    @DeleteMapping(path = ["/{id}"])
    fun delete(
               @PathVariable("id")
               id: String?) : ResponseEntity<WrappedResponse<PageDto<GameOfThronesDto>>> {
        metrics.meter("Delete character with id $id").mark()
        return gameOfThronesService.delete(id)
    }


}