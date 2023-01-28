package com.tiesiogdvd.testingroomhilt.ui.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tiesiogdvd.testingroomhilt.data.Task
import com.tiesiogdvd.testingroomhilt.databinding.ItemTaskBinding


//List adapter extends to RecyclerView adapter
//It can calculate differences between old and new lists and provide correct methods and animations
//All of it done in background thread
class TasksAdapter : ListAdapter<Task, TasksAdapter.TasksViewHolder>(DiffCallBack()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TasksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    //Requires viewbinding enabled to show ItemTaskBinding, which is generated automatically from layout
    class TasksViewHolder(private val binding: ItemTaskBinding):RecyclerView.ViewHolder(binding.root){
        //This function will the the one to put Task items in the views layout
        fun bind(task:Task){
            //This function just makes it less tedious, doesn't require to write binding.checkbox... and etc.
            binding.apply {
                itemCheckbox.isChecked = task.completed
                itemTextviewName.text = task.name
                itemTextviewName.paint.isStrikeThruText = task.completed
                itemLabelPriority.isVisible = task.important
            }
        }
    }

    //Implementing DiffCalback to pass to ListAdapter for it to know which contents to change and etc.
    class DiffCallBack: DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem.id == newItem.id
            //Checks if maybe the items have switches positions
            //Uses id because they are unique to the item

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem==newItem
            //Checks if two item contents are the same
            //Return oldItem==newItem, just shortened Kotlin version
            //Because of the use of data class as a class for Task it automatically implements the equals method
            //For that reason we don't need to compare each of the task elements
    }

}