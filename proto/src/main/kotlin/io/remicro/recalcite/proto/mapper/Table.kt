package io.remicro.recalcite.proto.mapper

data class Table(
    val name: String,
    val columns: List<RowItem>
)
