package hu.szeptamas.tictactoe

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import hu.szeptamas.tictactoe.TicTacToeModel.gameActive
import kotlinx.android.synthetic.main.activity_main.view.*

class TicTacToeView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var paintBackground: Paint = Paint()
    private var paintLine: Paint = Paint()

    init {
        paintBackground.color = Color.BLACK
        paintBackground.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(
            0f, 0f, width.toFloat(),
            height.toFloat(), paintBackground
        )

        drawGameArea(canvas)

        drawPlayers(canvas)
    }


    private fun drawGameArea(canvas: Canvas) {
        // játéktér méretének állítása
        val params: ViewGroup.LayoutParams = ticView.layoutParams
        params.width = context.resources.displayMetrics.widthPixels
        params.height = params.width
        ticView.layoutParams = params

        // border
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        // two horizontal lines
        canvas.drawLine(
            0f, (height / MainActivity.boardSize).toFloat(), width.toFloat(), (height / MainActivity.boardSize).toFloat(),
            paintLine
        )
        canvas.drawLine(
            0f, (2 * height / MainActivity.boardSize).toFloat(), width.toFloat(),
            (2 * height / MainActivity.boardSize).toFloat(), paintLine
        )

        // two vertical lines
        canvas.drawLine(
            (width / MainActivity.boardSize).toFloat(), 0f, (width / MainActivity.boardSize).toFloat(), height.toFloat(),
            paintLine
        )
        canvas.drawLine(
            (2 * width / MainActivity.boardSize).toFloat(), 0f, (2 * width / MainActivity.boardSize).toFloat(), height.toFloat(),
            paintLine
        )
    }


    private fun drawPlayers(canvas: Canvas) {
        for (i in 0 until MainActivity.boardSize) {
            for (j in 0 until MainActivity.boardSize) {
                if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.State.CIRCLE) {
                    val centerX = (i * width / MainActivity.boardSize + width / (MainActivity.boardSize * 2)).toFloat()
                    val centerY = (j * height / MainActivity.boardSize + height / (MainActivity.boardSize * 2)).toFloat()
                    val radius = height / (MainActivity.boardSize * 2) - 2

                    canvas.drawCircle(centerX, centerY, radius.toFloat(), paintLine)
                } else if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.State.CROSS) {
                    canvas.drawLine(
                        (i * width / MainActivity.boardSize).toFloat(), (j * height / MainActivity.boardSize).toFloat(),
                        ((i + 1) * width / MainActivity.boardSize).toFloat(),
                        ((j + 1) * height / MainActivity.boardSize).toFloat(), paintLine
                    )

                    canvas.drawLine(
                        ((i + 1) * width / MainActivity.boardSize).toFloat(), (j * height / MainActivity.boardSize).toFloat(),
                        (i * width / MainActivity.boardSize).toFloat(), ((j + 1) * height / MainActivity.boardSize).toFloat(), paintLine
                    )
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (gameActive == false) {
            return false
        }
        if (event.action == MotionEvent.ACTION_DOWN) {
            val tX = event.x.toInt() / (width / MainActivity.boardSize)
            val tY = event.y.toInt() / (height / MainActivity.boardSize)

            if (tX < MainActivity.boardSize && tY < MainActivity.boardSize && TicTacToeModel.getFieldContent(tX, tY) ==
                TicTacToeModel.State.EMPTY
            ) {
                // van-e győztes
                when (TicTacToeModel.checkWin(tX, tY)) {
                    TicTacToeModel.State.EMPTY -> {
                        (context as MainActivity).showText("It's a draw!")
                        gameActive = false
                        return true
                    }
                    TicTacToeModel.State.CIRCLE -> {
                        (context as MainActivity).showText("O wins!")
                        gameActive = false
                        return true
                    }
                    TicTacToeModel.State.CROSS -> {
                        (context as MainActivity).showText("X wins!")
                        gameActive = false
                        return true
                    }
                }

                TicTacToeModel.changeNextPlayer()
                TicTacToeModel.setFieldContent(tX, tY, TicTacToeModel.getNextPlayer())

                var next = "O"
                if (TicTacToeModel.getNextPlayer() == TicTacToeModel.State.CROSS) {
                    next = "X"
                }
                (context as MainActivity).showText("Next player is: $next")

                invalidate()
            }

        }

        return true
    }

    fun resetGame() {
        TicTacToeModel.resetModel()
        invalidate()
    }

}