package be.mickverm.widget.validatingedittext.validators

import androidx.annotation.StringRes

private const val MAC_REGEX = "((\\d|([a-f]|[A-F])){2}:){5}(\\d|([a-f]|[A-F])){2}"

class MacAddressInputValidator(
    @StringRes private val errorRes: Int
) : RegexInputValidator(errorRes, MAC_REGEX)