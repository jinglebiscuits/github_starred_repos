package com.wehby.githubstarredrepos.model

data class GitHubRepository(
    val id: Int,
    val name: String,
    val owner: Owner,
    val description: String,
    val stargazers_count: Int
)