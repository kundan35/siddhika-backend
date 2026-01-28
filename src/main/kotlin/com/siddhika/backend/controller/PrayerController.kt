package com.siddhika.backend.controller

import com.siddhika.backend.model.PrayerEntity
import com.siddhika.backend.repository.PrayerRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/prayers")
class PrayerController(private val repository: PrayerRepository) {

    @GetMapping
    fun getAll(): List<PrayerEntity> = repository.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<PrayerEntity> =
        repository.findById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @GetMapping("/category/{category}")
    fun getByCategory(@PathVariable category: String): List<PrayerEntity> =
        repository.findByCategory(category)

    @PostMapping
    fun create(@RequestBody entity: PrayerEntity): PrayerEntity =
        repository.save(entity.copy(id = 0))

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody entity: PrayerEntity): ResponseEntity<PrayerEntity> =
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
