package com.example.fallingword.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fallingword.MyApplication
import com.example.fallingword.R
import com.example.fallingword.databinding.FragmentWelcomeBinding
import com.example.fallingword.di.ViewModelProviderFactory
import com.example.fallingword.presentation.viewmodel.WelcomeViewModel
import javax.inject.Inject

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private val viewModel: WelcomeViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MyApplication).provideAppComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeOnViewState()
        binding.startButton.setOnClickListener {
            startTheGame()
        }
    }

    override fun onResume() {
        viewModel.populateHighScore()
        super.onResume()
    }

    private fun observeOnViewState() {
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            binding.highScoreTextView.text = getString(R.string.high_score, viewState.highScore)
        }
    }

    private fun startTheGame() {
        val action = WelcomeFragmentDirections.startTheGameAction()
        findNavController().navigate(action)
    }
}