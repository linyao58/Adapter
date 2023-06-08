package com.example.adapter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.adapter.BR


abstract class RecycleAdapter<T>: RecyclerView.Adapter<RecycleAdapter.ViewHolder>() {

    private var datalist: MutableList<T> = ArrayList()
    private var inflater: LayoutInflater? = null

    private var listener: OnItemClickListener<T>? = null
    private var longClickListener: OnItemLongClickListener<T>? = null

    protected abstract fun getLayoutId(position: Int): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (inflater == null){
            inflater = LayoutInflater.from(parent.context)
        }

        val binding = inflater?.let { inflate<ViewDataBinding>(it, viewType, parent, false) }

        return ViewHolder(binding!!)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        //setup item click listener
        setupListener(holder.itemView, position)
        if (holder.binding != null){
            onBindData(holder.binding!!, data, position)
        }
    }

    protected open fun onBindData(binding: ViewDataBinding, data: T, position: Int) {
        binding.setVariable(BR.item, data)
        binding.executePendingBindings()
    }

    override fun getItemViewType(position: Int): Int{
        return getLayoutId(position)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

   open fun getItem(position: Int): T{
        return datalist[position]
    }

    open fun setOnItemClickListener(listeners: OnItemClickListener<T>?) {
        listener = listeners
    }

    open fun setOnItemLongClickListener(listener: OnItemLongClickListener<T>?) {
        longClickListener = listener
    }

    open fun appendToDataList(dataList: List<T>?) {
        datalist.addAll(dataList!!)
    }


    open fun notifyDataChange(){
        notifyDataSetChanged()
    }

    private  fun setupListener(view: View, position: Int) {
        val adapter = this
        if (listener != null) {
            view.setOnClickListener { v: View? ->
                listener!!.onItemClick(
                    adapter,
                    v,
                    position
                )
            }
        } else {
            view.setOnClickListener(null)
        }
        if (longClickListener != null) {
            view.setOnLongClickListener { v: View? ->
                longClickListener!!.onItemLongClick(
                    adapter,
                    v,
                    position
                )
            }
        } else {
            view.setOnLongClickListener(null)
        }
    }

    open class ViewHolder(rootView: View?) : RecyclerView.ViewHolder(
        rootView!!
    ) {
         var binding: ViewDataBinding? = null

        constructor(binding: ViewDataBinding) : this(binding.root) {
            this.binding = binding
        }
    }

   open fun setDataList(dataList: MutableList<T>?){
        datalist.clear()
        if (dataList != null) {
            datalist.addAll(dataList)
        }

    }

   open fun getDataList(): MutableList<T>{
        return datalist
    }

   open fun getLayoutInflater(): LayoutInflater?{
        return inflater
    }

   open fun getHeaderView(): View? {
        return null
    }

   open fun getFooterView(): View? {
        return null
    }

    interface OnItemClickListener<T> {
        fun onItemClick(adapter: RecycleAdapter<T>?, v: View?, position: Int)
    }

    interface OnItemLongClickListener<T> {
        fun onItemLongClick(adapter: RecycleAdapter<T>?, v: View?, position: Int): Boolean
    }

}