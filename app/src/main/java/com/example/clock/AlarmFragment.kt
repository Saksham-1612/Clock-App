import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.alarm.AlarmManagerBroadcast
import com.example.clock.R
import java.util.*

class AlarmFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_alarm, container, false)
        val start = view.findViewById<Button>(R.id.start)
        val end = view.findViewById<Button>(R.id.end)
        val timePicker = view.findViewById<TimePicker>(R.id.timePicker)

        var alarmManager: AlarmManager

        val intent = Intent(requireContext(), AlarmManagerBroadcast::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            224,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // Add FLAG_IMMUTABLE here
        )

        start.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            calendar.set(Calendar.MINUTE, timePicker.minute)
            calendar.set(Calendar.SECOND, 0)
            val selectedTimeInMillis = calendar.timeInMillis

            alarmManager =
                requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                selectedTimeInMillis,
                pendingIntent
            )
            Toast.makeText(
                requireContext(),
                "Alarm set for ${timePicker.hour}:${timePicker.minute}",
                Toast.LENGTH_SHORT
            ).show()
        }

        end.setOnClickListener {
            alarmManager =
                requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            Toast.makeText(requireContext(), "Alarm is cancelled", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
