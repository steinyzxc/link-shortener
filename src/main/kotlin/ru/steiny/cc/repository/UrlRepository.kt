package ru.steiny.cc.repository

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.steiny.cc.model.Url
import ru.steiny.cc.model.User

@Repository
interface UrlRepository : JpaRepository<Url, Long> {
    fun findByUrl(url: String): Url

    fun findNullableByUrl(url: String): Url?

    fun findByShortUrl(shortUrl: String): Url

    fun findNullableByShortUrl(url: String): Url?

    fun findNullableByUrlAndUser(url: String, user: User): Url?

    fun findAllByUserOrderByCreationTimeDesc(user: User): List<Url>

    fun countByUrl(url: String): Int

    fun countByShortUrl(shortUrl: String): Int

    @Modifying
    @Transactional
    @Query("update Url u set u.visited = u.visited + 1 where u.shortUrl = ?1")
    fun incrementVisitedByShortUrl(shortUrl: String)

}