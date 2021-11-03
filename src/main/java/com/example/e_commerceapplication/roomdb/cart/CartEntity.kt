package com.example.e_commerceapplication.roomdb.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartEntity(

    @PrimaryKey(autoGenerate = true)
    val cartId: Int = 0,

    @ColumnInfo(name = "cart_image",  typeAffinity = ColumnInfo.BLOB)
    val cart_image : ByteArray? = null,

    @ColumnInfo(name = "cart_name")
    val cart_name : String,

    @ColumnInfo(name = "cart_amount")
    val cart_amount : String,

    @ColumnInfo(name = "cart_total")
    val cart_total : String,

    @ColumnInfo(name = "cart_description")
    val cart_description : String,

    @ColumnInfo(name = "cart_category")
    val cart_category : String,

    @ColumnInfo(name = "cart_userid")
    val cart_userid : Int,

    @ColumnInfo(name = "cart_save")
    val cart_save : Int,

    @ColumnInfo(name = "cart_qty")
    val cart_qty : Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CartEntity

        if (cart_image != null) {
            if (other.cart_image == null) return false
            if (!cart_image.contentEquals(other.cart_image)) return false
        } else if (other.cart_image != null) return false

        return true
    }

    override fun hashCode(): Int {
        return cart_image?.contentHashCode() ?: 0
    }
}
