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
    val question: QuizQuestion
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
        )
    )
)
