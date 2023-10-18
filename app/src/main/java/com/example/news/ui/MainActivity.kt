package com.example.news.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.news.R
import com.example.news.databinding.ActivityMainBinding
import com.example.news.db.ArticleDatabase
import com.example.news.repository.NewsRepository
import com.example.news.viewmodel.NewsViewModel
import com.example.news.viewmodel.NewsViewModelProviderFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var newsViewModel: NewsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        newsViewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]

        // status bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        val config = Configuration(resources.configuration)
        config.setLayoutDirection(Locale("en"))
        resources.updateConfiguration(config, resources.displayMetrics)
        //  supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.secondary_color)))

    }

    override fun onResume() {
        super.onResume()
        //bottomNavigationView
        val navController=findNavController(R.id.newsNavHostGraph)
        binding.bottomNavigation.setupWithNavController(navController)

    }


}