package com.codepath.kotlinprimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.codepath.kotlinprimer.data.quizTopics
import com.codepath.kotlinprimer.screens.CodePlaygroundScreen
import com.codepath.kotlinprimer.screens.DeepLessonScreen
import com.codepath.kotlinprimer.screens.LearnHubScreen
import com.codepath.kotlinprimer.screens.TopicQuizScreen
import com.codepath.kotlinprimer.screens.LessonScreen
import com.codepath.kotlinprimer.screens.QuestionScreen
import com.codepath.kotlinprimer.screens.ReferenceScreen
import com.codepath.kotlinprimer.screens.ResultsScreen
import com.codepath.kotlinprimer.screens.WelcomeScreen
import com.codepath.kotlinprimer.ui.theme.CodePathNavy
import com.codepath.kotlinprimer.ui.theme.CodePathTeal
import com.codepath.kotlinprimer.ui.theme.KotlinPrimerTheme
import com.codepath.kotlinprimer.ui.theme.SurfaceCard
import com.codepath.kotlinprimer.ui.theme.TextMuted

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Learn : Screen("learn")
    object Reference : Screen("reference")
    object DeepLesson : Screen("deep_lesson/{topicIndex}") {
        fun createRoute(index: Int) = "deep_lesson/$index"
    }
    object TopicQuiz : Screen("topic_quiz/{topicIndex}") {
        fun createRoute(index: Int) = "topic_quiz/$index"
    }
    object Lesson : Screen("lesson/{topicIndex}") {
        fun createRoute(index: Int) = "lesson/$index"
    }
    object Question : Screen("question/{topicIndex}") {
        fun createRoute(index: Int) = "question/$index"
    }
    object Results : Screen("results")
    object Code : Screen("code")
}

private val bottomNavRoutes = setOf(Screen.Home.route, Screen.Learn.route, Screen.Reference.route, Screen.Code.route)

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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in bottomNavRoutes

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            if (showBottomBar) {
                KotlinBottomNav(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                WelcomeScreen(
                    onStartClick = {
                        answers.clear()
                        navController.navigate(Screen.Lesson.createRoute(0))
                    }
                )
            }

            composable(Screen.Learn.route) {
                LearnHubScreen(
                    topics = quizTopics,
                    onTopicClick = { index ->
                        navController.navigate(Screen.DeepLesson.createRoute(index))
                    }
                )
            }

            composable(Screen.Reference.route) {
                ReferenceScreen()
            }

            composable(
                route = Screen.DeepLesson.route,
                arguments = listOf(navArgument("topicIndex") { type = NavType.IntType })
            ) { backStackEntry ->
                val topicIndex = backStackEntry.arguments?.getInt("topicIndex") ?: 0
                val topic = quizTopics[topicIndex]
                DeepLessonScreen(
                    topic = topic,
                    topicIndex = topicIndex,
                    totalTopics = quizTopics.size,
                    onBackClick = { navController.popBackStack() },
                    onTakeQuizClick = {
                        navController.navigate(Screen.TopicQuiz.createRoute(topicIndex))
                    }
                )
            }

            composable(
                route = Screen.TopicQuiz.route,
                arguments = listOf(navArgument("topicIndex") { type = NavType.IntType })
            ) { backStackEntry ->
                val topicIndex = backStackEntry.arguments?.getInt("topicIndex") ?: 0
                val topic = quizTopics[topicIndex]
                TopicQuizScreen(
                    topic = topic,
                    onBackClick = { navController.popBackStack() }
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
                                popUpTo(Screen.Home.route) { inclusive = false }
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
                            popUpTo(Screen.Home.route) { inclusive = false }
                        }
                    }
                )
            }

            composable(Screen.Code.route) {
                CodePlaygroundScreen()
            }
        }
    }
}

@Composable
private fun KotlinBottomNav(currentRoute: String?, onNavigate: (String) -> Unit) {
    NavigationBar(
        containerColor = SurfaceCard,
        contentColor = CodePathTeal
    ) {
        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,
            onClick = { onNavigate(Screen.Home.route) },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = CodePathNavy,
                selectedTextColor = CodePathTeal,
                indicatorColor = CodePathTeal,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted
            )
        )
        NavigationBarItem(
            selected = currentRoute == Screen.Learn.route,
            onClick = { onNavigate(Screen.Learn.route) },
            icon = { Icon(Icons.Filled.MenuBook, contentDescription = "Learn") },
            label = { Text("Learn") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = CodePathNavy,
                selectedTextColor = CodePathTeal,
                indicatorColor = CodePathTeal,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted
            )
        )
        NavigationBarItem(
            selected = currentRoute == Screen.Reference.route,
            onClick = { onNavigate(Screen.Reference.route) },
            icon = { Icon(Icons.Filled.Code, contentDescription = "Reference") },
            label = { Text("Reference") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = CodePathNavy,
                selectedTextColor = CodePathTeal,
                indicatorColor = CodePathTeal,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted
            )
        )
        NavigationBarItem(
            selected = currentRoute == Screen.Code.route,
            onClick = { onNavigate(Screen.Code.route) },
            icon = { Icon(Icons.Filled.Terminal, contentDescription = "Code") },
            label = { Text("Code") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = CodePathNavy,
                selectedTextColor = CodePathTeal,
                indicatorColor = CodePathTeal,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted
            )
        )
    }
}
