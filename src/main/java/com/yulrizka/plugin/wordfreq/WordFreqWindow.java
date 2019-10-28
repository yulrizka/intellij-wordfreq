package com.yulrizka.plugin.wordfreq;

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

    public void setTable(@NotNull List<Token> tokens) {
        new TableManager(table, tokens);
    }
}
