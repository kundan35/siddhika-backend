package com.siddhika.backend.model

import jakarta.persistence.*

@Entity
@Table(name = "scriptures")
data class ScriptureEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val title: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val description: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val content: String = "",

    @Column(nullable = false)
    val category: String = "",

    @Column(nullable = false)
    val language: String = "en",

    @Column(nullable = false)
    val totalChapters: Int = 1
)
