package com.jxareas.athenaklient.annotations

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Component
@MustBeDocumented
annotation class KlientRunner(
    @get:AliasFor(annotation = Component::class)
    val value: String = ""
)
