package com.siddhika.backend.model

import jakarta.persistence.*

@Entity
@Table(name = "quotes")
data class QuoteEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, columnDefinition = "TEXT")
    val text: String = "",

    @Column(nullable = false)
    val author: String = "",

    val source: String? = null,

    @Column(nullable = false)
    val category: String = "",

    @Column(nullable = false)
    val isFavorite: Boolean = false,

    @Column(nullable = false)
    val createdAt: Long = System.currentTimeMillis()
)
