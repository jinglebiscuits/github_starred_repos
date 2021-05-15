package com.wehby.githubstarredrepos.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wehby.githubstarredrepos.R
import com.wehby.githubstarredrepos.adapters.GitHubRepoAdapter
import com.wehby.githubstarredrepos.listeners.OnUriContainerClickedListener
import com.wehby.githubstarredrepos.model.GitHubRepository
import com.wehby.githubstarredrepos.viewmodels.RepoViewModel

private const val LOG_TAG = "MainFragment"

class MainFragment : Fragment(), OnUriContainerClickedListener {

    private val viewModel: RepoViewModel by viewModels()
    private lateinit var repoRecyclerView: RecyclerView
    private lateinit var repoListAdapter: GitHubRepoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        repoRecyclerView = view.findViewById(R.id.repo_list)
        repoRecyclerView.setHasFixedSize(true)
        repoRecyclerView.layoutManager = LinearLayoutManager(activity)
        repoListAdapter = GitHubRepoAdapter(this)
        repoRecyclerView.adapter = repoListAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getRepos().observe(viewLifecycleOwner) {
            repoListAdapter.updateList(it as ArrayList<GitHubRepository>)
        }
    }

    override fun onUriContainerClicked(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        startActivity(intent)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}