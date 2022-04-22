package com.yhe64.treasurehunt.ui.info

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yhe64.treasurehunt.R
import android.util.Log
import com.yhe64.treasurehunt.BuildConfig
import com.yhe64.treasurehunt.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buildTimeTextView.text = resources.getString(R.string.builtTime)
            infoTitleTextView.text = resources.getString(R.string.app_name)
            versionTextView.text = BuildConfig.VERSION_NAME
            copyrightTextView.text = resources.getString(R.string.copyright)
            if (BuildConfig.DEBUG) {
                debugBuildTextView.visibility = View.VISIBLE
            }
        }
    }

}