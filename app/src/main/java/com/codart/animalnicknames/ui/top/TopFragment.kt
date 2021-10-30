package com.codart.animalnicknames.ui.top

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codart.animalnicknames.R
import com.codart.animalnicknames.ui.MainViewModel
import com.codart.animalnicknames.ui.NicknamesAdapter
import com.codart.animalnicknames.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = requireView().findViewById(R.id.top_recycler)
        setAdapter()
        getNicknames()
        requireView().findViewById<ImageView>(R.id.top_back).setOnClickListener {
            findNavController().navigateUp()
        }
    }
    private fun setAdapter(){
        val adapter: NicknamesAdapter = NicknamesAdapter(requireContext(),viewModel, viewLifecycleOwner)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter =adapter
        recyclerView.layoutManager = linearLayoutManager
    }
    private fun getNicknames(){
        viewModel.getTopNicknamesWithGenderFiltration(viewModel.getViewModelUser().token, 0).observe(viewLifecycleOwner, {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    val adapter: NicknamesAdapter = NicknamesAdapter(requireContext(),viewModel, viewLifecycleOwner)
                    adapter.setItems(it.data!!.data)
                    recyclerView.adapter= adapter
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    Log.d("Get random", "Error, ${it.message}")
                    try {
                        Log.d("Get nicknames", "Success: ${it.data!!.success}")
                        Log.d("Get nicknames", "Error: ${it.data.error}")
                    }
                    catch (e:Exception)
                    {
                        Log.d("Get nicknames", e.toString())
                    }
                }

                Resource.Status.LOADING -> {
                }
            }
        })
    }

}