package com.gabo.moviesapp.other.common

import android.content.Context
import android.graphics.Color.green
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.gabo.moviesapp.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Checkers(private val context: Context, private val binding: ViewBinding) {
    fun emptyError(et: TextInputEditText, til: TextInputLayout): Boolean {
        val color: Int
        val errorMsg: String?
        var helperTextEnabled = true
        val readyToSave: Boolean
        with(til) {
            if (et.text.toString().isEmpty()) {
                errorMsg = context.getString(R.string.errorEmpty)
                color = (R.color.red_required)
                readyToSave = false
            } else {
                errorMsg = null
                color = (R.color.green)
                helperTextEnabled = false
                readyToSave = true
            }
        }
        with(til) {
            error = errorMsg
            setStartIconTintList(AppCompatResources.getColorStateList(context, color))
            boxStrokeColor = ContextCompat.getColor(context, color)
            isHelperTextEnabled = helperTextEnabled
        }
        return readyToSave
    }

    fun repeatPassCheck(
        et: TextInputEditText,
        etRepeated: TextInputEditText,
        tilRepeated: TextInputLayout
    ): Boolean {
        val color: Int
        val errorMsg: String?
        var helperTextEnabled = true
        val readyToSave: Boolean
        with(tilRepeated) {
            when {
                et.text.toString().isNotEmpty() && et.text.toString() == etRepeated.text.toString() -> {
                    errorMsg = null
                    color = (R.color.green)
                    helperTextEnabled = false
                    readyToSave = true
                }
                (et.text.toString().isNotEmpty() && et.text.toString() != etRepeated.text.toString()) -> {
                    errorMsg = context.getString(R.string.passDoesNotMatch)
                    color = (R.color.red_required)
                    readyToSave = false
                }
                else -> {
                    errorMsg = context.getString(R.string.errorEmpty)
                    color = (R.color.red_required)
                    readyToSave = false

                }
            }
        }
        with(tilRepeated) {
            error = errorMsg
            setStartIconTintList(AppCompatResources.getColorStateList(context, color))
            boxStrokeColor = ContextCompat.getColor(context, color)
            isHelperTextEnabled = helperTextEnabled
        }
        return readyToSave
    }

    fun emailCheck(tiet: TextInputEditText, til: TextInputLayout): Boolean {
        val color: Int
        val errorMsg: String?
        var helperTextEnabled = true
        val readyToSave: Boolean
        when {
            tiet.text.toString().isEmpty() -> {
                color = R.color.red_required
                errorMsg = context.getString(R.string.errorEmpty)
                readyToSave = false
            }
            android.util.Patterns.EMAIL_ADDRESS.matcher(tiet.text.toString()).matches() -> {
                errorMsg = null
                color = R.color.green
                helperTextEnabled = false
                readyToSave = true
            }
            else -> {
                errorMsg = context.getString(R.string.errorEmail)
                color = R.color.red_required
                readyToSave = false
            }
        }
        with(til) {
            error = errorMsg
            setStartIconTintList(AppCompatResources.getColorStateList(context, color))
            boxStrokeColor = ContextCompat.getColor(context, color)
            isHelperTextEnabled = helperTextEnabled
        }
        return readyToSave
    }
}