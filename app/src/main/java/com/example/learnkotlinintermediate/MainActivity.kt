package com.example.learnkotlinintermediate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.learnkotlinintermediate.databinding.ActivityMainBinding
import com.example.learnkotlinintermediate.repository.Respositry

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Respositry()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
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

        val options: HashMap<String, String> = HashMap()
        options["_short"] = "id"
        options["_order"] = "desc"


        binding.button.setOnClickListener {
            val myNumber = binding.numberEdittext.text.toString()
            viewModel.getCustomPosts2(Integer.parseInt(myNumber), options)
            viewModel.myCustomPosts2.observe(this) { response ->
                if (response.isSuccessful) {
                    binding.textView.text = response.body().toString()
                    response.body()?.forEach {
                        Log.d("Response", it.userId.toString())
                        Log.d("Response", it.id.toString())
                        Log.d("Response", it.title)
                        Log.d("Response", it.body)
                        Log.d("Response", "------------------")
                    }
                } else {
                    Log.d("Response", response.errorBody().toString())
                    binding.textView.text = response.code().toString()
                }
            }
        }
    }
}