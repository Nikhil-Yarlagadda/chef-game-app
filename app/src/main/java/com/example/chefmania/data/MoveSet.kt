package com.example.chefmania.data

import com.example.chefmania.R

class MoveSet(
    val moves: List<Coordinate>,
    val name: String,
    val resId: Int,
    val resBoardId: Int
) {


    fun possibleMoves(piece: Piece, player:Player, board:List<List<Coordinate>>): List<Coordinate>
    {
        val m:MutableList<Coordinate> =  List<Coordinate>(moves.size){index ->
            if(player.homeBase.y == 0){
                piece.pos.add(moves.get(index))
            }
            else{
                piece.pos.sub(moves.get(index))
            }
        }.toMutableList()
        val iterator = m.iterator()
        while(iterator.hasNext()){
            val item = iterator.next()
            if(item.x > 4 || item.x < 0){
                iterator.remove()
                continue
            }
            if(item.y > 4 || item.y < 0){
                iterator.remove()
                continue
            }
            if(board[item.x][item.y].occupant == piece.pos.occupant){
                iterator.remove()
                continue
            }
        }
        return m
    }

    companion object{
        fun staticSelect5():List<MoveSet>{
            var mutableMovesets: MutableList<MoveSet> = movesets.toMutableList()
            mutableMovesets.shuffle()
            return mutableMovesets.take(5);
        }

        val movesets: List<MoveSet> = listOf(
            MoveSet(name = "Spaghetti", moves = listOf(Coordinate(-1,1),
                Coordinate(-2,0), Coordinate(2,0)),
                resId = R.drawable.spaghetti,
                resBoardId = R.drawable.spaghettiboard
            ),
            MoveSet(name = "Wings", moves = listOf(Coordinate(0,-1),
                Coordinate(1,0), Coordinate(1,1)),
                resId = R.drawable.wings,
                resBoardId = R.drawable.wingsboard),
            MoveSet(name = "Eggs", moves = listOf(Coordinate(0,1),
                Coordinate(0,-1), Coordinate(1,0)),
                resId = R.drawable.eggs,
                resBoardId = R.drawable.eggsboard),
            MoveSet(name = "Hotdog", moves = listOf(Coordinate(-1,0),
                Coordinate(1,0), Coordinate(2,1)),
                resId = R.drawable.hotdog,
                resBoardId = R.drawable.hotdogboard),
            MoveSet(name = "Taco", moves = listOf(Coordinate(-1,0),
                Coordinate(0,1), Coordinate(1,0)),
                resId = R.drawable.taco,
                resBoardId = R.drawable.tacoboard),
            MoveSet(name = "Hamburger", moves = listOf(Coordinate(-1,1),
                Coordinate(1,1), Coordinate(1,0)),
                resId = R.drawable.burger,
                resBoardId = R.drawable.burgerboard),
            MoveSet(name = "Curry", moves = listOf(Coordinate(-1,0),
                Coordinate(-1,0), Coordinate(1,0)),
                resId = R.drawable.curry,
                resBoardId = R.drawable.curryboard),
            MoveSet(name = "Sushi", moves = listOf(Coordinate(-1,0),
                Coordinate(-2,1), Coordinate(1,0)),
                resId = R.drawable.spaghetti,
                resBoardId = R.drawable.spaghettiboard),
            MoveSet(name = "Mac n' Cheese", moves = listOf(Coordinate(0,1),
                Coordinate(-1,0), Coordinate(0,-1)),
                resId = R.drawable.macandcheese,
                resBoardId = R.drawable.macandcheeseboard),
            MoveSet(name = "Steak", moves = listOf(Coordinate(-1,1),
                Coordinate(-1,0), Coordinate(0,-1)),
                resId = R.drawable.steak,
                resBoardId = R.drawable.steakboard),
            MoveSet(name = "Cake", moves = listOf(Coordinate(0,-1),
                Coordinate(-1,0), Coordinate(1,1)),
                resId = R.drawable.cake,
                resBoardId = R.drawable.cakeboard),
            MoveSet(name = "Salad", moves = listOf(Coordinate(-1,1),
                Coordinate(-1,-1), Coordinate(1,-1)),
                resId = R.drawable.salad,
                resBoardId = R.drawable.saladboard),
            MoveSet(name = "Rice", moves = listOf(Coordinate(0,2),
                Coordinate(-2,-2), Coordinate(2,-2)),
                resId = R.drawable.rice,
                resBoardId = R.drawable.riceboard),
            MoveSet(name = "Pizza", moves = listOf(Coordinate(0,1),
                Coordinate(-1,-1), Coordinate(1,-1)),
                resId = R.drawable.pizza,
                resBoardId = R.drawable.pizzaboard),
            MoveSet(name = "Ice Cream", moves = listOf(Coordinate(0,1),
                Coordinate(0,-1), Coordinate(0,-2)),
                resId = R.drawable.icecream,
                resBoardId = R.drawable.icecreamboard),
        )

    }
}

/*fun main(){
    val squares: List<List<Coordinate>> = listOf(
        listOf(Coordinate(0,0),
            Coordinate(0,1),
            Coordinate(0,2),
            Coordinate(0,3),
            Coordinate(0,4)),
        listOf(Coordinate(1,0),
            Coordinate(1,1),
            Coordinate(1,2),
            Coordinate(1,3),
            Coordinate(1,4)),
        listOf(Coordinate(2,0),
            Coordinate(2,1),
            Coordinate(2,2),
            Coordinate(2,3),
            Coordinate(2,4)),
        listOf(Coordinate(3,0),
            Coordinate(3,1),
            Coordinate(3,2),
            Coordinate(3,3),
            Coordinate(3,4)),
        listOf(Coordinate(4,0),
            Coordinate(4,1),
            Coordinate(4,2),
            Coordinate(4,3),
            Coordinate(4,4)),
    )
    var piece: Piece = Piece(squares[0][0])
    piece.pos.occupant = Occupancy.Plyr
    var card: MoveSet = MoveSet(name = "str", moves = listOf(Coordinate(-1,-1), Coordinate(1,1)))

    var list = card.possibleMoves(piece,squares,)
    for(c in list){
        print("" + c.x + ", " +c.y)
        println()
    }
}*/

