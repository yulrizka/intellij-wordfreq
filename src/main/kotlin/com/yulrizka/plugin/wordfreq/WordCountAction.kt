package com.yulrizka.plugin.wordfreq

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.ui.content.ContentFactory

class WordCountAction : AnAction("Count Word Frequency"), ToolWindowFactory {
    private val logger = Logger.getInstance("wordFreq")
    var wordFreqWindow: WordFreqWindow? = null

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        toolWindow.hide(null)
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.getData(PlatformDataKeys.PROJECT)

        val editor = e.getRequiredData(CommonDataKeys.EDITOR)

        //val document = editor.document
        val selectionModel = editor.selectionModel
        val selectedText = selectionModel.selectedText


        if (selectedText != null && project != null) {
            val wordCounter = WordCounter()
            val wordCount = wordCounter.wordCount(selectedText)

            // setup tool window if not exist
            val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Word Frequency")
            if (this.wordFreqWindow == null) {
                val win = WordFreqWindow(toolWindow)
                this.wordFreqWindow = win

                val contentFactory = ContentFactory.SERVICE.getInstance()
                val content = contentFactory.createContent(win.content, "", false)
                toolWindow.contentManager.addContent(content)
            }

            this.wordFreqWindow?.setText(wordCount)
            toolWindow.show(null)

        }
    }

    override fun update(e: AnActionEvent) {
        //Get required data keys
        val project = e.project
        val editor = e.getData(CommonDataKeys.EDITOR)
        //Set visibility only in case of existing project and editor
        e.presentation.setVisible(project != null && editor != null && editor.selectionModel.hasSelection())
    }

}

class WordCounter {
    fun wordCount(text: String): String {
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
                    val t = Token(word, lineNum, firstIndex)
                    t
                }
                t.count += 1
                t.lastLine = lineNum
            }
        }

        val totalLine = lines.count()
        val wordGroups = tokenMap.map {
            it.value.calculateSpan(totalLine)
            Pair(it, it.value.count)
        }.sortedByDescending { it.second }


        val s = StringBuilder()


        for ((token, freq) in wordGroups) {
            s.append("%4d %4d %.2f %s\n".format(freq, token.value.span, token.value.proportionPct, token.value.word))
        }

        return s.toString()
    }
}

class Token(val word: String, val firstLine: Int, val firstCol: Int) {
    var count = 0
    var lastLine = 0
    var span = 0 // line difference between the first and last occurrence
    var proportionPct: Float = 0F //

    fun calculateSpan(totalLine: Int) {
        span = lastLine - firstLine + 1
        proportionPct = span / totalLine.toFloat() * 100
    }
}
