package com.example.remind.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.remind.databinding.TodoItemBinding
import com.example.remind.model.TodoItem

class TodoAdapter(private var todos: List<TodoItem>, private val clickListener: TodoClickListener) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todos[position], clickListener)
    }

    override fun getItemCount() = todos.size

    fun setTodoList(newTodos: List<TodoItem>) {
        todos = newTodos
        notifyDataSetChanged()
    }

    class TodoViewHolder(private val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: TodoItem, clickListener: TodoClickListener) {
            binding.todo = todo
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }
}




