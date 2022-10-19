package com.example.secretario_digital.helper

import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.example.secretario_digital.R
import com.example.secretario_digital.databinding.BottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Calendar

fun Fragment.showBottomDialogg(
    textTitle: Int? = null,
    textButton: Int? = null,
    message: Int,
    onClick: () -> Unit = {}
){
    val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)

    val bottomSheetDialogBinding: BottomSheetDialogBinding =
        BottomSheetDialogBinding.inflate(layoutInflater,null,false)

    bottomSheetDialogBinding.textTitle.text = getString(textTitle ?: R.string.word_atençao)
    bottomSheetDialogBinding.textInfo.text = getString(message)
    bottomSheetDialogBinding.btnClose.text = getString(textButton ?: R.string.word_entendi)
    bottomSheetDialogBinding.btnClose.setOnClickListener {
        onClick()
        bottomSheetDialog.dismiss()
    }

    bottomSheetDialog.setContentView(bottomSheetDialogBinding.root)
    bottomSheetDialog.show()
}

private val c = Calendar.getInstance()

private val year = c.get(Calendar.YEAR)
private val month = c.get(Calendar.MONTH)
private val day = c.get(Calendar.DAY_OF_MONTH)





















