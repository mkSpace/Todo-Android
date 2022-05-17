package com.funin.todo.ui.sign

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.funin.base.extensions.showToast
import com.funin.todo.R
import com.funin.todo.databinding.ActivitySignBinding
import com.funin.todo.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull

@AndroidEntryPoint
class SignActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivitySignBinding

    private val viewModel by viewModels<SignViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavController()
        bindViewModels()
    }

    private fun setupNavController() {
        navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                as NavHostFragment).navController
    }

    private fun bindViewModels() {
        lifecycleScope.launchWhenCreated {
            viewModel.errorMessage
                .filterNotNull()
                .collectLatest(::showToast)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.isLoading.collectLatest {
                binding.loadingView.isVisible = it
            }
        }

    }
}