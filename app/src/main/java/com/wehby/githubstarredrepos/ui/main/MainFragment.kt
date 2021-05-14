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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.wehby.githubstarredrepos.R
import com.wehby.githubstarredrepos.adapters.GitHubRepoAdapter
import com.wehby.githubstarredrepos.model.GitHubRepository
import com.wehby.githubstarredrepos.model.Owner
import org.json.JSONObject

private const val LOG_TAG = "MainFragment"

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var makeRequestButton: Button
    private lateinit var repoRecyclerView: RecyclerView
    private lateinit var repoListAdapter: GitHubRepoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        repoRecyclerView = view.findViewById(R.id.repo_list)
        makeRequestButton = view.findViewById(R.id.make_request_button)
        makeRequestButton.setOnClickListener {
            Log.d(LOG_TAG, "clicking")
            val queue = Volley.newRequestQueue(requireContext())
            val url = "https://api.github.com/search/repositories?q=stars"

            //need to find stargazers_count
            val stringRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
                var gson = Gson()

                val jsonArray = response.getJSONArray("items")
                var repoList = mutableListOf<GitHubRepository>()
                for (i in 0 until jsonArray.length()) {
                    var item: JSONObject = response.getJSONArray("items").get(i) as JSONObject
                    repoList.add(gson.fromJson(item.toString(), GitHubRepository::class.java))
                }

                repoListAdapter = GitHubRepoAdapter(repoList)
                repoRecyclerView.setHasFixedSize(true)
                repoRecyclerView.layoutManager = LinearLayoutManager(activity)
                repoRecyclerView.adapter = repoListAdapter
                Log.d(LOG_TAG, "got the data")
            },
                { Log.e(LOG_TAG, "error") })
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