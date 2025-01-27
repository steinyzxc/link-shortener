package ru.steiny.cc.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.util.*

@Entity
@Table(
    name = "url",
    indexes = [Index(columnList = "url, short_url, creation_time")],
    uniqueConstraints = [UniqueConstraint(columnNames = ["url", "short_url"])]
)
class Url(
    @Column(name = "url", nullable = false)
    val url: String,
    @Column(name = "short_url", nullable = false)
    val shortUrl: String,

    ) {
    @Id
    @GeneratedValue()
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Column(name = "creation_time", nullable = false)
    @CreationTimestamp
    var creationTime: Date? = null

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    var user: User? = null

    @Column(name = "visited", nullable = false)
    var visited: Long = 0
}