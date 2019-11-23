package com.billyyccc.ex03_collector

import io.vertx.sqlclient.Row
import java.util.function.BiConsumer
import java.util.function.BinaryOperator
import java.util.function.Function
import java.util.function.Supplier
import java.util.stream.Collector

@FunctionalInterface
interface RowMapper<T> : Collector<Row, MutableList<T>, List<T>> {
    fun map(row: Row): T

    override fun supplier(): Supplier<MutableList<T>> {
        return Supplier {
            ArrayList<T>()
        }
    }

    override fun accumulator(): BiConsumer<MutableList<T>, Row> {
        return BiConsumer { acc: MutableList<T>, row: Row ->
            acc.add(map(row))
        }
    }

    override fun combiner(): BinaryOperator<MutableList<T>> {
        return BinaryOperator { r1: MutableList<T>, r2: MutableList<T> ->
            r1.addAll(r2)
            r1
        }
    }

    override fun finisher(): Function<MutableList<T>, List<T>> {
        return Function { list -> list }
    }

    override fun characteristics(): Set<Collector.Characteristics> {
        return DEFAULT_CHARACTERISTICS
    }

    companion object {
        val DEFAULT_CHARACTERISTICS: Set<Collector.Characteristics> = setOf(Collector.Characteristics.IDENTITY_FINISH)
    }
}
