package com.yhe64.treasurehunt.ui.main

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yhe64.treasurehunt.PointViewModel
import com.yhe64.treasurehunt.R
import com.yhe64.treasurehunt.databinding.DialogDataEntryBinding
import com.yhe64.treasurehunt.database.Point

class DataEntryDialog : BottomSheetDialogFragment(), AdapterView.OnItemSelectedListener {

    private val sharedViewModel: PointViewModel by activityViewModels()
    private var _binding: DialogDataEntryBinding? = null
    private val binding get() = _binding!!
    private var point = Point()

    override fun onStart() {
        super.onStart()
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogDataEntryBinding.inflate(inflater, container, false)
        binding.apply {
            buttonAdd.setOnClickListener{
                with(point){
                    name = nameEditText.text.toString()
                    x_position = Integer.parseInt( xPositionEditText.text.toString())
                    y_position = Integer.parseInt( yPositionEditText.text.toString())
                    score = Integer.parseInt( scoreEditText.text.toString())
                }
                sharedViewModel.insert(point)
                itemAddedAlert(point = point)
                context?.hideKeyboard(it)
                dismiss()
            }

            buttonCancel.setOnClickListener{
                context?.hideKeyboard(it)
                dismiss()
            }
            return binding.root

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    private fun itemAddedAlert(point: Point){
        val msg = getString(R.string.addmsg, point.name)
        val builder = AlertDialog.Builder(context)
        with(builder){
            setTitle("Alert")
            setMessage(msg)
            setPositiveButton(R.string.ok, null)
            show()
        }
    }
}

fun Context.hideKeyboard(view: View){
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
