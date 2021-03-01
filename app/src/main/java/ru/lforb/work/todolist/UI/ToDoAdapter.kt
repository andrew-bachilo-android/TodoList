package ru.lforb.work.todolist.UI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.lforb.work.todolist.Model.ToDo
import ru.lforb.work.todolist.R

class ToDoAdapter(val list:MutableList<ToDo>, val listener: ToDoFragment):RecyclerView.Adapter<ToDoAdapter.ViewHolder>(){
    class ViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.textTitle)
        val description = itemView.findViewById<TextView>(R.id.textDescription)
        val delete = itemView.findViewById<ImageView>(R.id.btnDelete)
        val addToDone = itemView.findViewById<CheckBox>(R.id.checkboxInDone)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        val holder = ViewHolder(itemView)
        holder.delete.setOnClickListener {
            val position = holder.adapterPosition
            listener.deleteTask(position)
        }

        holder.addToDone.setOnClickListener {
            val position = holder.adapterPosition
            listener.addToDone(position)
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = list[position].title
        holder.description.text = list[position].description
        holder.addToDone.setChecked(false)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}