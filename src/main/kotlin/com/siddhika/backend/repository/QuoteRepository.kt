package com.siddhika.backend.repository

import com.siddhika.backend.model.QuoteEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuoteRepository : JpaRepository<QuoteEntity, Long> {
    fun findByCategory(category: String): List<QuoteEntity>
}
