package be.mickverm.widget.textfield

import androidx.appcompat.app.AppCompatActivity
import be.mickverm.widget.textfield.validators.InputValidator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController


private const val TEXT_RES = android.R.string.ok
private const val ERROR_RES = android.R.string.cancel
private const val ERROR_RES_2 = android.R.string.copy

@RunWith(RobolectricTestRunner::class)
class ValidatingEditTextTest {

    private lateinit var activityController: ActivityController<AppCompatActivity>
    private lateinit var activity: AppCompatActivity

    private lateinit var validatingEditText: ValidatingEditText

    private val emptyValidator = object : InputValidator {
        override fun validate(input: String?): Int? {
            return if (input.isNullOrEmpty()) ERROR_RES
            else null
        }
    }

    private val lowerValidator = object : InputValidator {
        override fun validate(input: String?): Int? = when {
            input == null || input != input.toLowerCase() -> ERROR_RES_2
            else -> null
        }
    }

    @Before
    fun setup() {
        activityController = Robolectric.buildActivity(AppCompatActivity::class.java)
        activity = activityController.get()

        validatingEditText = ValidatingEditText(activity)
    }

    @Test
    fun shouldNot_DisplayAnyError_ByDefault() {
        validatingEditText.addValidator(emptyValidator)
        assertEquals(null, validatingEditText.error)
    }

    @Test
    fun shouldNot_DisplayAnyError_AfterInitialFocus() {
        validatingEditText.addValidator(emptyValidator)
        validatingEditText.requestFocus()
        assertEquals(null, validatingEditText.error)
    }

    @Test
    fun should_ValidateInput_AfterLosingFocus() {
        validatingEditText.addValidator(emptyValidator)
        validatingEditText.requestFocus()
        validatingEditText.clearFocus()
        assertNotEquals(null, validatingEditText.error)
    }

    @Test
    fun should_ValidateInput_AfterTextChanged() {
        validatingEditText.addValidator(emptyValidator)
        validatingEditText.setText(TEXT_RES)
        assertEquals(null, validatingEditText.error)
        validatingEditText.setText("")
        assertNotEquals(null, validatingEditText.error)
    }

    @Test
    fun should_ValidateInput_AfterClearingValidators() {
        validatingEditText.addValidator(emptyValidator)
        validatingEditText.setText(TEXT_RES)
        validatingEditText.setText("")
        assertNotEquals(null, validatingEditText.error)
        validatingEditText.clearValidators(true)
        assertEquals(null, validatingEditText.error)
    }

    @Test
    fun shouldNot_ValidateInput_AfterClearingValidators() {
        validatingEditText.addValidator(emptyValidator)
        validatingEditText.setText(TEXT_RES)
        validatingEditText.setText("")
        assertNotEquals(null, validatingEditText.error)
        validatingEditText.clearValidators(false)
        assertNotEquals(null, validatingEditText.error)
    }

    @Test
    fun should_ValidateInput_AfterRemovingValidator() {
        validatingEditText.addValidator(emptyValidator)
        validatingEditText.setText(TEXT_RES)
        validatingEditText.setText("")
        assertNotEquals(null, validatingEditText.error)
        validatingEditText.removeValidator(emptyValidator, true)
        assertEquals(null, validatingEditText.error)
    }

    @Test
    fun shouldNot_ValidateInput_AfterRemovingValidator() {
        validatingEditText.addValidator(emptyValidator)
        validatingEditText.setText(TEXT_RES)
        validatingEditText.setText("")
        assertNotEquals(null, validatingEditText.error)
        validatingEditText.removeValidator(emptyValidator, false)
        assertNotEquals(null, validatingEditText.error)
    }

    @Test
    fun should_Validate_AfterAddingOrRemovingValidators() {
        validatingEditText.setText("")
        validatingEditText.addValidators(emptyValidator, lowerValidator, revalidate = true)
        assertNotEquals(null, validatingEditText.error)
        validatingEditText.setText(TEXT_RES)
        validatingEditText.removeValidator(emptyValidator, true)
        assertEquals(activity.getString(ERROR_RES_2), validatingEditText.error)
        validatingEditText.removeValidators(lowerValidator, revalidate = true)
        assertEquals(null, validatingEditText.error)
    }
}
