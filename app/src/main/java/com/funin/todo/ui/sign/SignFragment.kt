package com.funin.todo.ui.sign

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.funin.base.autoCleared
import com.funin.todo.R
import com.funin.todo.databinding.FragmentSignBinding
import com.funin.todo.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentSignBinding>()
    private val viewModel by viewModels<SignViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        val signUpText = getString(R.string.sign_up)
        val signUpFullText = getString(R.string.sign_up_text, signUpText)
        val signUpIndex = signUpFullText.indexOf(signUpText)
        SpannableStringBuilder(signUpFullText).apply {
            setSpan(
                ForegroundColorSpan(resources.getColor(R.color.link, null)),
                signUpIndex,
                signUpIndex + signUpText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                StyleSpan(Typeface.BOLD),
                signUpIndex,
                signUpIndex + signUpText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                object : ClickableSpan() {
                    override fun onClick(p0: View) {
                        findNavController().navigate(R.id.fragment_sign_up)
                    }
                },
                signUpIndex,
                signUpIndex + signUpText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }.let {
            binding.signUpTextField.movementMethod = LinkMovementMethod.getInstance()
            binding.signUpTextField.setText(it, TextView.BufferType.SPANNABLE)
        }

        binding.signLoginButton.setOnClickListener { viewModel.signIn() }
    }
}