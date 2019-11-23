package com.billyyccc.ex03_collector

import com.billyyccc.User
import io.vertx.sqlclient.Row
import java.time.LocalDate

object Mappers {
    val USER_MAPPER: RowMapper<User> = object :
        RowMapper<User> {
        override fun map(row: Row): User {
            val id: Int = row.getInteger("id")
            val name: String = row.getString("name")
            val email: String = row.getString("email")
            val registeredDate: LocalDate = row.getLocalDate("registered_date")
            return User(id, name, email, registeredDate)
        }
    }
}
