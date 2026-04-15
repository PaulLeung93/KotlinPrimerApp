package com.codepath.kotlinprimer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codepath.kotlinprimer.data.PASS_THRESHOLD
import com.codepath.kotlinprimer.data.QuizTopic
import com.codepath.kotlinprimer.ui.theme.CodePathNavy
import com.codepath.kotlinprimer.ui.theme.CodePathTeal
import com.codepath.kotlinprimer.ui.theme.CorrectGreen
import com.codepath.kotlinprimer.ui.theme.WrongRed
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Spread
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

@Composable
fun ResultsScreen(
    answers: Map<Int, Boolean>,
    topics: List<QuizTopic>,
    onRetakeClick: () -> Unit
) {
    val totalQuestions = topics.size
    val correctAnswers = answers.values.count { it }
    val missedTopics = topics.filter { (answers[it.number - 1] == false) || (answers[it.number - 1] == null) }
    val isPassed = correctAnswers >= PASS_THRESHOLD

    Box(modifier = Modifier.fillMaxSize()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 48.dp, bottom = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Score Ring
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier.size(160.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                progress = { correctAnswers.toFloat() / totalQuestions },
                modifier = Modifier.fillMaxSize(),
                color = if (isPassed) CodePathTeal else WrongRed,
                strokeWidth = 12.dp,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$correctAnswers/$totalQuestions",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (isPassed) "PASSED" else "FAILED",
                    color = if (isPassed) CodePathTeal else WrongRed,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    letterSpacing = 2.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = if (isPassed) "Great work, student!" else "Keep practicing!",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (isPassed) "You've mastered the Kotlin basics. Your logic flow and syntax structure are looking sharp."
            else "You didn't quite hit the $PASS_THRESHOLD/$totalQuestions threshold. Review the missed topics and try again.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp),
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Breakdown Cards
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ScoreCard(modifier = Modifier.weight(1f), title = "$correctAnswers", subtitle = "Correct", color = CorrectGreen)
            ScoreCard(modifier = Modifier.weight(1f), title = "${totalQuestions - correctAnswers}", subtitle = "Missed", color = WrongRed)
            ScoreCard(modifier = Modifier.weight(1f), title = "${(correctAnswers * 100) / totalQuestions}%", subtitle = "Score", color = MaterialTheme.colorScheme.onSurface)
        }

        if (missedTopics.isNotEmpty()) {
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "Missed Topics",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                missedTopics.forEach { topic ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(CodePathNavy),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "${topic.number}", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = topic.title,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Actions
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isPassed) {
                val primaryGradient = Brush.linearGradient(
                    colors = listOf(Color(0xFF42E5B0), CodePathTeal)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(primaryGradient)
                ) {
                    Button(
                        onClick = { /* In real app, might launch implicit intent or show dialog */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Submit my prework →",
                            color = CodePathNavy,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                TextButton(onClick = onRetakeClick) {
                    Text("Retake to improve", color = CodePathTeal, fontWeight = FontWeight.Bold)
                }
            } else {
                Button(
                    onClick = onRetakeClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CodePathTeal),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Retake the primer",
                        color = CodePathNavy,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Take a screenshot or push your latest code to GitHub to track your prework progress.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }

    if (isPassed) {
        KonfettiView(
            modifier = Modifier.fillMaxSize(),
            parties = listOf(
                Party(
                    angle = Angle.BOTTOM,
                    spread = Spread.ROUND,
                    speed = 2f,
                    maxSpeed = 8f,
                    damping = 0.95f,
                    colors = listOf(0x42E5B0, 0x1A3A5C, 0xFFFFFF, 0x4FC3F7),
                    emitter = Emitter(duration = 3, TimeUnit.SECONDS).perSecond(60),
                    position = Position.Relative(0.0, 0.0).between(Position.Relative(1.0, 0.0))
                )
            )
        )
    }
    } // end Box
}

@Composable
fun ScoreCard(modifier: Modifier = Modifier, title: String, subtitle: String, color: Color) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = color,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
