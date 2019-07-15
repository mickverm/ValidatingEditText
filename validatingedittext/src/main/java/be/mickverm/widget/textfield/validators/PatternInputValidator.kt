package be.mickverm.widget.textfield.validators

import androidx.annotation.StringRes
import java.util.regex.Pattern

open class PatternInputValidator(
    private val pattern: Pattern,
    @StringRes private val errorRes: Int
) : InputValidator {

    constructor(
        pattern: String,
        @StringRes errorRes: Int
    ) : this(Pattern.compile(pattern), errorRes)

    final override fun validate(input: String?) = when {
        pattern.matcher(input).matches() -> null
        else -> errorRes
    }
}