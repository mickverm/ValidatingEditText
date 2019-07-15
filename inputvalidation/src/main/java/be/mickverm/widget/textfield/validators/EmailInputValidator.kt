package be.mickverm.widget.textfield.validators

import android.util.Patterns
import androidx.annotation.StringRes

class EmailInputValidator(
    @StringRes private val errorRes: Int
) : PatternInputValidator(Patterns.EMAIL_ADDRESS, errorRes)