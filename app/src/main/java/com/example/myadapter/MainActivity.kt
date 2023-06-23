package com.example.myadapter

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.adapter.LooperLayoutManager
import com.example.adapter.VerticallyLayoutManager
import com.example.adapter.adapter.RecycleAdapter
import com.example.myadapter.databinding.FruitItemBinding
import com.permissionx.linyaodev.PermissionX
import com.pgyersdk.update.PgyUpdateManager
import com.pgyersdk.update.UpdateManagerListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val fruitList = ArrayList<User>()


    private val adapter: RecycleAdapter<User> = object : RecycleAdapter<User>(){
        override fun getLayoutId(position: Int): Int {
            return R.layout.fruit_item
        }

        override fun onBindData(binding: ViewDataBinding, data: User, position: Int) {
            super.onBindData(binding, data, position)

            val bind: FruitItemBinding = binding as FruitItemBinding

            bind.item = data

            bind.root.setOnClickListener {

                startActivity<MainActivity2>(this@MainActivity)

            }

            Log.e("TAG", "onBindData: ${data.name}")

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFruit()
        adapter.getDataList().clear()
        adapter.getDataList().addAll(fruitList)
        adapter.notifyDataChange()
        val layoutManager = LinearLayoutManager(this)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter

        PermissionX.request(this,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE
        ){allGranted, deniedList ->
            if (allGranted){
//                PgyUpdateManager.setIsForced(true)
//                PgyUpdateManager.register(this)
//                showDialog()
            }
        }

//        点击item
//        adapter.setOnItemClickListener( object : RecycleAdapter.OnItemClickListener<User>{
//            override fun onItemClick(adapter: RecycleAdapter<User>?, v: View?, position: Int) {
//                val name = adapter?.getItem(position)?.name
//                Toast.makeText(this@MainActivity, name, Toast.LENGTH_SHORT).show()
//            }
//
//        })

//        长按item
//        adapter.setOnItemLongClickListener(object : RecycleAdapter.OnItemLongClickListener<User>{
//            override fun onItemLongClick(
//                adapter: RecycleAdapter<User>?,
//                v: View?,
//                position: Int
//            ): Boolean {
//                val name = adapter?.getItem(position)?.name
//                Toast.makeText(this@MainActivity, name, Toast.LENGTH_SHORT).show()
//                return true
//            }
//        })

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
        PgyUpdateManager.unregister()
    }

    fun showDialog(){

        PgyUpdateManager.register(this,
        object : UpdateManagerListener(){
            override fun onNoUpdateAvailable() {

            }

            override fun onUpdateAvailable(result: String?) {
                val appBen = getAppBeanFromString(result)

                AlertDialog.Builder(this@MainActivity)
                    .setTitle("更新")
                    .setMessage("")
                    .setNegativeButton("确定", object : DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            startDownloadTask(this@MainActivity, appBen.downloadURL)
                        }

                    }).show()

                UpdateManagerListener.updateLocalBuildNumber(result)

            }

        })



    }

    private fun initFruit(){
        repeat(2){

            fruitList.add(User("aaaaa", "24"))
            fruitList.add(User("bbbbb", "24"))
            fruitList.add(User("ccccc", "24"))
            fruitList.add(User("ddddd", "24"))
            fruitList.add(User("eeeee", "24"))
            fruitList.add(User("fffff", "24"))
            fruitList.add(User("ggggg", "24"))

        }
    }

    inline fun <reified T> startActivity(context: Context){
        val intent = Intent(context, T::class.java)
        context.startActivity(intent)
    }

}