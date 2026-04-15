package com.codepath.kotlinprimer.data

data class KeyConcept(val label: String, val description: String)

data class DeepLesson(
    val intro: String,
    val keyConcepts: List<KeyConcept>,
    val advancedCode: String,
    val readTimeMinutes: Int
)

val deepLessons: Map<Int, DeepLesson> = mapOf(

    1 to DeepLesson(
        intro = """
            In Kotlin, every variable declaration starts with a deliberate choice: val or var. A val (value) is read-only — once assigned, it cannot be reassigned anywhere in its scope. A var (variable) is mutable and its value can change freely over time. This distinction is not just syntax — it communicates intent to every developer who reads the code, and the compiler enforces it rigorously at compile time. The Kotlin community strongly favors val by default, treating mutability as something you consciously opt into rather than the default state, which aligns with the broader philosophy of writing safe, predictable programs.
        """.trimIndent(),
        keyConcepts = listOf(
            KeyConcept(
                "Prefer val by default",
                "Declaring a variable as val signals to readers that this value is stable and won't change. This reduces the mental load of tracking where and how a value might mutate throughout a function. Only switch to var when mutation is genuinely necessary — for example, a counter in a loop or a running total that accumulates across iterations."
            ),
            KeyConcept(
                "Compile-time enforcement",
                "The Kotlin compiler treats val reassignment as a hard error, not a suggestion or warning. This means violations are caught before the code runs — no unit tests or defensive runtime checks needed to guard these guarantees. The compiler is your first line of defense."
            ),
            KeyConcept(
                "val vs const val",
                "A val is runtime-immutable: the reference is set once when that line executes. A const val is a compile-time constant — it must be a primitive type or String, must live at the top level or inside an object, and its value is inlined directly at every usage site by the compiler, making it slightly more efficient."
            ),
            KeyConcept(
                "Immutability and thread safety",
                "Because val references cannot be reassigned after initialization, they are inherently safe to share and read from multiple threads simultaneously without synchronization primitives. Immutability is one of the foundational tools for writing correct concurrent Kotlin code — a property that becomes critical as soon as coroutines enter the picture."
            )
        ),
        advancedCode = """
            const val MAX_SCORE = 100  // compile-time constant

            fun runGame(player: String) {
                val displayName = player.uppercase() // fixed for this function call
                var lives = 3                         // must change — so it's a var

                while (lives > 0) {
                    val roll = (1..6).random()        // new val each iteration
                    if (roll < 2) lives--
                }
                println("${'$'}displayName finished with ${'$'}{MAX_SCORE - lives * 10} pts")
            }
        """.trimIndent(),
        readTimeMinutes = 6
    ),

    2 to DeepLesson(
        intro = """
            Kotlin is 100% statically typed — every variable, parameter, and return value has a fixed type known to the compiler. What makes Kotlin different from Java is that you rarely have to write those types yourself. The compiler's type inference engine analyses the right-hand side of every assignment and determines the type automatically, giving you the safety of static typing with the brevity of a dynamic language. This is not type guessing or dynamic dispatch — the inferred type is locked in at compile time, catches mismatches immediately, and produces no runtime overhead. You only need to write explicit type annotations when the compiler genuinely cannot infer them, or when you want to be more expressive about your intent.
        """.trimIndent(),
        keyConcepts = listOf(
            KeyConcept(
                "Inferred at compile time, not runtime",
                "Even when you omit the type annotation, the compiler resolves the type precisely and records it in the bytecode. There is no dynamic typing or reflection happening — you have full static safety. The IDE can show you the inferred type by hovering over a declaration."
            ),
            KeyConcept(
                "When to write explicit types",
                "Annotate a type explicitly when: (1) declaring a variable without an immediate value, (2) intentionally widening inference (val x: Number = 42 instead of val x = 42), or (3) clarifying complex generic or lambda types where the inferred type is hard to read at a glance. Explicit types are documentation as much as they are instructions."
            ),
            KeyConcept(
                "Inferred return types for functions",
                "Single-expression functions infer their return type automatically: fun double(x: Int) = x * 2 is fully typed without annotation. For block-body functions, Kotlin requires an explicit return type — this is intentional, as the return type is part of a function's public contract and should be stated clearly."
            ),
            KeyConcept(
                "Smart casts",
                "Type inference extends beyond declarations into control flow. After a type check (`if (x is String)`), Kotlin automatically casts x to String within that branch — no explicit cast needed. This feature, called smart casting, eliminates a large category of redundant casting code common in Java."
            )
        ),
        advancedCode = """
            // All types inferred — compiler knows each precisely
            val name    = "Jordan"         // String
            val score   = 42               // Int
            val ratio   = 0.85             // Double
            val active  = true             // Boolean
            val items   = listOf(1, 2, 3)  // List<Int>

            // Widening: annotate to get Number instead of Int
            val flexible: Number = 42

            // Smart cast in action
            fun describe(value: Any): String {
                return if (value is Int) {
                    "Int: ${'$'}{value + 1}"   // value is smart-cast to Int here
                } else {
                    "Other: ${'$'}value"
                }
            }
        """.trimIndent(),
        readTimeMinutes = 5
    ),

    3 to DeepLesson(
        intro = """
            String templates are one of Kotlin's most immediately appreciated features for developers coming from Java. Instead of concatenating strings with the + operator — which is verbose and error-prone — you embed values directly inside string literals using the $ prefix. For any expression more complex than a simple variable name, you wrap it in curly braces ${"\$"}{...} and Kotlin evaluates it, calling toString() on the result automatically. This works with variables, function calls, arithmetic, conditionals, method chains — anything that produces a value. The result is code that reads almost like plain English, significantly reducing noise and the chance of missing a + or mismatched quote.
        """.trimIndent(),
        keyConcepts = listOf(
            KeyConcept(
                "Simple variable interpolation (\$)",
                "Prefix any variable name with $ inside a string literal to inject its value inline. Kotlin calls toString() automatically, so you never need explicit conversions for numbers or booleans. This replaces the common Java pattern of \"Hello, \" + name + \"!\" with the far cleaner \"Hello, \$name!\"."
            ),
            KeyConcept(
                "Expression interpolation (\${...})",
                "Any valid Kotlin expression goes inside \${...}: arithmetic, function calls, conditional expressions, property access, or chained method calls. The expression is evaluated first, then its toString() result is embedded. This makes templates far more powerful than simple variable substitution."
            ),
            KeyConcept(
                "Multi-line raw strings",
                "Triple-quoted strings (\"\"\"...\"\"\"`) preserve literal whitespace and newlines — no escape sequences needed. This is ideal for SQL, JSON templates, file paths, or any formatted text. Combined with .trimIndent(), leading indentation is stripped while the intended structure is preserved."
            ),
            KeyConcept(
                "Escaping the dollar sign",
                "To include a literal dollar sign in a string template, use \${'$'}. This is most common in code generation, SQL amounts, or any domain where $ has a specific meaning. In triple-quoted strings you can also use the \${'$'}{\"\\$\"} pattern, though \${'$'} is cleaner."
            )
        ),
        advancedCode = """
            data class Player(val name: String, val score: Int, val level: Int)

            fun formatReport(player: Player, maxScore: Int): String {
                val pct = (player.score * 100) / maxScore
                val grade = if (pct >= 80) "Pass" else "Retry"
                val bar = "#".repeat((pct / 10).coerceAtMost(10))

                // Templates work with any expression inside ${'$'}{...}
                return "Player : ${'$'}{player.name.uppercase()}\n" +
                       "Level  : ${'$'}{player.level}\n" +
                       "Score  : ${'$'}{player.score}/${'$'}maxScore (${'$'}pct%)\n" +
                       "Bar    : [${'$'}bar]\n" +
                       "Grade  : ${'$'}grade"
            }
        """.trimIndent(),
        readTimeMinutes = 5
    ),

    4 to DeepLesson(
        intro = """
            Functions are the fundamental unit of computation in Kotlin, declared with the fun keyword. Every parameter must carry an explicit name and type, and the return type follows a colon after the closing parenthesis. What sets Kotlin apart from Java is that functions are first-class values — they exist independently of classes, can be stored in variables, passed as arguments, and returned from other functions. Kotlin also provides several ergonomic features that eliminate the boilerplate of function overloads: default parameter values, named arguments at call sites, and single-expression syntax. Mastering these patterns makes your code dramatically more readable and reduces the number of functions you need to write.
        """.trimIndent(),
        keyConcepts = listOf(
            KeyConcept(
                "Named & default parameters",
                "Any parameter can have a default value, and callers can override any subset of them by name. This eliminates the need for multiple overloaded versions of the same function — one definition handles all variants. Named arguments also make call sites self-documenting: formatScore(85, showPercent = true) is immediately clear, while formatScore(85, true) is not."
            ),
            KeyConcept(
                "Single-expression functions",
                "When a function body is a single expression, drop the curly braces and return keyword and use = instead. The return type is usually inferred. This is not just a shortcut — it's idiomatic Kotlin for small, pure functions and communicates clearly that the function has exactly one operation."
            ),
            KeyConcept(
                "Unit return type",
                "Functions with no meaningful return value implicitly return Unit — Kotlin's equivalent of Java's void. Unlike void, Unit is a real type with exactly one value (also called Unit). The return type annotation is optional and usually omitted for cleanliness, but understanding it matters when working with higher-order functions and lambdas."
            ),
            KeyConcept(
                "Top-level functions",
                "In Kotlin, functions don't have to live inside a class. Top-level functions — declared directly in a file — are idiomatic and preferred for utility operations that aren't tied to a specific object's state. This eliminates the Java pattern of empty utility classes like StringUtils or MathHelper."
            )
        ),
        advancedCode = """
            // Default parameters replace overloads
            fun formatScore(
                score: Int,
                label: String = "Score",
                showPercent: Boolean = false,
                maxScore: Int = 100
            ): String {
                val suffix = if (showPercent) " / ${'$'}maxScore (${"\$"}{score * 100 / maxScore}%)" else ""
                return "${'$'}label: ${'$'}score${'$'}suffix"
            }

            // Single-expression functions
            fun double(n: Int) = n * 2
            fun isPass(score: Int) = score >= 80

            // Named args at call site — readable without overloads
            println(formatScore(85))                          // Score: 85
            println(formatScore(85, showPercent = true))      // Score: 85 / 100 (85%)
            println(formatScore(72, label = "Points"))        // Points: 72
        """.trimIndent(),
        readTimeMinutes = 6
    ),

    5 to DeepLesson(
        intro = """
            In most languages, if/else is a statement — it executes code but produces no value. In Kotlin, if is both a statement and a full expression that returns a value. This means you can assign the result of an if directly to a variable without any intermediary mutation. The last expression in each branch becomes the returned value, and the compiler ensures every branch produces a compatible type. This seemingly small change eliminates an entire class of mutable-variable patterns where you declare an empty variable and then fill it in through branching — patterns that are both verbose and fragile.
        """.trimIndent(),
        keyConcepts = listOf(
            KeyConcept(
                "if as an expression",
                "When if appears on the right side of an assignment, each branch's final expression is the value that branch returns. The compiler infers the type as the common supertype of all branches. All branches must produce a value — an if expression without an else clause is only valid as a statement, not when its result is used."
            ),
            KeyConcept(
                "Replaces the ternary operator",
                "Kotlin deliberately has no ternary operator (? :). The idiomatic replacement is `val x = if (condition) valueA else valueB`. This is not a limitation — the Kotlin team found that ternaries reduce readability at the call site, and the if expression version is consistently clearer for new readers."
            ),
            KeyConcept(
                "Multi-line expression branches",
                "Each branch of an if expression can contain multiple statements. The last statement's value is what the branch returns, similar to how a function's last expression is returned implicitly. This is useful for branches that require some computation before producing the final value."
            ),
            KeyConcept(
                "Nesting and exhaustiveness",
                "When using if as an expression, the compiler requires an else branch — without it, the expression has no guaranteed value when all conditions are false. For complex multi-condition logic, consider whether when (Kotlin's switch expression) would express the intent more clearly than a chain of if/else if."
            )
        ),
        advancedCode = """
            data class Player(val score: Int, val streak: Int, val lives: Int)

            fun getStatusMessage(player: Player): String {
                // Multi-line expression branches — last expression is the value
                val rank = if (player.score >= 90) {
                    val bonus = if (player.streak >= 5) " (Hot streak!)" else ""
                    "Expert${'$'}bonus"
                } else if (player.score >= 70) {
                    "Intermediate"
                } else {
                    "Beginner"
                }

                val lifeStatus = if (player.lives > 1) "${'$'}{player.lives} lives left"
                                 else if (player.lives == 1) "Last life!"
                                 else "Game over"

                return "${'$'}rank — ${'$'}lifeStatus"
            }
        """.trimIndent(),
        readTimeMinutes = 5
    ),

    6 to DeepLesson(
        intro = """
            The when expression is Kotlin's replacement for the switch statement — but it is significantly more powerful. Where switch can only match against constant values and is riddled with fall-through pitfalls, when can match values, ranges, type checks, and arbitrary boolean conditions, all in a clean branch syntax. Like if, when can be used as a statement or as an expression that returns a value. When used as an expression, Kotlin requires that the branches are exhaustive — every possible input must be covered — which the compiler verifies for you. This exhaustiveness guarantee becomes especially powerful when combined with sealed classes.
        """.trimIndent(),
        keyConcepts = listOf(
            KeyConcept(
                "Value and range matching",
                "Each branch uses -> to separate the condition from the result. You can match a single value, multiple comma-separated values on one branch (`1, 2 -> ...`), or ranges using the in operator (`in 1..10 -> ...`). There is no fall-through between branches — matching stops at the first satisfied condition."
            ),
            KeyConcept(
                "Type checking with smart casts",
                "A when branch can check the type of the subject using `is TypeName`. Inside that branch, the subject is automatically smart-cast to that type — you can access type-specific properties and methods without any explicit cast. This makes when the natural tool for working with class hierarchies."
            ),
            KeyConcept(
                "when without a subject",
                "You can omit the subject entirely and use when as a chain of boolean conditions: `when { x > 0 -> ... }`. This is functionally equivalent to if/else if but often reads more cleanly when there are many conditions. Each branch is a full boolean expression."
            ),
            KeyConcept(
                "Exhaustiveness with sealed classes",
                "A sealed class restricts its subclasses to a known, fixed set defined in the same file. When you use when over a sealed class as an expression, the compiler can verify that every possible subclass has a branch — making else unnecessary and ensuring that adding a new subclass will immediately cause a compile error wherever it isn't handled."
            )
        ),
        advancedCode = """
            sealed class AppEvent
            data class Login(val username: String) : AppEvent()
            data class ScoreUpdate(val delta: Int) : AppEvent()
            data class LevelUp(val newLevel: Int) : AppEvent()
            object Logout : AppEvent()

            fun handleEvent(event: AppEvent): String = when (event) {
                is Login      -> "Welcome, ${'$'}{event.username}!"
                is ScoreUpdate -> when {
                    event.delta > 0 -> "+${'$'}{event.delta} points"
                    event.delta < 0 -> "${'$'}{event.delta} points (penalty)"
                    else            -> "No change"
                }
                is LevelUp    -> "Level ${'$'}{event.newLevel} unlocked!"
                Logout        -> "See you next time."
                // No else needed — sealed class is exhaustive
            }
        """.trimIndent(),
        readTimeMinutes = 6
    ),

    7 to DeepLesson(
        intro = """
            Null pointer exceptions are widely considered one of the most costly mistakes in language design — so pervasive in Java that they earned the nickname "the billion-dollar mistake." Kotlin fixes this at the type system level. Every type is non-null by default: a String cannot hold null, and the compiler will reject any attempt to assign null to it. To allow null, you must explicitly opt in with a ? suffix, creating a nullable type (String?). This makes nullability a visible, documented part of every type signature and forces you to handle the null case at the call site — instead of discovering it as a NullPointerException in production at 2am.
        """.trimIndent(),
        keyConcepts = listOf(
            KeyConcept(
                "Non-null by default",
                "In Kotlin, `String` and `String?` are genuinely different types. `String` is guaranteed non-null — the compiler will not let you assign null to it or pass null where a String is expected. `String?` can hold either a String value or null. This separation makes the presence of potential null visible in every function signature."
            ),
            KeyConcept(
                "Safe call operator (?.)",
                "The ?. operator chains a property access or function call on a nullable receiver. If the receiver is null at runtime, the entire expression short-circuits and evaluates to null rather than crashing. You can chain multiple safe calls: user?.address?.city evaluates safely even if user or address is null."
            ),
            KeyConcept(
                "Elvis operator (?:)",
                "The ?: operator (named for its resemblance to Elvis Presley's hair) provides a fallback value when the left side is null. `val city = user?.address?.city ?: \"Unknown\"` reads: \"city, or Unknown if any part of the chain was null.\" The fallback can be any expression, including a function call or a throw."
            ),
            KeyConcept(
                "Non-null assertion (!!) and when to use it",
                "The !! operator asserts that a nullable value is not null — it throws a NullPointerException if it is. This should be rare in well-written Kotlin. Use it only when you have external knowledge that the compiler cannot verify (e.g., a value set by an initialization framework before first use). Treat !! as a code smell that warrants a comment explaining why."
            )
        ),
        advancedCode = """
            data class Address(val street: String, val city: String?)
            data class User(val name: String, val address: Address?)

            fun describeUser(user: User?): String {
                // Chained safe calls — null at any point yields null
                val city = user?.address?.city

                // Elvis provides a fallback for any null in the chain
                val displayCity = city ?: "location unknown"

                // Safe call with a side-effect
                user?.address?.let { addr ->
                    println("Street: ${'$'}{addr.street}")
                }

                return "${'$'}{user?.name ?: "Guest"} — ${'$'}displayCity"
            }

            // In a when expression, null is a valid branch
            fun greet(name: String?) = when (name) {
                null  -> "Hello, Guest!"
                else  -> "Hello, ${'$'}name!"
            }
        """.trimIndent(),
        readTimeMinutes = 6
    ),

    8 to DeepLesson(
        intro = """
            Kotlin enforces an explicit distinction between read-only and mutable collections at the type level — a design decision that makes code dramatically safer. The standard collection interfaces List, Set, and Map are read-only: they expose only query operations and no mutation methods. If you need to modify a collection, you must explicitly declare it with the mutable variant (MutableList, MutableSet, MutableMap). This means that when you see a List parameter in a function signature, you can immediately know the function will not modify it — something impossible to guarantee in Java. Combined with Kotlin's rich standard library of functional operations, you rarely need to mutate a collection directly.
        """.trimIndent(),
        keyConcepts = listOf(
            KeyConcept(
                "Read-only vs Mutable",
                "listOf() returns a List<T> — no add(), remove(), or set() methods exist on the interface. mutableListOf() returns a MutableList<T> with full mutation support. This isn't just defensive programming — it's a type-level guarantee. A function accepting a List<T> literally cannot call mutating methods."
            ),
            KeyConcept(
                "Functional operations",
                "Kotlin's collection stdlib includes filter, map, flatMap, find, any, all, none, groupBy, sortedBy, partition, fold, reduce, and many more. All of them return new collections without mutating the original, enabling a declarative pipeline style. For example: `scores.filter { it >= 70 }.map { it * 1.1 }.sortedDescending()` reads as a data transformation, not a series of imperative mutations."
            ),
            KeyConcept(
                "Safe element access",
                "list[i] throws IndexOutOfBoundsException if i is out of range. The safer alternative is list.getOrNull(i), which returns null for invalid indices. Similarly, map[key] returns null for missing keys (since the return type is V?), while map.getValue(key) throws for missing keys. Choose based on whether a missing element is an expected case or a programming error."
            ),
            KeyConcept(
                "Sequences for large collections",
                "Standard collection operations are eager — each step processes the entire collection before passing results to the next step. For large collections with many chained operations, use .asSequence() to make them lazy: each element flows through the full pipeline before the next is processed. This avoids creating intermediate lists and can significantly improve performance."
            )
        ),
        advancedCode = """
            data class Student(val name: String, val score: Int, val grade: String)

            val students = listOf(
                Student("Alice", 92, "A"),
                Student("Bob", 74, "C"),
                Student("Carol", 88, "B"),
                Student("Dave", 61, "D"),
                Student("Eve", 95, "A")
            )

            // Declarative pipeline — no mutation, no intermediate variables
            val topStudents = students
                .filter { it.score >= 80 }
                .sortedByDescending { it.score }
                .map { "${'$'}{it.name}: ${'$'}{it.score}" }

            println(topStudents)  // [Eve: 95, Alice: 92, Carol: 88]

            // groupBy produces a Map<K, List<V>>
            val byGrade = students.groupBy { it.grade }
            println(byGrade["A"]?.map { it.name })  // [Alice, Eve]
        """.trimIndent(),
        readTimeMinutes = 6
    ),

    9 to DeepLesson(
        intro = """
            Kotlin's for loop iterates over any object implementing the Iterable interface — collections, arrays, strings, and custom types alike. Ranges are first-class objects in Kotlin, created with the .. operator or the until function, and they are fully iterable. This means you can iterate over a range just like a list, without needing array indices or manual counter management. Ranges are also efficient: an IntRange created with 1..1000 does not allocate a list of 1000 integers — it is a lightweight object that generates values on demand. Beyond ranges, Kotlin provides rich utilities like withIndex(), forEachIndexed(), and destructuring in for loops that make the most common iteration patterns feel natural and clean.
        """.trimIndent(),
        keyConcepts = listOf(
            KeyConcept(
                "Inclusive (..) vs exclusive (until) ranges",
                "The .. operator creates an inclusive range where both the start and end values are included. The until function (or infix notation 0 until 5) creates a half-open range that excludes the end, producing 0, 1, 2, 3, 4. Use until when working with list indices (0 until list.size) to avoid off-by-one errors — though list.indices is even more idiomatic."
            ),
            KeyConcept(
                "Step sizes and downTo",
                "Any range can use a custom step with the step infix function: `1..10 step 2` produces 1, 3, 5, 7, 9. Ranges can run backwards with downTo: `10 downTo 1` counts down inclusively. Combine them: `10 downTo 0 step 2` produces 10, 8, 6, 4, 2, 0. These produce IntProgression objects, not actual lists."
            ),
            KeyConcept(
                "Iterating with index",
                "When you need both the element and its position, use withIndex() in a destructuring for loop: `for ((index, value) in list.withIndex())`. Alternatively, use list.forEachIndexed { index, value -> }. Both approaches are cleaner than maintaining a manual counter variable."
            ),
            KeyConcept(
                "Range checks outside loops",
                "The in and !in operators work for membership checks anywhere in code, not just in for loops. `if (score in 80..89)` is idiomatic Kotlin for a range check — cleaner than `if (score >= 80 && score <= 89)`. This works for collections too: `if (name in listOf(\"Admin\", \"Mod\"))`."
            )
        ),
        advancedCode = """
            val languages = listOf("Kotlin", "Swift", "Rust", "Go", "Python")

            // Destructuring with index
            for ((index, lang) in languages.withIndex()) {
                println("${'$'}{index + 1}. ${'$'}lang")
            }

            // Range with step — generates no intermediate list
            print("Countdown: ")
            for (i in 10 downTo 1 step 3) print("${'$'}i ")  // 10 7 4 1
            println()

            // Range check as a boolean expression
            fun letterGrade(score: Int) = when (score) {
                in 90..100 -> "A"
                in 80..89  -> "B"
                in 70..79  -> "C"
                in 60..69  -> "D"
                else       -> "F"
            }

            // list.indices is cleaner than 0 until list.size
            for (i in languages.indices) {
                if (languages[i].startsWith("K")) println("Found at ${'$'}i")
            }
        """.trimIndent(),
        readTimeMinutes = 5
    ),

    10 to DeepLesson(
        intro = """
            The while loop is the tool of choice when the number of iterations is not known in advance and depends entirely on a runtime condition. Kotlin provides two variants: while, which checks the condition before the first iteration (so the body may never execute if the condition starts false), and do-while, which checks the condition after the first iteration (guaranteeing at least one execution regardless of the condition). While for loops and functional operations handle most collection iteration, while loops are indispensable for polling, retrying, event-driven processing, and any scenario where iteration continues until some external state changes.
        """.trimIndent(),
        keyConcepts = listOf(
            KeyConcept(
                "while vs do-while",
                "Choose while when the body should execute zero or more times depending on the initial condition state. Choose do-while when the body must execute at least once before checking — for example, showing a prompt and then validating the response, or performing an operation and then checking if it should repeat."
            ),
            KeyConcept(
                "break and continue",
                "break immediately exits the innermost enclosing loop. continue skips the rest of the current iteration and jumps back to the condition check (for while) or to the next element (for for). Both are used sparingly in idiomatic Kotlin — if you find yourself using them frequently, consider whether a functional approach (filter, takeWhile) would express the logic more clearly."
            ),
            KeyConcept(
                "Labelled break and continue",
                "In nested loops, break and continue only affect the immediately enclosing loop. To exit or skip in an outer loop, you label the outer loop with @label and use break@label or continue@label. Labels are defined by writing an identifier followed by @ before the loop: `outer@ for (...) { inner@ for (...) { break@outer } }`."
            ),
            KeyConcept(
                "Infinite loops with break",
                "while (true) is a legitimate and idiomatic pattern for event loops, server listeners, or game loops that should run indefinitely until an explicit break condition is reached. In Kotlin coroutines, these are often expressed differently, but in synchronous code while (true) { ... break condition ... } is clear and correct."
            )
        ),
        advancedCode = """
            // do-while: input must be collected at least once before validating
            fun promptUntilValid(): Int {
                var input: Int
                do {
                    print("Enter a number between 1 and 10: ")
                    input = readLine()?.toIntOrNull() ?: 0
                } while (input !in 1..10)
                return input
            }

            // Labelled break exits the outer loop, not just the inner one
            fun findPair(matrix: List<List<Int>>, target: Int): Pair<Int, Int>? {
                var result: Pair<Int, Int>? = null
                outer@ for (row in matrix.indices) {
                    for (col in matrix[row].indices) {
                        if (matrix[row][col] == target) {
                            result = Pair(row, col)
                            break@outer
                        }
                    }
                }
                return result
            }
        """.trimIndent(),
        readTimeMinutes = 5
    ),

    11 to DeepLesson(
        intro = """
            A data class is Kotlin's concise shorthand for classes whose primary purpose is to hold structured data. By adding the data modifier to a class declaration, you instruct the compiler to automatically generate toString(), equals(), hashCode(), copy(), and componentN() functions — all derived from the properties declared in the primary constructor. This eliminates hundreds of lines of boilerplate that Java developers write and maintain by hand in POJOs (Plain Old Java Objects). Data classes represent one of the clearest examples of Kotlin's philosophy: you should express what you want, not how to implement it.
        """.trimIndent(),
        keyConcepts = listOf(
            KeyConcept(
                "Auto-generated toString()",
                "A data class's toString() returns a human-readable string showing the class name and each constructor property with its value: `User(name=Alice, score=85)`. This is invaluable for logging and debugging. A regular class would print a cryptic memory address like `User@3a4b5c` unless you write toString() yourself."
            ),
            KeyConcept(
                "Structural equality with equals()",
                "For regular classes, == checks reference equality (same object in memory). For data classes, == checks structural equality — two instances are equal if all their constructor properties are equal, regardless of whether they are the same object. This is almost always what you want when comparing data-holding objects."
            ),
            KeyConcept(
                "Immutable updates with copy()",
                "copy() creates a new instance of the data class with the same property values, but you can override any subset using named arguments: `val updated = user.copy(score = 100)`. This is the idiomatic way to \"modify\" an immutable data class — you never mutate the original, you create a new version. This pattern is central to safe state management in Compose."
            ),
            KeyConcept(
                "Destructuring declarations",
                "Data classes auto-generate componentN() functions (component1(), component2(), etc.) corresponding to each constructor property in order. This enables destructuring: `val (name, score) = user` assigns the first and second properties to local variables. Destructuring also works in for loops: `for ((key, value) in map)`."
            )
        ),
        advancedCode = """
            data class User(
                val id: Int,
                val name: String,
                val score: Int = 0,
                val active: Boolean = true
            )

            val alice = User(1, "Alice", 85)
            val bob   = User(2, "Bob")          // defaults: score=0, active=true

            // Structural equality
            val alice2 = User(1, "Alice", 85)
            println(alice == alice2)            // true  (same values)
            println(alice === alice2)           // false (different objects)

            // Immutable update — alice is unchanged
            val promoted = alice.copy(score = 100)
            println(alice.score)                // 85
            println(promoted.score)             // 100

            // Destructuring — order matches constructor
            val (id, name, score) = alice
            println("${'$'}name earned ${'$'}score pts")  // Alice earned 85 pts

            // Destructuring in a loop
            val users = listOf(alice, bob)
            for ((_, userName, userScore) in users) {  // _ ignores id
                println("${'$'}userName: ${'$'}userScore")
            }
        """.trimIndent(),
        readTimeMinutes = 7
    ),

    12 to DeepLesson(
        intro = """
            A class in Kotlin defines a blueprint that combines state (properties) and behavior (functions) into a single reusable unit. Unlike data classes, which exist primarily to hold data and have their identity defined by their property values, regular classes model objects with their own identity, lifecycle, and behavior — objects where two instances with the same data might still be meaningfully different. Kotlin's class syntax is more concise than Java's: constructor parameters can be declared directly in the class header and automatically become properties, eliminating the separate field declaration, constructor parameter, and assignment steps that Java requires. One critical design decision in Kotlin is that classes are final (non-inheritable) by default — you must explicitly mark a class open to allow subclassing, which prevents accidental inheritance and makes APIs safer.
        """.trimIndent(),
        keyConcepts = listOf(
            KeyConcept(
                "Primary constructor and properties",
                "Properties declared in the class header with val or var are automatically created and initialized as instance properties — no explicit body needed. The header `class Dog(val name: String, var age: Int)` creates a class with two properties, a full constructor, and property accessors, all in one line."
            ),
            KeyConcept(
                "init blocks and secondary constructors",
                "The init block runs immediately after the primary constructor and is used for validation or setup logic that can't be expressed in a single initializer. Secondary constructors use the constructor keyword and must delegate to the primary constructor with this(...). In practice, default parameter values usually eliminate the need for secondary constructors."
            ),
            KeyConcept(
                "open, override, and the closed-by-default rule",
                "Kotlin classes are final (sealed from inheritance) by default. Mark a class with open to allow subclassing. Methods are also final by default — mark them open to allow overriding. Subclasses use override to provide a new implementation. This design makes inheritance explicit and prevents the fragile base class problem common in Java."
            ),
            KeyConcept(
                "Companion objects",
                "Kotlin has no static keyword. Instead, you define a companion object inside a class for factory methods and class-level state. A companion object is a real object — it can implement interfaces and be passed around. Access its members using the class name: `User.create(\"Alice\")` where create is defined in User's companion object."
            )
        ),
        advancedCode = """
            open class Animal(val name: String, val sound: String) {
                init {
                    require(name.isNotBlank()) { "Name cannot be blank" }
                }
                open fun speak() = "${'$'}name says ${'$'}sound"
                override fun toString() = name
            }

            class Dog(name: String, val breed: String) : Animal(name, "Woof") {
                override fun speak() = "${'$'}{super.speak()} — I'm a ${'$'}breed!"
            }

            class Cat(name: String) : Animal(name, "Meow") {
                private var lives = 9
                fun loseLife(): Boolean {
                    if (lives > 0) lives--
                    return lives == 0
                }
            }

            // Companion object replaces static factory methods
            class User private constructor(val name: String, val role: String) {
                companion object {
                    fun admin(name: String) = User(name, "ADMIN")
                    fun guest() = User("Guest", "VIEWER")
                }
            }

            val user = User.admin("Alice")  // clean factory syntax
        """.trimIndent(),
        readTimeMinutes = 8
    )
)
