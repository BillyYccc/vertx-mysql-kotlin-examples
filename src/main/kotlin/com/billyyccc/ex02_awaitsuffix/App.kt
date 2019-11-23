package com.billyyccc.ex02_awaitsuffix

import io.vertx.core.Vertx

fun main() {
    val vertx = Vertx.vertx()
    vertx.deployVerticle(DatabaseVerticle())
}