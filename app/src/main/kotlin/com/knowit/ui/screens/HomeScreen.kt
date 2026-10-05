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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knowit.R
import com.knowit.model.Category
import com.knowit.model.Scoring
import com.knowit.ui.theme.*
import com.knowit.viewmodel.GameViewModel
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    highScore: Int,
    onPlay: () -> Unit
) {
    // Title pulse animation
    val infiniteTransition = rememberInfiniteTransition(label = "titlePulse")
    val titleScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "titleScale"
    )

    // Staggered category emoji entrance
    val categories = Category.entries
    val emojiVisible = remember { mutableStateListOf(*Array(categories.size) { false }) }

    LaunchedEffect(Unit) {
        categories.indices.forEach { i ->
            delay(200L * i)
            emojiVisible[i] = true
        }
    }
    val titleDescription = stringResource(R.string.home_title_description)
    val bestScoreDescription = stringResource(R.string.home_best_score_description, highScore)
    val playDescription = stringResource(R.string.home_play_description)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .screenBackground()
            .safeDrawingPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(32.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.ExtraBold,
                color = KnowItSecondary,
                modifier = Modifier
                    .scale(titleScale)
                    .semantics { contentDescription = titleDescription }
            )

            Text(
                text = stringResource(R.string.home_tagline),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Emoji strip with staggered animation
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                categories.forEachIndexed { index, category ->
                    val label = stringResource(category.labelRes)
                    val scale by animateFloatAsState(
                        targetValue = if (emojiVisible[index]) 1f else 0f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        ),
                        label = "emoji_$index"
                    )
                    Text(
                        text = category.emoji,
                        fontSize = 32.sp,
                        modifier = Modifier
                            .scale(scale)
                            .semantics { contentDescription = label }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // High score display
            if (highScore > 0) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = KnowItCard),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.semantics { contentDescription = bestScoreDescription }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.home_best_score_label),
                            style = MaterialTheme.typography.labelLarge,
                            color = StreakGold
                        )
                        Text(
                            text = stringResource(R.string.points, highScore),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = StreakGold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Play button
            Button(
                onClick = onPlay,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .semantics { contentDescription = playDescription },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = KnowItPrimary
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.home_play),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Subtitle info
            Text(
                text = stringResource(
                    R.string.home_rules,
                    GameViewModel.QUESTIONS_PER_GAME,
                    Category.entries.size,
                    Scoring.POINTS_PER_CORRECT
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        }
    }
}
