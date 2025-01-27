package ru.steiny.cc.service

import org.springframework.stereotype.Service
import ru.steiny.cc.CustomProperties
import ru.steiny.cc.dto.JwtDTO
import ru.steiny.cc.dto.UserDTO
import ru.steiny.cc.model.User
import ru.steiny.cc.repository.UserRepository
import java.security.spec.KeySpec
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

@Service
class UserService(
    val userRepository: UserRepository,
    val jwtService: JwtService,
    val customProperties: CustomProperties
) {
    @OptIn(ExperimentalStdlibApi::class)
    fun generateHash(password: String, salt: String): String {
        val algorithm = "PBKDF2WithHmacSHA512"
        val iterationCount = 120_000
        val keyLength = 256
        val combinedSalt = (salt + customProperties.passwordSalt).toByteArray()
        val factory: SecretKeyFactory = SecretKeyFactory.getInstance(algorithm)
        val spec: KeySpec = PBEKeySpec(password.toCharArray(), combinedSalt, iterationCount, keyLength)
        val key: SecretKey = factory.generateSecret(spec)
        val hash: ByteArray = key.encoded
        return hash.toHexString()
    }

    fun findByLogin(login: String): User? = userRepository.findNullableByLogin(login)
    fun createUser(userDTO: UserDTO): JwtDTO {
        val user = User(userDTO.login, generateHash(userDTO.password, userDTO.login))
        userRepository.save(user)
        return JwtDTO(jwtService.generateJwt(user))
    }

    fun login(userDTO : UserDTO): JwtDTO? {
        val user = userRepository.findByLogin(userDTO.login)
        if (user.passwordSha == generateHash(userDTO.password, userDTO.login)) {
            return JwtDTO(jwtService.generateJwt(user))
        }
        return null
    }

    fun findUserByJwt(jwt: String): User? {
        val id = jwtService.extractJwt(jwt) ?: return null
        return userRepository.findNullableById(id)
    }



}