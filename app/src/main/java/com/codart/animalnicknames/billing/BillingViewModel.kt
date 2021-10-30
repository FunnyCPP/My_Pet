package com.codart.animalnicknames.billing

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.codart.animalnicknames.billing.localdb.AugmentedSkuDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

class BillingViewModel(application: Application) : AndroidViewModel(application) {

    val subsSkuDetailsListLiveData: LiveData<List<AugmentedSkuDetails>>
    val inappSkuDetailsListLiveData: LiveData<List<AugmentedSkuDetails>>

    private val LOG_TAG = "BillingViewModel"
    private val viewModelScope = CoroutineScope(Job() + Dispatchers.Main)
    private val repository: BillingRepository

    init {
        repository = BillingRepository.getInstance(application)
        repository.startDataSourceConnections()
        subsSkuDetailsListLiveData = repository.subsSkuDetailsListLiveData
        inappSkuDetailsListLiveData = repository.inappSkuDetailsListLiveData
    }

    /**
     * Not used in this sample app. But you may want to force refresh in your own app (e.g.
     * pull-to-refresh)
     */
    fun queryPurchases() = repository.queryPurchasesAsync()

    override fun onCleared() {
        super.onCleared()
        Log.d(LOG_TAG, "onCleared")
        repository.endDataSourceConnections()
        viewModelScope.coroutineContext.cancel()
    }

    fun makePurchase(activity: Activity, augmentedSkuDetails: AugmentedSkuDetails) {
        repository.launchBillingFlow(activity, augmentedSkuDetails)
    }

    /**
     * It's important to save after decrementing since gas can be updated by both clients and
     * the data sources.
     *
     * Note that even the ViewModel does not need to worry about thread safety because the
     * repo has already taken care it. So definitely the clients also don't need to worry about
     * thread safety.
     */


}