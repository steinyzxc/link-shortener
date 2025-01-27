package ru.steiny.cc.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.view.RedirectView
import ru.steiny.cc.CustomProperties
import ru.steiny.cc.dto.UrlDTO
import ru.steiny.cc.dto.ShortUrlDTO
import ru.steiny.cc.service.UrlService

@RestController
class UrlController(
    val urlService: UrlService,
    val customProperties: CustomProperties
) {
    @PostMapping("/api/v1/new_url")
    fun getShort(@Valid @RequestBody urlDTO: UrlDTO): ShortUrlDTO {
        val url = urlService.findOrCreateUrl(urlDTO) ?: throw ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Invalid user token, please, login again"
        )
        return urlService.urlToShortUrlDTO(url)
    }

    @GetMapping("/{shortUrl}")
    fun getUrl(@PathVariable shortUrl: String): RedirectView {
        val url = urlService.findByShortUrl(shortUrl)?.url ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        urlService.incrementVisitedByShortUrl(shortUrl)
        return RedirectView(url)
    }
}