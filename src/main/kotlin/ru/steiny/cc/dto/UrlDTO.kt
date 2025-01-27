package ru.steiny.cc.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

class UrlDTO(
    @field:NotNull
    @field:NotBlank
    @field:Pattern(regexp = "^https?://(?:www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b[-a-zA-Z0-9()@:%_+.~#?&/=]*\$")
    val url : String,
    val jwt: String? = null,
)