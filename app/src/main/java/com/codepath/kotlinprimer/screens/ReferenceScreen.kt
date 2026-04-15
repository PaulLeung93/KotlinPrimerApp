package com.codepath.kotlinprimer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codepath.kotlinprimer.ui.theme.CodeBg
import com.codepath.kotlinprimer.ui.theme.CodePathTeal
import com.codepath.kotlinprimer.ui.theme.CodeText

private data class ReferenceSection(val title: String, val code: String)

private val referenceSections = listOf(
    ReferenceSection(
        title = "Variables",
        code = "val name: String = \"Alex\"  // read-only\nvar score: Int = 0         // mutable\n\n// Type inference — no annotation needed\nval greeting = \"Hello\"     // String\nval count    = 42          // Int\n\nconst val MAX = 100        // compile-time constant"
    ),
    ReferenceSection(
        title = "Functions",
        code = "// Standard function\nfun greet(name: String): String {\n    return \"Hello, \$name\"\n}\n\n// Single-expression shorthand\nfun double(n: Int) = n * 2\n\n// Default & named parameters\nfun log(msg: String, tag: String = \"APP\") {\n    println(\"\$tag: \$msg\")\n}\nlog(\"Ready\", tag = \"BOOT\")"
    ),
    ReferenceSection(
        title = "String Templates",
        code = "val player = \"Jordan\"\nval score  = 85\n\nprintln(\"Welcome, \$player!\")          // simple\nprintln(\"Next: \${score + 1}\")          // expression\n\n// Multi-line string\nval msg = \"\"\"\n    Player : \$player\n    Score  : \$score\n\"\"\".trimIndent()"
    ),
    ReferenceSection(
        title = "Control Flow",
        code = "// if as expression\nval rank = if (score >= 90) \"Expert\" else \"Beginner\"\n\n// when expression\nval label = when (score) {\n    in 90..100 -> \"A\"\n    in 80..89  -> \"B\"\n    in 70..79  -> \"C\"\n    else       -> \"F\"\n}\n\n// when without subject\nwhen {\n    score > 90 -> println(\"Top!\")\n    score > 70 -> println(\"Good\")\n    else       -> println(\"Keep going\")\n}"
    ),
    ReferenceSection(
        title = "Null Safety",
        code = "var name: String  = \"Alex\"  // non-null\nvar nick: String? = null    // nullable\n\n// Safe call — returns null instead of crashing\nprintln(nick?.length)\n\n// Elvis — fallback if null\nval display = nick ?: \"Guest\"\n\n// Safe cast\nval len = (nick as? String)?.length\n\n// Non-null assertion — use sparingly!\nval forced = nick!!"
    ),
    ReferenceSection(
        title = "Collections",
        code = "// Read-only\nval items = listOf(\"A\", \"B\", \"C\")\nval map   = mapOf(\"a\" to 1, \"b\" to 2)\n\n// Mutable\nval list = mutableListOf<Int>()\nlist.add(42)\n\n// Common operations\nval evens   = items.filter { it != \"B\" }\nval lengths = items.map { it.length }\nval found   = items.find { it == \"A\" }\nval sorted  = list.sortedDescending()"
    ),
    ReferenceSection(
        title = "Loops",
        code = "// for with range (inclusive)\nfor (i in 1..5) print(\"\$i \")\n\n// for with until (exclusive end)\nfor (i in 0 until 5) print(\"\$i \")\n\n// downTo with step\nfor (i in 10 downTo 1 step 2) print(\"\$i \")\n\n// iterate collection with index\nfor ((index, value) in items.withIndex()) {\n    println(\"\$index: \$value\")\n}\n\n// while\nvar x = 0\nwhile (x < 3) { print(\"\$x \"); x++ }"
    ),
    ReferenceSection(
        title = "Classes & Data Classes",
        code = "// Regular class\nclass Dog(val name: String) {\n    fun speak() = \"Woof! I'm \$name\"\n}\n\n// Data class — equals/toString/copy generated\ndata class User(val name: String, val score: Int = 0)\n\nval user    = User(\"Alex\", 85)\nval updated = user.copy(score = 100)  // immutable update\nval (name, score) = user              // destructuring\n\n// Inheritance — classes are final by default\nopen class Animal(val name: String)\nclass Cat(name: String) : Animal(name)"
    )
)

@Composable
fun ReferenceScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 48.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(
                text = "QUICK REFERENCE",
                color = CodePathTeal,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Kotlin\nCheat Sheet",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                lineHeight = 38.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Syntax at a glance — no fluff",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            referenceSections.forEach { section ->
                ReferenceEntry(section)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ReferenceEntry(section: ReferenceSection) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        // Section header
        Text(
            text = section.title.uppercase(),
            color = CodePathTeal,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )

        // Code block
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                .background(CodeBg)
                .padding(16.dp)
        ) {
            Text(
                text = section.code,
                fontFamily = FontFamily.Monospace,
                color = CodeText,
                fontSize = 13.sp,
                lineHeight = 22.sp
            )
        }
    }
}
