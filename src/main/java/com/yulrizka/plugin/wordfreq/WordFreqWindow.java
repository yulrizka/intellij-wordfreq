package com.yulrizka.plugin.wordfreq;

import com.intellij.openapi.wm.ToolWindow;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class WordFreqWindow {
    private JPanel panel;
    private JTable table;

    public WordFreqWindow() {

    }

    public JPanel getContent() {
        return panel;
    }

    public void setTable(@NotNull List<Token> wordGroups) {
        DefaultTableModel dtm = new DefaultTableModel(0,0);
        String[] header = new String[]{"Word", "#", "Span", "Proportion"};
        dtm.setColumnIdentifiers(header);
        table.setModel(dtm);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);

        for (Token token : wordGroups) {
            dtm.addRow(new Object[]{
                    token.getWord(),
                    token.getCount(),
                    token.getSpan(),
                    String.format("%.2f%%", token.getProportionPct())
            });
        }
    }
}
