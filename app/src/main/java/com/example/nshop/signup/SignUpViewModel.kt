package com.example.nshop.signup

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpViewModel : ViewModel() {

    var shopName = MutableLiveData<String>("")
    var shopEmail = MutableLiveData<String>("")
    var shopPassword = MutableLiveData<String>("")
    var shopPhone = MutableLiveData<String>("")
    var shopAddress = MutableLiveData<String>("")

    var mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var shopRef : DatabaseReference = database.getReference("ShopReference")
    var showProgress = MutableLiveData<Boolean>(false)

    var signUpState = MutableLiveData<String>("")
    var checkDataValidation = MutableLiveData<String>("")
    fun signUp(){

        if(checkDate()) {
            showProgress.value = true
            mAuth.createUserWithEmailAndPassword(
                shopEmail.value.toString(),
                shopPassword.value.toString()
            )
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        var map = HashMap<String, String>()
                        map["ShopName"] = shopName.value.toString()
                        map["ShopEmail"] = shopEmail.value.toString()
                        map["ShopPhone"] = shopPhone.value.toString()
                        map["ShopAddress"] = shopAddress.value.toString()
                        shopRef.setValue(map)
                        signUpState.value = "Shop Created Successfully"
                        showProgress.value = false

                    } else {
                        signUpState.value = it.exception!!.message.toString()
                        showProgress.value = false
                    }
                }
        }
    }

    private fun checkDate() : Boolean{
        if(shopName.value!!.isEmpty()){
            checkDataValidation.value = "Please enter name"
            return false
        }
        if(shopEmail.value!!.isEmpty()){
            checkDataValidation.value = "Please enter email"
            return false
        }
        if(shopPassword.value!!.isEmpty()){
            checkDataValidation.value = "Please enter password"
            return false
        }
        if(shopPhone.value!!.isEmpty()){
            checkDataValidation.value = "Please enter phone"
            return false
        }
        if(shopAddress.value!!.isEmpty()){
            checkDataValidation.value = "Please enter address"
            return false
        }else{
            return true
        }
    }
}