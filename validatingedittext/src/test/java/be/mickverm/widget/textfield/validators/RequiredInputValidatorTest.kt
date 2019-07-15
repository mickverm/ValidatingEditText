package be.mickverm.widget.textfield.validators

import org.junit.Assert.assertEquals
import org.junit.Test

class RequiredInputValidatorTest {

    @Test
    fun should_ReturnErrorRes_WhenInputIsNull() {
        assertEquals(-1, RequiredInputValidator(-1).validate(null))
    }

    @Test
    fun should_ReturnErrorRes_WhenInputIsEmpty() {
        assertEquals(-1, RequiredInputValidator(-1).validate(""))
    }

    @Test
    fun should_ReturnNull_WhenInputIsNotNull() {
        assertEquals(null, RequiredInputValidator(-1).validate("input"))
    }
}