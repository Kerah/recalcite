package io.remicro.recalcite.pqsource.conf

import org.apache.calcite.adapter.jdbc.JdbcSchema
import org.apache.calcite.jdbc.CalciteConnection
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.sql.Connection
import java.sql.DriverManager
import javax.sql.DataSource

private const val PSQL_HOSTNAME = "psql.hostname"
private const val PSQL_PORT = "psql.port"
private const val PSQL_DATABASE = "psql.database"
private const val PSQL_USER = "psql.user"
private const val PSQL_PASSWORD = "psql.password"


@Configuration
class DataSourceConfiguration(
        @Value("\${$PSQL_HOSTNAME}") private val hostname: String,
        @Value("\${$PSQL_PORT}") private val port: Int,
        @Value("\${$PSQL_DATABASE}") private val database: String,
        @Value("\${$PSQL_USER}") private val user: String,
        @Value("\${$PSQL_PASSWORD}") private val passowrd: String
) {
    @Bean
    fun dataSourceConnection(): Connection {
        val con = DriverManager.getConnection("jdbc:calcite:")
        val calciteCon = con.unwrap(CalciteConnection::class.java)
        val rootSchema = calciteCon.rootSchema
        val ds = JdbcSchema.dataSource("jdbc:postgresql://$hostname/$database", "org.postgresql.Driver", user, passowrd)
        rootSchema.add("db1", JdbcSchema.create(rootSchema, "db1", ds, null, null))
        return con
    }
}