package com.example.learnkotlinintermediate.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnkotlinintermediate.adapter.MyAdapter
import com.example.learnkotlinintermediate.databinding.ActivityMainBinding
import com.example.learnkotlinintermediate.repository.Respositry
import com.example.learnkotlinintermediate.viewmodel.MainViewModel
import com.example.learnkotlinintermediate.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private val myAdapter by lazy { MyAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupRecyclerView()

        val repository = Respositry()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getPost("123456789")
        viewModel.myCustomPosts.observe(this) { response ->
           if (response.isSuccessful) {
               Log.d("Main", response.body().toString())
               Log.d("Main", response.code().toString())
               Log.d("Main", response.headers().toString())
           } else {
               Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
           }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}