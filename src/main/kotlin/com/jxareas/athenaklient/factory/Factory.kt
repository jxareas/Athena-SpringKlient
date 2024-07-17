package com.jxareas.athenaklient.factory

@FunctionalInterface
interface Factory<T> {
    operator fun invoke(): T
}