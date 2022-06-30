package com.example.fallingword.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fallingword.MyApplication
import com.example.fallingword.R
import com.example.fallingword.databinding.FragmentGameBinding
import com.example.fallingword.di.ViewModelProviderFactory
import com.example.fallingword.presentation.viewmodel.GameViewModel
import com.example.fallingword.presentation.viewstate.GameViewState
import timber.log.Timber
import javax.inject.Inject

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private val viewModel: GameViewModel by viewModels { viewModelFactory }

    private lateinit var animation: Animation

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MyApplication).provideAppComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down)
        binding.spanishWordTextView.startAnimation(animation)

        binding.correctButton.setOnClickListener {
            viewModel.clickOnCorrect()
            binding.spanishWordTextView.startAnimation(animation)
        }
        binding.incorrectButton.setOnClickListener {
            viewModel.clickOnIncorrect()
            binding.spanishWordTextView.startAnimation(animation)
        }
        binding.playAgainButton.setOnClickListener {
            viewModel.getWords()
            binding.spanishWordTextView.startAnimation(animation)
        }
        observerOnViewState()

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.stopTimer()
                    findNavController().navigateUp()
                }
            }
        )
    }

    private fun observerOnViewState() {
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            binding.gamingGroup.isVisible = viewState is GameViewState.Gaming
            binding.lostGroup.isVisible = viewState is GameViewState.Lost
            binding.progressBar.isVisible = viewState is GameViewState.Loading
            when (viewState) {
                is GameViewState.Gaming -> {
                    binding.timerTextView.text = getString(
                        R.string.time,
                        viewState.secondsLeft
                    )
                    binding.englishWordTextView.text = viewState.englishWord
                    binding.spanishWordTextView.text = viewState.spanishWord
                    binding.highScoreTextView.text = getString(
                        R.string.gaming_high_score,
                        viewState.highScore
                    )
                    binding.currentScoreTextView.text = getString(
                        R.string.current_score,
                        viewState.currentScore
                    )
                }
                GameViewState.Loading -> {}
                is GameViewState.Lost -> {
                    binding.lostTitleTextView.text = getString(viewState.message)
                    binding.lostHighScoreTextView.text = getString(
                        R.string.high_score,
                        viewState.highScore
                    )
                }
            }
        }
    }
}