package com.udacity.shoestore.pages.shoeDetail

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
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding

class ShoeDetailFragment : Fragment() {

    private lateinit var binding: FragmentShoeDetailBinding
    private val viewModel: StoreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_detail, container, false)
        binding.storeViewModel = viewModel

        viewModel.eventNavigateToList.observe(viewLifecycleOwner){ isCompleted ->
            if (isCompleted){
                findNavController().navigate(ShoeDetailFragmentDirections.actionShoeDetailToShoeList())
                viewModel.onNavigateToListComplete()
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