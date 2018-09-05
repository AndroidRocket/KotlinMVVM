package com.redapple.views.adapters

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redapple.R
import com.redapple.models.TaskItem
import com.redapple.models.TaskItems
import kotlinx.android.synthetic.main.list_layout.view.*

/**
 * Created by perfect on 9/13/17.
 */
class TaskListAdapter(private val context: Context): RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    private var taskItems: TaskItems? = null

    var items: TaskItems
        get() = taskItems ?: emptyList()
        set(newList) {
            val oldList = items
            taskItems = newList

            DiffUtil.calculateDiff(object: DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return false
                }

                override fun getOldListSize(): Int = oldList.size

                override fun getNewListSize(): Int = newList.size

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldItem = oldList[oldItemPosition]
                    val newItem = newList[newItemPosition]

                    return oldItem.name == newItem.name && oldItem.id == newItem.id
                }

            }).dispatchUpdatesTo(this)
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        taskItems?.also { items -> holder.bindItem(items[position]) }
    }

    override fun getItemCount(): Int = taskItems?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val feedItemView = LayoutInflater.from(context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(feedItemView)
    }

    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        fun bindItem(item: TaskItem) {
            itemView.name.text = item.name
        }
    }
}