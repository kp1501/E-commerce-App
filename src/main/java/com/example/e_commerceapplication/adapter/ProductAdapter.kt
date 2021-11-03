package com.example.e_commerceapplication.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapplication.R
import com.example.e_commerceapplication.roomdb.cart.CartViewModel
import com.example.e_commerceapplication.roomdb.product.ProductEntity
import kotlinx.android.synthetic.main.product_item.view.*
import android.graphics.BitmapFactory
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.example.e_commerceapplication.DetailActivity
import com.example.e_commerceapplication.roomdb.cart.CartEntity
import com.example.e_commerceapplication.roomdb.product.ProductViewModel

class ProductAdapter(private val cartViewModel: CartViewModel, val context: Context,private val productViewModel: ProductViewModel): ListAdapter<ProductEntity, ProductAdapter.ViewHolder>(ProductComparator()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        return ProductAdapter.ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, cartViewModel,context,productViewModel)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val items = itemView

        fun bind(item: ProductEntity, cartViewModel: CartViewModel, context: Context,productViewModel: ProductViewModel) {

            items.product_iv.setImageBitmap(getImage(item.product_image))
            items.product_name.text = item.product_name
            items.product_amount.text = item.product_amount
            items.product_description.text = item.product_description
            items.product_category.text = item.product_category
            items.product_userid.text = item.product_userid.toString()
            val save = item.product_save

            if (save == 0){
                items.product_save.setImageDrawable(getDrawable(context,R.drawable.save_foreground))
            }else {
                items.product_save.setImageDrawable(getDrawable(context,R.drawable.filled_save_foreground))
            }
            items.product_save.setOnClickListener {
                if (item.product_save == 1){
                    val updateProduct = ProductEntity(item.productId,item.product_image,item.product_name,item.product_amount,item.product_description,item.product_category,item.product_userid,0)
                    productViewModel.updateProduct(updateProduct)
                }else {
                    val updateProduct = ProductEntity(item.productId,item.product_image,item.product_name,item.product_amount,item.product_description,item.product_category,item.product_userid,1)
                    productViewModel.updateProduct(updateProduct)
                    Toast.makeText(context, "Product saved", Toast.LENGTH_SHORT).show()
                }
            }

            items.product_add_to_cart.setOnClickListener {
                val cart = CartEntity(0,item.product_image,item.product_name,
                    item.product_amount,item.product_amount,item.product_description,item.product_category,
                    item.product_userid,1,1)
                cartViewModel.insertProduct(cart)
                Toast.makeText(context, "Product added into cart.", Toast.LENGTH_SHORT).show()
            }
            items.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("image",item.product_image)
                intent.putExtra("name",items.product_name.text.toString())
                intent.putExtra("amount",items.product_amount.text.toString())
                intent.putExtra("category",items.product_category.text.toString())
                intent.putExtra("description",items.product_description.text.toString())
                context.startActivity(intent)
            }
        }

        companion object{
            fun create(parent: ViewGroup): ProductAdapter.ViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.product_item, parent, false)
                return ProductAdapter.ViewHolder(view)
            }

            private fun getImage(byteArray: ByteArray?): Bitmap {
                return BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
            }
        }
    }

    class ProductComparator : DiffUtil.ItemCallback<ProductEntity>() {
        override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
            return oldItem.product_image.contentEquals(newItem.product_image)
        }
    }
}