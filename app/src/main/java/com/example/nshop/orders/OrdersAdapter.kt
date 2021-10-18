package com.example.nshop.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nshop.R

class OrdersAdapter(private var dataSet: ArrayList<OrdersModel> , var onOrderItemClick: OnOrdereItemClick) : RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var name: TextView
        lateinit var phone: TextView
        lateinit var address: TextView
        init {
            name = view.findViewById(R.id.userNameId)
            phone = view.findViewById(R.id.userPhoneId)
            address = view.findViewById(R.id.userAddressId)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.orders_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.setOnClickListener {
            onOrderItemClick.onItemClicked(dataSet[position].id,dataSet[position].UID)
        }
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.name.text = dataSet[position].name
        viewHolder.phone.text = dataSet[position].phone
        viewHolder.address.text = dataSet[position].address
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun filterData( newList :ArrayList<OrdersModel> ){
        this.dataSet = newList
        notifyDataSetChanged() // refresh
    }
}