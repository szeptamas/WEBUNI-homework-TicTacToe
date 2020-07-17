package hu.szeptamas.tictactoe

import hu.szeptamas.tictactoe.MainActivity.Companion.boardSize
import kotlin.math.pow

object TicTacToeModel {
    enum class State {EMPTY, CIRCLE, CROSS}

    private val board = Array(boardSize) { Array(boardSize) { State.EMPTY } }
    internal var moveCount = 0
    internal var actualPlayer = State.CIRCLE
    var gameActive: Boolean = true

    fun resetModel() {
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                board[i][j] = State.EMPTY
            }
        }
        gameActive = true
        actualPlayer =  State.CIRCLE
        moveCount = 0
    }

    fun getFieldContent(x: Int, y: Int) = board[x][y]

    fun setFieldContent(x: Int, y: Int, content: State) {
        board[x][y] = content
    }

    fun changeNextPlayer(): State {
        actualPlayer = if (actualPlayer == State.CIRCLE) State.CROSS else State.CIRCLE
        return actualPlayer
    }

    /*
    győzelem ellenőrzésre ötlet innen:
    https://stackoverflow.com/questions/1056316/algorithm-for-determining-tic-tac-toe-game-over
    */
    fun checkWin(tX: Int, tY: Int): State? {
        // oszlopok
        for (i in 0 until boardSize) {
            if (board[tX][i] != getFieldContent(tX, tY)) break
            if (i == boardSize - 1) {
                return actualPlayer
            }
        }

        // sorok
        for (i in 0 until boardSize) {
            if (board[i][tY] != getFieldContent(tX, tY)) break
            if (i == boardSize - 1) {
                return actualPlayer
            }
        }

        // átlók egyik irány
        if (tX == tY) {
            for (i in 0 until boardSize) {
                if (board[i][i] != getFieldContent(tX, tY)) break
                if (i == boardSize - 1) {
                    return actualPlayer
                }
            }
        }

        // átlók másik irány
        if (tX + tY == boardSize - 1) {
            for (i in 0 until boardSize) {
                if (board[i][boardSize - 1 - i] != getFieldContent(tX, tY)) break
                if (i == boardSize - 1) {
                    return actualPlayer
                }
            }
        }

        // döntetlen
        if (moveCount.toDouble() == boardSize.toDouble().pow(2.0)) {
            return State.EMPTY
        }

        return null
    }

}