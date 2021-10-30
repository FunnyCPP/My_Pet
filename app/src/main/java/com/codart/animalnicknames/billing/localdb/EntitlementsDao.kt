package com.codart.animalnicknames.billing.localdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EntitlementsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(goldStatus: GoldStatus)

    @Update
    fun update(goldStatus: GoldStatus)

    @Query("SELECT * FROM gold_status LIMIT 1")
    fun getGoldStatus(): LiveData<GoldStatus>

    @Delete
    fun delete(goldStatus: GoldStatus)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(premium: PremiumCar)

    @Update
    fun update(premium: PremiumCar)

    @Query("SELECT * FROM premium_car LIMIT 1")
    fun getPremiumCar(): LiveData<PremiumCar>

    @Delete
    fun delete(premium: PremiumCar)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gasLevel: GasTank)

    @Update
    fun update(gasLevel: GasTank)

    @Query("SELECT * FROM gas_tank LIMIT 1")
    fun getGasTank(): LiveData<GasTank>

    @Delete
    fun delete(gasLevel: GasTank)

    /**
     * This is purely for convenience. The clients of this DAO don't have to discriminate among
     * [GasTank] vs [PremiumCar] vs [GoldStatus] but can simply send in a list of
     * [entitlements][Entitlement].
     */
    @Transaction
    fun insert(vararg entitlements: Entitlement) {
        entitlements.forEach {
            when (it) {
                is GasTank -> insert(it)
                is PremiumCar -> insert(it)
                is GoldStatus -> insert(it)
            }
        }
    }

    @Transaction
    fun update(vararg entitlements: Entitlement) {
        entitlements.forEach {
            when (it) {
                is GasTank -> update(it)
                is PremiumCar -> update(it)
                is GoldStatus -> update(it)
            }
        }
    }
}