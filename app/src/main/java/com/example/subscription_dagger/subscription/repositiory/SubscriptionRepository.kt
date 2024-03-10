package com.example.subscription_dagger.subscription.repositiory

import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.example.subscription_dagger.subscription.BillingClient
import com.example.subscription_dagger.subscription.SubscriptionConstant.SUBSCRIPTION_PHOTO
import com.example.subscription_dagger.subscription.SubscriptionConstant.SUBSCRIPTION_VIDEO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * subscription repository
 *
 *
 */

class SubscriptionRepository @Inject constructor(private var billingClient: BillingClient) {
    /**
        it return true when subscription is Auto renewable
     */
    val hasRenewableVideoSubscription: Flow<Boolean> = billingClient.purchases.map { purchaseList ->
        purchaseList.any { purchase ->
            purchase.products.contains(SUBSCRIPTION_VIDEO) && purchase.isAutoRenewing
        }
    }

    /**
      it return true when subscription is prepaid plan
     */
    val hasPrepaidVideoSubscription: Flow<Boolean> = billingClient.purchases.map { purchaseList ->
        purchaseList.any { purchase ->
            !purchase.isAutoRenewing && purchase.products.contains(SUBSCRIPTION_VIDEO)
        }
    }

    val hasRenewablePhotoSubscription: Flow<Boolean> = billingClient.purchases.map { purchaseList ->
        purchaseList.any { purchase ->
            purchase.products.contains(SUBSCRIPTION_PHOTO) && purchase.isAutoRenewing
        }
    }

    val hasPrepaidPhotoSubscription: Flow<Boolean> = billingClient.purchases.map { purchaseList ->
        purchaseList.any { purchase ->
            !purchase.isAutoRenewing && purchase.products.contains(SUBSCRIPTION_PHOTO)
        }
    }

    // ProductDetails for the video subscription and Display in Ui
    val videoEditProductDetails: Flow<ProductDetails> =
        billingClient.productDetail.filter {
            it.containsKey(
                SUBSCRIPTION_VIDEO
            )
        }.map { it[SUBSCRIPTION_VIDEO]!! }

    // ProductDetails for the photo subscription and Display in Ui
    val photoEditProductDetail: Flow<ProductDetails> =
        billingClient.productDetail.filter {
            it.containsKey(
                SUBSCRIPTION_PHOTO
            )
        }.map { it[SUBSCRIPTION_PHOTO]!! }

   /**
    * purchase is reture current purchase and
    * query purchase throw get active subscription purchase details
    * it can contain purchase orderId package name purchase product
    * signature isacknoledge flag this all detail send to your server api
    */
    val purchases: Flow<List<Purchase>> = billingClient.purchases

    /**
     * acknowledge is return successfully  purchase subscription
     * and enable your product feature for users
     */
    val isNewPurchaseAcknowledged: Flow<Boolean> = billingClient.isNewPurchaseAcknowledged

}