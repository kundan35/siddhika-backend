package com.siddhika.backend.model

import jakarta.persistence.*

@Entity
@Table(name = "meditations")
data class MeditationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val title: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val description: String = "",

    @Column(nullable = false)
    val durationMinutes: Int = 0,

    @Column(nullable = false)
    val category: String = "",

    val imageUrl: String? = null,

    val audioUrl: String? = null
)
