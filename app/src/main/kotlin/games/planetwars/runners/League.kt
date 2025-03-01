package games.planetwars.runners

import java.io.File

class League {
}

data class LeagueEntry(
    val agentName: String,
    val nWins: Int,
    val nLosses: Int,
    val nDraws: Int,
)

data class LeagueResult(
    val entries: List<LeagueEntry>
)

fun generateMarkdownTable(league: LeagueResult): String {
    // Sort by wins (descending), then losses (ascending), then draws (descending)
    val sortedEntries = league.entries.sortedWith(
        compareByDescending<LeagueEntry> { it.nWins }
            .thenBy { it.nLosses }
            .thenByDescending { it.nDraws }
    )

    val header = "| Rank | Agent Name | Wins | Losses | Draws |\n|------|------------|------|--------|-------|\n"
    val rows = sortedEntries.mapIndexed { index, entry ->
        "| ${index + 1} | ${entry.agentName} | ${entry.nWins} | ${entry.nLosses} | ${entry.nDraws} |"
    }.joinToString("\n")

    return header + rows
}

// Function to save the Markdown string to a file
fun saveMarkdownToFile(markdownContent: String, outputDir: String, filename: String) {
    val dir = File(outputDir)
    if (!dir.exists()) dir.mkdirs() // Ensure the directory exists

    val outputFile = File(dir, filename)
    outputFile.writeText(markdownContent)

    println("League results saved to ${outputFile.absolutePath}")
}

// Example usage
fun main() {
    val league = LeagueResult(
        listOf(
            LeagueEntry("AlphaBot", 10, 2, 1),
            LeagueEntry("BetaAI", 8, 4, 2),
            LeagueEntry("GammaSolver", 8, 3, 3),
            LeagueEntry("DeltaAgent", 6, 5, 3)
        )
    )

    val markdownContent = generateMarkdownTable(league)
    saveMarkdownToFile(markdownContent, "results/sample/", "league.md")
}
