package com.wehby.githubstarredrepos.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.wehby.githubstarredrepos.R
import com.wehby.githubstarredrepos.adapters.GitHubRepoAdapter
import com.wehby.githubstarredrepos.model.Contributor
import com.wehby.githubstarredrepos.model.GitHubRepository
import org.json.JSONObject
import java.net.URL

private const val LOG_TAG = "MainFragment"
private const val REPO_SEARCH_URL = "https://api.github.com/search/repositories?q=stars%3A%3E0&per_page=100"

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

            //need to find stargazers_count
            val stringRequest = JsonObjectRequest(Request.Method.GET, REPO_SEARCH_URL, null, { response ->
                val gson = Gson()

                val jsonArray = response.getJSONArray("items")
                val repoList = ArrayList<GitHubRepository>()
                repoListAdapter = GitHubRepoAdapter(repoList)
                for (i in 0 until jsonArray.length()) {
                    val item: JSONObject = response.getJSONArray("items").get(i) as JSONObject
                    var repo = gson.fromJson(item.toString(), GitHubRepository::class.java)
                    repoList.add(gson.fromJson(item.toString(), GitHubRepository::class.java))
                    val url = URL("https", "api.github.com", "/repos/${repo.owner.login}/${repo.name}/contributors?per_page=3")
                    val contributorRequest = JsonArrayRequest(Request.Method.GET, url.toString(), null, { response ->
                        val responseString = response.toString()
                        val contributorList = mutableListOf<Contributor>()
                        for (x in 0 until response.length()) {
                            val contributorItem: JSONObject = response[x] as JSONObject
                            contributorList.add(gson.fromJson(contributorItem.toString(), Contributor::class.java))
                        }
                        repo.contributors = contributorList
                        repoListAdapter.updateItem(repo, i)
                        repoListAdapter.notifyDataSetChanged()
                        Log.d(LOG_TAG, responseString)
                    }, {
                        it.printStackTrace()
                        Log.e(LOG_TAG, "error getting contributors ${it.localizedMessage}")
                    })
                    queue.add(contributorRequest)
                }

                repoRecyclerView.setHasFixedSize(true)
                repoRecyclerView.layoutManager = LinearLayoutManager(activity)
                repoRecyclerView.adapter = repoListAdapter
                Log.d(LOG_TAG, "got the data ${repoList.size}")
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