package com.yhe64.treasurehunt.ui.main

import android.content.SharedPreferences
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yhe64.treasurehunt.R
import com.yhe64.treasurehunt.PointViewModel
import com.yhe64.treasurehunt.databinding.MainFragmentBinding
import com.yhe64.treasurehunt.database.Point
import com.yhe64.treasurehunt.TreasurehuntApp.Companion.CARD_COLOR
import com.yhe64.treasurehunt.TreasurehuntApp.Companion.SHOW_POINT_SCORE

class MainFragment : Fragment(){

    private val sharedViewModel: PointViewModel by activityViewModels()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val prefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    private val pointAdapter = PointAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container,false)
        binding.apply {
            addPointFab.setOnClickListener{
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToDataEntryFragment())
            }
            goGame.setOnClickListener{
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToGameFragment())
            }
            pointsRecyclerview.run{
                layoutManager = LinearLayoutManager(context)
                adapter = pointAdapter
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.points.observe(viewLifecycleOwner){
            pointAdapter.updatePoints(it)
        }

        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ) = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val thisFriend = pointAdapter.getPointAtPosition(viewHolder.adapterPosition)
                    //context?.toast("Delete: ${thisPoint.name}")
                    itemDeletedAlert(thisFriend)
                }
            }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.pointsRecyclerview)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun itemDeletedAlert(point: Point){
        val msg = getString(R.string.delmsg, point.name)
        val builder = AlertDialog.Builder(context)
        with(builder){
            setTitle("Alert")
            setMessage(msg)
            setPositiveButton(R.string.yes){_,_ ->
                sharedViewModel.delete(point = point)
            }
            setNegativeButton(R.string.no){_,_ ->
                pointAdapter.notifyDataSetChanged()
            }
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class PointViewHolder(view: View): RecyclerView.ViewHolder(view){
        private lateinit var point: Point

        private val xDistantceTextView: TextView = itemView.findViewById(R.id.x_distantce_textView)
        private val yDistantceTextView: TextView = itemView.findViewById(R.id.y_distantce_textView)
        private val scoreTextView: TextView = itemView.findViewById(R.id.score_textView)
        private val nameTextView: TextView = itemView.findViewById(R.id.name_textView)
        private val container: ConstraintLayout = itemView.findViewById(R.id.recycler_constraint_view)

        private val xDistanceTitleTextView: TextView = itemView.findViewById(R.id.x_distantce_title_textView)
        private val yDistanceTitleTextView: TextView = itemView.findViewById(R.id.y_distantce_title_textView)
        private val scoreTitleTextView: TextView = itemView.findViewById(R.id.score_title_textView)

        fun bind(point: Point){
            this.point = point
            xDistanceTitleTextView.text = resources.getString(R.string.x_position)
            yDistanceTitleTextView.text = resources.getString(R.string.y_position)
            nameTextView.text = point.name
            xDistantceTextView.text = point.x_position.toString()
            yDistantceTextView.text = point.y_position.toString()
            scoreTextView.text = point.score.toString()

            val cardColor = when (prefs.getString(CARD_COLOR, "0")?.toInt()){
                0 -> resources.getColor(R.color.Windose_blue)
                1 -> resources.getColor(R.color.Windose_pink)
                else -> resources.getColor(R.color.Windose_blue)
            }

            val showScore = (prefs.getBoolean(SHOW_POINT_SCORE, true))
            if (showScore){
                scoreTitleTextView.visibility = View.VISIBLE
                scoreTextView.visibility = View.VISIBLE
            }else{
                scoreTitleTextView.visibility = View.INVISIBLE
                scoreTextView.visibility = View.INVISIBLE
            }

            container.setBackgroundColor(cardColor)
        }
    }

    private inner class PointAdapter : RecyclerView.Adapter<PointViewHolder>(){
        var points:List<Point> = emptyList()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder {
            val view = layoutInflater.inflate(R.layout.recycleview_item,parent,false)
            return PointViewHolder(view)
        }

        override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
            return holder.bind(points[position])
        }

        override fun getItemCount() = points.size

        @SuppressLint("NotifyDataSetChange")
        fun updatePoints(newPoints: List<Point>){
            this.points = newPoints
            notifyDataSetChanged()
        }

        fun getPointAtPosition(position: Int): Point{
            return points[position]
        }
    }
}