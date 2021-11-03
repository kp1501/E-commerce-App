package com.example.e_commerceapplication.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapplication.R
import com.example.e_commerceapplication.roomdb.cart.CartEntity
import com.example.e_commerceapplication.roomdb.cart.CartViewModel
import com.squareup.picasso.Picasso

class CartAdapter(private val cartViewModel: CartViewModel) : ListAdapter<CartEntity, CartAdapter.ViewHolder>(CartComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current,cartViewModel)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val cartImage: ImageView = itemView.findViewById(R.id.cart_item_iv)
        private val cartPlus: ImageView = itemView.findViewById(R.id.cart_item_plus)
        private val cartMinus: ImageView = itemView.findViewById(R.id.cart_item_minus)
        private val cartName: TextView = itemView.findViewById(R.id.cart_item_name)
        private val cartAmount: TextView = itemView.findViewById(R.id.cart_item_price)
        private val cartTotalPrice: TextView = itemView.findViewById(R.id.cart_total_price)
        private val cartQty: TextView = itemView.findViewById(R.id.cart_item_qty)

        fun bind(current: CartEntity, cartViewModel: CartViewModel) {
            cartImage.setImageBitmap(getImage(current.cart_image))
            cartName.text = current.cart_name
            cartAmount.text = current.cart_amount
            cartTotalPrice.text = current.cart_total
            cartQty.text = current.cart_qty.toString()

            cartPlus.setOnClickListener {
                val id = current.cartId
                var qty = current.cart_qty
                var amount = current.cart_amount
                qty += 1
                amount = (qty * amount.toInt()).toString()
                cartViewModel.updateCart(CartEntity(id,current.cart_image,current.cart_name,current.cart_amount,amount,current.cart_description,current.cart_category,current.cart_userid,current.cart_save,qty))
            }
            cartMinus.setOnClickListener {
                val id = current.cartId
                var qty = current.cart_qty
                var amounts = current.cart_amount
                if (qty>1){
                    qty -= 1
                    amounts = (qty * amounts.toInt()).toString()
                    cartViewModel.updateCart(CartEntity(id,current.cart_image,current.cart_name,current.cart_amount,amounts,current.cart_description,current.cart_category,current.cart_userid,current.cart_save,qty))
                }else {
                    cartViewModel.deleteCart(current)
                }
            }
        }

        companion object{
            fun create(parent: ViewGroup): ViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.cart_item, parent, false)
                return ViewHolder(view)
            }

            private fun getImage(byteArray: ByteArray?): Bitmap {
                return BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
            }
        }
    }

    class CartComparator : DiffUtil.ItemCallback<CartEntity>() {
        override fun areItemsTheSame(oldItem: CartEntity, newItem: CartEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CartEntity, newItem: CartEntity): Boolean {
            return oldItem.cart_image.contentEquals(newItem.cart_image)
        }
    }
}