package io.remicro.recalcite.proto.mapper

import org.apache.calcite.sql.type.SqlTypeName

enum class ColumnType {
    NULL,
    INT, LONG, DOUBLE, FLOAT,
    STRING,
    BOOL,
    DATE, TIMESTAMP,
    BIG_DECIMAL,
    BYTE_ARRAY,
    UUID,
    ANY
}

fun SqlTypeName.toColumnType(): ColumnType {
    return when (this) {
        SqlTypeName.INTEGER -> ColumnType.INT
        SqlTypeName.DOUBLE -> ColumnType.DOUBLE
        SqlTypeName.FLOAT -> ColumnType.FLOAT
        in SqlTypeName.BOOLEAN_TYPES -> ColumnType.BOOL
        in SqlTypeName.STRING_TYPES -> ColumnType.STRING
        SqlTypeName.DATE -> ColumnType.DATE
        SqlTypeName.TIMESTAMP -> ColumnType.TIMESTAMP
        SqlTypeName.ANY -> ColumnType.ANY
        else -> throw IllegalArgumentException("unknown column type ${this}")
    }
}
