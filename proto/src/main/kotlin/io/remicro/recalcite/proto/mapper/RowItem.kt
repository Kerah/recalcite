package io.remicro.recalcite.proto.mapper

import java.math.BigDecimal
import java.sql.Date
import java.sql.ResultSet
import java.sql.Timestamp
import java.util.*

data class RowItem(val type: ColumnType, val name: String, val value: Any? = null)

fun MutableList<List<RowItem>>.fillFromResultSet(set: ResultSet) {
    val md = set.metaData
    val columns = md.columnCount
    val row = mutableListOf<RowItem>()
    for (i in 1..columns) {
        val fieldName = md.getColumnName(i)
        when (val obj = set.getObject(i)) {
            null -> row.add(RowItem(ColumnType.NULL, fieldName))
            is Int -> row.add(RowItem(ColumnType.INT, fieldName, obj))
            is String -> row.add(RowItem(ColumnType.STRING, fieldName, obj))
            is Boolean -> row.add(RowItem(ColumnType.BOOL, fieldName, obj))
            is Date -> row.add(RowItem(ColumnType.DATE, fieldName, obj.toString()))
            is Long -> row.add(RowItem(ColumnType.LONG, fieldName, obj))
            is Double -> row.add(RowItem(ColumnType.DOUBLE, fieldName, obj))
            is Float -> row.add(RowItem(ColumnType.FLOAT, fieldName, obj))
            is BigDecimal -> row.add(RowItem(ColumnType.BIG_DECIMAL, fieldName, obj))
            is ByteArray -> row.add(RowItem(ColumnType.BYTE_ARRAY, fieldName, obj))
            is UUID -> row.add(RowItem(ColumnType.UUID, fieldName, obj))
            is Timestamp -> row.add(RowItem(ColumnType.TIMESTAMP, fieldName, obj.toString()))
            else -> throw IllegalArgumentException("Unmappable type: " + obj.javaClass.toString())
        }
    }
    add(row)
}
