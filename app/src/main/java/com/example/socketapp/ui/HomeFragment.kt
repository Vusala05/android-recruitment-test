package com.example.socketapp.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socketapp.R
import com.example.socketapp.databinding.FragmentMainBinding
import com.example.socketapp.domain.util.SocketConnectionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MarketViewModel by viewModels()
    private val adapter = MarketAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMarketList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HomeFragment.adapter
        }

        observeUiState()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    updateConnectionIndicator(state.connectionState)
                    adapter.submitList(state.marketList)
                }
            }
        }
    }

    private fun updateConnectionIndicator(state: SocketConnectionState) {
        val (colorRes, textRes) = when (state) {
            is SocketConnectionState.Connected -> R.color.status_connected to R.string.status_connected
            is SocketConnectionState.Connecting -> R.color.status_connecting to R.string.status_connecting
            is SocketConnectionState.Disconnected, SocketConnectionState.Idle ->
                R.color.status_disconnected to R.string.status_disconnected
            is SocketConnectionState.Error -> R.color.status_error to R.string.status_error
        }
        binding.viewConnectionDot.setBackgroundColor(
            ContextCompat.getColor(requireContext(), colorRes)
        )
        binding.tvConnectionStatus.text = getString(textRes)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}