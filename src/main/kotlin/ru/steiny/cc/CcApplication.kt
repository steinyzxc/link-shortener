package ru.steiny.cc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CcApplication

fun main(args: Array<String>) {
	runApplication<CcApplication>(*args)
}
