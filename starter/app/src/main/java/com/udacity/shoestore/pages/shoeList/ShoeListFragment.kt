package com.udacity.shoestore.pages.shoeList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.udacity.shoestore.R
import com.udacity.shoestore.StoreViewModel
import com.udacity.shoestore.databinding.FragmentShoeListBinding
import com.udacity.shoestore.databinding.ShoeListItemBinding


class ShoeListFragment : Fragment() {

    private lateinit var binding: FragmentShoeListBinding
    private lateinit var navController: NavController
    private val viewModel: StoreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        navController = findNavController()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_list, container, false)

        binding.storeViewModel = viewModel

        observeShoes(inflater, container)
        binding.shoeDetailButton.setOnClickListener {
            navController.navigate(ShoeListFragmentDirections.actionShoeListToShoeDetail())
        }

        return binding.root
    }

    private fun observeShoes(inflater: LayoutInflater, container: ViewGroup?){
        viewModel.shoes.observe(viewLifecycleOwner) {  shoesList ->
            shoesList.forEach { shoe->
                val listItemBinding: ShoeListItemBinding = DataBindingUtil.inflate(inflater, R.layout.shoe_list_item, container, false)
                Glide
                    .with(listItemBinding.root)
                    .load(if (shoe.images.isEmpty()) "" else shoe.images.first())
                    .placeholder(R.drawable.placeholder_2)
                    .into(listItemBinding.shoeImage)

                listItemBinding.shoeImage
                listItemBinding.shoe = shoe
                binding.shoesLinearContainer.addView(listItemBinding.root)
            }
        }
    }
}