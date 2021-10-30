package com.codart.animalnicknames.ui.splash

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.codart.animalnicknames.R
import com.codart.animalnicknames.data.entities.User
import com.codart.animalnicknames.ui.MainViewModel
import com.codart.animalnicknames.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       getUser()
       /* Handler().postDelayed({

        },3000)*/
    }
    private fun getUser(){
       runBlocking {
            viewModel.getUser().observe(viewLifecycleOwner, { user ->
                if (user?.isLogged.isNullOrEmpty()) {
                    viewModel.getToken().observe(viewLifecycleOwner, {
                        when (it.status) {
                            Resource.Status.SUCCESS -> {
                                try {
                                    launch {
                                        viewModel.addUser(
                                            User(
                                                user.id,
                                                "Bearer ${it.data?.data?.access_token}",
                                                user.isSubscribed,
                                                "true"
                                            )
                                        )
                                    }
                                    viewModel.setViewModelUser(
                                        User(
                                            user.id,
                                            "Bearer ${it.data?.data?.access_token}",
                                            user.isSubscribed,
                                            "true"
                                        )
                                    )
                                } catch (e: Exception) {
                                    launch {
                                        viewModel.addUser(
                                            User(
                                                1,
                                                "Bearer ${it.data?.data?.access_token}",
                                                "false",
                                                "true"
                                            )
                                        )
                                    }
                                    viewModel.setViewModelUser(
                                        User(
                                            1,
                                            "Bearer ${it.data?.data?.access_token}",
                                            "false",
                                            "true"
                                        )
                                    )
                                }

                                Log.d("Splash user:", viewModel.getViewModelUser().toString())
                                findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
                            }
                            Resource.Status.ERROR -> {
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                                    .show()
                                Log.d("Auth", "Error, ${it.message}")
                                try {
                                    Log.d("Auth", "Success: ${it.data!!.success}")
                                    Log.d("Auth", "Error: ${it.data.error}")
                                } catch (e: Exception) {
                                    Log.d("Auth", e.toString())
                                }
                            }

                            Resource.Status.LOADING -> {
                            }

                        }
                    })
                } else {
                    viewModel.setViewModelUser(user)
                    Log.d("Splash user:", viewModel.getViewModelUser().toString())
                    findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
                }
            })
        }
    }
}