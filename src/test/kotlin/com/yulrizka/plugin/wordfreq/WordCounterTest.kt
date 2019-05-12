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

        // build text representation
        val s = StringBuilder()

        for (token in output) {
            s.append("%4d %4d %.2f%% %s\n".format(token.count, token.span, token.proportionPct, token.word))
        }

        println(s)
    }
}
