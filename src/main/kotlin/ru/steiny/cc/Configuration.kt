package ru.steiny.cc

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@EnableConfigurationProperties(JwtProperties::class, CustomProperties::class)
class Configuration


@ConfigurationProperties("jwt")
data class JwtProperties(
    val key: String,
    val accessTokenExpiration: Long,
    val refreshTokenExpiration: Long,
)

@ConfigurationProperties("custom-properties")
data class CustomProperties(
    val hostname: String,
    val passwordSalt: String
)