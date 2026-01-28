package com.siddhika.backend.controller

import com.siddhika.backend.model.ScriptureEntity
import com.siddhika.backend.repository.ScriptureRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/scriptures")
class ScriptureController(private val repository: ScriptureRepository) {

    @GetMapping
    fun getAll(): List<ScriptureEntity> = repository.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ScriptureEntity> =
        repository.findById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @GetMapping("/category/{category}")
    fun getByCategory(@PathVariable category: String): List<ScriptureEntity> =
        repository.findByCategory(category)

    @PostMapping
    fun create(@RequestBody entity: ScriptureEntity): ScriptureEntity =
        repository.save(entity.copy(id = 0))

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody entity: ScriptureEntity): ResponseEntity<ScriptureEntity> =
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
