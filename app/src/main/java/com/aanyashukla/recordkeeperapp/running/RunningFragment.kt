package com.aanyashukla.recordkeeperapp.running

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aanyashukla.recordkeeperapp.RUNNING
import com.aanyashukla.recordkeeperapp.databinding.FragmentsRunningBinding
import com.aanyashukla.recordkeeperapp.editrecord.EditRecordActivity
import com.aanyashukla.recordkeeperapp.editrecord.INTENT_EXTRA_SCREEN_DATA

class RunningFragment : Fragment() {

    private lateinit var binding: FragmentsRunningBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentsRunningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    override fun onResume() {
        super.onResume()
        displayRecords()
        /*
         We moved this here from onViewCreated because when we click the up-arrow, it works fine, but when the back-arrow is
         clicked onAttach, onCreateView and onViewCreated doesn't happen,so our records doesn't get displayed in real time,
         so putting this method here is a better option.
         */
    }

    private fun setOnClickListeners() {
        binding.container5km.setOnClickListener { launchRunningRecordScreen("5km") }
        binding.container10km.setOnClickListener { launchRunningRecordScreen("10km") }
        binding.containerHalfMarathon.setOnClickListener { launchRunningRecordScreen("Half Marathon") }
        binding.containerMarathon.setOnClickListener { launchRunningRecordScreen("Marathon") }
    }

    private fun displayRecords() {
        val runningPreferences = requireContext().getSharedPreferences(RUNNING, Context.MODE_PRIVATE)

        binding.textView5kmValue.text = runningPreferences.getString("5km record", null)
        binding.textView5kmDate.text = runningPreferences.getString("5km date", null)
        binding.textView10kmValue.text = runningPreferences.getString("10km record", null)
        binding.textView10kmDate.text = runningPreferences.getString("10km date", null)
        binding.textViewHalfMarathonValue.text = runningPreferences.getString("Half Marathon record", null)
        binding.textViewHalfMarathonDate.text = runningPreferences.getString("Half Marathon date", null)
        binding.textViewMarathonValue.text = runningPreferences.getString("Marathon record", null)
        binding.textViewMarathonDate.text = runningPreferences.getString("Marathon date", null)
    }

    private fun launchRunningRecordScreen(distance: String) {
        val intent = Intent(context, EditRecordActivity::class.java)
        intent.putExtra(INTENT_EXTRA_SCREEN_DATA, EditRecordActivity.ScreenData(distance, RUNNING, "Time"))
        startActivity(intent)
    }
}