package com.wehby.githubstarredrepos.viewmodels

import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.wehby.githubstarredrepos.BuildConfig
import org.json.JSONArray
import org.json.JSONObject

class GitHubArrayRequest(url: String, listener: Response.Listener<JSONArray>, errorListener: Response.ErrorListener) :
    JsonArrayRequest(Method.GET, url, null, listener, errorListener) {

    override fun getHeaders(): MutableMap<String, String> {
        return if (BuildConfig.GITHUB_TOKEN == "") {
            super.getHeaders()
        } else {
            val headers = HashMap<String, String>()
            headers["Authorization"] = BuildConfig.GITHUB_TOKEN
            headers
        }
    }
}