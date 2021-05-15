package com.wehby.githubstarredrepos.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.wehby.githubstarredrepos.BuildConfig
import com.wehby.githubstarredrepos.model.Contributor
import com.wehby.githubstarredrepos.model.GitHubRepository
import org.json.JSONObject
import java.net.URL

private const val LOG_TAG = "RepoViewModel"

class RepoViewModel(application: Application) : AndroidViewModel(application) {
    private val reposLiveData: MutableLiveData<List<GitHubRepository>> by lazy {
        MutableLiveData<List<GitHubRepository>>().also {
            loadRepos()
        }
    }

    fun getRepos(): LiveData<List<GitHubRepository>> {
        return reposLiveData
    }

    private fun loadRepos() {
        Log.d(LOG_TAG, "loading repos")
        val queue = Volley.newRequestQueue(getApplication())
        //need to find stargazers_count
        val pageSize = if (BuildConfig.GITHUB_TOKEN == "") 5 else 100
        val url = "https://api.github.com/search/repositories?q=stars%3A%3E0&per_page=$pageSize"
        val stringRequest = GitHubObjectRequest(
            url, { response ->
                val gson = Gson()

                val jsonArray = response.getJSONArray("items")
                val repoList = ArrayList<GitHubRepository>()
                for (i in 0 until jsonArray.length()) {
                    val item: JSONObject = response.getJSONArray("items").get(i) as JSONObject
                    var repo = gson.fromJson(item.toString(), GitHubRepository::class.java)
                    repoList.add(gson.fromJson(item.toString(), GitHubRepository::class.java))
                    val url = URL("https", "api.github.com", "/repos/${repo.owner.login}/${repo.name}/contributors?per_page=3")
                    val contributorRequest = GitHubArrayRequest(url.toString(), { response ->
                        val responseString = response.toString()
                        val contributorList = mutableListOf<Contributor>()
                        for (x in 0 until response.length()) {
                            val contributorItem: JSONObject = response[x] as JSONObject
                            contributorList.add(gson.fromJson(contributorItem.toString(), Contributor::class.java))
                        }
                        repo.contributors = contributorList
                        repoList[i] = repo
                        reposLiveData.value = repoList
                        Log.d(LOG_TAG, responseString)
                    }, {
                        it.printStackTrace()
                        Log.e(LOG_TAG, "error getting contributors ${it.localizedMessage}")
                    })
                    queue.add(contributorRequest)
                }
                Log.d(LOG_TAG, "got the data ${repoList.size}")
            },
            { Log.e(LOG_TAG, "error") })
        queue.add(stringRequest)
    }
}