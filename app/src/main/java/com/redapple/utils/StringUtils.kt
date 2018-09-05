package com.redapple.utils

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.util.Patterns

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Created by 1064 on 1/8/2016.
 */
object StringUtils {
    val EMPTY = ""

    /**
     * Created by Dev 1064 on 12/21/2015
     * Modified by Dev 1064 on 12/21/2015
     *
     *
     * To check out that given string is null or empty after trimming
     *
     * @param aString String to check
     * @return true if String is empty even if it is pad with white space
     */
    fun isTrimmedEmpty(aString: String?): Boolean {
        return aString == null || aString.trim { it <= ' ' }.length == 0
    }

    /**
     * This method is used to check is it a valid email id or not.
     *
     * @param email
     * @return
     */
    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     *
     * @param aInputStream
     * @return
     * Purpose : this method is used to convert Input stream to string
     */
//    fun convertStreamToString(aInputStream: InputStream): String {
//        val reader = BufferedReader(InputStreamReader(aInputStream))
//        val sb = StringBuilder()
//
//        var line: String? = null
//        try {
//            while ((line = reader.readLine()) != null) {
//                sb.append(line!! + "\n")
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } finally {
//            try {
//                aInputStream.close()
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//
//        }
//        return sb.toString()
//    }

    /**
     *
     *
     * @Name Utils.java
     * @CreatedBy Wilson 1299
     * @CreatedDate Jul 23, 2015
     * @ModifiedBy Wilson 1299
     * @ModifiedDate
     * @returns String
     * @Purpose This method is usedto  generate javascript for highlightning test on webapage
     */
//    @Throws(IOException::class)
//    fun getStringFromAssets(aContext: Context, aFilePath: String): String {
//        var orderListRes = ""
//        val `is` = aContext.assets.open(aFilePath)
//        orderListRes = convertStreamToString(`is`)
//        Log.e("highlight", orderListRes)
//
//        return orderListRes
//
//    }
}
