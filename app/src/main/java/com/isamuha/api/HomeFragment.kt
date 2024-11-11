package com.isamuha.api

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.isamuha.api.databinding.FragmentHomeBinding
import com.isamuha.api.model.User
import com.isamuha.api.model.UserResponse
import com.isamuha.api.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        fetchDataFromApi()
    }

    private fun fetchDataFromApi() {
        ApiClient.instance.getUsers(2).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val users = response.body()?.data ?: emptyList()
                    binding.recyclerView.adapter = UserAdapter(users) { user ->
                        // Aksi ketika item diklik: Pindah ke DetailFragment
                        navigateToDetailFragment(user)
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {

            }
        })
    }

    private fun navigateToDetailFragment(user: User) {
        val bundle = Bundle().apply {
            putInt("user_id", user.id)
            putString("user_email", user.email)
            putString("user_first_name", user.first_name)
            putString("user_last_name", user.last_name)
            putString("user_avatar", user.avatar)
        }
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}