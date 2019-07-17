package be.mickverm.widget.validatingedittext.validators

import android.util.Patterns
import androidx.annotation.StringRes

class EmailInputValidator(
    @StringRes private val errorRes: Int
) : PatternInputValidator(errorRes, Patterns.EMAIL_ADDRESS)