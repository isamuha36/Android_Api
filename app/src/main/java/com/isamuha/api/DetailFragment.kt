package com.isamuha.api

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.isamuha.api.databinding.FragmentDetailBinding
import com.isamuha.api.model.User


class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.getInt("user_id")
        val userEmail = arguments?.getString("user_email")
        val userFirstName = arguments?.getString("user_first_name")
        val userLastName = arguments?.getString("user_last_name")
        val userAvatar = arguments?.getString("user_avatar")

        if (userFirstName != null && userLastName != null) {
            binding.nameTextView.text = "$userFirstName $userLastName"
        }
        binding.emailTextView.text = userEmail
        userAvatar?.let {
            Glide.with(this).load(it).into(binding.avatarImageView)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}