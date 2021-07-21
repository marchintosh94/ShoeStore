package com.udacity.shoestore.pages.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.StoreViewModel
import com.udacity.shoestore.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: StoreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        binding.viewModel = viewModel
        viewModel.eventLogin.observe(viewLifecycleOwner){ loginSuccess ->
            if (loginSuccess){
                findNavController().navigate(LoginFragmentDirections.loginToWelcome())
                viewModel.onLoginSuccess()
            }
        }
        viewModel.errorMessage.observe(viewLifecycleOwner){ message ->
            if (message.isNotEmpty()){
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}