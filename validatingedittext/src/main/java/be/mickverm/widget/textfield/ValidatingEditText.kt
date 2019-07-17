package be.mickverm.widget.textfield

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import be.mickverm.widget.textfield.validators.InputValidator


class ValidatingEditText : EditText {

    private var valid = false
    private val validators = mutableListOf<InputValidator>()
    private var listener: ValidityChangeListener? = null

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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
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
        error = errorMessage
    }

    private fun clearErrorMessage() {
        error = null
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

    fun setValidityChangeListener(listener: ValidityChangeListener?) {
        this.listener = listener
    }

    fun setValidityChangeListener(listener: (EditText, String, Boolean) -> Unit) {
        this.listener = object : ValidityChangeListener {
            override fun onValidityChanged(editText: EditText, input: String, valid: Boolean) {
                listener.invoke(editText, input, valid)
            }
        }
    }
}
