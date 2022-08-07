package com.geekbrains.gibddyola.ui.stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentOneStockBinding
import com.geekbrains.gibddyola.utils.GenerateIdPromoCodes
import com.geekbrains.gibddyola.utils.GenerateIdPromoCodes.generateId
import kotlinx.coroutines.*

class OneStockFragment : Fragment() {

    private var _binding: FragmentOneStockBinding? = null
    val binding: FragmentOneStockBinding
        get() = _binding!!

    val url = "https://гибдд12.рф/img/photos/posters/1.jpg"

    companion object {
        @JvmStatic
        fun newInstance() = OneStockFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOneStockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startStockImage()
    }

    private fun startStockImage() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(5_00)
            withContext(Dispatchers.Main) {
                Glide.with(this@OneStockFragment)
                    .load(POSTERS_ONE)
                    .centerInside()
                    .transform(RoundedCorners(10))
                    .transition(DrawableTransitionOptions.withCrossFade(2000))
                    .error(R.mipmap.no_internet)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(binding.imageViewOneStock)

                binding.textviewPromoCodId.text = generateId()
            }
        }
    }
}