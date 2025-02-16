package com.aanyashukla.recordkeeperapp.cycling

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aanyashukla.recordkeeperapp.CYCLING
import com.aanyashukla.recordkeeperapp.databinding.FragmentsCyclingBinding
import com.aanyashukla.recordkeeperapp.editrecord.EditRecordActivity
import com.aanyashukla.recordkeeperapp.editrecord.INTENT_EXTRA_SCREEN_DATA

class CyclingFragment : Fragment() {

    private lateinit var binding: FragmentsCyclingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentsCyclingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    override fun onResume() {
        super.onResume()
        displayRecords()
    }

    private fun displayRecords() {
        val cyclingPreferences = requireContext().getSharedPreferences(CYCLING, Context.MODE_PRIVATE)

        binding.textViewLongestRideValue.text = cyclingPreferences.getString("Longest Ride record", null)
        binding.textViewLongestRideDate.text = cyclingPreferences.getString("Longest Ride date", null)
        binding.textViewBiggestClimbValue.text = cyclingPreferences.getString("Biggest Climb record", null)
        binding.textViewBiggestClimbDate.text = cyclingPreferences.getString("Biggest Climb date", null)
        binding.textViewBestSpeedValue.text = cyclingPreferences.getString("Best Average Speed record", null)
        binding.textViewBestSpeedDate.text = cyclingPreferences.getString("Best Average Speed date", null)
    }

    private fun setOnClickListeners(){
        binding.containerLongestRide.setOnClickListener { launchCyclingRecordScreen("Longest Ride", "Distance") }
        binding.containerBiggestClimb.setOnClickListener { launchCyclingRecordScreen("Biggest Climb", "Height") }
        binding.containerBestSpeed.setOnClickListener { launchCyclingRecordScreen("Best Average Speed", "Average Speed") }
    }

    private fun launchCyclingRecordScreen(record: String, recordFieldHint: String){
        val intent = Intent(context, EditRecordActivity::class.java)
        intent.putExtra(INTENT_EXTRA_SCREEN_DATA, EditRecordActivity.ScreenData(record, CYCLING, recordFieldHint))
        startActivity(intent)
    }
}