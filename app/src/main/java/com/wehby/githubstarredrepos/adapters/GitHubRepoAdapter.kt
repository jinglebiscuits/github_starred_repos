package com.wehby.githubstarredrepos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wehby.githubstarredrepos.R
import com.wehby.githubstarredrepos.model.GitHubRepository

class GitHubRepoAdapter(private val gitHubRepos: List<GitHubRepository>) :
    RecyclerView.Adapter<GitHubRepoAdapter.GitHubRepoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubRepoItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repository_layout, parent, false)
        return GitHubRepoItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: GitHubRepoItemViewHolder, position: Int) {
        holder.userName.text = gitHubRepos[position].owner.login
        holder.repoName.text = gitHubRepos[position].name
        holder.repoDescription.text = gitHubRepos[position].description
        holder.starCount.text = gitHubRepos[position].stargazers_count.toString()
        Glide.with(holder.userAvatar).load(gitHubRepos[position].owner.avatar_url).into(holder.userAvatar)
    }

    override fun getItemCount(): Int {
        return gitHubRepos.size
    }

    class GitHubRepoItemViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val userAvatar: ImageView = itemView.findViewById(R.id.user_avatar)
        val repoName: TextView = itemView.findViewById(R.id.repo_name)
        val repoDescription: TextView = itemView.findViewById(R.id.repo_description)
        val starCount: TextView = itemView.findViewById(R.id.star_count)
    }
}