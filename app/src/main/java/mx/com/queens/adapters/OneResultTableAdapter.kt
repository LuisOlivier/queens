package mx.com.queens.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.com.queens.R


class OneResultTableAdapter(items:ArrayList<Int>? , listTable:ArrayList<Boolean>?, var listener:ClickListener): RecyclerView.Adapter<OneResultTableAdapter.ViewHolder>() {
    var listTable: ArrayList<Boolean>? = null
    var items: ArrayList<Int>? = null
    var viewHolder:ViewHolder? = null

    init {
        this.listTable = listTable
        this.items = items
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OneResultTableAdapter.ViewHolder {
        val vista = LayoutInflater.from(p0.context).inflate(R.layout.item_table_layout, p0, false)
        viewHolder = ViewHolder(vista, listener)
        return  viewHolder!!
    }

    override fun getItemCount(): Int {
        return  items!!.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        val itemTable = listTable?.get(position)

        if(itemTable!!){
            holder.respuesta!!.setBackgroundColor(Color.BLACK)
        }else{
            holder.respuesta!!.setBackgroundColor(Color.WHITE)
        }

        if(item == 1){
            holder.respuesta?.setText(R.string.str_list_result_adapter_Q)
        }else{
            holder.respuesta?.text = ""
        }

    }

    class  ViewHolder(vista: View, listener: ClickListener): RecyclerView.ViewHolder(vista), View.OnClickListener {
        var vista = vista
        var respuesta: TextView? = null

        var listener: ClickListener? = null

        init {
            respuesta = vista.findViewById(R.id.tvRespuesta)
            this.listener = listener
            vista.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)

        }

    }
}