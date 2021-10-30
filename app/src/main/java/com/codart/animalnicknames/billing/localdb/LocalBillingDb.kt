package com.codart.animalnicknames.billing.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.CoroutineScope

@Database(
        entities = [
            AugmentedSkuDetails::class,
            CachedPurchase::class,
            GasTank::class,
            GoldStatus::class,
            PremiumCar::class
        ],
        version = 1,
        exportSchema = false
)
@TypeConverters(PurchaseTypeConverter::class)
abstract class LocalBillingDb : RoomDatabase() {
    abstract fun purchaseDao(): PurchaseDao
    abstract fun entitlementsDao(): EntitlementsDao
    abstract fun skuDetailsDao(): AugmentedSkuDetailsDao

    companion object {
        @Volatile
        private var INSTANCE: LocalBillingDb? = null
        private val DATABASE_NAME = "purchase3_db"
        fun getDatabase(
            context: Context
        ): LocalBillingDb {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalBillingDb::class.java,
                    DATABASE_NAME
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
