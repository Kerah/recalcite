package io.remicro.recalcite.proto.mapper

enum class ColumnType {
    NULL,
    INT, LONG, DOUBLE, FLOAT,
    STRING,
    BOOL,
    DATE, TIMESTAMP,
    BIG_DECIMAL,
    BYTE_ARRAY, 
    UUID,
}