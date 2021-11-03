package com.example.e_commerceapplication

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.viewModels
import com.example.e_commerceapplication.adapter.ProductAdapter
import com.example.e_commerceapplication.roomdb.cart.CartViewModel
import com.example.e_commerceapplication.roomdb.product.ProductEntity
import com.example.e_commerceapplication.roomdb.product.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_current.*
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class CurrentFragment : Fragment() {

    private val productViewModel: ProductViewModel by viewModels()
    private val cartViewModel : CartViewModel by viewModels()

    lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ProductAdapter(cartViewModel,requireContext(),productViewModel)
        val PREF_DATA = "user_room"
        val sp : SharedPreferences = requireActivity().getSharedPreferences(PREF_DATA, AppCompatActivity.MODE_PRIVATE)
        val currentId = sp.getInt("userid",0)
        val productEntity = ProductEntity(0,convertImage(getDrawable(requireContext(),R.drawable.tshirt)),"T-shirt","1900","T-shirt for men","t-shirt",currentId,0)
        productViewModel.allProducts(productEntity).observe(requireActivity()) {
            it.let { adapter.submitList(it)}
        }
        fragment_recyclerview.adapter = adapter
    }

    private fun convertImage(drawable: Drawable?): ByteArray? {
        val bitmap = (drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.WEBP, 25, stream)
        return stream.toByteArray()
    }
}