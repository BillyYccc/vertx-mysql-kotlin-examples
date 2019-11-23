package com.billyyccc.ex03_collector

import io.vertx.core.Vertx

fun main() {
    val vertx = Vertx.vertx()
    vertx.deployVerticle(DatabaseVerticle())
}

