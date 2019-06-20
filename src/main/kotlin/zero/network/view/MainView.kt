package zero.network.view

import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.Label
import javafx.scene.layout.Border
import javafx.scene.layout.BorderStroke
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.layout.BorderWidths
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import zero.network.app.Styles
import tornadofx.*
import zero.network.model.Board
import zero.network.model.machine

class MainView : View("4 in Line") {

    private val labels: Array<Array<Label?>> = Array(10){ arrayOfNulls<Label?>(10) }
    private var game = Board()

    override val root = gridpane {
        row {
            for(i in 1..10){
                button("$i") {
                    style { myStyle() }
                    action {
                        game.put(i-1,1)
                        game = machine(game)
                        fill()
                        val winner = game.existWinner()
                        if(winner != 0){
                            val message = if(winner == 1) "Player win" else "Bot win"
                            alert(Alert.AlertType.INFORMATION,"we have a winner",message)
                            game = Board()
                            fill()
                        }
                    }
                }
            }
        }
        for(i in labels.indices){
            row {
                for (j in labels[i].indices){
                    labels[i][j] = label {
                        style {
                            myStyle()
                        }
                    }
                }
            }
        }
    }

    private fun InlineCss.myStyle() {
        borderColor += box(c("#a1a1a1"))
        minWidth = 40.px
        minHeight = 40.px
        alignment = Pos.BASELINE_CENTER
    }

    private fun fill() {
        for (j in labels.indices) {
            for (k in labels[j].indices) {
                val bar = if (game.board[j][k] == 1) "X" else if (game.board[j][k] == 2) "O" else " "
                labels[j][k]!!.text = bar
            }
        }
    }
}