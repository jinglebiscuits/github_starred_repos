package com.wehby.githubstarredrepos.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.wehby.githubstarredrepos.R

private const val LOG_TAG = "MainFragment"

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var textView: TextView
    private lateinit var makeRequestButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        textView = view.findViewById(R.id.message)
        makeRequestButton = view.findViewById(R.id.make_request_button)
        makeRequestButton.setOnClickListener {
            Log.d(LOG_TAG, "clicking")
            val queue = Volley.newRequestQueue(requireContext())
            val url = "https://api.github.com/search/repositories?q=stars&order=desc"

            //need to find stargazers_count
            val stringRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
                textView.text = "Response is: %s".format(response.toString())
            },
                { textView.text = "That didn't work!" })
            queue.add(stringRequest)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}