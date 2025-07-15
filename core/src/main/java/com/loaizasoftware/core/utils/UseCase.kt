package com.loaizasoftware.core.utils

abstract class UseCase<in A, out B> {
    abstract suspend operator fun invoke(params: A): B
}