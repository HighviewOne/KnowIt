package com.knowit.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knowit.R
import com.knowit.model.Scoring
import com.knowit.ui.theme.*
import com.knowit.viewmodel.GameState

private fun letterGrade(score: Int, maxScore: Int): String {
    val pct = score.toFloat() / maxScore.toFloat()
    return when {
        pct >= 0.97f -> "A+"
        pct >= 0.93f -> "A"
        pct >= 0.90f -> "A-"
        pct >= 0.87f -> "B+"
        pct >= 0.83f -> "B"
        pct >= 0.80f -> "B-"
        pct >= 0.77f -> "C+"
        pct >= 0.70f -> "C"
        pct >= 0.60f -> "D"
        else -> "F"
    }
}

private fun gradeColor(grade: String): Color = when {
    grade.startsWith("A") -> CorrectGreen
    grade.startsWith("B") -> ScienceColor
    grade.startsWith("C") -> StreakGold
    grade.startsWith("D") -> WrongRedLight
    else -> WrongRed
}

@Composable
fun ResultScreen(
    state: GameState,
    onPlayAgain: () -> Unit
) {
    val maxScore = Scoring.maxScore(state.questions.size).coerceAtLeast(1)
    val grade = letterGrade(state.score, maxScore)
    val accuracy = if (state.questions.isNotEmpty()) {
        (state.correctCount.toFloat() / state.questions.size.toFloat() * 100).toInt()
    } else 0
    val isNewHighScore = state.isNewHighScore

    // Grade bounce entrance
    var gradeVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { gradeVisible = true }
    val gradeScale by animateFloatAsState(
        targetValue = if (gradeVisible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "gradeScale"
    )

    // Animated score counter
    val animatedScore by animateIntAsState(
        targetValue = state.score,
        animationSpec = tween(1000, easing = EaseOutCubic),
        label = "resultScore"
    )

    // High score pulse
    val infiniteTransition = rememberInfiniteTransition(label = "highScorePulse")
    val highScoreAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "highScoreAlpha"
    )

    val gradeDescription = stringResource(R.string.result_grade_description, grade)
    val newHighScoreDescription = stringResource(R.string.result_new_high_score_description)
    val scoreDescription = stringResource(R.string.result_score_description, state.score, accuracy)
    val playAgainDescription = stringResource(R.string.result_play_again_description)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .screenBackground()
            .safeDrawingPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.result_title),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            // Grade letter
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .scale(gradeScale)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                gradeColor(grade).copy(alpha = 0.3f),
                                gradeColor(grade).copy(alpha = 0.1f)
                            )
                        ),
                        shape = RoundedCornerShape(60.dp)
                    )
                    .semantics { contentDescription = gradeDescription },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = grade,
                    fontSize = 52.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = gradeColor(grade)
                )
            }

            // New high score banner
            if (isNewHighScore) {
                Surface(
                    color = AmberPulse.copy(alpha = highScoreAlpha * 0.25f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.semantics { contentDescription = newHighScoreDescription }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "🏆", fontSize = 20.sp)
                        Text(
                            text = stringResource(R.string.result_new_high_score),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = AmberPulse.copy(alpha = highScoreAlpha)
                        )
                    }
                }
            }

            // Score card
            Card(
                colors = CardDefaults.cardColors(containerColor = KnowItCard),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = scoreDescription }
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Final score
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.result_final_score),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Text(
                            text = stringResource(R.string.points, animatedScore),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = KnowItSecondary
                        )
                    }

                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f))

                    // Correct answers
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.result_correct),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Text(
                            text = stringResource(R.string.result_correct_count, state.correctCount, state.questions.size),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    // Accuracy
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.result_accuracy),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Text(
                            text = stringResource(R.string.result_accuracy_value, accuracy),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = when {
                                accuracy >= 80 -> CorrectGreenLight
                                accuracy >= 60 -> StreakGold
                                else -> WrongRedLight
                            }
                        )
                    }

                    // Best score
                    if (state.highScore > 0) {
                        HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(R.string.best_score),
                                style = MaterialTheme.typography.bodyLarge,
                                color = StreakGold.copy(alpha = 0.8f)
                            )
                            Text(
                                text = stringResource(R.string.points, state.highScore),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = StreakGold
                            )
                        }
                    }
                }
            }

            // Motivational message
            Text(
                text = stringResource(
                    when {
                        accuracy == 100 -> R.string.result_message_perfect
                        accuracy >= 80 -> R.string.result_message_excellent
                        accuracy >= 60 -> R.string.result_message_good
                        accuracy >= 40 -> R.string.result_message_ok
                        else -> R.string.result_message_low
                    }
                ),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            // Play Again button
            Button(
                onClick = onPlayAgain,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .semantics { contentDescription = playAgainDescription },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = KnowItPrimary),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.result_play_again),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
