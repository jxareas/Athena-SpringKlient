package com.jxareas.athenaklient.domain

@JvmInline
value class AthenaQuery(private val query: String) {
    init {
        require(query.isNotBlank()) { "Athena query must not be blank." }
    }

    override fun toString(): String = query
}