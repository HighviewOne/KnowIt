package com.knowit.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knowit.ui.theme.*
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

    // Staggered emoji entrance
    val emojis = listOf("🔬", "📜", "🌍", "🎬", "💻")
    val emojiVisible = remember { mutableStateListOf(*Array(emojis.size) { false }) }

    LaunchedEffect(Unit) {
        emojis.indices.forEach { i ->
            delay(200L * i)
            emojiVisible[i] = true
        }
    }

    // Play button press scale
    var buttonPressed by remember { mutableStateOf(false) }
    val buttonScale by animateFloatAsState(
        targetValue = if (buttonPressed) 0.93f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "buttonScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(KnowItBackground, Color(0xFF1A1A2E))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = "KnowIt",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.ExtraBold,
                color = KnowItSecondary,
                modifier = Modifier
                    .scale(titleScale)
                    .semantics { contentDescription = "KnowIt - Test Your Knowledge" }
            )

            Text(
                text = "Test Your Knowledge",
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
                emojis.forEachIndexed { index, emoji ->
                    val scale by animateFloatAsState(
                        targetValue = if (emojiVisible[index]) 1f else 0f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        ),
                        label = "emoji_$index"
                    )
                    Text(
                        text = emoji,
                        fontSize = 32.sp,
                        modifier = Modifier
                            .scale(scale)
                            .semantics { contentDescription = when(emoji) {
                                "🔬" -> "Science"
                                "📜" -> "History"
                                "🌍" -> "Geography"
                                "🎬" -> "Movies"
                                "💻" -> "Technology"
                                else -> emoji
                            }}
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // High score display
            if (highScore > 0) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = KnowItCard),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.semantics { contentDescription = "Best score: $highScore points" }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)
                    ) {
                        Text(
                            text = "🏆 Best Score",
                            style = MaterialTheme.typography.labelLarge,
                            color = StreakGold
                        )
                        Text(
                            text = "$highScore pts",
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
                onClick = {
                    buttonPressed = true
                    onPlay()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .scale(buttonScale)
                    .semantics { contentDescription = "Play game" },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = KnowItPrimary
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    text = "Play Now  ▶",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Subtitle info
            Text(
                text = "20 questions • 5 categories\n+10 pts per correct • streak bonuses!",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        }
    }
}
