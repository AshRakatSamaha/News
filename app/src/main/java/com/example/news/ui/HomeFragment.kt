package com.example.news.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.news.R
import com.example.news.adapter.MyPagerAdapter
import com.example.news.databinding.FragmentHomeBinding
import com.example.news.viewmodel.NewsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var viewPager: ViewPager2
    lateinit var newsViewModel: NewsViewModel
    private var fragments = listOf(
        BreakingNewsFragment(), SavedNewsFragment(), SearchNewsFragment()
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding= FragmentHomeBinding.inflate(layoutInflater)
        initBottomNavigation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel=(activity as MainActivity).newsViewModel

    }
    private fun initBottomNavigation() {


        viewPager = binding.viewPager
        bottomNavigationView = binding.bottomNavigation
        val adapter = MyPagerAdapter(childFragmentManager, fragments, lifecycle)
        binding.viewPager.adapter = adapter


        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.breakingNewsFragment -> viewPager.currentItem = 0
                R.id.savedNewsFragment -> viewPager.currentItem = 1
                R.id.searchNewsFragment -> viewPager.currentItem = 2

            }
            true
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val itemId = when (position) {
                    0 -> R.id.breakingNewsFragment
                    1 -> R.id.savedNewsFragment
                    2 -> R.id.searchNewsFragment

                    else -> null
                }
                bottomNavigationView.selectedItemId = itemId!!
            }
        })

    }
}