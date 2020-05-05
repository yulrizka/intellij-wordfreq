package com.yulrizka.plugin.wordfreq


class WordCounter(private val skipWordsCSV: String) {
    fun wordCount(text: String, startLine: Int): List<Token> {
        val r = Regex("""\p{Alnum}+""")
        val tokenMap = mutableMapOf<String, Token>()

        val skipWords = skipWordsCSV.split(",")

        // parse each line, and for each line parse word as token
        val lines = text.lines()
        lines.forEachIndexed { lineNum, str ->
            val realLine = lineNum + startLine
            val matches = r.findAll(str)
            matches.forEach {
                val word = it.value
                if (skipWords.contains(word)) {
                    return@forEach
                }
                val colIndex = it.groups.first()?.range?.first ?: 0
                val t = tokenMap.getOrPut(word) {
                    val t = Token(word, 0, 0, 0F, realLine, it.range.first)
                    t.firstLine = realLine
                    t.firstCol = colIndex

                    t
                }
                t.count += 1
                t.lastLine = realLine
                t.lastCol = colIndex
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
        var proportionPct: Float, // Percentages of span to total lines
        var row: Int,
        var col: Int
) {

    var firstLine = 0
    var firstCol = 0
    var lastLine = 0
    var lastCol = 0

    fun calculateSpan(totalLine: Int) {
        span = lastLine - firstLine + 1
        proportionPct = span / totalLine.toFloat()
    }
}
