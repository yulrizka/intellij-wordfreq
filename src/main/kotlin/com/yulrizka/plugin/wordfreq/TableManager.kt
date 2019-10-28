package com.yulrizka.plugin.wordfreq

import java.awt.Component
import java.text.DecimalFormat
import javax.swing.JLabel
import javax.swing.JTable
import javax.swing.table.DefaultTableCellRenderer

class TableManager(private val table: JTable, tokens : List<Token>) {
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


        table.selectionModel.addListSelectionListener {
            val selectedRowNum = table.selectedRow
            val row = this.dtm.dataVector.get(selectedRowNum)
            println(row.toString())
        }
    }
}