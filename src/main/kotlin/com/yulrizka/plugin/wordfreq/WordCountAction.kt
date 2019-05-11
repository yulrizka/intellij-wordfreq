package com.yulrizka.plugin.wordfreq

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.ui.content.ContentFactory

//[Markdown reference](/tutorials/kotlin.md)
class WordCountAction : AnAction("Count word frequency"), ToolWindowFactory {
    private val logger = Logger.getInstance("test")
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
            val wordCount = wordCount(selectedText)
            logger.info(wordCount)
            val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("wordfreqWindow")

            // setup tool window if not exist
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

    private fun wordCount(text: String): String {
        val r = Regex("""\p{Alnum}+""")
        val matches = r.findAll(text)
        val wordGroups = matches.map { it.value }
                .groupBy { it }
                .map { Pair(it.key, it.value.size) }
                .sortedByDescending { it.second }

        val s = StringBuilder()


        var rank = 1
        for ((word, freq) in wordGroups)
            s.append(rank++.toString() + " " +  word + " " + freq + "\n")

        return s.toString()
    }
}
