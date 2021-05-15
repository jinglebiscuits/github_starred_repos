package com.wehby.githubstarredrepos.model

class GitHubRepository(
    val id: Int,
    val name: String,
    val owner: Owner,
    val description: String,
    val stargazers_count: Int,
    val html_url: String,
    var contributors: MutableList<Contributor> = mutableListOf()
)