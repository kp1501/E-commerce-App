package com.example.e_commerceapplication.roomdb.product

import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(

    @PrimaryKey(autoGenerate = true)
    val productId: Int = 0,

    @ColumnInfo(name = "product_image", typeAffinity = ColumnInfo.BLOB)
    val product_image : ByteArray? = null,

    @ColumnInfo(name = "product_name")
    val product_name : String,

    @ColumnInfo(name = "product_amount")
    val product_amount : String,

    @ColumnInfo(name = "product_description")
    val product_description : String,

    @ColumnInfo(name = "product_category")
    val product_category : String,

    @ColumnInfo(name = "product_userid")
    val product_userid : Int,

    @ColumnInfo(name = "product_save")
    val product_save : Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductEntity

        if (product_image != null) {
            if (other.product_image == null) return false
            if (!product_image.contentEquals(other.product_image)) return false
        } else if (other.product_image != null) return false

        return true
    }

    override fun hashCode(): Int {
        return product_image?.contentHashCode() ?: 0
    }
}
