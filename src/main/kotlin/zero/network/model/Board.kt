package zero.network.model

class Board(o: Board? = null) {

    var board: Array<IntArray> = Array(10) { IntArray(10) }
        private set

    init {
        if (o == null){
            board.indices.forEach { board[it] = IntArray(10) }
        }else{
            board = o.board.map { it.clone() }.toTypedArray()
        }
    }

    fun existWinner(): Int{
        for( i in 0..9 ){
            for( j in 0..6) {
                if (board[0 + j][i] == board[1 + j][i] && board[1 + j][i] == board[2 + j][i] && board[2 + j][i] == board[3 + j][i] && board[3 + j][i] != 0)
                    return board[0 + j][i]
                if (board[i][0 + j] == board[i][1 + j] && board[i][1 + j] == board[i][2 + j] && board[i][2 + j] == board[i][3 + j] && board[i][3 + j] != 0)
                    return board[i][0 + j]
                if (i < 7){
                    var num = board[0 + i][0 + j]
                    if ( num == board[1 + i][1 + j] && num == board[2 + i][2 + j] && num == board[3 + i][3 + j] && num != 0)
                        return board[0 + i][0 + j]
                    num = board[0 + i][3 + j]
                    if ( num == board[1 + i][2 + j] && num == board[2 + i][1 + j] && num == board[3 + i][0 + j] && num != 0)
                        return board[0 + i][3 + j]
                }
                
            }
        }
        return 0
    }

    fun nearToWin(): MutableMap<Int,Int>{
        val count: MutableMap<Int,Int> = mutableMapOf(Pair(1,0), Pair(2,0))
        for( i in 0..9 ){
            for( j in 0..6) {
                if (board[0 + j][i] != 0 && board[0 + j][i] == board[1 + j][i] && board[1 + j][i] == board[2 + j][i] && board[3 + j][i] == 0)
                    count[board[0 + j][i]] = count[board[0 + j][i]]!! + 1
                if (board[i][0 + j] != 0&& board[i][0 + j] == board[i][1 + j] && board[i][1 + j] == board[i][2 + j] && board[i][3 + j] == 0)
                    count[board[i][0 + j]] = count[board[i][0 + j]]!! + 1
                if (i < 7){
                    var num = board[0 + i][0 + j]
                    if ( num != 0 && num == board[1 + i][1 + j] && num == board[2 + i][2 + j] && board[3 + i][3 + j] == 0)
                        count[board[0 + i][0 + j]] = count[board[0 + i][0 + j]]!! + 1
                    num = board[0 + i][3 + j]
                    if ( num != 0 && num == board[1 + i][2 + j] && num == board[2 + i][1 + j] && board[3 + i][0 + j] == 0)
                        count[board[0 + i][3 + j]] = count[board[0 + i][3 + j]]!! + 1
                }
            }
        }
        return count
    }

    fun put(col: Int, player: Int): String{
        if (board[0][col] != 0)
            return "impossible"
        for (i in 0..9) {
            if (board[i][col] != 0 && i - 1 >= 0) {
                board[i - 1][col] = player
                return ""
            }
        }
        if (board[9][col] == 0)
            board[9][col] = player
        else
            return "impossible"
        return ""
    }


}