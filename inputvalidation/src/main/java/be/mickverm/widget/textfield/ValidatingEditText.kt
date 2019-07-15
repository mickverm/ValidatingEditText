package be.mickverm.widget.textfield

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import androidx.annotation.RequiresApi
import be.mickverm.widget.textfield.validators.InputValidator


class ValidatingEditText : EditText {

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
        validators.forEach { validator ->
            val input = getInput()
            val error = validator.validate(input)
            valid = error == null

            listener?.onValidityChanged(this, input, valid)

            if (error == null) setError(null)
            else {
                setError(context.getString(error))
                return
            }
        }
    }

    fun addValidator(validator: InputValidator) {
        validators.add(validator)
    }

    fun addValidators(vararg validators: InputValidator) {
        this.validators.addAll(validators)
    }

    fun removeValidator(validator: InputValidator) {
        validators.remove(validator)
    }

    fun removeValidators(vararg validators: InputValidator) {
        this.validators.removeAll(validators)
    }

    fun clearValidators() {
        validators.clear()
    }

    fun isValid(): Boolean = valid

    fun getInput(): String = text.toString()

    fun setValidityChanged(listener: ValidityChangedListener?) {
        this.listener = listener
    }
}
