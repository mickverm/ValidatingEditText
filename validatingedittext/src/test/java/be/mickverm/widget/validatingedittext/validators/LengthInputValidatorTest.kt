package be.mickverm.widget.validatingedittext.validators

import org.junit.Assert.assertEquals
import org.junit.Test

class LengthInputValidatorTest {

    @Test
    fun should_ReturnErrorRes_WhenInputIsNull() {
        assertEquals(-1, LengthInputValidator(-1).validate(null))
        assertEquals(-1, LengthInputValidator(-1, 1).validate(null))
        assertEquals(-1, LengthInputValidator(-1, maxLength = 5).validate(null))
        assertEquals(-1, LengthInputValidator(-1, 1, 5).validate(null))
        assertEquals(-1, LengthInputValidator(-1, 0, 5).validate(null))
    }

    @Test
    fun should_ReturnErrorRes_WhenInputIsEmpty() {
        assertEquals(-1, LengthInputValidator(-1).validate(""))
        assertEquals(-1, LengthInputValidator(-1, 1).validate(""))
        assertEquals(-1, LengthInputValidator(-1, maxLength = 5).validate(""))
        assertEquals(-1, LengthInputValidator(-1, 1, 5).validate(""))
    }

    @Test
    fun should_ReturnNull_WhenInputIsEmpty() {
        assertEquals(null, LengthInputValidator(-1, 0).validate(""))
    }

    @Test
    fun should_ReturnNull_WhenInputLengthBetweenValues() {
        assertEquals(null, LengthInputValidator(-1, 1, 5).validate("a"))
        assertEquals(null, LengthInputValidator(-1, 1, 5).validate("ab"))
        assertEquals(null, LengthInputValidator(-1, 1, 5).validate("abc"))
        assertEquals(null, LengthInputValidator(-1, 1, 5).validate("abcd"))
        assertEquals(null, LengthInputValidator(-1, 1, 5).validate("abcde"))
    }

    @Test
    fun should_ReturnErrorRes_WhenInputLengthNotBetween() {
        assertEquals(-1, LengthInputValidator(-1, 2).validate("a"))
        assertEquals(-1, LengthInputValidator(-1, 2, 5).validate("a"))
        assertEquals(-1, LengthInputValidator(-1, 1, 5).validate("abcdef"))
    }
}