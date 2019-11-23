package com.billyyccc.ex01_future

import com.billyyccc.*
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Promise
import io.vertx.mysqlclient.MySQLPool
import io.vertx.sqlclient.Row
import io.vertx.sqlclient.RowSet
import io.vertx.sqlclient.Tuple

class DatabaseVerticle : AbstractVerticle() {
    private lateinit var client: MySQLPool

    override fun start(startPromise: Promise<Void>?) {
        client = MySQLPool.pool(vertx, "mysql://root:password@localhost:3306/test")

        /*
            Note these are not executed serially
            val insertFuture = insert()
            val findAllFuture = findAll()
         */
        insert()
            .flatMap {
                handleInsertResult(it)
                findAll()
            }
            .map {
                println("FindAll fetch ${it.size()} rows")
                val userList: List<User> = mapRowSetToUserList(it)
                userList.forEach { user ->
                    println("User id = [${user.id}], name = [${user.name}], email = [${user.email}], registered_date = [${user.registeredDate}]")
                }
                userList
            }
            .map {
                deleteById(4)
                it
            }
            .flatMap { allUserList ->
                findUsersIdGreaterThan(3)
                    .map { findResult ->
                        val filteredUserList = allUserList.filter { user -> user.id == 5 }
                        println(mapRowSetToUserList(findResult) == filteredUserList)
                    }
            }
            .map {
                deleteAll()
            }
            .setHandler {
                if (it.succeeded()) {
                    println("FINISH")
                } else {
                    it.cause().printStackTrace()
                }
            }
    }

    private fun insert(): Future<RowSet<Row>> {
        return client.query(INSERT_QUERY)
    }

    private fun handleInsertResult(insertResult: RowSet<Row>) {
        val updatedRows = insertResult.rowCount()
        println("Inserted rows: $updatedRows")
    }

    private fun findAll(): Future<RowSet<Row>> {
        return client.query(FIND_ALL)
    }

    private fun deleteById(id: Int): Future<RowSet<Row>> {
        return client.preparedQuery(DELETE_BY_ID, Tuple.of(id))
    }

    private fun findUsersIdGreaterThan(id: Int): Future<RowSet<Row>> {
        return client.preparedQuery(FIND_ID_GREATER_THAN, Tuple.of(id))
    }

    private fun deleteAll() {
        client.query(DELETE_ALL)
    }
}
