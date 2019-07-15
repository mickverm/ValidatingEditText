package be.mickverm.widget.textfield

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.StringRes
import be.mickverm.widget.textfield.validators.InputValidator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ValidatingTextInputEditText : TextInputEditText {

    private var valid = false
    private val validators = mutableListOf<InputValidator>()
    private var listener: ValidityChangedListener? = null

    constructor(
        context: Context
    ) : super(context) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validate()
        }

        addTextChangedListener(object : TextWatcher {

            private var initial = true

            override fun afterTextChanged(s: Editable) {
                if (!initial) {
                    validate()
                    return
                }
                initial = false
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun validate() {
        val input = getInput()

        if (validators.isEmpty()) {
            clearErrorMessage()
            valid = true
        } else {
            validators.forEach { validator ->
                val errorRes = validator.validate(input)
                valid = !setErrorMessage(errorRes)
                if (!valid) return
            }
        }

        listener?.onValidityChanged(this, input, valid)
    }

    private fun setErrorMessage(@StringRes errorRes: Int?): Boolean {
        return if (errorRes == null) {
            clearErrorMessage()
            false
        } else {
            setErrorMessage(context.getString(errorRes))
            true
        }
    }

    private fun setErrorMessage(errorMessage: String) {
        val parent = (parent as FrameLayout).parent as? TextInputLayout
        if (parent != null) parent.error = errorMessage
        else error = errorMessage
    }

    private fun clearErrorMessage() {
        val parent = (parent as FrameLayout).parent as? TextInputLayout
        if (parent != null) parent.error = null
        else error = null
    }

    fun addValidator(validator: InputValidator, revalidate: Boolean = false) {
        validators.add(validator)
        if (revalidate) validate()
    }

    fun addValidators(vararg validators: InputValidator, revalidate: Boolean = false) {
        this.validators.addAll(validators)
        if (revalidate) validate()
    }

    fun removeValidator(validator: InputValidator, revalidate: Boolean = false) {
        validators.remove(validator)
        if (revalidate) validate()
    }

    fun removeValidators(vararg validators: InputValidator, revalidate: Boolean = false) {
        this.validators.removeAll(validators)
        if (revalidate) validate()
    }

    fun clearValidators(revalidate: Boolean = false) {
        validators.clear()
        if (revalidate) validate()
    }

    fun isValid(): Boolean = valid

    fun getInput(): String = text.toString()

    @Deprecated(
        message = "Method is going to be replaced by setValidityChangedListener",
        replaceWith = ReplaceWith(
            "setValidityChangedListener(listener)"
        )
    )
    fun setValidityChanged(listener: ValidityChangedListener?) {
        this.listener = listener
    }

    fun setValidityChangedListener(listener: ValidityChangedListener?) {
        this.listener = listener
    }
}
