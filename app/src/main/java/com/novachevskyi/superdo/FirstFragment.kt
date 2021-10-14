package com.novachevskyi.superdo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.novachevskyi.superdo.databinding.FragmentFirstBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstFragment : Fragment(), RecyclerViewAdapter.ItemClickListener {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initConnection()
    }

    private fun initConnection() {
        val service = WebSocketService()
        var isConnectState = true
        binding.connect.setOnClickListener {
            if (isConnectState) {
                binding.connect.setText(R.string.connecting)
                binding.connect.isEnabled = false

                service.listenSocket({
                    GlobalScope.launch {
                        withContext(Dispatchers.Main) {
                            binding.connect.isEnabled = true
                            binding.connect.setText(
                                if (it) {
                                    isConnectState = false
                                    R.string.disconnect
                                } else {
                                    isConnectState = true
                                    R.string.connect
                                }
                            )
                        }
                    }
                }) {
                    adapter.addItem(it)
                    binding.recyclerView.smoothScrollToPosition(0)
                }
            } else {
                service.disconnect()
                isConnectState = true
                binding.connect.setText(R.string.connect)
            }
        }
    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = manager
        adapter = RecyclerViewAdapter(requireContext(), emptyList())
        adapter.setClickListener(this)
        binding.recyclerView.adapter = adapter
    }

    override fun onItemClick(view: ImageView, color: Int) {
        val detailIntent = Intent(requireActivity(), DetailActivity::class.java)
        val imageViewPair =
            androidx.core.util.Pair<View, String>(view, "CircleTransition")
        val options =
            ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), imageViewPair)
        detailIntent.putExtra("color", color)
        startActivity(detailIntent, options.toBundle())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
