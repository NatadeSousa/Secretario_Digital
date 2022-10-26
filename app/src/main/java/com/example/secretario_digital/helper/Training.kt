package com.example.secretario_digital.helper

import androidx.fragment.app.Fragment
import com.example.secretario_digital.R
import com.example.secretario_digital.databinding.BottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.showBottomDialog(
    titleText: Int? = null,
    infoText: Int,
    buttonText: Int? = null,
    onClick: () -> Unit = {}
) {
    val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)

    val bottomSheetDialogBinding: BottomSheetDialogBinding =
        BottomSheetDialogBinding.inflate(layoutInflater,null,false)

    bottomSheetDialogBinding.textTitle.text = getString(titleText ?: R.string.word_atençao)
    bottomSheetDialogBinding.textInfo.text = getString(infoText)
    bottomSheetDialogBinding.btnClose.text = getString(buttonText ?: R.string.word_entendi)
    bottomSheetDialogBinding.btnClose.setOnClickListener {
        onClick()
        bottomSheetDialog.dismiss()
    }

    bottomSheetDialog.setContentView(bottomSheetDialogBinding.root)
    bottomSheetDialog.show()


}













