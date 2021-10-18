package com.example.nshop.orderDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*

class OrderDetailsViewModel : ViewModel() {

    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var orderDetailsRef : DatabaseReference = database.getReference("OrderDetails")
    var notificationsRef : DatabaseReference = database.getReference("Notifications")
    var orderUsersRef : DatabaseReference = database.getReference("OrderUsers")
    var orders = MutableLiveData<ArrayList<OrderDetailsModel>>()
    var list = ArrayList<OrderDetailsModel>()
    var orderProductNames = MutableLiveData<String>("")

    fun getOrderDetails(orderId:String){
        orderDetailsRef.child(orderId).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    var productName = ds.child("ProductName").value.toString()
                    var productQuantity = ds.child("ProductQuantity").value.toString()
                    var productPrice = ds.child("ProductPrice").value.toString()
                    list.add(OrderDetailsModel(productName,productQuantity,productPrice))
                }
                orders.value = list
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    fun acceptOrder(UID: String, orderId: String){
        for (item in list.iterator()){
            orderProductNames.value += item.productName+" ,"
        }
        var map = HashMap<String , String>()
        map["statues"] = "Order Has been Accepted"
        map["orderProductNames"] = orderProductNames.value!!.substring(0,orderProductNames.value!!.length -1)
        notificationsRef.child(UID).push().setValue(map)
        deleteOrderFromDB(orderId)
    }

    fun denyOrder(UID: String, orderId: String){
        for (item in list.iterator()){
            orderProductNames.value += item.productName+" ,"
        }
        var map = HashMap<String , String>()
        map["statues"] = "Order Has been Rejected"
        map["orderProductNames"] = orderProductNames.value!!.substring(0,orderProductNames.value!!.length -1)
        notificationsRef.child(UID).push().setValue(map)
        deleteOrderFromDB(orderId)
    }

    fun deleteOrderFromDB(orderId:String){
        orderUsersRef.orderByChild("OrderID").equalTo(orderId).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    ds.ref.removeValue()
                }
            }

        })
    }

}