package com.yulrizka.plugin.wordfreq;

import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;

public class WordFreqWindow {
    private JPanel panel;
    private JTextPane textPane;

    public WordFreqWindow(ToolWindow toolWindow) {

    }

    public void setText(String t) {
        textPane.setText(t);
    }

    public JPanel getContent() {
        return panel;
    }
}
