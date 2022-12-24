package com.redapple.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.redapple.R
import com.redapple.views.activity.HomeActivity
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingFragment : DialogFragment() {




    companion object {

        val PREFS_FILENAME = "User"
        val IS_HIDDEN_REQUIRED = "isHidden"
        fun newInstance(): SettingFragment {
            return SettingFragment()
        }
    }


    private fun setHiddenRequired(isHiddenRequired: Boolean) {
        val editor = HomeActivity.prefs!!.edit()
        editor.putBoolean(IS_HIDDEN_REQUIRED, isHiddenRequired)
        editor.apply()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scHideNotes.isChecked = HomeActivity.hideRequired
       btnCancel.setOnClickListener{
           dismiss()
       }
        btnSubmit.setOnClickListener{
            setHiddenRequired(scHideNotes.isChecked)
            Log.e("flag",HomeActivity.hideRequired.toString())

            dismiss()
            (activity as HomeActivity).refreshLIst()
        }


    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog

        if (dialog != null) {
            dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            dialog.window?.attributes?.windowAnimations = R.style.You_Dialog
            dialog.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT)
//            dialog.window.statusBarColor = Color.RED
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_settings, container, false)
    }






}
