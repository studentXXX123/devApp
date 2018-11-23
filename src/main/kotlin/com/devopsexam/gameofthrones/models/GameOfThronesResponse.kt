package com.devopsexam.gameofthrones.models

import com.devopsexam.gameofthrones.models.dto.GameOfThronesDto
import com.devopsexam.gameofthrones.models.hal.PageDto

class GameOfThronesResponse(
        code: Int? = null,
        data: PageDto<GameOfThronesDto>? = null,
        message: String? = null,
        status: WrappedResponse.ResponseStatus? = null
) : WrappedResponse<PageDto<GameOfThronesDto>>(code, data, message, status)