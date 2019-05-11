package com.yulrizka.plugin.wordcount

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.ui.Messages

//[Markdown reference](/tutorials/kotlin.md)
class HelloAction : AnAction("Hello") {
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.getData(PlatformDataKeys.PROJECT)
        Messages.showMessageDialog(project, "Hello from Kotlin Wordfreq2!", "Greeting", Messages.getInformationIcon())
    }
}
