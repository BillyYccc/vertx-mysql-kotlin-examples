package com.billyyccc.ex03_collector

import com.billyyccc.*
import io.vertx.core.Future
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.await
import io.vertx.mysqlclient.MySQLPool
import io.vertx.sqlclient.Row
import io.vertx.sqlclient.RowSet
import io.vertx.sqlclient.SqlResult
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
        val insertResult: Future<RowSet<Row>> = client.query(INSERT_QUERY)
        val updatedRows = insertResult.await().rowCount()
        println("Inserted rows: $updatedRows")
    }

    private suspend fun findAll(): List<User> {
        val result: SqlResult<List<User>> = client.query(FIND_ALL, Mappers.USER_MAPPER).await()
        println("FindAll fetch ${result.size()} rows")
        return result.value()
    }

    private suspend fun deleteById(id: Int) {
        client.preparedQuery(DELETE_BY_ID, Tuple.of(id)).await()
        println("Deleted by id: $id")
    }

    private suspend fun findUsersIdGreaterThan(id: Int): List<User> {
        val result: SqlResult<List<User>> =
            client.preparedQuery(FIND_ID_GREATER_THAN, Tuple.of(id), Mappers.USER_MAPPER).await()
        return result.value()
    }

    private suspend fun deleteAll() {
        client.query(DELETE_ALL).await()
    }

    private fun printUserList(userList: List<User>) {
        userList.forEach { user ->
            println("User id = [${user.id}], name = [${user.name}], email = [${user.email}], registered_date = [${user.registeredDate}]")
        }
    }
}
