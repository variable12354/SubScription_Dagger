package com.example.subscription_dagger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.example.subscription_dagger.databinding.ActivityMainBinding
import com.example.subscription_dagger.subscription.model.SubscriptionState
import com.example.subscription_dagger.subscription.viewmodels.SubscriptionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),View.OnClickListener {

    private val viewModel:SubscriptionViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private var videoProductDetail: ProductDetails? = null
    private var photoProductDetails: ProductDetails? = null
    private var purchases: List<Purchase>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.listener = this
        initObserver()
        initView()
    }


    private fun initObserver () = with(viewModel){
        launchInLifecycle {
            currentPurchasesFlow.collectLatest {
                Log.e("TAG","Current Purchase")
            }
        }
        launchInLifecycle{
            productsForSaleFlows.collectLatest(::handleState)
        }

    }

    /**
     * Observe the available product details and show in Ui
     */
    private fun handleState(viewState:SubscriptionState) = with(binding){
        videoProductDetail = viewState.videoEditSubscription
        photoProductDetails = viewState.photoEditSubscription
    }

    private fun initView() = with(binding){}

    override fun onClick(v: View?) {
        when(v){
            /***
                launch the subscription purchase billing flow with params
                tag is a identification of product like in play console
                create a base plan and offer with offer tag throw fetch subscription
                and pass the offer token
             */
            binding.btnTestingNow -> {
                photoProductDetails?.let {
                    viewModel.buy(
                        it,
                        purchases,
                        this,
                        PHOTO_SUB_TAG
                    )
                }
            }

            binding.btnTestingNow2 -> {
                videoProductDetail?.let {
                    viewModel.buy(
                        it,
                        purchases,
                        this,
                        VIDEO_SUB_TAG
                    )
                }
            }
        }
    }


    companion object{
        const val VIDEO_SUB_TAG = "monthly_free"
        const val PHOTO_SUB_TAG = "monthly_free"
    }


}