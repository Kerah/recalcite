package io.remicro.recalcite.pqsource.controllers

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class NodeInfo(
    val name: String
)

@RestController
@RequestMapping("/api/v1/who/am")
class WhoAmMeController {
    @RequestMapping("/me")
    fun me() = NodeInfo("pqsource")
}