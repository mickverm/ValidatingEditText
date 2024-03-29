package be.mickverm.widget.validatingedittext.validators

import androidx.annotation.StringRes

private const val MAC_REGEX = "((\\d|([a-f]|[A-F])){2}:){2}(\\d|([a-f]|[A-F])){2}"

class ShortMacAddressInputValidator(
    @StringRes private val errorRes: Int
) : RegexInputValidator(errorRes, MAC_REGEX)