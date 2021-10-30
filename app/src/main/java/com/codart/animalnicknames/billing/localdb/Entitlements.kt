package com.codart.animalnicknames.billing.localdb

import androidx.room.Entity
import androidx.room.PrimaryKey

private const val FULL_TANK = 4
private const val EMPTY_TANK = 0
const val GAS_PURCHASE = 1

/**
 * Normally this would just be an interface. But since each of the entitlements only has
 * one item/row and so primary key is fixed, we can put the primary key here and so make
 * the class abstract.
 **/
abstract class Entitlement {
    @PrimaryKey
    var id: Int = 1

    /**
     * This method tells clients whether a user __should__ buy a particular item at the moment. For
     * example, if the gas tank is full the user should not be buying gas. This method is __not__
     * a reflection on whether Google Play Billing can make a purchase.
     */
    abstract fun mayPurchase(): Boolean
}

/**
 * Indicates whether the user owns a premium car.
 */
@Entity(tableName = "premium_car")
data class PremiumCar(val entitled: Boolean) : Entitlement() {
    override fun mayPurchase(): Boolean = !entitled
}

/**
 * Subscription is kept simple in this project. And so here the user either has a subscription
 * to gold status or s/he doesn't. For more on subscriptions, see the Classy Taxi sample app.
 */
@Entity(tableName = "gold_status")
data class GoldStatus(val entitled: Boolean) : Entitlement() {
    override fun mayPurchase(): Boolean = !entitled
}

/**
 * The level inside this gas tank goes up when user buys gas and goes down when user drives. This
 * means level can be updated from clients and from servers. By clients are Activities, Fragments
 * and Views; by servers are the Play BillingClient or your own secure server. Therefore you
 * should code your repository against race conditions and interleaves.
 */
@Entity(tableName = "gas_tank")
class GasTank(private var level: Int) : Entitlement() {

    /**
     * In order to exercise great control over how clients use the API, [setLevel] is made
     * private while keeping [getLevel] public. There is no idiomatic way to do this
     * in Kotlin for an [Entity] data class. So instead of going for "idiomatic", the favor is given
     * to "simple".  But in your own app feel free to go for idiomatic Kotlin.
     */
    fun getLevel() = level

    override fun mayPurchase(): Boolean = level < FULL_TANK

    fun needGas(): Boolean = level <= EMPTY_TANK

    fun decrement(by: Int = 1) {
        level -= by
    }
}