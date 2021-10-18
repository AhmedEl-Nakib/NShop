package com.example.nshop.addProduct

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.nshop.R
import com.example.nshop.databinding.FragmentAddProdcutBinding
import com.example.nshop.signin.SignInViewModel
import com.example.nshop.util.Constants
import com.google.android.material.snackbar.Snackbar

class AddProdcutFragment : Fragment() {

    lateinit var binding : FragmentAddProdcutBinding
    val model: AddProductViewModel by viewModels()
    val IMAGE_REQUEST_CODE = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddProdcutBinding.inflate(inflater)
        binding.viewModel = model
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClick()
        handleObserver()
    }

    private fun handleObserver() {
        model.checkDataValidation.observe(viewLifecycleOwner,{
            if(it.isNotEmpty()){
                Snackbar.make(binding.rootId, it.toString(), Snackbar.LENGTH_SHORT)
                    .show()
            }
        })
        model.uploadState.observe(viewLifecycleOwner,{
            if(it.isNotEmpty()){
                Snackbar.make(binding.rootId, it.toString(), Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    private fun handleClick() {
        binding.imageId.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK ){
            model.imageURI = data!!.data!!
            binding.imageId.setImageURI(model.imageURI)
        }
    }


}