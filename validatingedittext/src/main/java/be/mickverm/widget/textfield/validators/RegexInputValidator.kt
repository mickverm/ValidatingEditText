package be.mickverm.widget.textfield.validators

import androidx.annotation.StringRes

open class RegexInputValidator(
    @StringRes private val errorRes: Int,
    private val regex: Regex
) : InputValidator {

    constructor(
        @StringRes errorRes: Int,
        regex: String
    ) : this(errorRes, regex.toRegex())

    @Deprecated(
        message = "Constructor parameter order changed.",
        replaceWith = ReplaceWith(
            "RegexInputValidator(errorRes, regex)"
        )
    )
    constructor(
        regex: String,
        @StringRes errorRes: Int
    ) : this(errorRes, regex.toRegex())

    @Deprecated(
        message = "Constructor parameter order changed.",
        replaceWith = ReplaceWith(
            "RegexInputValidator(errorRes, regex)"
        )
    )
    constructor(
        pattern: Regex,
        @StringRes errorRes: Int
    ) : this(errorRes, pattern)

    final override fun validate(input: String?) = when {
        input == null -> errorRes
        input.matches(regex) -> null
        else -> errorRes
    }
}