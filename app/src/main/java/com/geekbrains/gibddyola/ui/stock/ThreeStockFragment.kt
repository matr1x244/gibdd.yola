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
import com.geekbrains.gibddyola.databinding.FragmentThreeStockBinding
import com.geekbrains.gibddyola.ui.stock.StockUrl.ALPHA_DURATION_POSTER
import com.geekbrains.gibddyola.ui.stock.StockUrl.DELAY_TIME_POSTER
import com.geekbrains.gibddyola.ui.stock.StockUrl.POSTERS_THREE
import com.geekbrains.gibddyola.utils.GenerateIdPromoCodes.generateId
import kotlinx.coroutines.*

class ThreeStockFragment : Fragment() {

    private var _binding: FragmentThreeStockBinding? = null
    val binding: FragmentThreeStockBinding
        get() = _binding!!

    companion object {
        @JvmStatic
        fun newInstance() = ThreeStockFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThreeStockBinding.inflate(inflater, container, false)
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
            delay(DELAY_TIME_POSTER)
            withContext(Dispatchers.Main) {
                Glide.with(this@ThreeStockFragment)
                    .load(POSTERS_THREE)
                    .centerInside()
                    .transform(RoundedCorners(10))
                    .transition(DrawableTransitionOptions.withCrossFade(ALPHA_DURATION_POSTER))
                    .error(R.mipmap.no_internet)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(binding.imageViewThreeStock)

                binding.textviewPromoCodId.text = generateId()
            }
        }
    }
}