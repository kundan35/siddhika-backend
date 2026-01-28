package com.siddhika.backend.repository

import com.siddhika.backend.model.ScriptureEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ScriptureRepository : JpaRepository<ScriptureEntity, Long> {
    fun findByCategory(category: String): List<ScriptureEntity>
}
