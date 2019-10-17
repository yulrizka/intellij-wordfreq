package com.yulrizka.plugin.wordfreq;

import org.jetbrains.annotations.NotNull;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
        DefaultTableModel dtm = new DefaultTableModel(0,0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 1:
                    case 2:
                        return Integer.class;
                    case 3:
                        return Float.class;
                    default:
                        return String.class;
                }
            }
        };
        String[] header = new String[]{"Word", "#", "Span", "Proportion"};
        dtm.setColumnIdentifiers(header);
        table.setModel(dtm);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.RIGHT);

        DefaultTableCellRenderer pctRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value != null) {
                    Float pct = (Float) value;
                    NumberFormat percentInstance = DecimalFormat.getPercentInstance();
                    percentInstance.setMaximumFractionDigits(2);
                    value = percentInstance.format(pct);
                }
                setHorizontalAlignment(JLabel.RIGHT);
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };

        table.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(pctRenderer);

        for (Token token : wordGroups) {
            dtm.addRow(new Object[]{
                    token.getWord(),
                    token.getCount(),
                    token.getSpan(),
                    token.getProportionPct()
            });
        }
    }
}
