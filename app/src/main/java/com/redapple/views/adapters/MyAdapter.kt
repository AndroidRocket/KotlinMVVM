package com.redapple.views.adapters


import android.graphics.Color
import android.util.Log
import android.view.*
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.loopeer.shadow.ShadowView
import com.redapple.R
import com.redapple.listeners.RecyclerViewItemListener
import com.redapple.models.TaskItem
import com.redapple.utils.Utils
import kotlinx.android.synthetic.main.list_layout.view.*
import java.util.*


/**
 * Created by Wilson on 14-02-2018.
 */
class MyAdapter(val myAndroidOSList: ArrayList<TaskItem>) : RecyclerView.Adapter<MyAdapter.ViewHolder>()
{
    lateinit var listener : RecyclerViewItemListener
    private val mRandom = Random()
    private var multiSelect= false
    var selectedItems : ArrayList<TaskItem> = ArrayList()
//    var stringRepresentation: ArrayList<TaskItem>
//        get() = selectedItems
//        set(value) {
//            selectedItems // parses the string and assigns values to other properties
//        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(v)
    }


    fun registerListener(listener: RecyclerViewItemListener)
    {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return myAndroidOSList.size //To change body of created functions use File | Settings | File Templates.
    }

    fun getRandomIntInRange(max: Int, min: Int): Int {
        return mRandom.nextInt(max - min + min) + min
    }

    fun getRandomHSVColor(): Int {
        // Generate a random hue value between 0 to 360
        val hue = mRandom.nextInt(361)
        // We make the color depth full
        val saturation = 0.2f
        // We make a full bright color
        val value = 1.0f
        // We avoid color transparency
        val alpha = 255
        // Finally, generate the color
        // Return the color
            return Color.HSVToColor(alpha, floatArrayOf(hue.toFloat(), saturation, value))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(myAndroidOSList[position])
        selectItem(myAndroidOSList[position],holder.itemView.card)
        holder.itemView.card.backgroundClr = getRandomHSVColor()
        ViewCompat.setElevation(holder.itemView.card, 1.0F)
//        holder.itemView.ckTaskChecked.setOnCheckedChangeListener{
//            compoundButton, b ->
//            listener.moveToDone(myAndroidOSList[position])
//        }
        holder.itemView.ivEdit.setOnClickListener{
            listener.onEditClick(position)
        }
//        holder.itemView.setOnLongClickListener( { view ->
//            (view.context as AppCompatActivity).startSupportActionMode(actionModeCallbacks)
//            selectItem(myAndroidOSList[position],holder.itemView.card)
//            true
//        })
        holder.itemView.setOnClickListener {
            selectItem(myAndroidOSList[position],holder.itemView.card)
        }
    }


    val actionModeCallbacks = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            multiSelect = true
            menu.add("Delete")
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            listener.onDeleteClick(selectedItems)
            mode.finish()
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            multiSelect = false
            selectedItems.clear()
            notifyDataSetChanged()
        }
    }


    fun selectItem(item: TaskItem,view : ShadowView) {
        if (multiSelect) {
            if (selectedItems.contains(item)) {
                Log.e(item.name,"contains")
                selectedItems.remove(item)
                view.alpha = 1.0f
            } else {
                selectedItems.add(item)
                Log.e(item.name," not contains")
                view.alpha = 0.5f
            }
        }
        else
        {
            view.alpha = 1.0f
        }
    }

    fun cleaRUI(view : ShadowView)
    {
        view.alpha = 1.0f
    }

    fun addItem(name: TaskItem) {
        myAndroidOSList.add(name)
        notifyItemInserted(myAndroidOSList.size)
    }

    fun removeAt(position: Int) {
        myAndroidOSList.removeAt(position)
        notifyItemRemoved(position)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: TaskItem) {
//            val textViewName = itemView.name as TextView
//            textViewName.text = user.name
            itemView.name.text = item.name
            itemView.date.text = Utils.getFormattedDate(item.createdDate)
            itemView.ckTaskChecked.setOnCheckedChangeListener(null)
            itemView.ckTaskChecked.isChecked = item.isDone

//            itemView.setOnClickListener { listener(item) }
        }

    }

}