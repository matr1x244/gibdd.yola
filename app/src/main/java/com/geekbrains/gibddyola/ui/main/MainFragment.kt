package com.geekbrains.gibddyola.ui.main

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.geekbrains.gibddyola.MainActivity
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentMainBinding
import com.geekbrains.gibddyola.domain.employee.ControllerOpenFragment
import com.geekbrains.gibddyola.ui.main.bottomNavigation.BottomNavigationDrawerFragment
import com.geekbrains.gibddyola.ui.main.recyclerView.Adapters
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val controller by lazy { activity as ControllerOpenFragment }
    private val viewModel: MainViewModel by viewModel()

    private var adapters = Adapters {
        controller.aboutFragment(it)
        Toast.makeText(context, it.textName, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initIncomingEvents()
        banner()
    }

    private fun banner() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                Glide.with(binding.imageViewMain)
                    .load(R.mipmap.logo)
                    .centerInside()
                    .transform(RoundedCorners(15))
                    .into(binding.imageViewMain)
            }
        }
    }

    private fun initViews() {
        recyclerViewMain()
        menuActionBar()
        textEditTitle()
        buttonPhone()
        viewModel.onShowListAvarkom()
    }

    private fun initIncomingEvents() {
        viewModel.repos.observe(viewLifecycleOwner) {
            adapters.setData(it)
        }
    }

    private fun buttonPhone() {
        binding.buttonPhone.setOnClickListener {
            val number = "709-709"
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$number")
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                BottomNavigationDrawerFragment().show(requireActivity().supportFragmentManager, "")
            } // бургер меню
        }
        return super.onOptionsItemSelected(item)
    }

    fun menuActionBar() {
        setHasOptionsMenu(true)
        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)
    }

    fun recyclerViewMain() {
        binding.recyclerViewListAvarkom.layoutManager = LinearLayoutManager(context)
        adapters.setHasStableIds(true)
        binding.recyclerViewListAvarkom.adapter = adapters
    }

    fun textEditTitle() {
        val textTitle = binding.textHello.text
        val spannableStringBuilder = SpannableStringBuilder(textTitle)
        val red = ForegroundColorSpan(Color.RED)
        val black = ForegroundColorSpan(Color.BLACK)
        val absoluteSizeSpanTitle = AbsoluteSizeSpan(50)

        spannableStringBuilder.setSpan(
            absoluteSizeSpanTitle,
            0,
            58,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableStringBuilder.setSpan(black, 0, 46, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableStringBuilder.setSpan(red, 47, 58, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.textHello.text = spannableStringBuilder
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}