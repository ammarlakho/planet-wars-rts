package games.planetwars.core

data class GameParams (
    val width: Int = 640,
    val height: Int = 480,
    val numPlanets: Int = 10,
    val initialNeutralRatio: Double = 0.5,
    val maxTicks: Int = 2000,
    val radialSeparation: Double = 1.5,  // separation between planets
    val minInitialShipsPerPlanet: Int = 5,
    val maxInitialShipsPerPlanet: Int = 20,
    val minGrowthRate: Double = 0.1,
    val maxGrowthRate: Double = 0.5,
    val growthToRadiusFactor: Double = 100.0,
    val transporterSpeed: Double = 0.1
){

}
