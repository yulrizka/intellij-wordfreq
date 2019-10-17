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
            // setup tool window if not exist
            val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Word Frequency")
            toolWindow.contentManager.removeAllContents(true)
            val win = WordFreqWindow()
            val contentFactory = ContentFactory.SERVICE.getInstance()
            val content = contentFactory.createContent(win.content, "", false)
            toolWindow.contentManager.addContent(content)

            val wordCounter = WordCounter()
            val wordGroups = wordCounter.wordCount(selectedText)

            win.setTable(wordGroups)
            toolWindow.show(null)

        }
    }

    override fun update(e: AnActionEvent) {
        //Get required data keys
        val project = e.project
        val editor = e.getData(CommonDataKeys.EDITOR)
        //Set visibility only in case of existing project and editor
        e.presentation.isVisible = project != null && editor != null && editor.selectionModel.hasSelection()
    }
}
