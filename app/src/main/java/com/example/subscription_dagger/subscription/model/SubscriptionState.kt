package com.example.subscription_dagger.subscription.model

import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase

data class SubscriptionState(
    val videoEditSubscription: ProductDetails? = null,
    val photoEditSubscription: ProductDetails? = null,
    val hasRenewableVideo : Boolean = false,
    val hasRenewablePhoto :Boolean = false,
    val hasPrepaidVideo :Boolean = false,
    val hasPrepaidPhoto :Boolean = false,
)
