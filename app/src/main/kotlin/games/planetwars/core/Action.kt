package games.planetwars.core

data class Action (
    val playerId: Player,
    val sourcePlanetId: Int,
    val destinationPlanetId: Int,
    val numShips: Int
) {
}

fun main() {
    val action = Action(Player.Player1, 2, 3, 12)
    println(action)
}
