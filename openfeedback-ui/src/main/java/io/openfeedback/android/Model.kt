package io.openfeedback.android

import io.openfeedback.android.model.Project
import kotlin.math.absoluteValue

data class Dot(val x: Float,
               val y: Float,
        // color as "rrggbb"
               val color: String)

data class VoteModel(
        val id: String,
        val text: String,
        val dots: List<Dot>,
        val votedByUser: Boolean
)

fun createUIModel(userVotes: List<String>, totalVotes: Map<String, Long>, project: Project): List<VoteModel> {
    return updateUIModel(emptyList(), userVotes, totalVotes, project)
}

fun updateUIModel(original: List<VoteModel>, userVotes: List<String>, totalVotes: Map<String, Long>, project: Project): List<VoteModel> {
    return project.voteItems.map { voteItem ->
        val count = totalVotes.entries.find { e ->
            voteItem.id == e.key
        }?.value
                ?.toInt()
                ?: 0

        val originalVoteModel = original.find { it.id == voteItem.id }
        if (originalVoteModel != null) {
            val diff = count - originalVoteModel.dots.size
            val newDots = if (diff > 0) {
                originalVoteModel.dots + dots(diff, project.chipColors)
            } else {
                originalVoteModel.dots.dropLast(diff.absoluteValue)
            }
            originalVoteModel.copy(
                    dots = newDots,
                    votedByUser = userVotes.contains(voteItem.id)
            )
        } else {
            VoteModel(
                    id = voteItem.id,
                    text = voteItem.name,
                    dots = dots(count, project.chipColors),
                    votedByUser = userVotes.contains(voteItem.id))
        }
    }
}