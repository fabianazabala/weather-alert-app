package com.climate_dissertation_app.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import com.climate_dissertation_app.R
import com.climate_dissertation_app.service.Settings
import com.climate_dissertation_app.service.SettingsService
import com.climate_dissertation_app.ui.notification.RegularNotificationService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings.*
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var settingsService: SettingsService

    private val chronoUnitPositions = listOf(
        ChronoUnit.SECONDS,
        ChronoUnit.MINUTES,
        ChronoUnit.HOURS,
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsService.fetchSettings(view.context)?.also {
            setInitialValues(it)
        }

        prepareChronoUnitSpinner(view)

        prepareSeekbar()

        prepareSaveButton(view)
    }

    private fun prepareSaveButton(view: View) {
        save_button.setOnClickListener {
            val newSettings = Settings(
                notification_seekbar.progress,
                chronoUnitPositions[timeframe_spinner.selectedItemPosition]
            )
            settingsService.storeSettings(
                view.context, newSettings
            )
            RegularNotificationService.rescheduleJob(view.context, newSettings)
            Toast.makeText(view.context, "New settings have been saved!", LENGTH_SHORT).show()
        }
    }

    private fun prepareSeekbar() {
        notification_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                updateCurrentSelectionText()
            }
        })
    }

    private fun prepareChronoUnitSpinner(view: View) {
        ArrayAdapter.createFromResource(
            view.context,
            R.array.timeframe_values,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            timeframe_spinner.adapter = adapter
        }
        timeframe_spinner.onItemSelectedListener =
            SpinnerItemSelectedListener { updateCurrentSelectionText() }
        updateCurrentSelectionText()

    }

    private fun updateCurrentSelectionText() {
        current_time_selection.text =
            "Notifications will be send every ${notification_seekbar.progress} ${timeframe_spinner.selectedItem}"
    }

    private fun setInitialValues(settings: Settings) {
        notification_seekbar.progress = settings.amount
        timeframe_spinner.post { timeframe_spinner.setSelection(chronoUnitPositions.indexOf(settings.unit)) }
        updateCurrentSelectionText()
    }
}

class SpinnerItemSelectedListener(private val runnable: Runnable) :
    AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        runnable.run()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

}