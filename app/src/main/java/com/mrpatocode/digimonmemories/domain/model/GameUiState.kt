package com.mrpatocode.digimonmemories.domain.model

import com.mrpatocode.digimonmemories.util.Resource

// Representa el estado continuo de la vista
data class GameUiState(
    val featuredDigimon: Resource<List<Digimon>> = Resource.Loading(),
    val success: Int = 0,
    val attempts: Int = 0,
    val errors: Int = 0,
    val changes: Int = 0,
    val level: Int = 1,
    val hearts: Int = 3,
    val hints: Int = 1,
    val stateChange: Boolean = false
)

// Envoltorio estándar en la comunidad Android para eventos únicos con LiveData
open class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}