package com.codepath.kotlinprimer.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codepath.kotlinprimer.data.QuizOption
import com.codepath.kotlinprimer.data.QuizTopic
import com.codepath.kotlinprimer.ui.theme.CodePathNavy
import com.codepath.kotlinprimer.ui.theme.CodePathTeal
import com.codepath.kotlinprimer.ui.theme.CorrectGreen
import com.codepath.kotlinprimer.ui.theme.CorrectGreenBg
import com.codepath.kotlinprimer.ui.theme.WrongRed
import com.codepath.kotlinprimer.ui.theme.WrongRedBg

@Composable
fun QuestionScreen(
    topic: QuizTopic,
    totalTopics: Int,
    onBackClick: () -> Unit,
    onNextTopicClick: (Boolean) -> Unit, // passes whether they got it right
    isLastTopic: Boolean
) {
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    val isAnswered = selectedAnswer != null
    val isCorrect = selectedAnswer == topic.question.correctLabel

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 48.dp, bottom = 24.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
            ) {
                LinearProgressIndicator(
                    progress = { topic.number.toFloat() / totalTopics },
                    modifier = Modifier.fillMaxSize(),
                    color = CodePathTeal,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "${topic.number}/$totalTopics",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 16.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Topic Badge
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(CodePathTeal),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "${topic.number}", color = CodePathNavy, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = topic.question.type.name.replace("_", " "),
                    color = CodePathTeal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = topic.question.prompt,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp
            )

            if (topic.question.codeSnippet != null) {
                Spacer(modifier = Modifier.height(16.dp))
                CodeBlock(code = topic.question.codeSnippet)
            }

            Spacer(modifier = Modifier.height(32.dp))

            topic.question.options.forEach { option ->
                val isSelected = selectedAnswer == option.label
                AnswerOptionCard(
                    option = option,
                    isSelected = isSelected,
                    isAnswered = isAnswered,
                    isCorrect = isCorrect,
                    onClick = {
                        if (!isAnswered) {
                            selectedAnswer = option.label
                        }
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            AnimatedVisibility(
                visible = isAnswered,
                enter = fadeIn() + expandVertically()
            ) {
                Column {
                    FeedbackPanel(
                        isCorrect = isCorrect,
                        explanation = topic.question.explanation
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = { onNextTopicClick(isCorrect) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CodePathTeal),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            text = if (isLastTopic) "See my results" else "Next topic →",
                            color = CodePathNavy,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AnswerOptionCard(
    option: QuizOption,
    isSelected: Boolean,
    isAnswered: Boolean,
    isCorrect: Boolean,
    onClick: () -> Unit
) {
    val borderColor = when {
        !isAnswered && isSelected -> CodePathTeal
        isAnswered && isSelected && isCorrect -> CorrectGreen
        isAnswered && isSelected && !isCorrect -> WrongRed
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    val iconColor = when {
        isAnswered && isSelected && isCorrect -> CorrectGreen
        isAnswered && isSelected && !isCorrect -> WrongRed
        else -> Color.Transparent
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !isAnswered, onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(2.dp, borderColor)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option.label,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = option.text,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            if (isAnswered && isSelected) {
                Icon(
                    imageVector = if (isCorrect) Icons.Filled.Check else Icons.Filled.Clear,
                    contentDescription = null,
                    tint = iconColor
                )
            }
        }
    }
}

@Composable
fun FeedbackPanel(isCorrect: Boolean, explanation: String) {
    val bgColor = if (isCorrect) CorrectGreenBg else WrongRedBg
    val borderColor = if (isCorrect) CorrectGreen else WrongRed
    val label = if (isCorrect) "Excellent! Correct Answer" else "Not Quite"

    Card(
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        border = BorderStroke(1.dp, borderColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = label,
                color = borderColor,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = explanation,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 22.sp
            )
        }
    }
}
