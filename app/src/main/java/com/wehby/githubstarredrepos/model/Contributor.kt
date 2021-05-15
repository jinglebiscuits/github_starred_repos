package com.wehby.githubstarredrepos.model

data class Contributor(
    val login: String,
    val contributions: Int,
    val avatar_url: String,
    val html_url: String
)
