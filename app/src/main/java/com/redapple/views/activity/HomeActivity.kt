package com.redapple.views.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Dialog
import android.content.*
import android.content.pm.ResolveInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.TextUtils
import android.view.*
import android.widget.ImageView
import com.google.android.gms.ads.AdRequest
import com.pakhee.common.CryptLib
import com.redapple.R
import com.redapple.listeners.OnShareIntentClickListener
import com.redapple.models.TaskItem
import com.redapple.utils.Utils
import com.redapple.views.fragments.DatabaseFragment
import com.redapple.views.fragments.SettingFragment
import com.redapple.views.fragments.ShareIntentFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.dialog.*
import kotlinx.android.synthetic.main.dialog.view.*
import java.io.UnsupportedEncodingException
import kotlin.properties.Delegates
import android.widget.Toast




class HomeActivity : AppCompatActivity(), OnShareIntentClickListener {
    override fun OnShareIntentClick(appInfo: ResolveInfo) {
        shareIntentFragment.dismiss()
       share(appInfo,"")

    }



    private var activityTitles: Array<String>? = null
    lateinit var fragment: Fragment
    lateinit var context: Context
    var fm = supportFragmentManager
    val shareIntentFragment = ShareIntentFragment()

    // flag to load home fragment when user presses back key
    private val shouldLoadHomeFragOnBackPress = true
    private var mHandler: Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        prefs = this.getSharedPreferences(SettingFragment.PREFS_FILENAME, 0)

        context = this

        val adRequest = AdRequest.Builder()
                .build()

        adView.loadAd(adRequest)
        mHandler = Handler()
        activityTitles = resources.getStringArray(R.array.nav_item_activity_titles)
//        fab.setOnClickListener { view ->
//            showDiag(null)
//        }
        toolbar.title = "Notes"
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        setUpNavigationView()
        loadHomeFragment()

    }


    private fun share(appInfo: ResolveInfo?, msg: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        try {
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
            var sAux = "\nLet me recommend you this application\n\n"
            sAux += "https://play.google.com/store/apps/details?id=com.redapple.todo \n\n"
            sendIntent.putExtra(Intent.EXTRA_TEXT, sAux)
            if (appInfo != null) {
                sendIntent.component = ComponentName(
                        appInfo.activityInfo.packageName,
                        appInfo.activityInfo.name)
            }
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_add_task -> consume { showDiag(null) }

        else -> super.onOptionsItemSelected(item)
    }


    inline fun consume(f: () -> Unit): Boolean {
        f()
        return true
    }
    fun refreshLIst()
    {
        (fragment as DatabaseFragment).refreshList()
    }
    fun showDiag(taskItem: TaskItem?) {

        val dialogView = View.inflate(this, R.layout.dialog, null)

        val dialog = Dialog(this, R.style.MyAlertDialogStyle)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogView)
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        val imageView = dialog.closeDialogImg as ImageView
        imageView.setOnClickListener { revealShow(dialogView, false, dialog) }

        if(taskItem!=null)
        {
            dialog.chkHide.isChecked = taskItem.isDone
            dialog.edTaskName.setText(taskItem.name)
            dialog.date.text = Utils.getFormattedDate(taskItem.createdDate)
        }
        else
        {
            dialog.date.text = Utils.getFormattedDate(Utils.getCurrentDateInString())
        }

        dialog.btnAddTask.setOnClickListener {
            if (!TextUtils.isEmpty(dialog.edTaskName.text.toString().trim())) {
                if(taskItem!=null)
                {
                    (fragment as DatabaseFragment).updateTask(dialog.edTaskName.text.toString(),dialog.chkHide.isChecked)
                }
                else
                {
                    (fragment as DatabaseFragment).insertTask(dialog.edTaskName.text.toString(),dialog.chkHide.isChecked)
                }
                revealShow(dialogView, false, dialog)
            } else {
                dialog.edTaskName.error = "Please Enter Note"
//                Toast.makeText(context,"Please enter note",Toast.LENGTH_LONG).show()
            }
        }
        dialog.setOnShowListener { revealShow(dialogView, true, null) }

        dialog.setOnKeyListener(DialogInterface.OnKeyListener { dialogInterface, i, keyEvent ->
            if (i == KeyEvent.KEYCODE_BACK) {

                revealShow(dialogView, false, dialog)
                return@OnKeyListener true
            }

            false
        })



        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    private fun revealShow(dialogView: View, b: Boolean, dialog: Dialog?) {

        val view = dialogView.dialog

        val w = view.width
        val h = view.height

        val endRadius = Math.hypot(w.toDouble(), h.toDouble()).toInt()

        val cx = (fab.x + fab.width / 2).toInt()
        val cy = fab.y.toInt() + fab.height + 56


        if (b) {
            val revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, endRadius.toFloat())

            view.visibility = View.VISIBLE
            revealAnimator.duration = 700
            revealAnimator.start()

        } else {

            val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius.toFloat(), 0f)

            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    dialog!!.dismiss()
                    view.visibility = View.INVISIBLE

                }
            })
            anim.duration = 700
            anim.start()
        }

    }


    private fun loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu()

        // set toolbar title
        setToolbarTitle()

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (supportFragmentManager.findFragmentByTag(CURRENT_TAG) != null) {
            drawer_layout!!.closeDrawers()

            // show or hide the fab button
//            toggleFab()
            return
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        val mPendingRunnable = Runnable {
            // update the main content by replacing fragments

            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out)
            fragmentTransaction.replace(R.id.container, homeFragment, CURRENT_TAG)
            fragmentTransaction.commitAllowingStateLoss()
        }

        // If mPendingRunnable is not null, then add to the message queue
        mHandler?.post(mPendingRunnable)

        // show or hide the fab button
//        toggleFab()

        //Closing drawer on item click
        drawer_layout!!.closeDrawers()

        // refresh toolbar menu
        invalidateOptionsMenu()
    }

    private fun setToolbarTitle() {
        toolbar!!.title = activityTitles!![navItemIndex]

    }

    private fun selectNavMenu() {
        nav_view!!.menu.getItem(navItemIndex).isChecked = true
    }

    private val homeFragment: Fragment
        get() {
            return when (navItemIndex) {
                0 -> {
                    fragment = DatabaseFragment.newInstance()
                    fragment
                }
                2 -> {
                    fragment = SettingFragment.newInstance()
                    fragment
                }
                else -> {
                    fragment = DatabaseFragment.newInstance()
                    fragment
                }
            }
        }


    private fun setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        nav_view!!.setNavigationItemSelectedListener({ menuItem ->
            // This method will trigger on item Click of navigation menu
            //Check to see which item was being clicked and perform appropriate action
            when (menuItem.itemId) {
                R.id.nav_notes -> {
                    navItemIndex = 0
                    CURRENT_TAG = TAG_NOTES
                    loadHomeFragment()
                }
                R.id.nav_share_app -> {
                    navItemIndex = 1
                    CURRENT_TAG = TAG_SHARE_APP


                    val bundle = Bundle()
//                    bundle.putString("share_message", shareLink)
//                    bundle.putString("url", productInfo.getSKUList().get(0).getImage().get(0))
//                    fragment1.setArguments(bundle)
                    shareIntentFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.You_Dialog)
                    shareIntentFragment!!.show(supportFragmentManager, "")
                    drawer_layout!!.closeDrawers()
//                    Utils.shareApp(this)
                }
                R.id.nav_settings -> {
                    navItemIndex = 2
                    CURRENT_TAG = TAG_SETTINGS
                    val dFragment = SettingFragment()
                    // Show DialogFragment
                    dFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.You_Dialog)
                    dFragment.show(fm, "Dialog Fragment")
                    drawer_layout!!.closeDrawers()
//                    loadHomeFragment()
                }

            }

            //Checking if the item is in checked state or not, if not make it in checked state
            menuItem.isChecked = !menuItem.isChecked
            menuItem.isChecked = true



            true
        })


    }

    companion object {

        // index to identify current nav menu item
        var navItemIndex = 0

        // tags used to attach the fragments
        private val TAG_NOTES = "notes"
        private val TAG_SHARE_APP = "share app"
        private val TAG_SETTINGS = "settings"
        var CURRENT_TAG = TAG_NOTES

        var prefs : SharedPreferences by Delegates.notNull()
        var hideRequired: Boolean
            get() = prefs!!.getBoolean(SettingFragment.IS_HIDDEN_REQUIRED, false)
            set(value) = prefs!!.edit().putBoolean(SettingFragment.IS_HIDDEN_REQUIRED, value).apply()
    }


}
