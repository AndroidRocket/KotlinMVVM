package com.redapple.utils

import java.text.SimpleDateFormat
import java.util.*
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity




/**
 * Created by Wilson on 10-03-2018.
 */
class Utils {

    companion object {
        val df = SimpleDateFormat("yyyy-mm-dd HH:MM:SS")
        val postFormater = SimpleDateFormat("MMM dd, yyyy")
        private val cache = Hashtable<String, Typeface>()
        fun getCurrentDateInString() : String
        {
            val c = Calendar.getInstance().getTime()
            return df.format(c)
        }

        fun getFormattedDate(date : String) : String
        {
            val dateObj = df.parse(date)
            return postFormater.format(dateObj)
        }

        fun getTypeFace(aContext: Context, assetFile: String): Typeface? {
            synchronized(cache) {
                if (!cache.containsKey(assetFile)) {
                    try {
                        val t = Typeface.createFromAsset(aContext.getAssets(),
                                assetFile)
                        cache.put(assetFile, t)
                    } catch (e: Exception) {
                        Log.e(TAG, "Could not get1 typeface '" + assetFile
                                + "' because " + e.message)
                        return null
                    }

                }
                return cache.get(assetFile)
            }
        }

        fun shareApp(mContext: Context)
        {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_SUBJECT, "My application name")
            var sAux = "\nLet me recommend you this application\n\n"
            sAux += "https://play.google.com/store/apps/details?id=com.redapple.todo \n\n"
            i.putExtra(Intent.EXTRA_TEXT, sAux)
            mContext.startActivity(Intent.createChooser(i, "choose one"))
        }
    }

}