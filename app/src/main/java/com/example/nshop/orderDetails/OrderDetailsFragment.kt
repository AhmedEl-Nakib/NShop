package com.example.nshop.orderDetails

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.nshop.databinding.FragmentOrderDetailsBinding

class OrderDetailsFragment : Fragment() {

    lateinit var binding : FragmentOrderDetailsBinding
    val args: OrderDetailsFragmentArgs by navArgs()
    val model: OrderDetailsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderDetailsBinding.inflate(inflater)
        binding.viewModel = model
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleView()
        handleObserver()
        binding.orderStateBtnId.setOnClickListener {
            showDialog()
        }
    }

    private fun handleObserver() {
        model.orders.observe(viewLifecycleOwner,{
            if(it.isNotEmpty()){
                binding.recyclerId.adapter = OrderDetailsAdapter(it)
            }
        })
    }

    private fun handleView() {
        model.getOrderDetails(args.orderId)
    }

    private fun showDialog(){
        var alert = AlertDialog.Builder(requireContext())
        alert.setTitle("Order Confirmation")
        alert.setMessage("Please select order state")
        alert.setCancelable(false)
        alert.setPositiveButton("Accept Order") { dialog , which ->
            model.acceptOrder(args.uid,args.orderId)
        }
        alert.setNegativeButton("Deny Order") {dialog , which ->
            model.denyOrder(args.uid,args.orderId)

        }
        alert.create().show()

    }


}