package ru.steiny.cc.dto

class ShortUrlDTO(
    val url: String,
    val shortUrl: String,
    val visited: Long = 0
) {
}