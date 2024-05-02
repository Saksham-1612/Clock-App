import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.clock.R

class TimerFragment : Fragment() {

    private var timeSelected: Int = 0
    private var timeCountDown: CountDownTimer? = null
    private var timeProgress = 0
    private var pauseOffset: Long = 0
    private var isStart = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_timer, container, false)

        val addBtn: ImageButton = view.findViewById(R.id.btnAdd)
        addBtn.setOnClickListener {
            setTimerFunction()
        }

        val startBtn = view.findViewById<Button>(R.id.btnPlayPause)
        startBtn.setOnClickListener {
            startTimerSetup()
        }

        val resetBtn = view.findViewById<ImageButton>(R.id.ib_reset)
        resetBtn.setOnClickListener {
            resetTime()
        }

        val addTimeTv = view.findViewById<TextView>(R.id.tv_addTime)
        addTimeTv.setOnClickListener {
            addExtraTime()
        }

        return view
    }

    private fun timePause()
    {
        if (timeCountDown!=null)
        {
            timeCountDown!!.cancel()
        }
    }

    private fun addExtraTime() {
        val progressBar: ProgressBar? = view?.findViewById(R.id.pbTimer)
        if (timeSelected != 0) {
            timeSelected += 15
            progressBar?.max = timeSelected
            timePause()
            startTimer(pauseOffset)
            Toast.makeText(requireContext(), "15 seconds added", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetTime() {
        if (timeCountDown != null) {
            timeCountDown?.cancel()
            timeProgress = 0
            timeSelected = 0
            pauseOffset = 0
            timeCountDown = null
            val startBtn: Button? = view?.findViewById(R.id.btnPlayPause)
            startBtn?.text = "Start"
            isStart = true
            val progressBar = view?.findViewById<ProgressBar>(R.id.pbTimer)
            progressBar?.progress = 0
            val timeLeftTv: TextView? = view?.findViewById(R.id.tvTimeLeft)
            timeLeftTv?.text = "0"
        }
    }


    private fun startTimerSetup() {
        val startBtn = view?.findViewById<Button>(R.id.btnPlayPause)
        if (timeSelected > timeProgress) {
            if (isStart) {
                startBtn?.text = "Pause"
                startTimer(pauseOffset)
                isStart = false
            } else {
                isStart = true
                startBtn?.text = "Resume"
                timePause()
            }
        } else {
            Toast.makeText(requireContext(), "Enter Time", Toast.LENGTH_SHORT).show()
        }
    }



    private fun startTimer(pauseOffsetL: Long) {
        val progressBar = view?.findViewById<ProgressBar>(R.id.pbTimer)
        progressBar?.progress = timeProgress
        timeCountDown = object : CountDownTimer(
            (timeSelected * 1000).toLong() - pauseOffsetL * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeProgress++
                pauseOffset = timeSelected.toLong() - millisUntilFinished / 1000
                progressBar?.progress = timeSelected - timeProgress
                val timeLeftTv = view?.findViewById<TextView>(R.id.tvTimeLeft)
                timeLeftTv?.text = (timeSelected - timeProgress).toString()
            }

            override fun onFinish() {
                resetTime()
                Toast.makeText(requireContext(), "Times Up!", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }




    private fun setTimerFunction() {
        val timeDialog = Dialog(requireContext())
        timeDialog.setContentView(R.layout.add_dialog)
        val timeSet = timeDialog.findViewById<EditText>(R.id.etGetTime)
        val timeLeftTv = view?.findViewById<TextView>(R.id.tvTimeLeft)
        val btnStart = view?.findViewById<Button>(R.id.btnPlayPause)
        val progressBar = view?.findViewById<ProgressBar>(R.id.pbTimer)
        timeDialog.findViewById<Button>(R.id.btnOk).setOnClickListener {
            if (timeSet.text.isEmpty()) {
                Toast.makeText(requireContext(), "Enter Time Duration", Toast.LENGTH_SHORT).show()
            } else {
                resetTime()
                timeLeftTv?.text = timeSet.text
                btnStart?.text = "Start"
                timeSelected = timeSet.text.toString().toInt()
                progressBar?.max = timeSelected
            }
            timeDialog.dismiss()
        }
        timeDialog.show()
    }


    companion object {
        private const val TAG = "TimerFragment"
    }
}
