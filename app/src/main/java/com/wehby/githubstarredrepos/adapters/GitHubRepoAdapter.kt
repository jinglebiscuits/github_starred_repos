package com.wehby.githubstarredrepos.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wehby.githubstarredrepos.R
import com.wehby.githubstarredrepos.listeners.OnUriContainerClickedListener
import com.wehby.githubstarredrepos.model.Contributor
import com.wehby.githubstarredrepos.model.GitHubRepository

private const val NAME_SEPARATOR = " / "

class GitHubRepoAdapter(
    private val gitHubRepos: ArrayList<GitHubRepository>,
    private val onUrlContainerClickedListener: OnUriContainerClickedListener
) :
    RecyclerView.Adapter<GitHubRepoAdapter.GitHubRepoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubRepoItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repository_layout, parent, false)
        return GitHubRepoItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: GitHubRepoItemViewHolder, position: Int) {
        holder.userAndRepoName.text = gitHubRepos[position].owner.login + NAME_SEPARATOR + gitHubRepos[position].name
        holder.repoDescription.text = gitHubRepos[position].description
        holder.starCount.text = gitHubRepos[position].stargazers_count.toString()
        Glide.with(holder.userAvatar).load(gitHubRepos[position].owner.avatar_url).into(holder.userAvatar)
        if (gitHubRepos[position].contributors != null) {
            setupContributorView(holder.contributor1, gitHubRepos[position].contributors[0])
            setupContributorView(holder.contributor2, gitHubRepos[position].contributors[1])
            setupContributorView(holder.contributor3, gitHubRepos[position].contributors[2])
        }
    }

    private fun setupContributorView(contributorView: ImageView, contributor: Contributor) {
        Glide.with(contributorView).load(contributor.avatar_url).into(contributorView)
        contributorView.setOnClickListener {
            onUrlContainerClickedListener.onUriContainerClicked(Uri.parse(contributor.html_url))
        }
    }

    override fun getItemCount(): Int {
        return gitHubRepos.size
    }

    fun updateItem(updatedRepo: GitHubRepository, position: Int) {
        gitHubRepos[position] = updatedRepo
        notifyItemChanged(position)
    }

    class GitHubRepoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userAndRepoName: TextView = itemView.findViewById(R.id.user_and_repo_name)
        val userAvatar: ImageView = itemView.findViewById(R.id.repo_owner_avatar)
        val contributor1: ImageView = itemView.findViewById(R.id.contributor1)
        val contributor2: ImageView = itemView.findViewById(R.id.contributor2)
        val contributor3: ImageView = itemView.findViewById(R.id.contributor3)
        val repoDescription: TextView = itemView.findViewById(R.id.repo_description)
        val starCount: TextView = itemView.findViewById(R.id.star_count)
    }
}