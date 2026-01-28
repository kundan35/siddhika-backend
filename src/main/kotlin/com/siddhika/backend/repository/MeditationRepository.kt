package com.siddhika.backend.repository

import com.siddhika.backend.model.MeditationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MeditationRepository : JpaRepository<MeditationEntity, Long> {
    fun findByCategory(category: String): List<MeditationEntity>
}
