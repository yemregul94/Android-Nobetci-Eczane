package com.moonlight.nobetcieczaneler.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.moonlight.nobetcieczaneler.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var cld : ConnectionLiveData
    private val viewModel: MainViewModel by viewModels()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isConnected.observe(this) {
            if(it){
                binding.layoutNoConnection.visibility = View.GONE
                binding.layoutNoConnection.isClickable = false

            }else{
                binding.layoutNoConnection.visibility = View.VISIBLE
                binding.layoutNoConnection.isClickable = true
            }
        }

        checkNetworkConnection()
    }

    private fun checkNetworkConnection() {
        cld = ConnectionLiveData(application)

        cld.observe(this) { isConnected ->
            if (isConnected) {
                viewModel.changeConnection(isConnected)
            } else {
                viewModel.changeConnection(isConnected)
            }
        }
    }
}


