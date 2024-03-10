import android.app.Application
import com.example.subscription_dagger.subscription.BillingClient
import com.example.subscription_dagger.subscription.repositiory.SubscriptionRepository
import com.google.android.datatransport.runtime.dagger.Provides
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBillingClass(application: Application) = BillingClient(application.baseContext)

    @Singleton
    @Provides
    fun subscriptionRepo(billingClient: BillingClient) = SubscriptionRepository(billingClient)

}