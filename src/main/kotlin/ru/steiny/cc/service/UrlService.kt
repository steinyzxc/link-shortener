package ru.steiny.cc.service

import org.springframework.stereotype.Service
import ru.steiny.cc.CustomProperties
import ru.steiny.cc.dto.ShortUrlDTO
import ru.steiny.cc.dto.UrlDTO
import ru.steiny.cc.model.Url
import ru.steiny.cc.repository.UrlRepository

@Service
class UrlService(
    val urlRepository: UrlRepository,
    val userService: UserService,
    val customProperties: CustomProperties
) {
    fun findOrCreateUrl(urlDTO: UrlDTO): Url? {
        if (urlDTO.jwt == null) {
            return urlRepository.findNullableByUrl(urlDTO.url) ?: run {
                var shortUrl = shortenUrl()
                while (urlRepository.countByShortUrl(shortUrl) != 0) {
                    shortUrl = shortenUrl()
                }
                val url = Url(urlDTO.url, shortUrl)
                urlRepository.save(url)
                return url
            }
        }
        val user = userService.findUserByJwt(urlDTO.jwt) ?: return null
        return urlRepository.findNullableByUrlAndUser(urlDTO.url, user) ?: run {
            var shortUrl = shortenUrl()
            while (urlRepository.countByShortUrl(shortUrl) != 0) {
                shortUrl = shortenUrl()
            }
            val url = Url(urlDTO.url, shortUrl)
            url.user = user
            urlRepository.save(url)
            return url
        }
    }

    fun findByShortUrl(shortUrl: String): Url? {
        return urlRepository.findNullableByShortUrl(shortUrl)
    }

    fun incrementVisitedByShortUrl(shortUrl: String) {
        urlRepository.incrementVisitedByShortUrl(shortUrl)
    }

    private fun shortenUrl(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..6)
            .map { allowedChars.random() }
            .joinToString("")
    }

    fun getUserUrls(jwt: String): List<ShortUrlDTO>? {
        val user = userService.findUserByJwt(jwt) ?: return null
        return urlRepository.findAllByUserOrderByCreationTimeDesc(user).map(::urlToShortUrlDTO)
    }

    fun urlToShortUrlDTO(url: Url): ShortUrlDTO {
        return ShortUrlDTO(url.url, customProperties.hostname + "/" + url.shortUrl, url.visited)
    }

}