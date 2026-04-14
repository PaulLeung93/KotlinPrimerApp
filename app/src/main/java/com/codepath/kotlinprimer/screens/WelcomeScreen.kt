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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.codepath.kotlinprimer.ui.theme.CodePathNavy
import com.codepath.kotlinprimer.ui.theme.CodePathTeal

@Composable
fun WelcomeScreen(onStartClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 48.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Logo Mark
        Box(
            modifier = Modifier
                .height(56.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(CodePathNavy)
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CodePath",
                color = CodePathTeal,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "Kotlin Language Primer",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            lineHeight = 44.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Master the syntax and functional paradigms of modern Android development through an architectural lens.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 24.sp
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // 4 Info Cards Grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoCard(modifier = Modifier.weight(1f), title = "12", subtitle = "Topics")
            InfoCard(modifier = Modifier.weight(1f), title = "12", subtitle = "Questions")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoCard(modifier = Modifier.weight(1f), title = "10/12", subtitle = "Pass Rate")
            InfoCard(modifier = Modifier.weight(1f), title = "Unlimited", subtitle = "Retakes")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Curriculum details
        Text(
            text = "The Curriculum",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "This primer covers the fundamental blocks of Kotlin, from null-safety and data classes to advanced coroutine patterns and flow-based architecture. Designed for performance-oriented engineers.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Total Estimated Time: 45m",
            style = MaterialTheme.typography.labelMedium,
            color = CodePathTeal
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Gradient Button
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
                onClick = onStartClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Start Primer",
                    color = CodePathNavy,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun InfoCard(modifier: Modifier = Modifier, title: String, subtitle: String) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
