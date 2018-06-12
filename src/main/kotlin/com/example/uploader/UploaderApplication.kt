package com.example.uploader

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class UploaderApplication

fun main(args: Array<String>) {
    runApplication<UploaderApplication>(*args)


}
