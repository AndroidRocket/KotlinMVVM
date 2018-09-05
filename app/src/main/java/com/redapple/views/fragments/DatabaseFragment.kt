package com.redapple.views.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import boonya.ben.callingwebservice.model.Species
import com.redapple.MyApp
import com.redapple.R
import com.redapple.listeners.RecyclerViewItemListener
import com.redapple.models.TaskItem
import com.redapple.repositories.TaskRepositoryImpl
import com.redapple.viewmodels.TaskViewModel
import com.redapple.views.activity.HomeActivity
import com.redapple.views.adapters.MyAdapter
import com.redapple.views.widgets.ItemOffsetDecoration
import kotlinx.android.synthetic.main.fragment_database.*
import kotlin.properties.Delegates


class DatabaseFragment : Fragment(), RecyclerViewItemListener {


    var dataList = ArrayList<TaskItem>()
    lateinit var speciesList: ArrayList<Species>
    lateinit var adapter: MyAdapter
    var lastpostion: Int by Delegates.notNull()
    var editpostion: Int by Delegates.notNull()
//    lateinit var recycler_view: RecyclerView
//    lateinit var btnAddTask : Button

    private val simpleViewModel: TaskViewModel by lazy {
        ViewModelProviders.of(this).get(TaskViewModel::class.java).also {
            MyApp.component.inject(it)
        }
    }

    companion object {

        fun newInstance(): DatabaseFragment {
            return DatabaseFragment()
        }
    }


//    private val postsViewModel: TaskViewModel by lazy {
//        val viewModelFactory = ViewModelFactory(this)
//        val viewModelProvider = ViewModelProviders.of(this, viewModelFactory)
//        viewModelProvider.get(PostsViewModel::class.java)
//    }

//    http://kotlinlang.org/docs/reference/basic-syntax.html


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        recycler_view = view.findViewById(R.id.recyclerView)
//        btnAddTask = view.findViewById(R.id.btnAddTask)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val itemDecoration = ItemOffsetDecoration(activity as Context, R.dimen.tasklist_space)
        recyclerView.addItemDecoration(itemDecoration)

        emptyView.setOnClickListener() {
            (activity as HomeActivity).showDiag(null)
        }
        adapter = MyAdapter(dataList)
        adapter.registerListener(this)


//        val swipeHandler = object : SwipeToDeleteCallback(activity as Context) {
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//
//                simpleViewModel.deleteTask(dataList[viewHolder.adapterPosition])
//                lastpostion = viewHolder.adapterPosition
//            }
//        }
//        val itemTouchHelper = ItemTouchHelper(swipeHandler)
//        itemTouchHelper.attachToRecyclerView(recyclerView)
        recyclerView.adapter = adapter

        attachObserver(simpleViewModel)


        simpleViewModel.getListFromDB()
//        simpleViewModel.getListFromApi()
//        btnAddTask.setOnClickListener{
//            simpleViewModel.insertIntoDB(TaskItem(name = edTaskName.text.toString().trim(),desc = edTaskName.text.toString().trim())) }
    }



    fun refreshList()
    {
        simpleViewModel.getListFromDB()
    }


    fun insertTask(name: String, desc: Boolean) {

        var note = name
//        if(desc) {
//            val _crypt = CryptLib()
//            note = _crypt.encrypt(note)
//        }
        val list: ArrayList<TaskItem> = ArrayList<TaskItem>()
        list.add(TaskItem(name = note, desc = note,isDone = desc))
        simpleViewModel.insertIntoDB(list)
    }

    fun updateTask(toString: String, checked: Boolean) {

        var note = toString
//        if(checked) {
//            val _crypt = CryptLib()
//            note = _crypt.encrypt(note)
//        }
        dataList[editpostion].desc = note
        dataList[editpostion].name = note
        dataList[editpostion].isDone = checked
        val list: ArrayList<TaskItem> = ArrayList<TaskItem>()
        list.add(dataList[editpostion])
        simpleViewModel.updateTask(list)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_database, container, false)
        return view
    }


    fun <T> with(t: T, body: T.() -> Unit) {
        t.body()
    }

    private fun attachObserver(viewModel: TaskViewModel) {
        viewModel.isLoading.observe(this, Observer<Boolean> {
            it?.let { Log.e("loading", it.toString()) }
        })
        viewModel.apiError.observe(this, Observer<Throwable> {
            it?.let { Log.e("error", it.localizedMessage) }
        })
//        viewModel.apiResponse.observe(this, Observer<List<Species>> {
//            it?.let { adapter.notifyDataSetChanged() }
//        })

        viewModel.apiResponse.observe(this, Observer<List<Species>> { it ->
            if (it != null) {
                speciesList = it as ArrayList<Species>
                for (i in 0..(speciesList.size - 1)) {
                    Log.e("name", speciesList[i].name)
                }
            }
        })

        viewModel.users.observe(this, Observer<List<TaskItem>> { users ->


            dataList.clear()
            users?.let { dataList.addAll(it) }

            if (dataList.size > 0) {
                emptyView.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                adapter.notifyDataSetChanged()
            } else {
                emptyView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }


        })
        viewModel.taskItem.observe(this, Observer<ArrayList<TaskItem>> { it ->


            it?.let { it ->

//                if(HomeActivity.hideRequired)
//                {
                    dataList.add(0, it[0])
                    adapter.notifyDataSetChanged()
                    adapter.notifyItemInserted(0)
//                }
//                else
//                {
//                    if(!it[0].isDone)
//                    {
//                        dataList.add(0, it[0])
//                        adapter.notifyDataSetChanged()
//                        adapter.notifyItemInserted(0)
//                    }
//                }

            }




            if (dataList.size > 0) {
                emptyView.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                adapter.notifyDataSetChanged()
            } else {
                emptyView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
            recyclerView.smoothScrollToPosition(0)
        })




        viewModel.delteTaskItem.observe(this, Observer<List<TaskItem>> { users ->


            users?.let { dataList.addAll(it) }

            if (dataList.size > 0) {
                emptyView.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                adapter.notifyDataSetChanged()
            } else {
                emptyView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }


        })

        viewModel.delteTaskItem.observe(this, Observer<List<TaskItem>> { deletedItems ->



            for (item in TaskRepositoryImpl.list)
                if(dataList.contains(item))
                    {dataList.remove(item)}

            if (dataList.size > 0) {
                emptyView.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                adapter.notifyDataSetChanged()
            } else {
                emptyView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
            adapter.notifyDataSetChanged()
            TaskRepositoryImpl.list.clear()
        })
        viewModel.updateTask.observe(this, Observer<ArrayList<TaskItem>> { it ->


            adapter.notifyItemChanged(editpostion)
        })
    }

    override fun onItemClick(taskItem: TaskItem) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveToDone(taskItem: TaskItem) {
        val list: ArrayList<TaskItem> = ArrayList()
        if(editpostion!=null)
        {
            list.add(dataList[editpostion])
            simpleViewModel.moveToDone(list)
        }

    }

    override fun moveToTrash(taskItem: TaskItem) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDeleteClick(taskList: ArrayList<TaskItem>) {

        simpleViewModel.deleteTask(taskList)

    }

    override fun onEditClick(taskItem: Int) {
        editpostion = taskItem
        (activity as HomeActivity).showDiag(dataList[taskItem])
    }


}
