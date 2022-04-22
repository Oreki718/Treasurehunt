package com.yhe64.treasurehunt.ui.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yhe64.treasurehunt.PointAdapter
import com.yhe64.treasurehunt.R
import com.yhe64.treasurehunt.PointViewModel
import com.yhe64.treasurehunt.databinding.MainFragmentBinding
import com.yhe64.treasurehunt.database.Point

class MainFragment : Fragment() {

    private val sharedViewModel: PointViewModel by activityViewModels()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val pointAdapter = PointAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container,false)
        binding.apply {
            addPointFab.setOnClickListener{
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToDataEntryFragment())
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
            pointAdapter.updateItems(it)
        }

        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ) = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val thisFriend = pointAdapter.getItemAtPosition(viewHolder.adapterPosition)
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
}