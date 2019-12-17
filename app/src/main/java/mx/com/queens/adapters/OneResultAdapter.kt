package mx.com.queens.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.com.queens.R
import mx.com.queens.entities.SolutionEntity


class OneResultAdapter(items:ArrayList<SolutionEntity>?, var listener:ClickListener): RecyclerView.Adapter<OneResultAdapter.ViewHolder>() {
    var items: ArrayList<SolutionEntity>? = null
    var viewHolder:ViewHolder? = null

    init {
        this.items = items
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OneResultAdapter.ViewHolder {
        val vista = LayoutInflater.from(p0.context).inflate(R.layout.item_list_results, p0, false)
        viewHolder = ViewHolder(vista, listener)
        return  viewHolder!!
    }

    override fun getItemCount(): Int {
        return  items?.count()!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = items!![position]
        holder.respuesta?.text = "(" + (position + 1) + " , " + (item.y.toString().toInt() + 1) + ")"

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