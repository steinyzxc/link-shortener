package ru.steiny.cc.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.CreationTimestamp
import java.util.*

@Entity
@Table(
    name = "users",
    indexes = [Index(columnList = "login")],
    uniqueConstraints = [UniqueConstraint(columnNames = ["login"])]
)

class User(
    @NotNull
    @NotBlank
    @Size(min = 2, max = 16)
    @Column(name = "login")
    val login: String,
    @JsonIgnore
    @Column(name = "password_sha", nullable = false)
    val passwordSha : String
) {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @CreationTimestamp
    @Column(name = "creation_time", nullable = false)
    var creationTime: Date? = null

    @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "user")
    var urls = mutableListOf<Url>()
}