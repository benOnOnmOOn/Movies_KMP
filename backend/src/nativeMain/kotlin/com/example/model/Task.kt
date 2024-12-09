package com.example.model

import kotlinx.serialization.Serializable

@Serializable
internal enum class Priority {
    Low,
    Medium,
    High,
    Vital,
}

@Serializable
internal data class Task(
    val name: String,
    val description: String,
    val priority: Priority,
)
