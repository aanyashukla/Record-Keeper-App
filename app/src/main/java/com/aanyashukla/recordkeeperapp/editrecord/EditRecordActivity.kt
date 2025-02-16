package com.aanyashukla.recordkeeperapp.editrecord

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.aanyashukla.recordkeeperapp.databinding.ActivityEditRecordBinding
import java.io.Serializable

const val INTENT_EXTRA_SCREEN_DATA = "screen_data"

class EditRecordActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditRecordBinding

    private val screenData: ScreenData by lazy {
        intent.getSerializableExtra(INTENT_EXTRA_SCREEN_DATA) as ScreenData
    }

    private val recordPreferences by lazy { getSharedPreferences(screenData.sharedPreferenceName, Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUi()
        displayRecord()
    }

    private fun setUpUi() {
        title = "${screenData.record} Record"
        binding.textInputRecord.hint = screenData.recordFieldHint
        binding.buttonSave.setOnClickListener {
            saveRecord()
            finish()
        }
        binding.buttonDelete.setOnClickListener {
            clearRecord()
            finish()
        }
    }

    private fun displayRecord() {
        binding.editTextRecord.setText(recordPreferences.getString("${screenData.record} record", null))
        binding.editTextDate.setText(recordPreferences.getString("${screenData.record} date", null))
    }

    private fun saveRecord() {
        val record = binding.editTextRecord.text.toString()
        val date = binding.editTextDate.text.toString()

        recordPreferences.edit {
            putString("${this@EditRecordActivity.screenData.record} record", record)
            putString("${this@EditRecordActivity.screenData.record} date", date)
        }
    }

    private fun clearRecord() {
        recordPreferences.edit {
            remove("${screenData.record} record")
            remove("${screenData.record} date")
        }
    }

    data class ScreenData(
        val record: String,
        val sharedPreferenceName: String,
        val recordFieldHint: String
    ) : Serializable
}