package com.yulrizka.plugin.wordfreq;

import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;

import javax.swing.JPanel;
import javax.swing.JTable;
import java.util.List;

public class WordFreqWindow {
    private JPanel panel;
    private JTable table;

    public WordFreqWindow() {

    }

    public JPanel getContent() {
        return panel;
    }

    public void setTable(@NotNull List<Token> tokens, Editor editor) {
        new TableManager(table, tokens, editor);
    }
}
