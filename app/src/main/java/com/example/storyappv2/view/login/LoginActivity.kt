package com.example.storyappv2.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.storyappv2.R
import com.example.storyappv2.databinding.ActivityLoginBinding
import com.example.storyappv2.utils.isLoading
import com.example.storyappv2.view.signup.SignupActivity
import com.example.storyappv2.view.story.StoryActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    private var job: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        binding.loginButton.setOnClickListener { login() }
        playAnimation()
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun login() {

        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        isLoading(true, binding.loginProgressBar)

        lifecycleScope.launchWhenResumed {
            if (job.isActive) job.cancel()
            job = launch {
                loginViewModel.login(email, password).collect { result ->
                    result.onSuccess { response ->
                        response.loginResult?.let { user ->
                            loginViewModel.setUser(user)
                            startActivity(Intent(this@LoginActivity, StoryActivity::class.java))
                            finish()
                            Toast.makeText(
                                this@LoginActivity,
                                getString(R.string.loginSuccess),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    result.onFailure {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.loginError),
                            Toast.LENGTH_SHORT
                        ).show()
                        isLoading(false, binding.loginProgressBar)
                    }
                }
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(200)
        val message =
            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(200)
        val email = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(200)
        val emailEdit =
            ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 1f).setDuration(200)
        val password =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(200)
        val passwordEdit =
            ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 1f).setDuration(200)

        AnimatorSet().apply {
            playSequentially(title, message, email, emailEdit, password, passwordEdit)
            startDelay = 500
        }.start()
    }
}