package com.yulrizka.plugin.wordfreq

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class WordCounterTest {

    @Test
    fun wordCount() {
        val sample =
                """
class Outer {

    val a = "Outside Nested class."

    class Nested {
        val b = "Inside Nested class."
        fun callMe() = "Function call from inside Nested class."
    }
}
                """.trimIndent()

        println(sample)
        val wc = WordCounter()
        val output = wc.wordCount(sample)
        println(output)
    }
}
