package com.yhe64.treasurehunt.ui.game

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yhe64.treasurehunt.PointViewModel
import com.yhe64.treasurehunt.R
import com.yhe64.treasurehunt.TreasurehuntApp.Companion.CARD_COLOR
import com.yhe64.treasurehunt.TreasurehuntApp.Companion.SHOW_POINT_SCORE
import com.yhe64.treasurehunt.database.Point
import com.yhe64.treasurehunt.databinding.FragmentGameBinding

class GameFragment: Fragment(){

    private val sharedViewModel: PointViewModel by activityViewModels()
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private var xpos: Int = 5
    private var ypos: Int = 5
    private var score: Int =0
    private var step:  Int =0
    private var pointnum : Int = 0

    private val prefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    private val pointAdapter = PointAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container,false)
        xpos = (0..10).random()
        ypos = (0..10).random()
        binding.apply {
            pointsRecyclerview2.run{
                layoutManager = LinearLayoutManager(context)
                adapter = pointAdapter
            }
            backToMainBtn.setOnClickListener{
                findNavController().navigate(GameFragmentDirections.actionGameFragmentToMainFragment())
            }
            startBtn.setOnClickListener{
                //scoreValueTextView.text = pointAdapter.itemCount.toString()
                for (point in pointAdapter.points){
                    if (point.x_position == xpos &&
                        point.y_position == ypos){
                        score += point.score
                        scoreValueTextView.text = score.toString()
                        sharedViewModel.delete(point)
                        pointnum--
                    }else{
                        point.x_distance = point.x_position - xpos
                        point.y_distance = point.y_position - ypos
                        sharedViewModel.update(point)
                    }
                }
            }
            forwardBtn.setOnClickListener{
                scoreValueTextView.text = ypos.toString()
                ypos = (ypos + 11) % 10
                step++
                stepValueTextView.text = step.toString()
                for (point in pointAdapter.points){
                    if (point.x_position == xpos &&
                        point.y_position == ypos){
                        score += point.score
                        scoreValueTextView.text = score.toString()
                        sharedViewModel.delete(point)
                        pointnum--
                    }else{
                        point.x_distance = point.x_position - xpos
                        point.y_distance = point.y_position - ypos
                        sharedViewModel.update(point)
                    }
                }
            }
            backBtn.setOnClickListener{
                ypos = (ypos + 9) % 10
                step++
                stepValueTextView.text = step.toString()
                for (point in pointAdapter.points){
                    if (point.x_position == xpos &&
                        point.y_position == ypos){
                        score += point.score
                        scoreValueTextView.text = score.toString()
                        sharedViewModel.delete(point)
                        pointnum--
                    }else{
                        point.x_distance = point.x_position - xpos
                        point.y_distance = point.y_position - ypos
                        sharedViewModel.update(point)
                    }
                }
            }
            leftBtn.setOnClickListener{
                xpos = (xpos + 9) % 10
                step++
                stepValueTextView.text = step.toString()
                for (point in pointAdapter.points){
                    if (point.x_position == xpos &&
                        point.y_position == ypos){
                        score += point.score
                        scoreValueTextView.text = score.toString()
                        sharedViewModel.delete(point)
                        pointnum--
                    }else{
                        point.x_distance = point.x_position - xpos
                        point.y_distance = point.y_position - ypos
                        sharedViewModel.update(point)
                    }
                }
            }
            rightBtn.setOnClickListener{
                xpos = (xpos + 11) % 10
                step++
                stepValueTextView.text = step.toString()
                for (point in pointAdapter.points){
                    if (point.x_position == xpos &&
                        point.y_position == ypos){
                        score += point.score
                        scoreValueTextView.text = score.toString()
                        sharedViewModel.delete(point)
                        pointnum--
                    }else{
                        point.x_distance = point.x_position - xpos
                        point.y_distance = point.y_position - ypos
                        sharedViewModel.update(point)
                    }
                }
            }
        }
        return binding.root
    }


    override fun onViewCreated(view:View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.points.observe(viewLifecycleOwner){
            pointAdapter.updatePoints(it)
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
            xDistanceTitleTextView.text = resources.getString(R.string.x_distance)
            yDistanceTitleTextView.text = resources.getString(R.string.y_distance)
            nameTextView.text = point.name
            xDistantceTextView.text = point.x_distance.toString()
            yDistantceTextView.text = point.y_distance.toString()
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