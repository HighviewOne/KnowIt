package com.knowit

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.knowit.ui.screens.GameScreen
import com.knowit.ui.screens.HomeScreen
import com.knowit.ui.screens.ResultScreen
import com.knowit.ui.theme.KnowItTheme
import com.knowit.viewmodel.GamePhase
import com.knowit.viewmodel.GameViewModel
import com.knowit.viewmodel.GameViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // The UI is always dark, so force light system-bar icons regardless of system theme.
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        )
        val factory = GameViewModelFactory(this, applicationContext)
        setContent {
            KnowItTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val viewModel: GameViewModel = viewModel(factory = factory)
                    val state by viewModel.state.collectAsStateWithLifecycle()

                    BackHandler(enabled = state.phase != GamePhase.HOME) {
                        viewModel.goHome()
                    }

                    when (state.phase) {
                        GamePhase.HOME -> HomeScreen(
                            highScore = state.highScore,
                            onPlay = { viewModel.startGame() }
                        )
                        GamePhase.PLAYING -> GameScreen(
                            state = state,
                            onMultipleChoiceAnswer = { viewModel.submitMultipleChoiceAnswer(it) },
                            onTypeInTextChange = { viewModel.updateTypeInText(it) },
                            onTypeInSubmit = { viewModel.submitTypeInAnswer() },
                            onNext = { viewModel.advanceToNextQuestion() }
                        )
                        GamePhase.GAME_OVER -> ResultScreen(
                            state = state,
                            onPlayAgain = { viewModel.startGame() }
                        )
                    }
                }
            }
        }
    }
}
