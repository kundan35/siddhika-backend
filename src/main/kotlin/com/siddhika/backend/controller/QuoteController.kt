package com.siddhika.backend.controller

import com.siddhika.backend.model.QuoteEntity
import com.siddhika.backend.repository.QuoteRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/quotes")
class QuoteController(private val repository: QuoteRepository) {

    @GetMapping
    fun getAll(): List<QuoteEntity> = repository.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<QuoteEntity> =
        repository.findById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @GetMapping("/category/{category}")
    fun getByCategory(@PathVariable category: String): List<QuoteEntity> =
        repository.findByCategory(category)

    @PostMapping
    fun create(@RequestBody entity: QuoteEntity): QuoteEntity =
        repository.save(entity.copy(id = 0))

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody entity: QuoteEntity): ResponseEntity<QuoteEntity> =
        if (repository.existsById(id)) {
            ResponseEntity.ok(repository.save(entity.copy(id = id)))
        } else {
            ResponseEntity.notFound().build()
        }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> =
        if (repository.existsById(id)) {
            repository.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
}
