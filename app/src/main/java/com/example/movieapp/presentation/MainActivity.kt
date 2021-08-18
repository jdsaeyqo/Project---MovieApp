package com.example.movieapp.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: ActivityMainBinding

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.item_movie -> {

                val movieFragment =
                    MovieFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, movieFragment)
                    .commit()
                return true
            }
            R.id.item_tvshow -> {
                val tvFragment = TvShowFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, tvFragment)
                    .commit()
                return true
            }

        }
        return false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnNavigationItemSelectedListener(this)

        binding.bottomNav.selectedItemId = R.id.item_movie


    }

}