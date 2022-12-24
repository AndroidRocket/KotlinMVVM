package com.redapple.views.fragments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redapple.models.Species
import com.redapple.R
import com.redapple.views.fragments.RemoteDataFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.list_layout.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(private val mValues: List<Species>, val listener: (Species) -> Unit) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_remote_data, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(mValues[position],listener)
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: Species, listener: (Species) -> Unit) {
//            val textViewName = itemView.name as TextView
//            textViewName.text = user.name
            itemView.name.text = item.name
            itemView.setOnClickListener { listener(item) }
        }

    }
}
