package com.example.learnkotlinintermediate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnkotlinintermediate.adapter.MyAdapter
import com.example.learnkotlinintermediate.databinding.ActivityMainBinding
import com.example.learnkotlinintermediate.model.Post
import com.example.learnkotlinintermediate.repository.Respositry

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
        viewModel.pushPost2(2, 2, "My Title", "My Text")
        viewModel.myCustomPosts.observe(this) { response ->
           if (response.isSuccessful) {
               Log.d("Main", response.body().toString())
               Log.d("Main", response.code().toString())
               Log.d("Main", response.toString())
           } else {
               Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
           }
        }


//        viewModel.getPost()
//        viewModel.myResponse2.observe(this) { response ->
//            if (response.isSuccessful) {
//                Log.d("Response", response.body()?.userId.toString())
//                Log.d("Response", response.body()?.id.toString())
//                binding.textView.text = response.body()?.title
//                Log.d("Response", response.body()?.body.toString())
//            } else {
//                Log.d("Response", response.errorBody().toString())
//                binding.textView.text = response.code().toString()
//            }
//        }

//        val options: HashMap<String, String> = HashMap()
//        options["_short"] = "id"
//        options["_order"] = "desc"
//
//
//        binding.button.setOnClickListener {
//            val myNumber = binding.numberEdittext.text.toString()
//            viewModel.getCustomPosts2(Integer.parseInt(myNumber), options)
//            viewModel.myCustomPosts2.observe(this) { response ->
//                if (response.isSuccessful) {
//                    binding.textView.text = response.body().toString()
//                    response.body()?.forEach {
//                        Log.d("Response", it.userId.toString())
//                        Log.d("Response", it.id.toString())
//                        Log.d("Response", it.title)
//                        Log.d("Response", it.body)
//                        Log.d("Response", "------------------")
//                    }
//                } else {
//                    Log.d("Response", response.errorBody().toString())
//                    binding.textView.text = response.code().toString()
//                }
//            }
//        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}