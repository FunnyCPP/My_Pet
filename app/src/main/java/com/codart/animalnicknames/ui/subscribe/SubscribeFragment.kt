package com.codart.animalnicknames.ui.subscribe

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.codart.animalnicknames.R
import com.codart.animalnicknames.billing.BillingViewModel
import com.codart.animalnicknames.billing.localdb.AugmentedSkuDetails

class SubscribeFragment : Fragment() {

    lateinit var sub1Month: ConstraintLayout
    lateinit var sub3Month: ConstraintLayout
    lateinit var sub6Month: ConstraintLayout
    private lateinit var billingViewModel: BillingViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subscribe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        billingViewModel = ViewModelProviders.of(this).get(BillingViewModel::class.java)
        requireView().findViewById<ImageView>(R.id.subscribe_back).setOnClickListener {
            findNavController().navigateUp()
        }
        sub1Month = requireView().findViewById(R.id.layout_noads_1month)
        sub3Month = requireView().findViewById(R.id.layout_noads_3monthes)
        sub6Month = requireView().findViewById(R.id.layout_noads_6monthes)
        val SUB_1MONTH = "subscribe_1"
        val SUB_3MONTH = "subscribe_3"
        val SUB_6MONTH = "subscribe_6"
        var list: List<AugmentedSkuDetails> = arrayListOf( AugmentedSkuDetails(true, "sku","type","price","title","description","originalJson"))
        billingViewModel.subsSkuDetailsListLiveData.observe(viewLifecycleOwner, {
            it?.let { list  = it
            Log.d("Sub list  size",list.size.toString())}
        })

        sub1Month.setOnClickListener{
            for(i in list)
            {
                if(i.sku == SUB_1MONTH )
                {
                    Toast.makeText(requireContext(),i.title, Toast.LENGTH_LONG).show()
                    onPurchase(i)
                }
            }
        }
        sub3Month.setOnClickListener{
            for(i in list)
            {
                if(i.sku == SUB_3MONTH )
                {
                    Toast.makeText(requireContext(),i.title, Toast.LENGTH_LONG).show()
                    onPurchase(i)
                }
            }
        }
        sub6Month.setOnClickListener{
            for(i in list)
            {
                if(i.sku == SUB_6MONTH )
                {
                    Toast.makeText(requireContext(),i.title, Toast.LENGTH_LONG).show()
                    onPurchase(i)
                }
            }
        }
    }
    private fun onPurchase(item: AugmentedSkuDetails) {
        billingViewModel.makePurchase(requireActivity(), item)
        Log.d("Making purchase", "starting purchase flow for SkuDetail:\n ${item}")
    }
}