package com.example.nshop.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SignInViewModel : ViewModel() {
    var email = MutableLiveData<String>("")
    var password = MutableLiveData<String>("")
    var checkDataValidation = MutableLiveData<String>("")
    var mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    var showProgress = MutableLiveData<Boolean>(false)
    var signInState = MutableLiveData<String>("")
    fun signIn(){
        if(checkDate()){
            showProgress.value = true
            mAuth.signInWithEmailAndPassword(email.value.toString(),password.value.toString()).addOnCompleteListener {
                if(it.isSuccessful){
                    signInState.value = "success"
                    showProgress.value = false
                }else{
                    signInState.value = it.exception!!.message.toString()
                    showProgress.value = false
                }
            }
        }
    }

    private fun checkDate() : Boolean{

        if(email.value!!.isEmpty()){
            checkDataValidation.value = "Please enter email"
            return false
        }
        if(password.value!!.isEmpty()){
            checkDataValidation.value = "Please enter password"
            return false
        } else{
            return true
        }
    }
}