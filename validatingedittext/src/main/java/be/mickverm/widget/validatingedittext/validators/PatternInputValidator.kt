package be.mickverm.widget.validatingedittext.validators

import androidx.annotation.StringRes
import java.util.regex.Pattern

open class PatternInputValidator(
    @StringRes private val errorRes: Int,
    private val pattern: Pattern
) : InputValidator {

    constructor(
        @StringRes errorRes: Int,
        pattern: String
    ) : this(errorRes, Pattern.compile(pattern))

    @Deprecated(
        message = "Constructor parameter order changed.",
        replaceWith = ReplaceWith(
            "PatternInputValidator(errorRes, pattern)"
        )
    )
    constructor(
        pattern: String,
        @StringRes errorRes: Int
    ) : this(errorRes, Pattern.compile(pattern))

    @Deprecated(
        message = "Constructor parameter order changed.",
        replaceWith = ReplaceWith(
            "PatternInputValidator(errorRes, pattern)"
        )
    )
    constructor(
        pattern: Pattern,
        @StringRes errorRes: Int
    ) : this(errorRes, pattern)

    final override fun validate(input: String?) = when {
        pattern.matcher(input).matches() -> null
        else -> errorRes
    }
}