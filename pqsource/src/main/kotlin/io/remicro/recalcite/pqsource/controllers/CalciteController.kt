package io.remicro.recalcite.pqsource.controllers

import io.remicro.recalcite.pqsource.dto.CalciteQueryRequest
import io.remicro.recalcite.proto.mapper.RowItem
import io.remicro.recalcite.proto.mapper.Table
import io.remicro.recalcite.proto.mapper.fillFromResultSet
import io.remicro.recalcite.proto.mapper.toColumnType
import org.apache.calcite.adapter.jdbc.JdbcSchema
import org.apache.calcite.jdbc.CalciteConnection
import org.apache.calcite.schema.Schema
import org.springframework.web.bind.annotation.*
import java.sql.Connection

@RestController
@RequestMapping("/api/v1/calcite")
class CalciteController(
    val conn: Connection,
    val schema: JdbcSchema
) {
    @PutMapping("/query")
    fun query(@RequestBody req: CalciteQueryRequest): List<List<RowItem>> {
        val statement = conn.createStatement()
        val result = mutableListOf<List<RowItem>>()
        statement.use { stmt ->
            val res = stmt.executeQuery(req.query)
            res.let { while (res.next()) result.fillFromResultSet(res) }
        }
        return result
    }

    @GetMapping("/schema")
    fun schema(): List<Table> {
        conn.metaData
        val c = conn as CalciteConnection
        return schema.tableNames
            .asSequence()
            .filter { !it.startsWith("databasechangelog") }
            .map { Pair(it, schema.getTable(it)) }
            .filter { it.second.jdbcTableType == Schema.TableType.TABLE }
            .map {
                
                val columns = it.second.getRowType(c.typeFactory).fieldList.map { col ->
                    RowItem(
                        name = col.name,
                        type = col.type.sqlTypeName.toColumnType()
                    )
                }
                Table(name = it.first, columns = columns)
            }
            .toList()
    }
}
