package com.funin.todo.ui.sign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.funin.base.autoCleared
import com.funin.todo.databinding.FragmentSignUpBinding
import com.funin.todo.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SignUpFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentSignUpBinding>()
    private val viewModel by viewModels<SignViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSetupViews(view: View) {
        binding.userEmailEditTextView.doOnTextChanged { text, _, _, _ ->
            viewModel.setEmailField(text.toString())
        }
        binding.userNicknameEditTextView.doOnTextChanged { text, _, _, _ ->
            viewModel.setNicknameField(text.toString())
        }
        binding.userPasswordEditTextView.doOnTextChanged { text, _, _, _ ->
            viewModel.setPasswordField(text.toString())
        }
        binding.userRePasswordEditTextView.doOnTextChanged { text, _, _, _ ->
            viewModel.setRewritePasswordField(text.toString())
        }
    }

    override fun onBindViewModelsOnViewCreated() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.isSignUpButtonEnabled.collectLatest {
                binding.signUpButton.isEnabled = it
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.isLoading.collectLatest {
                binding.loadingView.isVisible = it
            }
        }
    }
}