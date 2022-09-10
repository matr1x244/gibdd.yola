package com.geekbrains.gibddyola.ui.stock

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentThreeStockBinding
import com.geekbrains.gibddyola.ui.stock.StockUrl.ALPHA_DURATION_POSTER
import com.geekbrains.gibddyola.ui.stock.StockUrl.DELAY_TIME_POSTER
import com.geekbrains.gibddyola.ui.stock.StockUrl.POSTERS_THREE
import com.geekbrains.gibddyola.utils.GenerateIdPromoCodes.generateId
import com.geekbrains.gibddyola.utils.ViewBindingFragment
import com.geekbrains.gibddyola.utils.showSnackBarNoAction
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*

class ThreeStockFragment :
    ViewBindingFragment<FragmentThreeStockBinding>(FragmentThreeStockBinding::inflate) {

    companion object {
        @JvmStatic
        fun newInstance() = ThreeStockFragment()
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
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.imageViewErrorInternet.visibility = View.VISIBLE
                            binding.fragmentThreeStock.showSnackBarNoAction(
                                R.string.no_internet_stock,
                                Snackbar.LENGTH_LONG
                            )
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.imageViewErrorInternet.visibility = View.GONE
                            binding.textviewPromoCodId.text = getString(R.string.promo_code_id, generateId())
                            return false
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(binding.imageViewThreeStock)
            }
        }
    }
}