package com.geekbrains.gibddyola.ui.company

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentAboutCompanyBinding
import com.geekbrains.gibddyola.utils.CallIntent
import com.geekbrains.gibddyola.utils.ViewBindingFragment
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

const val API_KEY = "ffdf1ffa-349f-4d48-bba7-812ec5201452"

class CompanyFragment :
    ViewBindingFragment<FragmentAboutCompanyBinding>(FragmentAboutCompanyBinding::inflate) {

    companion object {
        fun newInstance() = CompanyFragment()
    }

    private var yandexMap: MapView? = null
    private val pointOffice: Point = Point(56.64583, 47.87126)
    private val pointOfficeParking: Point = Point(56.64569, 47.87196)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitInitializer.initialize(API_KEY, requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        maps()
        textEditTitle()
    }

    private fun maps() {
        binding.mapView.map?.mapObjects?.addPlacemark(
            pointOfficeParking,
            ImageProvider.fromResource(requireContext(), R.mipmap.cars_crash)
        )
        binding.mapView.map?.move(
            CameraPosition(pointOffice, 17.2f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 1f), null
        )
        binding.zoomUp.setOnClickListener {
            binding.mapView.map.move(
                CameraPosition(
                    binding.mapView.map?.cameraPosition!!.target,
                    binding.mapView.map.cameraPosition.zoom + 1, 0.0f, 0.0f
                ),
                Animation(Animation.Type.SMOOTH, 1f),
                null
            )
        }
        binding.zoomDown.setOnClickListener {
            binding.mapView.map.move(
                CameraPosition(
                    binding.mapView.map?.cameraPosition!!.target,
                    binding.mapView.map.cameraPosition.zoom - 1, 0.0f, 0.0f
                ),
                Animation(Animation.Type.SMOOTH, 1f),
                null
            )
        }
        binding.itemTextMapsLicense.setOnClickListener {
            val siteMapsYandex = "https://yandex.ru/legal/maps_termsofuse"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(siteMapsYandex)
            startActivity(intent)
        }
    }

    private fun textEditTitle() {
        val organizationTextView = "ИП Ковалёва Ю.С. \n инн 121524178574 огрнип 316121500063612"
        binding.textViewOrganization.text = organizationTextView
        val contactTime = "ПН–ПТ: 09:00–18:00 \n СБ-ВС: Выходной"
        binding.itemTextContactTime.text = contactTime
        val contactAdress = "ул. Красноармейская, 84а, офис 2 \n Йошкар-ола, 424000, Россия"
        binding.itemTextContactContactAbout.text = contactAdress

        val textTitle = binding.itemTittle.text
        val spannableStringBuilder = SpannableStringBuilder(textTitle)
        val red = ForegroundColorSpan(Color.RED)

        spannableStringBuilder.setSpan(red, 0, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.itemTittle.text = spannableStringBuilder

        binding.itemTextContactCallAbout.setOnClickListener {
            CallIntent.check(requireActivity())
        }
    }

    override fun onStop() {
        yandexMap?.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        yandexMap?.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
