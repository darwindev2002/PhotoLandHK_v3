package com.darwin.photolandhk.main_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.darwin.photolandhk.R
import com.darwin.photolandhk.databinding.FragmentMainLibraryBinding
import me.gilo.woodroid.Woocommerce
import me.gilo.woodroid.models.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LibraryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMainLibraryBinding>(inflater, R.layout.fragment_main_library, container, false)
        binding.textView2.text = getString(R.string.title_library) + "\nComing soon :)"
        val woocommerce = Woocommerce.Builder()
            .setSiteUrl("https://photolandhk.com")
            .setApiVersion(Woocommerce.API_V3)
            .setConsumerKey("ck_67d2c2aa1fd712826886f9cb8bbe6e6710673fc7")
            .setConsumerSecret("cs_cd02e4741981fc9693e67e11c477cde22abfa9f4")
            .build()
//        val product = Product(0, "name")
//        val coupon =
        woocommerce.ProductRepository().products().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response:Response<List<Product>>){
                if (response.isSuccessful) {
                    val productsResponse = response.body()
                    for (product in productsResponse!!) {
                        Log.i("WooDroid", "product=$product, ${product.description}")
                    }
                } else {
                    var error = ""
                    try {
                        error = response.errorBody()?.string() ?: "error"
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    if (error == "error"){
                        error = "Something went wrong"
                    }
                    Log.e("WooDroid", error)
                }
            }
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
        return binding.root
    }
}