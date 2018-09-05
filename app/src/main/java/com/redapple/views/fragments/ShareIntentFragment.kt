package com.redapple.views.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ResolveInfo
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.LinearLayout
import com.redapple.R
import com.redapple.listeners.OnShareIntentClickListener
import com.redapple.views.activity.HomeActivity
import com.redapple.views.adapters.ShareIntentAdapter
import kotlinx.android.synthetic.main.dialog_shareintent_layout.*
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.ArrayList
import kotlin.properties.Delegates


class ShareIntentFragment : DialogFragment() {


//    var prefs : SharedPreferences by Delegates.notNull()
//    var hideRequired: Boolean
//        get() = prefs!!.getBoolean(IS_HIDDEN_REQUIRED, false)
//        set(value) = prefs!!.edit().putBoolean(IS_HIDDEN_REQUIRED, value).apply()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shareIntentList.layoutManager = GridLayoutManager(activity, 3)
        val mCancelReasonAdapter = ShareIntentAdapter(showAllShareApp(), activity as Context, "")
        shareIntentList.adapter = mCancelReasonAdapter
    }



    private fun showAllShareApp(): List<ResolveInfo> {

        var listApp: List<ResolveInfo> = ArrayList()

        val intent = Intent(Intent.ACTION_SEND, null)
//        intent.putExtra(Intent.EXTRA_TEXT, imageLink)
        intent.type = "text/plain"
        val pManager = activity!!.packageManager
        listApp = pManager.queryIntentActivities(intent,
                0)


        return listApp
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            dialog.window.attributes.windowAnimations = R.style.You_Dialog
            dialog.window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.dialog_shareintent_layout, container, false)
    }






}
