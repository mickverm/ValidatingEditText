package be.mickverm.widget.textfield.validators

import org.junit.Assert.assertEquals
import org.junit.Test

class MacAddressInputValidatorTest {

    @Test
    fun should_ReturnErrorRes_WhenMacAddressIsInvalid() {
        assertEquals(-1, MacAddressInputValidator(-1).validate(""))
        assertEquals(-1, MacAddressInputValidator(-1).validate("TEST"))
        assertEquals(-1, MacAddressInputValidator(-1).validate("AA:"))
        assertEquals(-1, MacAddressInputValidator(-1).validate("aa:"))
        assertEquals(-1, MacAddressInputValidator(-1).validate("AA:BB"))
        assertEquals(-1, MacAddressInputValidator(-1).validate("Aa:Bb"))
        assertEquals(-1, MacAddressInputValidator(-1).validate("AA:BB:CC"))
        assertEquals(-1, MacAddressInputValidator(-1).validate("AA:BB:CC"))
        assertEquals(-1, MacAddressInputValidator(-1).validate("AA:BB:CC:DD:EE:F"))
        assertEquals(-1, MacAddressInputValidator(-1).validate("Aa:Bb:Cc:Dd:Ee:F"))
        assertEquals(-1, MacAddressInputValidator(-1).validate("GG:GG:GG:GG:GG:GG"))
    }

    @Test
    fun should_ReturnNull_WhenMacAddressIsValid(){
        assertEquals(null, MacAddressInputValidator(-1).validate("AA:AA:AA:AA:AA:AA"))
        assertEquals(null, MacAddressInputValidator(-1).validate("aa:aa:aa:aa:aa:aa"))
        assertEquals(null, MacAddressInputValidator(-1).validate("aA:Aa:aA:Aa:aA:aa"))
        assertEquals(null, MacAddressInputValidator(-1).validate("A1:B2:C3:D4:E5:F6"))
        assertEquals(null, MacAddressInputValidator(-1).validate("a1:B2:c3:D4:e5:F6"))
        assertEquals(null, MacAddressInputValidator(-1).validate("12:34:56:78:90:AB"))
        assertEquals(null, MacAddressInputValidator(-1).validate("12:34:56:78:90:Ab"))
    }
}