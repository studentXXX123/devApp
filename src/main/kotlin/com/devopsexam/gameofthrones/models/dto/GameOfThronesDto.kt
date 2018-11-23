package com.devopsexam.gameofthrones.models.dto


data class GameOfThronesDto(

        var id: String? = null,

        var characterName: String? = null,

        var houseName: MutableSet<String>? = null,

        var royal: Boolean? = null,

        var parents: MutableSet<String>? = null,

        var killedBy: MutableSet<String>? = null,

        var characterImageThumb: String? = null,

        var characterImageFull: String? = null,

        var killed: MutableSet<String>? = null,

        var parentOf: MutableSet<String>? = null,

        var siblings: MutableSet<String>? = null

)