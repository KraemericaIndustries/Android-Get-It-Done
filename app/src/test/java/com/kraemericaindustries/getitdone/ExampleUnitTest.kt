package com.kraemericaindustries.getitdone

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun subtraction_isCorrect() {
        // Perform an action
        val result = 7 - 2

        // Assert the result
        assertEquals(5, result)
    }

    @Test
    fun greeter_greetsPersonCorrectly() {
        // Perform an action
        val greeter = Greeter()
        val result = greeter.greet("Bob")

        // Assert the result
        assertEquals("Hello, Bob!", result)

    }

}

class Greeter() {
    fun greet(name: String) : String {
        return "Hello, $name!"
    }
}