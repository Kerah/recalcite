package io.remicro.recalcite.pqsource.controllers

import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.remicro.recalcite.pqsource.dto.CalciteQueryRequest
import io.remicro.recalcite.proto.mapper.RowItem
import io.remicro.recalcite.proto.mapper.fillFromResultSet
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.sql.Connection
import java.sql.Date
import java.sql.Timestamp
import java.util.*

@RestController
@RequestMapping("/api/v1/calcite")
class CalciteController(
    val conn: Connection
) {
    @PutMapping("/query")
    fun query(@RequestBody req: CalciteQueryRequest): List<List<RowItem>> {
        val statement = conn.createStatement()
        val result = mutableListOf<List<RowItem>>()
        statement.use { stmt ->
            val res = stmt.executeQuery(req.query)
            res.let { while(res.next()) result.fillFromResultSet(res) }
        }
        return result
    }
}
