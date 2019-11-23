package com.billyyccc.ex01_future

import io.vertx.core.Vertx

fun main() {
    val vertx = Vertx.vertx()
    vertx.deployVerticle(DatabaseVerticle())
}