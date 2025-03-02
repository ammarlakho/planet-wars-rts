package games.planetwars.agents.evo

import games.planetwars.agents.Action
import games.planetwars.agents.DoNothingAgent
import games.planetwars.agents.PlanetWarsAgent
import games.planetwars.agents.PlanetWarsPlayer
import games.planetwars.core.ForwardModel
import games.planetwars.core.GameParams
import games.planetwars.core.GameState
import games.planetwars.core.Player
import kotlin.random.Random

data class GameStateWrapper(
    // todo: write a neat gamer state wrapper


    val gameState: GameState,
    val params: GameParams,
    val opponentModel: PlanetWarsAgent = DoNothingAgent())
{
    val forwardModel = ForwardModel(gameState, params)
    fun isTerminal() = forwardModel.isTerminal()
//    fun step(actions: Map<Player, Int>) = forwardModel.step(actions)
}

data class SimpleEvoAgent(
    var flipAtLeastOneValue: Boolean = true,
    // var expectedMutations: Double = 10.0,
    var probMutation: Double = 0.2,
    var sequenceLength: Int = 200,
    var nEvals: Int = 20,
    var useShiftBuffer: Boolean = true,
    var repeatProb: Double = 0.5,  // only used with mutation transducer
    var epsilon: Double = 1e-6,
    var timeLimitMillis: Long = 10,

) : PlanetWarsPlayer() {
    override fun getAgentType(): String {
        return "EvoAgent-$sequenceLength-$nEvals-$probMutation-$useShiftBuffer"
    }

    internal var random = Random

    // these are all the parameters that control the agend
    internal var buffer: FloatArray? = null // randomPoint(sequenceLength)

    // SimplePlayerInterface opponentModel = new RandomAgent();
//    override fun prepareToPlayAs(player: Player): PlanetWarsAgent {
//        this.player = player
//    }

    val solutions = ArrayList<FloatArray>()
    val scores = ArrayList<DoubleArray>()

    var bestScore: Double? = null

    var x: Int? = 1

    fun getActions(gameState: GameState): FloatArray {
        var solution = buffer ?: randomPoint()
        if (useShiftBuffer) {
            solution = shiftLeftAndRandomAppend(solution)
        }
        solutions.clear()
        solutions.add(solution)
        scores.clear()
        for (i in 0 until nEvals) {
            // evaluate the current one
            val scoreArrray1 = DoubleArray(solution.size)
            val scoreArrray2 = DoubleArray(solution.size)
            val mut = mutate(solution, probMutation)
            val curScore = epsilon * random.nextDouble() + evalSeq(gameState.copy(), solution, scoreArrray1)
            val mutScore = epsilon * random.nextDouble() + evalSeq(gameState.copy(), mut, scoreArrray2)
            bestScore = if (curScore > mutScore) curScore else mutScore
            if (mutScore >= curScore) {
                solution = mut
                if (mutScore > curScore) {
//                    println(scoreArrray1.asList())
//                    println(scoreArrray2.asList())
                    // println("$i:\t $mutScore > $curScore")

                }
            }
            solutions.add(mut)
            scores.add(scoreArrray1)
            scores.add(scoreArrray2)
        }
        // println(Arrays.)
        // println(StatSummary("Raw Scores").add(scores[0].asList()))
        buffer = solution
        return solution
    }

    private fun mutate(v: FloatArray, mutProb: Double): FloatArray {

        val n = v.size
        val x = FloatArray(n)
        // pointwise probability of additional mutations
        // choose element of vector to mutate
        var ix = random.nextInt(n)
        if (!flipAtLeastOneValue) {
            // setting this to -1 means it will never match the first clause in the if statement in the loop
            // leaving it at the randomly chosen value ensures that at least one bit (or more generally value) is always flipped
            ix = -1
        }
        // copy all the values faithfully apart from the chosen one
        for (i in 0 until n) {
            if (i == ix || random.nextDouble() < mutProb) {
                x[i] = random.nextFloat()
            } else {
                x[i] = v[i]
            }
        }
        return x
    }

    // random point in n-dimensional space in unit hypercube; n = sequenceLength
    private fun randomPoint(): FloatArray {
        val p = FloatArray(sequenceLength)
        for (i in p.indices) {
            p[i] = random.nextFloat()
        }
        return p
    }

    // todo: change this to use a circular buffer to avoid all the shifting

    private fun shiftLeftAndRandomAppend(v: FloatArray): FloatArray {
        val p = FloatArray(v.size)
        for (i in 0 until p.size - 1) {
            p[i] = v[i + 1]
        }
        p[p.size - 1] = random.nextFloat()
        return p
    }



    private fun evalSeq(gameState: GameState, seq: FloatArray, scoreArray: DoubleArray): Double {
//        var gameState = gameState
//        val current = gameState.score()
//        var ix = 0
//        for (action in seq) {
//            gameState = gameState.next(action)
//            scoreArray[ix++] = gameState.score()
//        }
//        val delta = gameState.score() - current
//        return delta
        return 0.0
    }

    override fun getAction(gameState: GameState): Action {
        return Action.doNothing()
    }

}
