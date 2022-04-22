package com.yhe64.treasurehunt

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yhe64.treasurehunt.database.Point
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yhe64.treasurehunt.databinding.RecycleviewItemBinding

class PointAdapter: RecyclerView.Adapter<PointAdapter.PointViewHolder>() {

    class PointViewHolder(private val binding: RecycleviewItemBinding): RecyclerView.ViewHolder(binding.root){
        private val picasso = Picasso.get()
        fun bind(point: Point){
            binding.apply {
                nameTextView.text = point.name
                xDistantceTextView.text = point.x_distance.toString()
                yDistantceTextView.text = point.y_distance.toString()
            }
        }
    }

    private var points: List<Point> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= PointViewHolder(
        RecycleviewItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        holder.bind(points[position])
    }

    override fun getItemCount() = points.size

    fun getItemAtPosition(position: Int) = points[position]

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newFriends: List<Point>){
        this.points = newFriends
        notifyDataSetChanged()
    }
}