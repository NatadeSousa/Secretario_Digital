package com.example.secretario_digital.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.fragment.app.Fragment
import com.example.secretario_digital.R
import com.example.secretario_digital.databinding.BottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Calendar


fun Fragment.showBottomDialog(
    titleDialog: Int? = null,
    titleButton: Int? = null,
    message: Int,
    onClick: () -> Unit = {}
){
    val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)

    val bottomSheetDialogBinding: BottomSheetDialogBinding =
        BottomSheetDialogBinding.inflate(layoutInflater,null,false)

    bottomSheetDialogBinding.textTitle.text = getString(titleDialog ?: R.string.word_atençao)
    bottomSheetDialogBinding.textInfo.text = getString(message)
    bottomSheetDialogBinding.btnClose.text = getString(titleButton ?: R.string.word_entendi)
    bottomSheetDialogBinding.btnClose.setOnClickListener {
        onClick()
        bottomSheetDialog.dismiss()
    }

    bottomSheetDialog.setContentView(bottomSheetDialogBinding.root)
    bottomSheetDialog.show()

}






fun isNetworkAvailable(context: Context?): Boolean {
    if (context == null) return false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            return true
        }
    }
    return false
}