package com.example.myadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.adapter.LooperLayoutManager
import com.example.adapter.VerticallyLayoutManager
import com.example.adapter.adapter.RecycleAdapter
import com.example.myadapter.databinding.FruitItemBinding
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

                Toast.makeText(this@MainActivity, data.name, Toast.LENGTH_SHORT).show()

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

}