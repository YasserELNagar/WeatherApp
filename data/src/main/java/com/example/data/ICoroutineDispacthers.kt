package com.example.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * there will be 2 wrapper classes 1 for real code [AppCoroutineDispatchers]
 * and the other for unit testing [TestCoroutineDispatchers]
 * *
 */
interface ICoroutineDispatchers {
    val IO: CoroutineDispatcher
    val MAIN: CoroutineDispatcher
    val DEFAULT: CoroutineDispatcher
}

/**
 * Wrapper class , wraps the [Dispatchers]
 * for the real usage inside the code
 */
class AppCoroutineDispatchers(
    override val IO: CoroutineDispatcher = Dispatchers.IO,
    override val MAIN: CoroutineDispatcher = Dispatchers.Main,
    override val DEFAULT: CoroutineDispatcher = Dispatchers.Default
) : ICoroutineDispatchers

/**
 * Wrapper class , wraps the [Dispatchers]
 * for the unit testing to replace the io , main with [TestCoroutineDispatchers]
 */
class TestCoroutineDispatchers(
    override val IO: CoroutineDispatcher,
    override val MAIN: CoroutineDispatcher,
    override val DEFAULT: CoroutineDispatcher
) : ICoroutineDispatchers