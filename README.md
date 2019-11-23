# vertx-mysql-kotlin-examples

Examples showing how to use the Reactive MySQL Client with Kotlin

## prepare the database

Starting the MySQL database with `docker-compose`

```shell script
./start-mysql.sh
```

## examples

* [Future-based APIs](src/main/kotlin/com/billyyccc/ex01_future)
* [Using with Kotlin Coroutines by await-suffix APIs](src/main/kotlin/com/billyyccc/ex02_awaitsuffix)
* [Using with Kotlin Coroutines by future based APIs and `await` extension methods](src/main/kotlin/com/billyyccc/ex03_collector) 