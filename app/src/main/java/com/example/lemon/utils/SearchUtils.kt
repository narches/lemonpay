
package com.example.lemon.components


import com.example.lemon.model.SearchIntent

fun detectSearchIntent(query: String): SearchIntent {
    val trimmed = query.trim()

    return when {
        trimmed.all { it.isDigit() } && trimmed.length >= 10 ->
            SearchIntent.ACCOUNT_NUMBER

        trimmed.matches(Regex("""\d+(\.\d{1,2})?""")) ->
            SearchIntent.AMOUNT

        trimmed.matches(Regex("""\d{4}-\d{2}-\d{2}""")) ->
            SearchIntent.DATE

        else ->
            SearchIntent.UNKNOWN
    }
}

annotation class SearchUtils
