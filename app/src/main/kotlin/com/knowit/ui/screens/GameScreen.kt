package com.knowit.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knowit.model.Category
import com.knowit.model.QuestionType
import com.knowit.ui.theme.*
import com.knowit.viewmodel.AnswerStatus
import com.knowit.viewmodel.GameState
import kotlin.random.Random

data class ConfettiParticle(
    val x: Float,
    val y: Float,
    val color: Color,
    val rotation: Float,
    val width: Float,
    val height: Float,
    val speedY: Float,
    val speedX: Float
)

@Composable
fun GameScreen(
    state: GameState,
    onMultipleChoiceAnswer: (String) -> Unit,
    onTypeInTextChange: (String) -> Unit,
    onTypeInSubmit: () -> Unit,
    onNext: () -> Unit
) {
    val currentQuestion = state.questions[state.currentQuestionIndex]
    val questionNumber = state.currentQuestionIndex + 1
    val totalQuestions = state.questions.size

    // Animated score counter
    val animatedScore by animateIntAsState(
        targetValue = state.score,
        animationSpec = tween(600, easing = EaseOutCubic),
        label = "scoreAnim"
    )

    // Progress bar animation
    val progress = questionNumber.toFloat() / totalQuestions.toFloat()
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(500, easing = EaseOutCubic),
        label = "progressAnim"
    )

    // Shake animation for wrong answer
    val shakeAnim = remember { Animatable(0f) }
    LaunchedEffect(state.answerStatus) {
        if (state.answerStatus == AnswerStatus.WRONG) {
            repeat(4) {
                shakeAnim.animateTo(
                    targetValue = if (it % 2 == 0) 12f else -12f,
                    animationSpec = tween(80, easing = LinearEasing)
                )
            }
            shakeAnim.animateTo(0f, animationSpec = tween(80))
        }
    }

    // Green glow for correct
    val glowAlpha by animateFloatAsState(
        targetValue = if (state.answerStatus == AnswerStatus.CORRECT) 0.35f else 0f,
        animationSpec = tween(400),
        label = "glowAlpha"
    )

    // Confetti particles state
    var confettiParticles by remember { mutableStateOf<List<ConfettiParticle>>(emptyList()) }
    var confettiActive by remember { mutableStateOf(false) }
    var confettiStartTime by remember { mutableStateOf(0L) }
    val confettiDuration = 1500L
    val confettiColors = listOf(
        CorrectGreen, KnowItPrimary, StreakGold, WrongRedLight, KnowItSecondary
    )

    LaunchedEffect(state.answerStatus) {
        if (state.answerStatus == AnswerStatus.CORRECT) {
            confettiParticles = List(30) {
                ConfettiParticle(
                    x = Random.nextFloat(),
                    y = Random.nextFloat() * 0.3f,
                    color = confettiColors[Random.nextInt(confettiColors.size)],
                    rotation = Random.nextFloat() * 360f,
                    width = Random.nextFloat() * 12f + 6f,
                    height = Random.nextFloat() * 8f + 4f,
                    speedY = Random.nextFloat() * 0.0004f + 0.0002f,
                    speedX = (Random.nextFloat() - 0.5f) * 0.0002f
                )
            }
            confettiActive = true
            confettiStartTime = System.currentTimeMillis()
        } else if (state.answerStatus == AnswerStatus.NONE) {
            confettiActive = false
            confettiParticles = emptyList()
        }
    }

    // Animate confetti
    var confettiTime by remember { mutableStateOf(0f) }
    LaunchedEffect(confettiActive) {
        if (confettiActive) {
            val startMs = System.currentTimeMillis()
            while (confettiActive && (System.currentTimeMillis() - startMs) < confettiDuration) {
                withFrameMillis { frameTimeMs ->
                    confettiTime = (frameTimeMs - startMs).toFloat()
                }
            }
            confettiActive = false
        }
    }

    val categoryColor = when (currentQuestion.category) {
        Category.SCIENCE -> ScienceColor
        Category.HISTORY -> HistoryColor
        Category.GEOGRAPHY -> GeographyColor
        Category.POP_CULTURE -> PopCultureColor
        Category.TECH -> TechColor
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(KnowItBackground, Color(0xFF1A1A2E))
                )
            )
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Top bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Question counter
                Text(
                    text = "Q$questionNumber / $totalQuestions",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Bold
                )

                // Streak indicator
                if (state.streak >= 2) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🔥", fontSize = 20.sp)
                        Text(
                            text = " ×${state.streak}",
                            style = MaterialTheme.typography.titleMedium,
                            color = StreakGold,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                // Animated score
                Text(
                    text = "$animatedScore pts",
                    style = MaterialTheme.typography.titleMedium,
                    color = KnowItSecondary,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            // Category pill + progress bar
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = currentQuestion.category.emoji,
                        fontSize = 14.sp
                    )
                    Text(
                        text = currentQuestion.category.displayName,
                        style = MaterialTheme.typography.labelLarge,
                        color = categoryColor,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Progress bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(Color.White.copy(alpha = 0.12f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(animatedProgress)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(3.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(categoryColor.copy(alpha = 0.7f), categoryColor)
                                )
                            )
                    )
                }
            }

            // Question card with shake + glow effects
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer { translationX = shakeAnim.value }
            ) {
                // Green glow overlay
                if (glowAlpha > 0f) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        CorrectGreen.copy(alpha = glowAlpha),
                                        Color.Transparent
                                    )
                                ),
                                shape = RoundedCornerShape(20.dp)
                            )
                    )
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = KnowItCard),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Question type badge
                        Surface(
                            color = categoryColor.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = if (currentQuestion.type == QuestionType.MULTIPLE_CHOICE)
                                    "Multiple Choice" else "Type In",
                                style = MaterialTheme.typography.labelLarge,
                                color = categoryColor,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = currentQuestion.questionText,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            lineHeight = 28.sp
                        )
                    }
                }
            }

            // Answer section
            when (currentQuestion.type) {
                QuestionType.MULTIPLE_CHOICE -> {
                    MultipleChoiceSection(
                        options = state.shuffledOptions,
                        correctAnswer = currentQuestion.correctAnswer,
                        selectedOption = state.selectedOption,
                        answerStatus = state.answerStatus,
                        onOptionSelected = onMultipleChoiceAnswer
                    )
                }
                QuestionType.TYPE_IN -> {
                    TypeInSection(
                        text = state.typeInText,
                        onTextChange = onTypeInTextChange,
                        onSubmit = onTypeInSubmit,
                        isSubmitted = state.answerStatus != AnswerStatus.NONE
                    )
                }
            }

            // Feedback row
            if (state.answerStatus != AnswerStatus.NONE) {
                FeedbackRow(
                    answerStatus = state.answerStatus,
                    correctAnswer = currentQuestion.correctAnswer,
                    pointsAwarded = state.lastPointsAwarded,
                    streak = state.streak,
                    onNext = onNext
                )
            }
        }

        // Confetti overlay
        if (confettiActive && confettiParticles.isNotEmpty()) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val elapsedFraction = (confettiTime / confettiDuration).coerceIn(0f, 1f)
                confettiParticles.forEach { particle ->
                    val currentY = particle.y + particle.speedY * confettiTime
                    val currentX = particle.x + particle.speedX * confettiTime
                    val alpha = (1f - elapsedFraction * 1.2f).coerceIn(0f, 1f)

                    if (alpha > 0f && currentY < 1.2f) {
                        val screenX = currentX * size.width
                        val screenY = currentY * size.height
                        rotate(
                            degrees = particle.rotation + elapsedFraction * 180f,
                            pivot = Offset(screenX, screenY)
                        ) {
                            drawRect(
                                color = particle.color.copy(alpha = alpha),
                                topLeft = Offset(
                                    screenX - particle.width / 2,
                                    screenY - particle.height / 2
                                ),
                                size = androidx.compose.ui.geometry.Size(
                                    particle.width,
                                    particle.height
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MultipleChoiceSection(
    options: List<String>,
    correctAnswer: String,
    selectedOption: String?,
    answerStatus: AnswerStatus,
    onOptionSelected: (String) -> Unit
) {
    val revealed = answerStatus != AnswerStatus.NONE

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // 2×2 grid
        val chunked = options.chunked(2)
        chunked.forEach { rowOptions ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowOptions.forEach { option ->
                    val isCorrect = option == correctAnswer
                    val isSelected = option == selectedOption

                    val containerColor by animateColorAsState(
                        targetValue = when {
                            !revealed -> KnowItCard
                            isCorrect -> CorrectGreen.copy(alpha = 0.25f)
                            isSelected && !isCorrect -> WrongRed.copy(alpha = 0.25f)
                            else -> KnowItCard.copy(alpha = 0.5f)
                        },
                        animationSpec = tween(300),
                        label = "optionBg_$option"
                    )
                    val borderColor by animateColorAsState(
                        targetValue = when {
                            !revealed -> Color.White.copy(alpha = 0.15f)
                            isCorrect -> CorrectGreen
                            isSelected && !isCorrect -> WrongRed
                            else -> Color.White.copy(alpha = 0.08f)
                        },
                        animationSpec = tween(300),
                        label = "optionBorder_$option"
                    )
                    val textColor by animateColorAsState(
                        targetValue = when {
                            !revealed -> Color.White
                            isCorrect -> CorrectGreenLight
                            isSelected && !isCorrect -> WrongRedLight
                            else -> Color.White.copy(alpha = 0.4f)
                        },
                        animationSpec = tween(300),
                        label = "optionText_$option"
                    )

                    Button(
                        onClick = { if (!revealed) onOptionSelected(option) },
                        modifier = Modifier
                            .weight(1f)
                            .height(72.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = containerColor,
                            disabledContainerColor = containerColor
                        ),
                        enabled = !revealed,
                        border = androidx.compose.foundation.BorderStroke(
                            width = if (revealed && (isCorrect || isSelected)) 2.dp else 1.dp,
                            color = borderColor
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = textColor,
                            textAlign = TextAlign.Center,
                            lineHeight = 18.sp
                        )
                    }
                }
                // Fill empty slot if odd number
                if (rowOptions.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun TypeInSection(
    text: String,
    onTextChange: (String) -> Unit,
    onSubmit: () -> Unit,
    isSubmitted: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        OutlinedTextField(
            value = text,
            onValueChange = { if (!isSubmitted) onTextChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Your answer") },
            readOnly = isSubmitted,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { if (!isSubmitted) onSubmit() }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = KnowItPrimary,
                unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                focusedLabelColor = KnowItSecondary,
                unfocusedLabelColor = Color.White.copy(alpha = 0.5f),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = KnowItSecondary,
                disabledTextColor = Color.White.copy(alpha = 0.6f)
            ),
            shape = RoundedCornerShape(14.dp)
        )

        if (!isSubmitted) {
            Button(
                onClick = onSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = KnowItPrimary),
                enabled = text.isNotBlank()
            ) {
                Text(
                    text = "Submit",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun FeedbackRow(
    answerStatus: AnswerStatus,
    correctAnswer: String,
    pointsAwarded: Int,
    streak: Int,
    onNext: () -> Unit
) {
    val isCorrect = answerStatus == AnswerStatus.CORRECT

    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isCorrect)
                CorrectGreen.copy(alpha = 0.15f)
            else
                WrongRed.copy(alpha = 0.15f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                if (isCorrect) {
                    val bonusText = if (streak >= 2) " 🔥" else ""
                    Text(
                        text = "+$pointsAwarded pts!$bonusText",
                        style = MaterialTheme.typography.titleMedium,
                        color = CorrectGreenLight,
                        fontWeight = FontWeight.ExtraBold
                    )
                    if (streak >= 2) {
                        Text(
                            text = "Streak ×$streak",
                            style = MaterialTheme.typography.bodyMedium,
                            color = StreakGold
                        )
                    }
                } else {
                    Text(
                        text = "Wrong!",
                        style = MaterialTheme.typography.titleMedium,
                        color = WrongRedLight,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "Answer: $correctAnswer",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = onNext,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isCorrect) CorrectGreen else NeutralGray
                )
            ) {
                Text(
                    text = "Next ›",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
