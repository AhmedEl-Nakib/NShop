package com.example.nshop.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.nshop.R
import com.example.nshop.databinding.FragmentOrdersBinding
import com.example.nshop.signup.SignUpViewModel

class OrdersFragment : Fragment(), OnOrdereItemClick {

    lateinit var binding : FragmentOrdersBinding
    val model: OrdersViewModel by viewModels()
    lateinit var adapter: OrdersAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrdersBinding.inflate(inflater)
        binding.viewModel = model
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleView()
        handleObserver()
        adapter = OrdersAdapter(ArrayList(),this)
    }

    private fun handleObserver() {
        model.orders.observe(viewLifecycleOwner,{
            if(it.isNotEmpty()){
                adapter.filterData(it)
                binding.recyclerId.adapter = adapter
            }else{
                adapter.filterData(model.list)
            }
        })
        model.searchWord.observe(viewLifecycleOwner,{
            if(it.isNotEmpty()){
                filter(it)
            }else{
                adapter.filterData(model.list)
            }

        })
    }

    private fun filter(searchWord: String) {
        var filterdList = ArrayList<OrdersModel>() // result
        for(name in model.list){
            if(name.name.toLowerCase().contains(searchWord)){
                filterdList.add(name)
            }
        }
        adapter.filterData(filterdList)
    }

    private fun handleView() {
        model.getOrders()
    }

    override fun onItemClicked(orderId: String, UID: String) {
        Navigation.findNavController(requireView()).navigate(OrdersFragmentDirections.actionOrdersNavIdToOrderDetailsFragment(
            orderId,UID
        ))
    }

}