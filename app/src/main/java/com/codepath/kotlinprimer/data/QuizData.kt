package com.codepath.kotlinprimer.data

data class QuizOption(val label: String, val text: String)

enum class QuestionType { MULTIPLE_CHOICE, TRUE_FALSE }

data class QuizQuestion(
    val type: QuestionType,
    val prompt: String,
    val codeSnippet: String? = null,
    val options: List<QuizOption>,
    val correctLabel: String,
    val explanation: String
)

data class QuizTopic(
    val number: Int,
    val title: String,
    val lesson: String,
    val codeExample: String,
    val question: QuizQuestion,
    val topicQuestions: List<QuizQuestion> = emptyList()
)

const val PASS_THRESHOLD = 10

val quizTopics = listOf(
    QuizTopic(
        number = 1,
        title = "val vs var",
        lesson = "Kotlin provides two ways to declare variables: val and var. A 'val' (value) is read-only and cannot be reassigned once initialized. A 'var' (variable) is mutable and its value can be changed over time. Prefer using 'val' by default to make your code more predictable and less prone to side effects.",
        codeExample = "val name = \"Alex\" // Cannot be changed\nvar score = 0    // Can be changed\nscore += 10      // Allowed\n// name = \"Sam\"  // Compiler error!",
        question = QuizQuestion(
            type = QuestionType.MULTIPLE_CHOICE,
            prompt = "You need to store a player's score that increases with each correct answer. Which keyword should you use to declare this variable?",
            codeSnippet = null,
            options = listOf(
                QuizOption("A", "val"),
                QuizOption("B", "var"),
                QuizOption("C", "const"),
                QuizOption("D", "final")
            ),
            correctLabel = "B",
            explanation = "Because the score changes (increases), it must be mutable. Therefore, you must use 'var'."
        ),
        topicQuestions = listOf(
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "Which line will cause a compiler error?",
                codeSnippet = "val city = \"Paris\"\ncity = \"London\"",
                options = listOf(
                    QuizOption("A", "Only line 1"),
                    QuizOption("B", "Only line 2"),
                    QuizOption("C", "Both lines"),
                    QuizOption("D", "Neither line")
                ),
                correctLabel = "B",
                explanation = "Line 1 is valid — it declares an immutable val. Line 2 is the error because you cannot reassign a val after it has been initialized."
            ),
            QuizQuestion(
                type = QuestionType.TRUE_FALSE,
                prompt = "A `var` variable can hold different values at different points in the program.",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "True"),
                    QuizOption("B", "False")
                ),
                correctLabel = "A",
                explanation = "That's exactly what var is for — it declares a mutable variable whose value can be updated as many times as needed."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What keyword should you use to declare a variable whose value should never change?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "var"),
                    QuizOption("B", "let"),
                    QuizOption("C", "val"),
                    QuizOption("D", "final")
                ),
                correctLabel = "C",
                explanation = "`val` marks a variable as read-only. Once assigned, its value cannot be changed. `final` and `let` are not Kotlin variable keywords."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What will this code print?",
                codeSnippet = "var total = 0\ntotal = total + 5\ntotal = total + 3\nprintln(total)",
                options = listOf(
                    QuizOption("A", "0"),
                    QuizOption("B", "5"),
                    QuizOption("C", "3"),
                    QuizOption("D", "8")
                ),
                correctLabel = "D",
                explanation = "`total` starts at 0, then 5 is added (total = 5), then 3 is added (total = 8). Because `total` is a `var`, both reassignments are allowed."
            ),
            QuizQuestion(
                type = QuestionType.TRUE_FALSE,
                prompt = "In Kotlin, `val` is generally preferred over `var` when the value does not need to change.",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "True"),
                    QuizOption("B", "False")
                ),
                correctLabel = "A",
                explanation = "Using `val` by default makes your code safer and more predictable by preventing accidental reassignment. Only reach for `var` when mutability is truly needed."
            )
        )
    ),
    QuizTopic(
        number = 2,
        title = "Type inference",
        lesson = "Kotlin is statically typed, but it's smart enough to infer the type of a variable from the value assigned to it. You don't always need to explicitly write the type. Explicit types are only required when you are declaring a variable without assigning an initial value.",
        codeExample = "val greeting = \"Hello\" // Inferred as String\nval count = 42         // Inferred as Int\nval explicit: Double = 3.14\n\nval lateAssigned: String\nlateAssigned = \"Hi\"    // Allowed",
        question = QuizQuestion(
            type = QuestionType.TRUE_FALSE,
            prompt = "You must always explicitly write the type when declaring a variable in Kotlin.",
            codeSnippet = null,
            options = listOf(
                QuizOption("A", "True"),
                QuizOption("B", "False")
            ),
            correctLabel = "B",
            explanation = "Kotlin supports type inference, meaning it automatically figures out the type from the assigned value."
        ),
        topicQuestions = listOf(
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What type does Kotlin infer for this variable?",
                codeSnippet = "val count = 42",
                options = listOf(
                    QuizOption("A", "String"),
                    QuizOption("B", "Double"),
                    QuizOption("C", "Int"),
                    QuizOption("D", "Long")
                ),
                correctLabel = "C",
                explanation = "The literal `42` is a whole number, so Kotlin infers the type as `Int`. If you needed a Long you'd write `42L`."
            ),
            QuizQuestion(
                type = QuestionType.TRUE_FALSE,
                prompt = "Kotlin can always infer the type of a variable — even when no initial value is provided.",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "True"),
                    QuizOption("B", "False")
                ),
                correctLabel = "B",
                explanation = "Type inference only works when a value is assigned at the point of declaration. If you declare a variable without a value (e.g., `val x`), you must supply an explicit type annotation."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "Which declaration requires an explicit type annotation to compile?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "val name = \"Alice\""),
                    QuizOption("B", "val score = 100"),
                    QuizOption("C", "val result: String"),
                    QuizOption("D", "val pi = 3.14")
                ),
                correctLabel = "C",
                explanation = "When a variable is declared without an initial value (`val result: String`), there is nothing for the compiler to infer from — an explicit type is required."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What type does Kotlin infer for this variable?",
                codeSnippet = "val ratio = 7.5",
                options = listOf(
                    QuizOption("A", "Float"),
                    QuizOption("B", "Int"),
                    QuizOption("C", "Double"),
                    QuizOption("D", "Number")
                ),
                correctLabel = "C",
                explanation = "Decimal literals like `7.5` are inferred as `Double` by default in Kotlin. To get a `Float`, you'd write `7.5f`."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "Which of the following is valid Kotlin using type inference?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "val x: = 10"),
                    QuizOption("B", "val x = 10"),
                    QuizOption("C", "var x = 10: Int"),
                    QuizOption("D", "let x = 10")
                ),
                correctLabel = "B",
                explanation = "`val x = 10` is correct — Kotlin infers the type as `Int`. Option A has invalid syntax (colon with no type), C puts the type in the wrong place, and D is not Kotlin syntax."
            )
        )
    ),
    QuizTopic(
        number = 3,
        title = "String templates",
        lesson = "String templates allow you to seamlessly embed variable values directly into strings using the \$ symbol. For more complex expressions, you wrap them in curly braces like \${expression}. This is much cleaner and preferred over Java-style string concatenation with the + operator.",
        codeExample = "val player = \"Jordan\"\nval score = 85\n\n// Simple variable\nprintln(\"Welcome, \$player!\")\n\n// Expression in curly braces\nprintln(\"Next point: \${score + 1}\")",
        question = QuizQuestion(
            type = QuestionType.MULTIPLE_CHOICE,
            prompt = "Given `val player = \"Jordan\"`, which of the following prints `Welcome, Jordan!`?",
            codeSnippet = null,
            options = listOf(
                QuizOption("A", "println(\"Welcome, {player}!\")"),
                QuizOption("B", "println(\"Welcome, (player)!\")"),
                QuizOption("C", "println(\"Welcome, \$player!\")"),
                QuizOption("D", "println(\"Welcome, \" + player + \"!\")")
            ),
            correctLabel = "C",
            explanation = "\$player correctly injects the variable into the string using Kotlin's string templates."
        ),
        topicQuestions = listOf(
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What does `\${score + 1}` inside a string demonstrate?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "A simple variable reference"),
                    QuizOption("B", "An expression embedded in a string template"),
                    QuizOption("C", "A function call"),
                    QuizOption("D", "A null-safe access")
                ),
                correctLabel = "B",
                explanation = "Curly braces `{}` let you embed any expression — not just variable names — inside a string template. The expression is evaluated and its result is inserted into the string."
            ),
            QuizQuestion(
                type = QuestionType.TRUE_FALSE,
                prompt = "You can embed expressions (not just variable names) in Kotlin string templates using `\${}`.",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "True"),
                    QuizOption("B", "False")
                ),
                correctLabel = "A",
                explanation = "Any valid Kotlin expression can go inside `\${}`. For example, `\${a + b}` evaluates the addition and inserts the result."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What will this print?",
                codeSnippet = "val a = 3\nval b = 4\nprintln(\"Sum: \${a + b}\")",
                options = listOf(
                    QuizOption("A", "Sum: \${a + b}"),
                    QuizOption("B", "Sum: 34"),
                    QuizOption("C", "Sum: 7"),
                    QuizOption("D", "It won't compile")
                ),
                correctLabel = "C",
                explanation = "The expression `\${a + b}` evaluates `3 + 4 = 7`, so the string becomes `\"Sum: 7\"`."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "Which of the following correctly embeds a variable named `city` into a string?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "\"Hello {city}\""),
                    QuizOption("B", "\"Hello %city\""),
                    QuizOption("C", "\"Hello \$city\""),
                    QuizOption("D", "\"Hello #city\"")
                ),
                correctLabel = "C",
                explanation = "Kotlin uses the `\$` prefix to embed a variable directly in a string. Curly braces are only needed when embedding a full expression like `\${city.length}`."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What will this print?",
                codeSnippet = "val fruit = \"apple\"\nprintln(\"I have an \$fruit\")",
                options = listOf(
                    QuizOption("A", "I have an \$fruit"),
                    QuizOption("B", "I have an apple"),
                    QuizOption("C", "I have an {fruit}"),
                    QuizOption("D", "It won't compile")
                ),
                correctLabel = "B",
                explanation = "`\$fruit` is replaced by the value of the variable `fruit`, which is `\"apple\"`. The output is `I have an apple`."
            )
        )
    ),
    QuizTopic(
        number = 4,
        title = "Functions",
        lesson = "Functions in Kotlin are declared using the 'fun' keyword. Parameters must explicitly define their names and types (name: Type). The return type is defined after a colon following the parentheses. If the function doesn't return anything meaningful, it returns 'Unit', which can be omitted from the signature.",
        codeExample = "fun greet(name: String): String {\n    return \"Hello, \$name\"\n}\n\nfun printMessage(msg: String) {\n    // Return type is implicitly Unit\n    println(msg)\n}",
        question = QuizQuestion(
            type = QuestionType.MULTIPLE_CHOICE,
            prompt = "What does the following function return?",
            codeSnippet = "fun describe(score: Int): String {\n    return \"Score: \$score\"\n}",
            options = listOf(
                QuizOption("A", "An Int"),
                QuizOption("B", "Nothing"),
                QuizOption("C", "A String"),
                QuizOption("D", "It won't compile")
            ),
            correctLabel = "C",
            explanation = "The function signature explicitly specifies `: String` after the parameters, indicating it returns a String."
        ),
        topicQuestions = listOf(
            QuizQuestion(
                type = QuestionType.TRUE_FALSE,
                prompt = "In Kotlin, a function that does not explicitly return a value implicitly returns `Unit`.",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "True"),
                    QuizOption("B", "False")
                ),
                correctLabel = "A",
                explanation = "`Unit` is Kotlin's equivalent of `void`. If your function has no return type annotation and no return statement, it automatically returns `Unit`."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What keyword is used to declare a function in Kotlin?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "func"),
                    QuizOption("B", "def"),
                    QuizOption("C", "function"),
                    QuizOption("D", "fun")
                ),
                correctLabel = "D",
                explanation = "Kotlin uses the `fun` keyword to declare functions. `func` is Swift, `def` is Python/Ruby, and `function` is JavaScript."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What value does `result` hold after this code runs?",
                codeSnippet = "fun add(a: Int, b: Int): Int {\n    return a + b\n}\nval result = add(3, 4)",
                options = listOf(
                    QuizOption("A", "3"),
                    QuizOption("B", "4"),
                    QuizOption("C", "7"),
                    QuizOption("D", "\"34\"")
                ),
                correctLabel = "C",
                explanation = "`add(3, 4)` computes `3 + 4 = 7` and returns an `Int`. So `result` holds the value `7`."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "Which function signature correctly defines a function that takes a String and returns nothing?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "fun greet(name: String): String { }"),
                    QuizOption("B", "fun greet(name: String) { }"),
                    QuizOption("C", "fun greet(): String { }"),
                    QuizOption("D", "fun String greet(name) { }")
                ),
                correctLabel = "B",
                explanation = "When a function has no return type annotation, it implicitly returns `Unit` (nothing meaningful). Option A returns a `String`, C takes no parameters, and D is not valid Kotlin syntax."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What is the return type of this function?",
                codeSnippet = "fun logMessage(msg: String) {\n    println(msg)\n}",
                options = listOf(
                    QuizOption("A", "String"),
                    QuizOption("B", "Nothing"),
                    QuizOption("C", "Unit"),
                    QuizOption("D", "Void")
                ),
                correctLabel = "C",
                explanation = "A function with no return type annotation implicitly returns `Unit`. `Void` is Java — Kotlin uses `Unit` instead."
            )
        )
    ),
    QuizTopic(
        number = 5,
        title = "if/else expressions",
        lesson = "Kotlin's if/else statements evaluate conditions from top to bottom. The code inside the first condition that evaluates to true is executed. A powerful feature in Kotlin is that 'if' can be used as an expression, meaning it can directly return a value.",
        codeExample = "val temp = 45\nval weather = if (temp > 80) {\n    \"Hot\"\n} else if (temp > 60) {\n    \"Warm\"\n} else {\n    \"Cold\"\n}\nprintln(weather) // Prints Cold",
        question = QuizQuestion(
            type = QuestionType.MULTIPLE_CHOICE,
            prompt = "What will print to the console given this code snippet?",
            codeSnippet = "val temp = 45\nif (temp > 100) print(\"Hot\")\nelse if (temp > 32) print(\"Warm\")\nelse print(\"Cold\")",
            options = listOf(
                QuizOption("A", "Hot"),
                QuizOption("B", "Warm"),
                QuizOption("C", "Cold"),
                QuizOption("D", "HotWarm")
            ),
            correctLabel = "B",
            explanation = "45 is not > 100, but it is > 32. Since 32 is the first matched condition, \"Warm\" prints."
        ),
        topicQuestions = listOf(
            QuizQuestion(
                type = QuestionType.TRUE_FALSE,
                prompt = "In Kotlin, `if` can be used as an expression that directly returns a value.",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "True"),
                    QuizOption("B", "False")
                ),
                correctLabel = "A",
                explanation = "Unlike Java, Kotlin's `if` is an expression. You can assign its result to a variable: `val label = if (score > 90) \"A\" else \"B\"`."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What will this print?",
                codeSnippet = "val x = 10\nval result = if (x > 5) \"big\" else \"small\"\nprintln(result)",
                options = listOf(
                    QuizOption("A", "big"),
                    QuizOption("B", "small"),
                    QuizOption("C", "It won't compile"),
                    QuizOption("D", "null")
                ),
                correctLabel = "A",
                explanation = "`x` is 10, which is greater than 5, so the `if` expression evaluates to `\"big\"`."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What will this print?",
                codeSnippet = "val num = 0\nif (num > 0) println(\"positive\")\nelse if (num < 0) println(\"negative\")\nelse println(\"zero\")",
                options = listOf(
                    QuizOption("A", "positive"),
                    QuizOption("B", "negative"),
                    QuizOption("C", "zero"),
                    QuizOption("D", "Nothing prints")
                ),
                correctLabel = "C",
                explanation = "`num` is 0, so it's not > 0 and not < 0. The final `else` branch runs and prints `\"zero\"`."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "Which statement about `if` as an expression in Kotlin is true?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "`else` is always required in any `if`"),
                    QuizOption("B", "When used as an expression (e.g., assigned to a variable), `else` is required"),
                    QuizOption("C", "`if` cannot be used as an expression"),
                    QuizOption("D", "`if` expressions return `Unit` by default")
                ),
                correctLabel = "B",
                explanation = "When `if` is used as an expression, the compiler needs to guarantee a value for every branch — so `else` is required. As a plain statement, `else` is optional."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What will this print?",
                codeSnippet = "val score = 85\nval grade = if (score >= 90) \"A\"\n    else if (score >= 80) \"B\"\n    else \"C\"\nprintln(grade)",
                options = listOf(
                    QuizOption("A", "A"),
                    QuizOption("B", "B"),
                    QuizOption("C", "C"),
                    QuizOption("D", "It won't compile")
                ),
                correctLabel = "B",
                explanation = "`score` is 85, which is not >= 90 but is >= 80. The second branch matches, so `grade` is `\"B\"`."
            )
        )
    ),
    QuizTopic(
        number = 6,
        title = "when expressions",
        lesson = "The 'when' expression is Kotlin's highly upgraded version of the switch statement. It matches a value against multiple branches. The 'else' branch acts as a catch-all. If 'when' is used as an expression (assigning its result to a variable), the compiler requires an 'else' branch to guarantee a value is returned.",
        codeExample = "val status = 200\nval message = when (status) {\n    200 -> \"OK\"\n    404 -> \"Not Found\"\n    else -> \"Unknown Error\"\n}",
        question = QuizQuestion(
            type = QuestionType.MULTIPLE_CHOICE,
            prompt = "What will print to the console?",
            codeSnippet = "val day = \"Mon\"\nval text = when (day) {\n    \"Mon\" -> \"Start of the week\"\n    \"Fri\" -> \"Almost there\"\n    else -> \"Midweek\"\n}\nprint(text)",
            options = listOf(
                QuizOption("A", "Almost there"),
                QuizOption("B", "Midweek"),
                QuizOption("C", "Start of the week"),
                QuizOption("D", "It won't compile")
            ),
            correctLabel = "C",
            explanation = "The variable `day` matches the branch `\"Mon\"`, so `text` becomes \"Start of the week\"."
        ),
        topicQuestions = listOf(
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What is the Kotlin equivalent of Java's `switch` statement?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "match"),
                    QuizOption("B", "case"),
                    QuizOption("C", "when"),
                    QuizOption("D", "select")
                ),
                correctLabel = "C",
                explanation = "`when` is Kotlin's more powerful replacement for `switch`. Unlike `switch`, it supports ranges, conditions, and can be used as an expression."
            ),
            QuizQuestion(
                type = QuestionType.TRUE_FALSE,
                prompt = "The `else` branch is required in every `when` expression.",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "True"),
                    QuizOption("B", "False")
                ),
                correctLabel = "B",
                explanation = "`else` is only required when `when` is used as an expression (its result is assigned to a variable). As a plain statement, `else` is optional."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What will this print?",
                codeSnippet = "val code = 404\nval msg = when (code) {\n    200 -> \"OK\"\n    404 -> \"Not Found\"\n    else -> \"Unknown\"\n}\nprintln(msg)",
                options = listOf(
                    QuizOption("A", "OK"),
                    QuizOption("B", "Not Found"),
                    QuizOption("C", "Unknown"),
                    QuizOption("D", "404")
                ),
                correctLabel = "B",
                explanation = "`code` is 404, which matches the second branch. So `msg` is assigned `\"Not Found\"`."
            ),
            QuizQuestion(
                type = QuestionType.TRUE_FALSE,
                prompt = "In a `when` expression, a single branch can match multiple values separated by commas (e.g., `1, 2 -> \"low\"`).",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "True"),
                    QuizOption("B", "False")
                ),
                correctLabel = "A",
                explanation = "Kotlin's `when` supports comma-separated values in a branch: `1, 2 -> \"low\"` matches when the subject equals 1 or 2."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What will this print?",
                codeSnippet = "val score = 75\nval grade = when {\n    score >= 90 -> \"A\"\n    score >= 70 -> \"B\"\n    else -> \"C\"\n}\nprintln(grade)",
                options = listOf(
                    QuizOption("A", "A"),
                    QuizOption("B", "B"),
                    QuizOption("C", "C"),
                    QuizOption("D", "It won't compile")
                ),
                correctLabel = "B",
                explanation = "`score` is 75, which is not >= 90 but is >= 70. The second branch matches and `grade` is `\"B\"`. Note that `when` without a subject checks each branch as a boolean condition."
            )
        )
    ),
    QuizTopic(
        number = 7,
        title = "Nullable types & safe calls",
        lesson = "In Kotlin, the type system distinguishes between references that can hold null and those that cannot. By default, types are non-null. Adding a '?' to the end of a type (like String?) allows it to hold null. To safely access properties of a nullable object, use the '?.' safe call operator, which returns null instead of throwing a NullPointerException.",
        codeExample = "var nonNull: String = \"abc\"\n// nonNull = null // Compiler error!\n\nvar nullable: String? = \"abc\"\nnullable = null\n\n// Safe call: evaluates to null\nprintln(nullable?.length)",
        question = QuizQuestion(
            type = QuestionType.MULTIPLE_CHOICE,
            prompt = "What will print to the console?",
            codeSnippet = "var username: String? = null\nprintln(username?.length)",
            options = listOf(
                QuizOption("A", "0"),
                QuizOption("B", "The app will crash"),
                QuizOption("C", "null"),
                QuizOption("D", "An empty string")
            ),
            correctLabel = "C",
            explanation = "Because `username` is null, the safe call `?.` short-circuits and safely returns `null` instead of crashing."
        ),
        topicQuestions = listOf(
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What does the `?` after a type (e.g., `String?`) mean in Kotlin?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "The variable is optional"),
                    QuizOption("B", "The variable can hold null"),
                    QuizOption("C", "The variable's type is inferred"),
                    QuizOption("D", "The variable is read-only")
                ),
                correctLabel = "B",
                explanation = "Appending `?` to a type makes it nullable. `String` can never be null; `String?` can hold either a string value or null."
            ),
            QuizQuestion(
                type = QuestionType.TRUE_FALSE,
                prompt = "A variable declared as `String` (without `?`) can be assigned `null` in Kotlin.",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "True"),
                    QuizOption("B", "False")
                ),
                correctLabel = "B",
                explanation = "Kotlin's type system is null-safe by default. Only nullable types (those ending in `?`) can hold null. Assigning null to a plain `String` is a compile-time error."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "Which operator provides a default value when a nullable expression is null?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "!!"),
                    QuizOption("B", "?."),
                    QuizOption("C", "?:"),
                    QuizOption("D", "::")
                ),
                correctLabel = "C",
                explanation = "The `?:` operator is called the Elvis operator. It returns the left side if non-null, or the right side as a default: `val len = name?.length ?: 0`."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What will this print?",
                codeSnippet = "val name: String? = \"Alice\"\nprintln(name?.uppercase())",
                options = listOf(
                    QuizOption("A", "ALICE"),
                    QuizOption("B", "null"),
                    QuizOption("C", "Alice"),
                    QuizOption("D", "It won't compile")
                ),
                correctLabel = "A",
                explanation = "`name` is not null — it holds `\"Alice\"`. The safe call `?.` proceeds normally and `uppercase()` returns `\"ALICE\"`."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What does the `!!` operator do in Kotlin?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "Safely accesses a nullable value"),
                    QuizOption("B", "Throws a NullPointerException if the value is null"),
                    QuizOption("C", "Converts null to an empty string"),
                    QuizOption("D", "Checks whether a value is non-null")
                ),
                correctLabel = "B",
                explanation = "`!!` is the not-null assertion operator. It tells the compiler \"trust me, this is not null\" — but if it is null at runtime, a `NullPointerException` is thrown. Use it sparingly."
            )
        )
    ),
    QuizTopic(
        number = 8,
        title = "Lists",
        lesson = "Kotlin provides distinct interfaces for standard and mutable collections. The `listOf()` function creates a read-only list. You cannot add or remove elements once it's created. To create a list that allows modifications, you must use `mutableListOf()`. Elements inside lists are zero-indexed.",
        codeExample = "val fixedList = listOf(\"A\", \"B\")\n// fixedList.add(\"C\") // Compiler error\n\nval dynamicList = mutableListOf(\"X\")\ndynamicList.add(\"Y\")\nprintln(dynamicList[0]) // Prints X",
        question = QuizQuestion(
            type = QuestionType.TRUE_FALSE,
            prompt = "Given `val items = listOf(\"a\", \"b\", \"c\")`, you can call `items.add(\"d\")`.",
            codeSnippet = null,
            options = listOf(
                QuizOption("A", "True"),
                QuizOption("B", "False")
            ),
            correctLabel = "B",
            explanation = "`listOf` creates a read-only list. You would need `mutableListOf` to use `.add()`."
        ),
        topicQuestions = listOf(
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "How do you access the second element of a list in Kotlin?",
                codeSnippet = "val nums = listOf(10, 20, 30)",
                options = listOf(
                    QuizOption("A", "nums.get(2)"),
                    QuizOption("B", "nums[1]"),
                    QuizOption("C", "nums.second()"),
                    QuizOption("D", "nums.element(2)")
                ),
                correctLabel = "B",
                explanation = "Kotlin lists are zero-indexed, so the second element is at index 1. `nums[1]` returns `20`. `nums.get(2)` would actually return the third element."
            ),
            QuizQuestion(
                type = QuestionType.TRUE_FALSE,
                prompt = "`mutableListOf()` creates a list that allows adding and removing elements.",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "True"),
                    QuizOption("B", "False")
                ),
                correctLabel = "A",
                explanation = "`mutableListOf()` returns a `MutableList` which exposes `add()`, `remove()`, and other modification functions. `listOf()` is read-only."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What will this print?",
                codeSnippet = "val nums = listOf(10, 20, 30)\nprintln(nums[1])",
                options = listOf(
                    QuizOption("A", "10"),
                    QuizOption("B", "20"),
                    QuizOption("C", "30"),
                    QuizOption("D", "null")
                ),
                correctLabel = "B",
                explanation = "Lists are zero-indexed: index 0 is 10, index 1 is 20, index 2 is 30. So `nums[1]` returns `20`."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "Which function creates a modifiable list in Kotlin?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "listOf()"),
                    QuizOption("B", "arrayOf()"),
                    QuizOption("C", "mutableListOf()"),
                    QuizOption("D", "setOf()")
                ),
                correctLabel = "C",
                explanation = "`mutableListOf()` returns a `MutableList` you can modify. `listOf()` is read-only, `arrayOf()` creates a fixed-size array, and `setOf()` creates a read-only set."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What will this print?",
                codeSnippet = "val items = mutableListOf(\"a\", \"b\")\nitems.add(\"c\")\nprintln(items.size)",
                options = listOf(
                    QuizOption("A", "2"),
                    QuizOption("B", "3"),
                    QuizOption("C", "1"),
                    QuizOption("D", "It won't compile")
                ),
                correctLabel = "B",
                explanation = "The list starts with 2 elements. After `add(\"c\")`, it has 3 elements. `items.size` returns `3`."
            )
        )
    ),
    QuizTopic(
        number = 9,
        title = "for loops & ranges",
        lesson = "Kotlin uses 'for' loops primarily to iterate over iterables (like lists) or ranges. A range uses the '..' operator and builds an inclusive range (both start and end are included). Using 'until' builds a range that excludes the final number.",
        codeExample = "// Prints 1 2 3\nfor (i in 1..3) {\n    print(\"\$i \")\n}\n\n// Prints 1 2\nfor (i in 1 until 3) {\n    print(\"\$i \")\n}",
        question = QuizQuestion(
            type = QuestionType.MULTIPLE_CHOICE,
            prompt = "What will this loop print to the console?",
            codeSnippet = "for (i in 1..4) {\n    print(\"\$i \")\n}",
            options = listOf(
                QuizOption("A", "1 2 3"),
                QuizOption("B", "1 2 3 4 5"),
                QuizOption("C", "1 2 3 4"),
                QuizOption("D", "0 1 2 3")
            ),
            correctLabel = "C",
            explanation = "The `..` operator creates a range that is inclusive on both ends, so it includes 4."
        ),
        topicQuestions = listOf(
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What values does `1 until 5` produce?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "1, 2, 3, 4, 5"),
                    QuizOption("B", "1, 2, 3, 4"),
                    QuizOption("C", "2, 3, 4, 5"),
                    QuizOption("D", "1, 2, 3")
                ),
                correctLabel = "B",
                explanation = "`until` creates a range that excludes the upper bound. So `1 until 5` produces 1, 2, 3, 4 — stopping before 5."
            ),
            QuizQuestion(
                type = QuestionType.TRUE_FALSE,
                prompt = "The range `1..5` includes both 1 and 5.",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "True"),
                    QuizOption("B", "False")
                ),
                correctLabel = "A",
                explanation = "The `..` operator creates an inclusive range — both the start and end values are included. `1..5` iterates over 1, 2, 3, 4, and 5."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What will this print?",
                codeSnippet = "for (i in 1 until 4) {\n    print(\"\$i \")\n}",
                options = listOf(
                    QuizOption("A", "1 2 3 4"),
                    QuizOption("B", "1 2 3"),
                    QuizOption("C", "0 1 2 3"),
                    QuizOption("D", "1 2 3 4 5")
                ),
                correctLabel = "B",
                explanation = "`1 until 4` excludes 4, so the loop iterates over 1, 2, and 3. Output: `1 2 3`."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What will this print?",
                codeSnippet = "val letters = listOf(\"A\", \"B\", \"C\")\nfor (letter in letters) {\n    print(letter)\n}",
                options = listOf(
                    QuizOption("A", "A"),
                    QuizOption("B", "ABC"),
                    QuizOption("C", "[A, B, C]"),
                    QuizOption("D", "A B C")
                ),
                correctLabel = "B",
                explanation = "The `for` loop iterates over each element in `letters` and `print` (without `ln`) outputs each one without a newline or space, resulting in `ABC`."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "Which operator creates an inclusive range in Kotlin?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "to"),
                    QuizOption("B", ".."),
                    QuizOption("C", "until"),
                    QuizOption("D", "range")
                ),
                correctLabel = "B",
                explanation = "The `..` operator creates an inclusive range. `until` creates an exclusive upper-bound range. `to` and `range` are not range operators."
            )
        )
    ),
    QuizTopic(
        number = 10,
        title = "while loops",
        lesson = "A 'while' loop repeats a block of code as long as a specified condition is true. The condition evaluates before the loop block executes. If the condition is false initially, the block is completely skipped.",
        codeExample = "var count = 3\nwhile (count > 0) {\n    print(\"\$count \")\n    count--\n}\n// Prints: 3 2 1",
        question = QuizQuestion(
            type = QuestionType.MULTIPLE_CHOICE,
            prompt = "What will print to the console at the end of this script?",
            codeSnippet = "var count = 0\nwhile (count < 3) {\n    count++\n}\nprintln(count)",
            options = listOf(
                QuizOption("A", "2"),
                QuizOption("B", "3"),
                QuizOption("C", "4"),
                QuizOption("D", "0")
            ),
            correctLabel = "B",
            explanation = "The loop runs for counts 0, 1, and 2. When count hits 3, the condition becomes false and the loop terminates, leaving count exactly at 3."
        ),
        topicQuestions = listOf(
            QuizQuestion(
                type = QuestionType.TRUE_FALSE,
                prompt = "A `while` loop's condition is checked before each iteration.",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "True"),
                    QuizOption("B", "False")
                ),
                correctLabel = "A",
                explanation = "`while` evaluates its condition first. If the condition is true, the body runs; if false, the loop is skipped entirely. This is in contrast to `do-while`, which always runs the body at least once."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What is the key difference between `while` and `do-while` in Kotlin?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "`do-while` always runs its body at least once"),
                    QuizOption("B", "`while` always runs its body at least once"),
                    QuizOption("C", "They are identical"),
                    QuizOption("D", "`do-while` uses a different condition syntax")
                ),
                correctLabel = "A",
                explanation = "A `do-while` loop executes its body first, then checks the condition. This guarantees at least one execution. A `while` loop may execute zero times if its condition starts false."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What will this print?",
                codeSnippet = "var n = 5\nwhile (n > 0) {\n    print(\"\$n \")\n    n -= 2\n}",
                options = listOf(
                    QuizOption("A", "5 3 1"),
                    QuizOption("B", "5 4 3 2 1"),
                    QuizOption("C", "5 3"),
                    QuizOption("D", "5 3 1 -1")
                ),
                correctLabel = "A",
                explanation = "`n` starts at 5. Each iteration subtracts 2: 5 → 3 → 1 → -1. The loop stops when `n` becomes -1 (not > 0). Values printed: `5 3 1`."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What happens if a `while` loop's condition is false from the very start?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "It runs once then stops"),
                    QuizOption("B", "The loop body never executes"),
                    QuizOption("C", "It causes a compile error"),
                    QuizOption("D", "It runs indefinitely")
                ),
                correctLabel = "B",
                explanation = "A `while` loop checks its condition before executing the body. If the condition is false initially, the body is skipped completely and execution continues after the loop."
            ),
            QuizQuestion(
                type = QuestionType.TRUE_FALSE,
                prompt = "An infinite loop can be intentionally created with `while (true) { }`.",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "True"),
                    QuizOption("B", "False")
                ),
                correctLabel = "A",
                explanation = "`while (true)` creates an infinite loop because the condition is always true. This is a common pattern for event loops or server listeners — you would use `break` inside to exit when needed."
            )
        )
    ),
    QuizTopic(
        number = 11,
        title = "Data classes",
        lesson = "A 'data class' is explicitly designed to hold data. By marking a class with 'data', the Kotlin compiler automatically generates helpful utility functions, including 'toString()' (which prints property names and values), 'equals()', 'hashCode()', and 'copy()'.",
        codeExample = "data class User(val name: String)\nval user1 = User(\"Alice\")\nval user2 = User(\"Alice\")\n\n// Prints: true\nprintln(user1 == user2)\n\n// Prints: User(name=Alice)\nprintln(user1)",
        question = QuizQuestion(
            type = QuestionType.MULTIPLE_CHOICE,
            prompt = "What prints over the console when printing this data class instance?",
            codeSnippet = "data class Movie(val title: String, val year: Int)\nval m = Movie(\"Inception\", 2010)\nprintln(m)",
            options = listOf(
                QuizOption("A", "Movie@3a4b5c"),
                QuizOption("B", "Inception"),
                QuizOption("C", "Movie(title=Inception, year=2010)"),
                QuizOption("D", "It won't compile")
            ),
            correctLabel = "C",
            explanation = "Because it's a `data class`, Kotlin auto-generates a clean `toString()` implementation showing the properties."
        ),
        topicQuestions = listOf(
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "Which of the following is NOT automatically generated by a `data class`?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "toString()"),
                    QuizOption("B", "equals()"),
                    QuizOption("C", "hashCode()"),
                    QuizOption("D", "sort()")
                ),
                correctLabel = "D",
                explanation = "Kotlin's `data class` auto-generates `toString()`, `equals()`, `hashCode()`, and `copy()`. It does not generate `sort()` — sorting is a collection-level operation, not something a single object provides."
            ),
            QuizQuestion(
                type = QuestionType.TRUE_FALSE,
                prompt = "Two `data class` instances with the same property values are considered equal when using `==`.",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "True"),
                    QuizOption("B", "False")
                ),
                correctLabel = "A",
                explanation = "A `data class` auto-generates `equals()` based on the constructor properties. Two instances with identical property values will return `true` for `==`, unlike regular classes which compare by reference."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What does the `copy()` function on a data class do?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "Creates an exact duplicate"),
                    QuizOption("B", "Creates a copy with optionally modified properties"),
                    QuizOption("C", "Copies the class definition to a new file"),
                    QuizOption("D", "Serializes the object to a string")
                ),
                correctLabel = "B",
                explanation = "`copy()` creates a new instance with the same values, but you can override specific properties: `val updated = user.copy(name = \"Bob\")`. This is very useful for working with immutable data."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What will this print?",
                codeSnippet = "data class Point(val x: Int, val y: Int)\nval p1 = Point(1, 2)\nval p2 = Point(1, 2)\nprintln(p1 == p2)",
                options = listOf(
                    QuizOption("A", "false"),
                    QuizOption("B", "true"),
                    QuizOption("C", "Point(x=1, y=2)"),
                    QuizOption("D", "It won't compile")
                ),
                correctLabel = "B",
                explanation = "`data class` generates `equals()` based on property values. Since both `p1` and `p2` have `x=1` and `y=2`, they are considered equal and `==` returns `true`."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What keyword marks a class as a data class in Kotlin?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "struct"),
                    QuizOption("B", "record"),
                    QuizOption("C", "data"),
                    QuizOption("D", "model")
                ),
                correctLabel = "C",
                explanation = "You place `data` before `class`: `data class User(val name: String)`. `struct` is Swift/C, `record` is Java 16+, and `model` is not a Kotlin keyword."
            )
        )
    ),
    QuizTopic(
        number = 12,
        title = "Classes",
        lesson = "A normal class defines a blueprint for creating objects, containing state (properties) and behavior (functions). You create an instance simply by calling the class name like a function and passing arguments to its constructor. Normal classes don't automatically generate pretty 'toString()' methods.",
        codeExample = "class Car(val brand: String) {\n    fun honk() {\n        println(\"Beep! I'm a \$brand\")\n    }\n}\n\nval myCar = Car(\"Toyota\")\nmyCar.honk()",
        question = QuizQuestion(
            type = QuestionType.MULTIPLE_CHOICE,
            prompt = "What will print to the console?",
            codeSnippet = "class Dog(val name: String) {\n    fun speak(): String {\n        return \"Woof! I'm \$name\"\n    }\n}\nval d = Dog(\"Rex\")\nprintln(d.speak())",
            options = listOf(
                QuizOption("A", "Woof! I'm Dog"),
                QuizOption("B", "Woof! I'm Rex"),
                QuizOption("C", "Rex"),
                QuizOption("D", "It won't compile")
            ),
            correctLabel = "B",
            explanation = "We instantiate `Dog` with the `name` \"Rex\". Calling `speak()` successfully returns the interpolated string."
        ),
        topicQuestions = listOf(
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What keyword is used to define a class in Kotlin?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "object"),
                    QuizOption("B", "class"),
                    QuizOption("C", "struct"),
                    QuizOption("D", "type")
                ),
                correctLabel = "B",
                explanation = "`class` is the keyword to define a blueprint for objects in Kotlin. `object` declares a singleton, `struct` is Swift/C, and `type` is not used for class definitions."
            ),
            QuizQuestion(
                type = QuestionType.TRUE_FALSE,
                prompt = "In Kotlin, you use the `new` keyword to create an instance of a class.",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "True"),
                    QuizOption("B", "False")
                ),
                correctLabel = "B",
                explanation = "Kotlin does not use `new`. You instantiate a class by calling it like a function: `val car = Car(\"Toyota\")`. The `new` keyword is a Java-ism that Kotlin intentionally dropped."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What will this print?",
                codeSnippet = "class Animal(val sound: String) {\n    fun makeSound() = println(sound)\n}\nval cat = Animal(\"Meow\")\ncat.makeSound()",
                options = listOf(
                    QuizOption("A", "Animal"),
                    QuizOption("B", "sound"),
                    QuizOption("C", "Meow"),
                    QuizOption("D", "It won't compile")
                ),
                correctLabel = "C",
                explanation = "`cat` is created with `sound = \"Meow\"`. Calling `makeSound()` prints the value of `sound`, which is `\"Meow\"`."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "What is the main advantage of using a `data class` over a regular `class`?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "`data class` runs faster at runtime"),
                    QuizOption("B", "`data class` auto-generates `toString()`, `equals()`, and `hashCode()`"),
                    QuizOption("C", "Regular `class` cannot have functions"),
                    QuizOption("D", "There is no practical difference")
                ),
                correctLabel = "B",
                explanation = "`data class` auto-generates `toString()`, `equals()`, `hashCode()`, and `copy()`. A regular `class` requires you to write these manually — and printing an instance shows something like `Dog@1a2b3c` instead of readable property values."
            ),
            QuizQuestion(
                type = QuestionType.MULTIPLE_CHOICE,
                prompt = "How do you call a method named `drive()` on a class instance called `car`?",
                codeSnippet = null,
                options = listOf(
                    QuizOption("A", "car->drive()"),
                    QuizOption("B", "car::drive()"),
                    QuizOption("C", "car.drive()"),
                    QuizOption("D", "drive(car)")
                ),
                correctLabel = "C",
                explanation = "Kotlin uses dot notation to call methods on an instance: `car.drive()`. `->` is C/C++, `::` is used for function references in Kotlin, and `drive(car)` would only work if `drive` were a top-level function."
            )
        )
    )
)
