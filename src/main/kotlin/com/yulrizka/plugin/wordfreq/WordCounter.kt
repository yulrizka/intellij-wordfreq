package com.yulrizka.plugin.wordfreq


class WordCounter {
    fun wordCount(text: String): List<Token> {
        val r = Regex("""\p{Alnum}+""")
        val tokenMap = mutableMapOf<String, Token>()

        // parse each line, and for each line parse word as token
        val lines = text.lines()
        lines.forEachIndexed { lineNum, str ->
            val matches = r.findAll(str)
            matches.forEach {
                val word = it.value
                val t = tokenMap.getOrPut(word) {
                    val firstIndex = it.groups.first()?.range?.first ?: 0
                    val t = Token(word, 0, 0, 0F)
                    t.firstLine = lineNum
                    t.firstCol = firstIndex

                    t
                }
                t.count += 1
                t.lastLine = lineNum
            }
        }

        val totalLine = lines.count()
        return tokenMap.map {
            it.value.calculateSpan(totalLine)
            it.value
        }.sortedByDescending { it.count }
    }
}

class Token(
        val word: String,
        var count: Int,
        var span: Int, // line difference between the first and last occurrence
        var proportionPct: Float // Percentages of span to total lines
) {

    var firstLine = 0
    var firstCol = 0
    var lastLine = 0

    fun calculateSpan(totalLine: Int) {
        span = lastLine - firstLine + 1
        proportionPct = span / totalLine.toFloat()
    }
}
