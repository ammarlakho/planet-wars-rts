package games.planetwars.core

import util.Vec2d

// define an enum class for the owner of a planet which could be either player 1, player 2, or neutral
enum class Player {
    Player1, Player2, Neutral
}

data class Planet (
    var owner: Player,
    var nShips: Int,
)

data class Transporter(
    var s: Vec2d,
    var v: Vec2d,
) {

}

data class GameState (val planets: List<Planet>){
    // can have game state as a list of planets?

}