package com.redapple.listeners

import android.content.pm.ResolveInfo

interface OnShareIntentClickListener {
    fun OnShareIntentClick(appInfo: ResolveInfo)
}
