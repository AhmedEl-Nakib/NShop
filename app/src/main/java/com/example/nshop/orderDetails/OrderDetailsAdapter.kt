package com.example.nshop.orderDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nshop.R

class OrderDetailsAdapter(var dataSet : ArrayList<OrderDetailsModel>) : RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var name: TextView
        lateinit var quantity: TextView
        lateinit var price: TextView
        init {
            name = view.findViewById(R.id.proNameId)
            quantity = view.findViewById(R.id.proQuantityId)
            price = view.findViewById(R.id.proPriceId)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.order_details_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.name.text = "Product Name : ${dataSet[position].productName}"
        viewHolder.quantity.text = "Quantity : ${dataSet[position].productQuantity}"
        viewHolder.price.text = "Price per Product : ${dataSet[position].productPrice}"
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}