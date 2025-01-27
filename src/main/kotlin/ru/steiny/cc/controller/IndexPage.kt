package ru.steiny.cc.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class IndexPage {
    @GetMapping("/", "")
    fun index() : String {
        return "aboba"
    }
}