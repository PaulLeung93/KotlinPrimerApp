package com.codepath.kotlinprimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.codepath.kotlinprimer.data.quizTopics
import com.codepath.kotlinprimer.screens.LessonScreen
import com.codepath.kotlinprimer.screens.QuestionScreen
import com.codepath.kotlinprimer.screens.ResultsScreen
import com.codepath.kotlinprimer.screens.WelcomeScreen
import com.codepath.kotlinprimer.ui.theme.KotlinPrimerTheme

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Lesson : Screen("lesson/{topicIndex}") {
        fun createRoute(index: Int) = "lesson/$index"
    }
    object Question : Screen("question/{topicIndex}") {
        fun createRoute(index: Int) = "question/$index"
    }
    object Results : Screen("results")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinPrimerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    KotlinPrimerApp()
                }
            }
        }
    }
}

@Composable
fun KotlinPrimerApp() {
    val navController = rememberNavController()
    val answers = remember { mutableStateMapOf<Int, Boolean>() }

    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onStartClick = {
                    answers.clear()
                    navController.navigate(Screen.Lesson.createRoute(0))
                }
            )
        }

        composable(
            route = Screen.Lesson.route,
            arguments = listOf(navArgument("topicIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val topicIndex = backStackEntry.arguments?.getInt("topicIndex") ?: 0
            val topic = quizTopics[topicIndex]
            
            LessonScreen(
                topic = topic,
                totalTopics = quizTopics.size,
                onBackClick = { navController.popBackStack() },
                onReadyClick = { navController.navigate(Screen.Question.createRoute(topicIndex)) }
            )
        }

        composable(
            route = Screen.Question.route,
            arguments = listOf(navArgument("topicIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val topicIndex = backStackEntry.arguments?.getInt("topicIndex") ?: 0
            val topic = quizTopics[topicIndex]
            val isLastTopic = topicIndex == quizTopics.size - 1

            QuestionScreen(
                topic = topic,
                totalTopics = quizTopics.size,
                onBackClick = { navController.popBackStack() },
                onNextTopicClick = { isCorrect ->
                    answers[topicIndex] = isCorrect
                    if (isLastTopic) {
                        navController.navigate(Screen.Results.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = false }
                        }
                    } else {
                        navController.navigate(Screen.Lesson.createRoute(topicIndex + 1))
                    }
                },
                isLastTopic = isLastTopic
            )
        }

        composable(Screen.Results.route) {
            ResultsScreen(
                answers = answers,
                topics = quizTopics,
                onRetakeClick = {
                    answers.clear()
                    navController.navigate(Screen.Lesson.createRoute(0)) {
                        popUpTo(Screen.Welcome.route) { inclusive = false }
                    }
                }
            )
        }
    }
}
