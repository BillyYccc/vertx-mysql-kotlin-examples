package com.billyyccc.ex02_awaitsuffix

import com.billyyccc.User
import io.vertx.sqlclient.Row
import io.vertx.sqlclient.RowSet
import java.time.LocalDate

fun mapRowSetToUserList(rowSet: RowSet<Row>): List<User> {
    val userList = ArrayList<User>()
    rowSet.forEach { row -> userList.add(mapRowToUser(row)) }
    return userList
}

private fun mapRowToUser(row: Row): User {
    val id: Int = row.getInteger("id")
    val name: String = row.getString("name")
    val email: String = row.getString("email")
    val registeredDate: LocalDate = row.getLocalDate("registered_date")
    return User(id, name, email, registeredDate)
}