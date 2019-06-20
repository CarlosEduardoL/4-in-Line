package zero.network.model

import kotlin.random.Random.Default.nextBoolean

class DecisionTree(private val board:Board,
                   private val turn: Int,
                   private val bot_number: Int = 1,
                   private val max_bots: Int = 4){

    private val working: MutableMap<Board,Boolean> = mutableMapOf()

    private val points: Double = 50.0

    fun getMovement() : Board{
        val possibilities = mutableListOf<Pair<DecisionTree,Double>>()
        for (i in 0..9){
            val subBoard = Board(board)
            if (subBoard.put(i,turn) != "impossible"){
                working[subBoard] = true
                Thread{
                    val tr = DecisionTree(subBoard,isTurn(),bot_number+1,max_bots)
                    possibilities.add(Pair(tr,tr.getCost()))
                    working[subBoard] = false
                }.start()
            }
        }
        print("thinking")
        while (true in working.values){
            print(".")
            Thread.sleep(10)
        }
        println()
        var ind = 0
        for (i in 0 until possibilities.size){
           if (possibilities[i].second > possibilities[ind].second)
               ind = i
           else if (possibilities[i].second == possibilities[ind].second && nextBoolean())
               ind = i
        }
        return if(possibilities.size>0) possibilities[ind].first.board else board
    }

    private fun getCost(): Double {
        val winner = board.existWinner()
        when {
            winner != 0 -> {
                if (winner == 1 && bot_number <= 4)
                    return -50000000.0
                if (winner == 2 && bot_number <= 3)
                    return 50000000.0
                return if (winner == 2) (points / bot_number) else -(points / bot_number)
            }
            bot_number == max_bots -> {
                val count = board.nearToWin()
                return (count[2]!! - count[1]!!) * (points / bot_number)
            }
            else -> {
                val costSons = DoubleArray(10)
                for(i in costSons.indices){
                    val subBoard = Board(board)
                    if (subBoard.put(i,turn) != "impossible")
                        costSons[i] = DecisionTree(subBoard,isTurn(),bot_number+1,max_bots).getCost()
                }
                return costSons.sum()
            }
        }
    }

    private fun isTurn(): Int {
        return when(turn){
            1 -> 2
            else -> 1
        }
    }
}

fun machine(board: Board):Board{
    return DecisionTree(board,2,1,4).getMovement()
}