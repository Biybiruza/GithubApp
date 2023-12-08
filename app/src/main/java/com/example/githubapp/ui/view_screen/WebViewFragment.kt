package com.example.githubapp.ui.view_screen

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.githubapp.MainActivity
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentWebViewBinding

class WebViewFragment : Fragment(R.layout.fragment_web_view){

    private lateinit var binding: FragmentWebViewBinding
    private var url = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWebViewBinding.bind(view)
        url = arguments?.getString("url") ?: ""

        binding.apply {
            webView.webViewClient = WebViewClient()
            webView.loadUrl(url)
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.apply {
                    if(webView.canGoBack()){
                        webView.goBack()
                    } else {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(),callback)
    }
}