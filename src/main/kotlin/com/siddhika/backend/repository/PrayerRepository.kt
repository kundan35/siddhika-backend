package com.siddhika.backend.repository

import com.siddhika.backend.model.PrayerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PrayerRepository : JpaRepository<PrayerEntity, Long> {
    fun findByCategory(category: String): List<PrayerEntity>
}
