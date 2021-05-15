package com.wehby.githubstarredrepos.viewmodels

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.wehby.githubstarredrepos.BuildConfig
import org.json.JSONObject

class GitHubObjectRequest(url: String, listener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener) :
    JsonObjectRequest(Method.GET, url, null, listener, errorListener) {

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