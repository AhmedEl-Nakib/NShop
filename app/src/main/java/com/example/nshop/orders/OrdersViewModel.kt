package com.example.nshop.orders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*

class OrdersViewModel : ViewModel() {

    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var orderUsersRef : DatabaseReference = database.getReference("OrderUsers")

    var orders = MutableLiveData<ArrayList<OrdersModel>>()
    var list = ArrayList<OrdersModel>()
    var progress = MutableLiveData<Boolean>(false)
    var searchWord = MutableLiveData<String>("")

    fun getOrders(){
        list.clear()
        progress.value = true
        orderUsersRef.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    var name = ds.child("OrderUserName").value.toString()
                    var orderId = ds.child("OrderID").value.toString()
                    var address = ds.child("OrderUserAddress").value.toString()
                    var phone = ds.child("OrderUserPhone").value.toString()
                    var UID = ds.child("UID").value.toString()
                    list.add(OrdersModel(orderId,name,phone,address,UID))
                }
                orders.value = list
                progress.value = false
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}