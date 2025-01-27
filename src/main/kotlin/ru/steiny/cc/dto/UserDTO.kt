package ru.steiny.cc.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

class UserDTO (
    @field:NotNull
    @field:NotBlank
    @field:Size(min=2, max=16)
    @field:Pattern(regexp = "[a-zA-Z0-9]+")
    val login : String,
    @field:NotNull
    @field:NotBlank
    @field:Size(min=4, max=64)
    val password: String
)