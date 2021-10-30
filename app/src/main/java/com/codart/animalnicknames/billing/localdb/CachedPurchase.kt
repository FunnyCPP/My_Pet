package com.codart.animalnicknames.billing.localdb

import androidx.room.*
import com.android.billingclient.api.Purchase

@Entity(tableName = "purchase_table")
@TypeConverters(PurchaseTypeConverter::class)
class CachedPurchase(val data: Purchase) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @Ignore
    val purchaseToken = data.purchaseToken
    @Ignore
    val sku = data.sku

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is CachedPurchase -> data.equals(other.data)
            is Purchase -> data.equals(other)
            else -> false
        }
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }

}

class PurchaseTypeConverter {
    @TypeConverter
    fun toString(purchase: Purchase): String = purchase.originalJson + '|' + purchase.signature

    @TypeConverter
    fun toPurchase(data: String): Purchase = data.split('|').let {
        Purchase(it[0], it[1])
    }
}