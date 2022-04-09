package com.android.example.ratemycourse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_rate.*

/**
 * A simple [Fragment] subclass.
 * Use the [RateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RateFragment : Fragment() {

    private val args: ClassDisplayFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rate_confirm.setOnClickListener {
            if (!rate_score.text.isEmpty()) {
                val score = rate_score.text.toString().toDouble()

                if (score < 0 || score > 5) {
                    Toast.makeText(this.context, "Invalid score", Toast.LENGTH_SHORT).show()
                } else {
                    User.curUser.postRate(args.classObject, score, rate_comment.text.toString()) { _ ->
                        val course = args.classObject
                        course.comments.add(rate_comment.text.toString())
                        val action = RateFragmentDirections.actionRateFragmentToClassDisplayFragment(course)
                        findNavController().navigate(action)
                    }
                }
            } else {
                Toast.makeText(this.context, "Please enter a score", Toast.LENGTH_SHORT).show()
            }
        }
    }
}