package com.billyyccc.ex02_awaitsuffix

import com.billyyccc.*
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.mysqlclient.preparedQueryAwait
import io.vertx.kotlin.mysqlclient.queryAwait
import io.vertx.mysqlclient.MySQLPool
import io.vertx.sqlclient.Row
import io.vertx.sqlclient.RowSet
import io.vertx.sqlclient.Tuple

class DatabaseVerticle : CoroutineVerticle() {
    private lateinit var client: MySQLPool

    override suspend fun start() {
        client = MySQLPool.pool(vertx, "mysql://root:password@localhost:3306/test")
        insert()
        val allUsers = findAll()
        printUserList(allUsers)
        deleteById(4)
        val userList = findUsersIdGreaterThan(3)
        val filteredUserList = allUsers.filter { user -> user.id == 5 }
        println(userList == filteredUserList)
        deleteAll()
        println("FINISH")
    }

    private suspend fun insert() {
        val insertResult: RowSet<Row> = client.queryAwait(INSERT_QUERY)
        val updatedRows = insertResult.rowCount()
        println("Inserted rows: $updatedRows")
    }

    private suspend fun findAll(): List<User> {
        val rowSet: RowSet<Row> = client.queryAwait(FIND_ALL)
        println("FindAll fetch ${rowSet.size()} rows")
        return mapRowSetToUserList(rowSet)
    }

    private suspend fun deleteById(id: Int) {
        client.preparedQueryAwait(DELETE_BY_ID, Tuple.of(id))
        println("Deleted by id: $id")
    }

    private suspend fun findUsersIdGreaterThan(id: Int): List<User> {
        val rowSet = client.preparedQueryAwait(FIND_ID_GREATER_THAN, Tuple.of(id))
        return mapRowSetToUserList(rowSet)
    }

    private suspend fun deleteAll() {
        client.queryAwait(DELETE_ALL)
    }

    private fun printUserList(userList: List<User>) {
        userList.forEach { user ->
            println("User id = [${user.id}], name = [${user.name}], email = [${user.email}], registered_date = [${user.registeredDate}]")
        }
    }
}
