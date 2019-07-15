package be.mickverm.widget.textfield.validators

import androidx.annotation.StringRes

class LengthInputValidator(
    private val minLength: Int = 1,
    private val maxLength: Int = Int.MAX_VALUE,
    @StringRes private val errorRes: Int
) : InputValidator {

    override fun validate(input: String?) = when {
        input == null || input.length < minLength || input.length > maxLength -> errorRes
        else -> null
    }
}