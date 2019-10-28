package com.yulrizka.plugin.wordfreq

import javax.swing.table.DefaultTableModel

class TokenTableModel(private val tokens: List<Token>): DefaultTableModel() {
    private val header = arrayOf("Word", "#", "Span", "Proportion")

    init {
        this.setColumnIdentifiers(header)

        for (token in tokens) {
            this.addRow(arrayOf<Any>(token.word, token.count, token.span, token.proportionPct, token.row, token.col))
        }
    }

    override fun isCellEditable(row: Int, column: Int): Boolean {
        return false
    }

    override fun getColumnClass(columnIndex: Int): Class<*> {
        return when (columnIndex) {
            1, 2 -> Int::class.java
            3 -> Float::class.java
            else -> String::class.java
        }
    }

}
