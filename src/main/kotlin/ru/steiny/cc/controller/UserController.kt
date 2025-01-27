package ru.steiny.cc.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import ru.steiny.cc.dto.JwtDTO
import ru.steiny.cc.dto.ShortUrlDTO
import ru.steiny.cc.dto.UserDTO
import ru.steiny.cc.service.UrlService
import ru.steiny.cc.service.UserService

@RestController
@RequestMapping("/api/v1")
class UserController(
    val userService: UserService,
    val urlService: UrlService
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody userDTO: UserDTO): JwtDTO {
        if (userService.findByLogin(userDTO.login) != null) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Login is already taken")
        }
        return userService.createUser(userDTO)
    }

    @PostMapping("/enter")
    fun enter(@Valid @RequestBody userDTO: UserDTO): JwtDTO {
        if (userService.findByLogin(userDTO.login) == null) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No user with such login")
        }
        return userService.login(userDTO) ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password")
    }

    @GetMapping("/all_urls")
    fun getAllUrls(@RequestBody jwtDTO: JwtDTO?): List<ShortUrlDTO> {
        jwtDTO?.jwt ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "You have to be logged in")
        return urlService.getUserUrls(jwtDTO.jwt) ?: throw ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Invalid user token, please, login again"
        )
    }
}