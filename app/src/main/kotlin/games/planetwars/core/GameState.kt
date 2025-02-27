package games.planetwars.core

import util.Vec2d

// define an enum class for the owner of a planet which could be either player 1, player 2, or neutral
enum class Player {
    Player1, Player2, Neutral;

    fun opponent(): Player {
        return when (this) {
            Player1 -> Player2
            Player2 -> Player1
            else -> throw IllegalArgumentException("Neutral does not have an opposite")
        }
    }
}

data class Planet (
    var owner: Player,
    var nShips: Int,
    val position: Vec2d,
    val growthRate: Double,
    val radius: Double,
    var transporter: Transporter? = null, // null means we're free to create one, otherwise it's in transit and not available
    val pending: MutableMap<Player, Int> = mutableMapOf(Player.Player1 to 0, Player.Player2 to 0),
    var id: Int = -1,  // will be more convenient to set id later
)

data class Transporter (
    var s: Vec2d,
    var v: Vec2d,
    val owner: Player,
    val destinationIndex: Int,
    val nShips: Int,
) {

}

data class GameState (
    val planets: List<Planet>,  // list of planets does not change in a given game
//    val transporters: MutableList<Transporter> = mutableListOf(),
){

}