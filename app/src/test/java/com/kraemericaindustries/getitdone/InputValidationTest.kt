package com.kraemericaindustries.getitdone

import com.kraemericaindustries.getitdone.util.InputValidator
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class InputValidationTest {

    @Test
    fun inputValidator_returnsFalseWhenEmpty() {

        // Perform an action
        val result = InputValidator.isInputValid("")

        // Assert the result
        assertFalse(result)
    }

    @Test
    fun inputValidator_returnsFalseWhenNull() {

        // Perform an action
        val result = InputValidator.isInputValid(null)

        // Assert the result
        assertFalse(result)
    }

    @Test
    fun inputValidator_returnsFalseWhenOnlyWhiteSpace() {

        // Perform an action
        val result = InputValidator.isInputValid("         ")

        // Assert the result
        assertFalse(result)
    }

    @Test
    fun inputValidator_returnsTrueWhenMoreThanOneWhiteSpaceCharacter() {

        // Perform an action
        val result = InputValidator.isInputValid("more than one character")

        // Assert the result
        assertTrue(result)
    }

    @Test
    fun inputValidator_returnsTrueWhenTwoNonWhiteSpaceCharacters() {

        // Perform an action
        val result = InputValidator.isInputValid("ab")

        // Assert the result
        assertTrue(result)
    }

    @Test
    fun inputValidator_returnsFalseWhenOnlyOneNonWhiteSpaceCharacter() {

        // Perform an action
        val result = InputValidator.isInputValid("z")

        // Assert the result
        assertFalse(result)
    }
}