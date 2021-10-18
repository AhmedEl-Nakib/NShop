package com.example.nshop.addProduct

import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddProductViewModel : ViewModel() {

    var proName = MutableLiveData<String>("")
    var proPrice = MutableLiveData<String>("")
    var proDescription = MutableLiveData<String>("")
    lateinit var imageURI : Uri
    var checkDataValidation = MutableLiveData<String>("")
    var mStorage : StorageReference =  FirebaseStorage.getInstance().reference
    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var productsRef : DatabaseReference = database.getReference("Products")
    var showProgress = MutableLiveData<Boolean>(false)
    var uploadState = MutableLiveData<String>("")
    fun uploadProduct(){
        if(checkDate()){
            showProgress.value = true

            var uploadRef : StorageReference = mStorage.child("Photos/"+System.currentTimeMillis())
            uploadRef.putFile(imageURI).addOnCompleteListener {
                if(it.isSuccessful){
                    uploadRef.downloadUrl.addOnSuccessListener {downloadURL ->
                        var map = HashMap<String,String>()
                        var ID = productsRef.push().key.toString()
                        map["ProductName"] = proName.value.toString()
                        map["ProductPrice"] = proPrice.value.toString()
                        map["ProductDescription"] = proDescription.value.toString()
                        map["downloadURL"] = downloadURL.toString()
                        map["ProductID"] = ID
                        productsRef.child(ID).setValue(map)
                        uploadState.value = "Upload Success"
                        showProgress.value = false
                    }

                }else {
                    uploadState.value ="Upload Fail ${it.exception?.message}"

                    showProgress.value = false

                }

            }
        }
    }


    private fun checkDate() : Boolean{

        if(proName.value!!.isEmpty()){
            checkDataValidation.value = "Please enter product name"
            return false
        }
        if(proPrice.value!!.isEmpty()){
            checkDataValidation.value = "Please enter product price"
            return false
        }
        if(proDescription.value!!.isEmpty()){
            checkDataValidation.value = "Please enter product description"
            return false
        }
        if(imageURI == null){
            checkDataValidation.value = "Please select photo"
            return false
        }
        else{
            return true
        }
    }
}