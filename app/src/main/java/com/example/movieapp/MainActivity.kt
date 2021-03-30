package com.example.movieapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.navigation.MovieFragment
import com.example.movieapp.navigation.TvShowFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),BottomNavigationView.OnNavigationItemSelectedListener {


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.item_movie ->{

                var movieFragment =
                    MovieFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content,movieFragment).commit()
                return true
            }
            R.id.item_tvshow->{
                var tvFragment = TvShowFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content,tvFragment).commit()
                return true
            }

        }
        return false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_nav.setOnNavigationItemSelectedListener(this)

        bottom_nav.selectedItemId = R.id.item_movie


    }

}