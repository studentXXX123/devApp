package com.devopsexam.gameofthrones.models.hal

open class HalObject(

        var _links: MutableMap<String, HalLink> = mutableMapOf()
)