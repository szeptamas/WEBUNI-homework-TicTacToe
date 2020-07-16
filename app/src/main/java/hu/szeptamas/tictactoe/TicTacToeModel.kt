package hu.szeptamas.tictactoe

import hu.szeptamas.tictactoe.MainActivity.Companion.boardSize

object TicTacToeModel {
    enum class State {EMPTY, CIRCLE, CROSS}

    private val board = Array(boardSize) { Array(boardSize) {State.EMPTY} }
    private var moveCount = 0
    private var nextPlayer = State.CIRCLE

    fun resetModel() {
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                board[i][j] = State.EMPTY
            }
        }
        nextPlayer =  State.CIRCLE
    }

    fun getFieldContent(x: Int, y: Int) = board[x][y]

    fun setFieldContent(x: Int, y: Int, content: State) {
        board[x][y] = content
    }

    fun getNextPlayer(): State {
        moveCount++
        return nextPlayer
    }

    fun changeNextPlayer() {
        nextPlayer = if (nextPlayer == State.CIRCLE) State.CROSS else State.CIRCLE
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
                return nextPlayer
            }
        }

        // sorok
        for (i in 0 until boardSize) {
            if (board.get(i).get(tY) !== getFieldContent(tX, tY)) break
            if (i == boardSize - 1) {
                return nextPlayer
            }
        }

        // átlók
        if (tX == tY) {
            // átlón vagyunk
            for (i in 0 until boardSize) {
                if (board.get(i).get(i) !== getFieldContent(tX, tY)) break
                if (i == boardSize - 1) {
                    return nextPlayer
                }
            }
        }

//        // anti átlók
//        if (x + y == n - 1) {
//            for (i in 0 until n) {
//                if (board.get(i).get(n - 1 - i) !== s) break
//                if (i == n - 1) {
//                    //report win for s
//                }
//            }
//        }

        // döntetlen
        if (moveCount.toDouble() == Math.pow(boardSize.toDouble(), 2.0) - 1) {
            return State.EMPTY
        }

        return null
    }

}