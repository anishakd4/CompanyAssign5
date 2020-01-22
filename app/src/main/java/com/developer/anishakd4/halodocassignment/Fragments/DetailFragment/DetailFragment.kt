package com.developer.anishakd4.halodocassignment.Fragments.DetailFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.developer.anishakd4.halodocassignment.R
import com.developer.anishakd4.halodocassignment.databinding.DetailFragmentBinding
import com.developer.anishakd4.halodocassignment.databinding.ListFragmentBinding

class DetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: DetailFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)


        binding.webview.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }

        val args = getArguments()
        if(args != null){
            val url = args.getString("url")
            binding.webview.loadUrl(url)
        }

        return binding.root
    }
}