package com.codepath.kotlinprimer.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
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
        code = """
val name: String = "Alex"  // read-only (immutable reference)
var score: Int = 0          // mutable

// Type inference — compiler infers type from the value
val greeting = "Hello"     // String
val count    = 42          // Int
val pi       = 3.14        // Double
val active   = true        // Boolean

// Compile-time constant — must be top-level or in object
const val MAX_SCORE = 100

// Lazy initialization — computed only on first access
val result: String by lazy { expensiveComputation() }

// lateinit — non-null var initialized after declaration
lateinit var adapter: MyAdapter
        """.trimIndent()
    ),
    ReferenceSection(
        title = "Functions",
        code = """
// Standard function
fun greet(name: String): String {
    return "Hello, ${"$"}name"
}

// Single-expression shorthand
fun double(n: Int) = n * 2

// Default & named parameters
fun log(msg: String, tag: String = "APP") {
    println("${"$"}tag: ${"$"}msg")
}
log("Ready", tag = "BOOT")

// Varargs
fun sum(vararg nums: Int) = nums.sum()
sum(1, 2, 3, 4)

// Extension function — adds behavior to an existing type
fun String.shout() = this.uppercase() + "!"
println("hello".shout())   // HELLO!

// Higher-order function — takes a function as parameter
fun repeat(n: Int, action: (Int) -> Unit) {
    for (i in 0 until n) action(i)
}
repeat(3) { println("Step ${"$"}it") }

// Infix function
infix fun Int.times(str: String) = str.repeat(this)
println(3 times "ha ")   // ha ha ha
        """.trimIndent()
    ),
    ReferenceSection(
        title = "Lambdas & Higher-Order Functions",
        code = """
// Lambda syntax
val square: (Int) -> Int = { x -> x * x }
val add: (Int, Int) -> Int = { a, b -> a + b }

// 'it' — implicit name for single-parameter lambdas
val isEven: (Int) -> Boolean = { it % 2 == 0 }

// Trailing lambda — last lambda arg goes outside parens
listOf(1, 2, 3).forEach { println(it) }

// Function references with ::
fun isPositive(n: Int) = n > 0
listOf(-1, 2, -3, 4).filter(::isPositive)

// Common functional operations
val nums = listOf(1, 2, 3, 4, 5)
nums.map { it * 2 }           // [2, 4, 6, 8, 10]
nums.filter { it % 2 == 0 }   // [2, 4]
nums.reduce { acc, n -> acc + n }  // 15
nums.fold(10) { acc, n -> acc + n } // 25
nums.any { it > 4 }           // true
nums.all { it > 0 }           // true
nums.none { it > 10 }         // true
nums.count { it % 2 == 0 }    // 2
nums.sumOf { it }              // 15
nums.maxOrNull()               // 5
nums.sortedDescending()        // [5, 4, 3, 2, 1]

// let, also, run, apply, with
val result = someNullable?.let { process(it) }   // run if non-null
val obj = MyClass().apply { field = "value" }    // configure and return
        """.trimIndent()
    ),
    ReferenceSection(
        title = "String Templates & Manipulation",
        code = """
val player = "Jordan"
val score  = 85

println("Welcome, ${"$"}player!")          // simple
println("Next: ${"$"}{score + 1}")          // expression in braces

// Multi-line / raw string (no escape needed inside)
val msg = ""${'"'}
    Player : ${"$"}player
    Score  : ${"$"}score
""${'"'}.trimIndent()

// Common String operations
val s = "  Hello, World!  "
s.trim()                  // "Hello, World!"
s.lowercase()             // "  hello, world!  "
s.uppercase()             // "  HELLO, WORLD!  "
s.replace("World", "Kotlin")
s.split(", ")             // ["  Hello", "World!  "]
s.contains("Hello")       // true
s.startsWith("  Hello")   // true
s.substring(2, 7)         // "Hello"
s.length                  // 17

// String to number conversions
"42".toInt()
"3.14".toDouble()
42.toString()

// String formatting
"%.2f".format(3.14159)   // "3.14"
        """.trimIndent()
    ),
    ReferenceSection(
        title = "Control Flow",
        code = """
// if as expression — returns a value
val rank = if (score >= 90) "Expert" else "Beginner"

// when expression — Kotlin's switch
val label = when (score) {
    in 90..100 -> "A"
    in 80..89  -> "B"
    in 70..79  -> "C"
    else       -> "F"
}

// when with multiple values per branch
when (day) {
    "Sat", "Sun" -> println("Weekend")
    else         -> println("Weekday")
}

// when without subject — replaces if/else chains
when {
    score > 90 -> println("Top!")
    score > 70 -> println("Good")
    else       -> println("Keep going")
}

// when with type checking
fun describe(obj: Any): String = when (obj) {
    is Int    -> "Int: ${"$"}obj"
    is String -> "String of length ${"$"}{obj.length}"
    is List<*>-> "List of size ${"$"}{obj.size}"
    else      -> "Unknown"
}
        """.trimIndent()
    ),
    ReferenceSection(
        title = "Null Safety",
        code = """
var name: String  = "Alex"  // non-nullable — never null
var nick: String? = null    // nullable — can be null

// Safe call — returns null instead of throwing NPE
println(nick?.length)

// Elvis operator — fallback value if null
val display = nick ?: "Guest"

// Chained safe calls
val city = user?.address?.city ?: "Unknown"

// Safe cast — returns null if cast fails (no ClassCastException)
val len = (nick as? String)?.length

// Non-null assertion — throws NPE if null; use sparingly!
val forced = nick!!.length

// let — execute block only if non-null
nick?.let { n ->
    println("Nickname is ${"$"}n")
}

// Null checks
if (nick != null) {
    println(nick.length)   // smart-cast: nick treated as String here
}

// requireNotNull / checkNotNull
val validated = requireNotNull(nick) { "nick must not be null" }
        """.trimIndent()
    ),
    ReferenceSection(
        title = "Collections",
        code = """
// Read-only — cannot add/remove elements
val items = listOf("A", "B", "C")
val map   = mapOf("a" to 1, "b" to 2)
val set   = setOf(1, 2, 3)

// Mutable
val list = mutableListOf<Int>()
list.add(42)
list.addAll(listOf(1, 2, 3))
list.remove(42)
list[0] = 99

val mutableMap = mutableMapOf<String, Int>()
mutableMap["key"] = 1
mutableMap.getOrDefault("missing", 0)

// Common List operations
items.first()               // "A"
items.last()                // "C"
items.firstOrNull { it == "Z" }  // null
items.isEmpty()             // false
items.size                  // 3
items.contains("B")         // true
items.indexOf("B")          // 1
items.reversed()            // ["C", "B", "A"]
items.shuffled()            // random order
items.zip(listOf(1, 2, 3))  // [("A",1), ("B",2), ("C",3)]
items.flatten()             // flattens nested lists
items.distinct()            // removes duplicates
items.chunked(2)            // [["A","B"], ["C"]]

// Transformations
items.map { it.lowercase() }
items.flatMap { listOf(it, it + "!") }
items.groupBy { it.length }        // Map<Int, List<String>>
items.associateWith { it.length }  // Map<String, Int>
items.partition { it > "A" }       // Pair<List, List>
        """.trimIndent()
    ),
    ReferenceSection(
        title = "Loops & Ranges",
        code = """
// for with inclusive range
for (i in 1..5) print("${"$"}i ")    // 1 2 3 4 5

// until — exclusive end
for (i in 0 until 5) print("${"$"}i ") // 0 1 2 3 4

// downTo with step
for (i in 10 downTo 1 step 2) print("${"$"}i ") // 10 8 6 4 2

// iterate collection with index
for ((index, value) in items.withIndex()) {
    println("${"$"}index: ${"$"}value")
}

// while
var x = 0
while (x < 3) { print("${"$"}x "); x++ }

// do-while — always executes body at least once
do {
    println("Running...")
    x++
} while (x < 5)

// repeat
repeat(3) { println("Hello ${"$"}it") }

// Range as a value
val range = 1..100
println(50 in range)   // true
println(range.sum())   // 5050

// forEach
(1..5).forEach { println(it) }

// break and continue
for (i in 1..10) {
    if (i == 3) continue   // skip 3
    if (i == 7) break      // stop at 7
    print("${"$"}i ")
}
        """.trimIndent()
    ),
    ReferenceSection(
        title = "Classes & Data Classes",
        code = """
// Regular class with primary constructor
class Dog(val name: String, var age: Int) {
    fun speak() = "Woof! I'm ${"$"}name"
    fun birthday() { age++ }
}
val dog = Dog("Rex", 3)
dog.birthday()

// Init block — runs when object is created
class Circle(val radius: Double) {
    val area: Double
    init {
        area = Math.PI * radius * radius
    }
}

// Data class — equals/hashCode/toString/copy auto-generated
data class User(val name: String, val score: Int = 0)
val user    = User("Alex", 85)
val updated = user.copy(score = 100)  // immutable update
val (name, score) = user              // destructuring

// Companion object — like static members in Java
class MathUtils {
    companion object {
        const val PI = 3.14159
        fun square(n: Int) = n * n
    }
}
MathUtils.square(4)   // 16

// Object declaration — singleton
object Config {
    val baseUrl = "https://api.example.com"
}

// Inheritance — classes are final by default, use open
open class Animal(val name: String) {
    open fun sound() = "..."
}
class Cat(name: String) : Animal(name) {
    override fun sound() = "Meow"
}

// Abstract class
abstract class Shape {
    abstract fun area(): Double
    fun describe() = "Area is ${"$"}{area()}"
}
        """.trimIndent()
    ),
    ReferenceSection(
        title = "Interfaces & Sealed Classes",
        code = """
// Interface
interface Clickable {
    fun click()
    fun longClick() = println("Long clicked!")  // default impl
}

class Button : Clickable {
    override fun click() = println("Clicked!")
}

// Sealed class — exhaustive when branches, like enums with data
sealed class Result<out T>
data class Success<T>(val data: T) : Result<T>()
data class Error(val message: String) : Result<Nothing>()
object Loading : Result<Nothing>()

fun handle(result: Result<String>) = when (result) {
    is Success -> println(result.data)
    is Error   -> println("Error: ${"$"}{result.message}")
    is Loading -> println("Loading...")
    // No else needed — compiler knows all subtypes
}

// Enum class
enum class Direction { NORTH, SOUTH, EAST, WEST }
enum class Color(val hex: String) {
    RED("#FF0000"), GREEN("#00FF00"), BLUE("#0000FF");
    fun describe() = "Color ${"$"}name is ${"$"}hex"
}

// Type aliases
typealias Predicate<T> = (T) -> Boolean
val isEven: Predicate<Int> = { it % 2 == 0 }
        """.trimIndent()
    ),
    ReferenceSection(
        title = "Coroutines",
        code = """
// Launch a coroutine — fire and forget
viewModelScope.launch {
    val data = fetchData()   // suspends here, doesn't block thread
    updateUI(data)
}

// async/await — concurrent work returning a value
val deferred = viewModelScope.async { fetchUser() }
val user = deferred.await()

// Run both concurrently
val (user, posts) = coroutineScope {
    val u = async { fetchUser() }
    val p = async { fetchPosts() }
    Pair(u.await(), p.await())
}

// withContext — switch dispatcher inside a coroutine
suspend fun loadFile(): String = withContext(Dispatchers.IO) {
    File("data.txt").readText()
}

// Common dispatchers
Dispatchers.Main   // UI thread
Dispatchers.IO     // I/O (network, disk)
Dispatchers.Default // CPU-intensive work

// Flow — cold asynchronous stream
fun countDown(): Flow<Int> = flow {
    for (i in 5 downTo 0) {
        emit(i)
        delay(1000)
    }
}

// Collect a flow
countDown()
    .filter { it % 2 == 0 }
    .collect { println(it) }

// StateFlow — hot flow, always has a value (ViewModel state)
private val _uiState = MutableStateFlow(UiState.Loading)
val uiState: StateFlow<UiState> = _uiState.asStateFlow()
        """.trimIndent()
    ),
    ReferenceSection(
        title = "Scope Functions",
        code = """
// let — transform a value or run block on nullable
val len = str?.let { it.length } ?: 0

// run — like let but uses 'this' (receiver)
val result = StringBuilder().run {
    append("Hello")
    append(", World")
    toString()
}

// apply — configure an object, returns the object
val list = mutableListOf<String>().apply {
    add("A")
    add("B")
    add("C")
}

// also — side effects (logging, debugging), returns the object
val user = createUser().also {
    println("Created: ${"$"}{it.name}")
}

// with — operate on an object without extension
val desc = with(user) {
    "${"$"}name has score ${"$"}score"
}

// Quick reference:
// | Function | Receiver | Returns  | Use for              |
// |----------|----------|----------|----------------------|
// | let      | it       | lambda   | nullables, transform |
// | run      | this     | lambda   | compute a result     |
// | apply    | this     | receiver | object configuration |
// | also     | it       | receiver | side effects         |
// | with     | this     | lambda   | group operations     |
        """.trimIndent()
    ),
    ReferenceSection(
        title = "Generics",
        code = """
// Generic function
fun <T> swap(a: T, b: T): Pair<T, T> = Pair(b, a)

// Generic class
class Box<T>(val value: T) {
    fun unwrap(): T = value
}
val box = Box(42)

// Upper bound constraint
fun <T : Comparable<T>> max(a: T, b: T) = if (a > b) a else b

// out (covariant) — can only produce T, not consume
class Producer<out T>(private val item: T) {
    fun get(): T = item
}

// in (contravariant) — can only consume T, not produce
class Consumer<in T> {
    fun process(item: T) { /* ... */ }
}

// reified — access type at runtime inside inline function
inline fun <reified T> isType(value: Any) = value is T
println(isType<String>("hello"))  // true
        """.trimIndent()
    )
)

@Composable
fun ReferenceScreen() {
    val expandedState = remember { mutableStateMapOf<String, Boolean>() }
    var query by remember { mutableStateOf("") }

    val filteredSections = remember(query) {
        if (query.isBlank()) referenceSections
        else referenceSections.filter { section ->
            section.title.contains(query, ignoreCase = true) ||
                section.code.contains(query, ignoreCase = true)
        }
    }

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
                text = "Syntax at a glance — tap a section to expand",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search bar
        TextField(
            value = query,
            onValueChange = { query = it },
            placeholder = {
                Text(
                    text = "Search — e.g. null, coroutine, map…",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = CodePathTeal,
                    modifier = Modifier.size(20.dp)
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = CodePathTeal
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (filteredSections.isEmpty()) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "No results for \"$query\"",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                filteredSections.forEach { section ->
                    val isExpanded = expandedState[section.title]
                        ?: (query.isNotBlank()) // auto-expand when filtering
                    ReferenceEntry(
                        section = section,
                        isExpanded = isExpanded,
                        onToggle = { expandedState[section.title] = !isExpanded }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ReferenceEntry(
    section: ReferenceSection,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    val arrowRotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "arrow_rotation"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        // Tappable header row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onToggle)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = section.title.uppercase(),
                color = CodePathTeal,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                tint = CodePathTeal,
                modifier = Modifier
                    .size(18.dp)
                    .rotate(arrowRotation)
            )
        }

        // Collapsible code block
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
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
}
