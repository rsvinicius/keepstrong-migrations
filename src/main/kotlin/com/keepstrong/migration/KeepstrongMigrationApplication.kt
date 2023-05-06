package com.keepstrong.migration

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KeepstrongMigrationApplication

fun main(args: Array<String>) {
	runApplication<KeepstrongMigrationApplication>(*args)
}
