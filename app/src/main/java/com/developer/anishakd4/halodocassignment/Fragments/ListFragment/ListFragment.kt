package com.developer.anishakd4.halodocassignment.Fragments.ListFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.anishakd4.halodocassignment.Adapter.RecyclerViewAdapter
import com.developer.anishakd4.halodocassignment.Database.NewsDatabase
import com.developer.anishakd4.halodocassignment.Fragments.DetailFragment.DetailFragment
import com.developer.anishakd4.halodocassignment.Model.HitsModel
import com.developer.anishakd4.halodocassignment.Model.NewsModel
import com.developer.anishakd4.halodocassignment.R
import com.developer.anishakd4.halodocassignment.databinding.ListFragmentBinding
import java.text.FieldPosition

class ListFragment : Fragment(), RecyclerViewAdapter.ClickInterFace {

    lateinit var hitsModel: List<HitsModel>

    override fun onClickedInAdapter(position: Int) {
        Log.i("anisham", "onClick")
        val detailFragment = DetailFragment()
        val args = Bundle()
        args.putString("url", hitsModel.get(position).url)
        detailFragment.setArguments(args)
        val manager = fragmentManager
        val transaction = manager?.beginTransaction()
        transaction?.add(R.id.frame_container, detailFragment)
        transaction?.addToBackStack("detailFragment")
        transaction?.commit()
    }

    lateinit var viewModel: ListFragmentViewModel
    lateinit var binding: ListFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)

        val dataSource = NewsDatabase.getInstance(context!!.applicationContext).newsDao
        val viewModelFactory = AcitivityViewModelFactory(dataSource)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListFragmentViewModel::class.java)

        viewModel.fetchNews()

        observeViewModel()

        return binding.root
    }

    fun observeViewModel() {
        viewModel.news.observe(this, Observer {
            if (it != null) {
//                this.hitsModel = it.hits
//                binding.newsList.visibility = View.VISIBLE
//                val adapter = RecyclerViewAdapter(it.hits, this)
//                binding.newsList.layoutManager = LinearLayoutManager(context)
//                binding.newsList.adapter = adapter

                viewModel.insertIntoDb()
            }
        })

        viewModel.news2.observe(this, Observer {
            if (it != null) {
                this.hitsModel = it
                binding.newsList.visibility = View.VISIBLE
                val adapter = RecyclerViewAdapter(it, this)
                binding.newsList.layoutManager = LinearLayoutManager(context)
                binding.newsList.adapter = adapter

                viewModel.haveData()
            }
        })

        viewModel.loadError.observe(this, Observer { isError ->
            binding.listError.visibility = if (isError == "" || isError == null) View.GONE else View.VISIBLE
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.listError.visibility = View.GONE
                    binding.newsList.visibility = View.GONE
                }
            }
        })
    }
}