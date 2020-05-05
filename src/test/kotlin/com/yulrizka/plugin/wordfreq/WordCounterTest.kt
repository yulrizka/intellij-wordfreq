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

        val wc = WordCounter("")
        val output = wc.wordCount(sample, 0)

        // build text representation
        val s = StringBuilder()

        for (token in output) {
            s.append("%4d %4d %.2f%% %s\n".format(token.count, token.span, token.proportionPct, token.word))
        }

        val want = """   6    7 77.78% class
   5    5 55.56% Nested
   3    4 44.44% val
   2    1 11.11% Outer
   2    1 11.11% a
   2    1 11.11% Outside
   2    1 11.11% b
   2    1 11.11% Inside
   2    1 11.11% fun
   2    1 11.11% callMe
   2    1 11.11% Function
   2    1 11.11% call
   2    1 11.11% from
   2    1 11.11% inside
"""
        assertEquals(s.toString(), want)
    }
}
