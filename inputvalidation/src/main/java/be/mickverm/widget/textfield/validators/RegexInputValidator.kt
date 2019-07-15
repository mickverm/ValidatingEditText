package be.mickverm.widget.textfield.validators

import androidx.annotation.StringRes

open class RegexInputValidator(
    private val regex: Regex,
    @StringRes private val errorRes: Int
) : InputValidator {

    constructor(
        regex: String,
        @StringRes errorRes: Int
    ) : this(regex.toRegex(), errorRes)

    final override fun validate(input: String?) = when {
        input == null -> errorRes
        input.matches(regex) -> null
        else -> errorRes
    }
}