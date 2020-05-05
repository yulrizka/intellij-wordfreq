package com.yulrizka.plugin.wordfreq

import com.intellij.openapi.editor.CaretModel
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.LogicalPosition
import java.awt.Component
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.text.DecimalFormat
import javax.swing.JLabel
import javax.swing.JTable
import javax.swing.table.DefaultTableCellRenderer

class TableManager(
        private val table: JTable,
        private val tokens: MutableList<Token>,
        private val editor: Editor):  MouseAdapter() {

    private val dtm: TokenTableModel = TokenTableModel(tokens)

    init {
        table.model = dtm

        val cellRenderer = DefaultTableCellRenderer()
        cellRenderer.horizontalAlignment = JLabel.RIGHT

        val pctRenderer = object : DefaultTableCellRenderer() {
            override fun getTableCellRendererComponent(table: JTable?, value: Any?, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int): Component {
                var v = value
                if (v != null) {
                    val pct = v as Float?
                    val percentInstance = DecimalFormat.getPercentInstance()
                    percentInstance.maximumFractionDigits = 2
                    v = percentInstance.format(pct)
                }
                horizontalAlignment = JLabel.RIGHT
                return super.getTableCellRendererComponent(table, v, isSelected, hasFocus, row, column)
            }
        }

        table.columnModel.getColumn(1).cellRenderer = cellRenderer
        table.columnModel.getColumn(2).cellRenderer = cellRenderer
        table.columnModel.getColumn(3).cellRenderer = pctRenderer


        table.addMouseListener(this)
    }


    override fun mouseClicked(e: MouseEvent?) {
        super.mouseClicked(e)

        val selectedRow = table.selectedRow
        if (selectedRow < 0) {
            return
        }
        val actualRow: Int = table.convertRowIndexToModel(selectedRow)
        val token: Token = this.tokens[actualRow]


        val caretModel: CaretModel = this.editor.caretModel

        var line: Int = token.row
        var column: Int = token.col

        // get current line
        val caretLine = caretModel.logicalPosition.line
        val caretCol = caretModel.logicalPosition.column
        if (line == caretLine && column == caretCol) {
            // already at the first occurrence, go to last occurrence instead
            line = token.lastLine
            column = token.lastCol
        }

        caretModel.moveToLogicalPosition(LogicalPosition(line, column))
        editor.selectionModel.removeSelection()
    }
}
