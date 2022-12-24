package com.redapple.views.adapters

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


import com.redapple.R
import com.redapple.listeners.OnShareIntentClickListener
import kotlinx.android.synthetic.main.layout_share_app.view.*



/**
 * Created by wssolanki on 1/29/2016.
 * Modified by wssolanki on 1/29/2016
 * Purpose : used to set list for filter on right drawer
 */
class ShareIntentAdapter
//    private CallbackManager callbackManager;
//    private LoginManager manager;
// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder

// Provide a suitable constructor (depends on the kind of dataset)
(private val listApp: List<ResolveInfo>, private val context: Context, internal var imageUrl: String) : RecyclerView.Adapter<ShareIntentAdapter.ViewHolder>() {
    var isCheck = true
    internal var mOnRecyclerItemClickListener: OnShareIntentClickListener
    internal var pm: PackageManager

    init {
        this.mOnRecyclerItemClickListener = context as OnShareIntentClickListener
        pm = context.packageManager
        //        setDefaultSelection();
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {


        internal var ivLogo: ImageView? = null

        internal var tvAppName: TextView? = null

        internal var tvPackageName: TextView? = null
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ShareIntentAdapter.ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_share_app, parent, false)
//        val lp = v.layoutParams as GridLayoutManager.LayoutParams
//        lp.height = parent.measuredHeight
//        v.layoutParams = lp
        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val appInfo = listApp[position]
        holder.itemView.iv_logo.setImageDrawable(appInfo.loadIcon(pm))
        holder.itemView.tv_app_name.text = appInfo.loadLabel(pm)
//        holder.itemView.tv_app_package_name.text = appInfo.activityInfo.packageName
        holder.itemView.setOnClickListener { mOnRecyclerItemClickListener.OnShareIntentClick(listApp[position]) }
    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return listApp.size
    }


    fun notifyDataSetChange() {
        notifyDataSetChanged()
    }

}