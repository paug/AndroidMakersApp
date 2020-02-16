package io.openfeedback.android.model

class Project(
        val chipColors: List<String> = emptyList(),
        val voteItems: List<VoteItem> = emptyList()
)

class VoteItem(
        val id: String = "",
        val name: String = "",
        val position: Int = 0,
        val type: String = ""
)

class VoteCounts(val total: Long, val votedByUser: Boolean)

/*
 * key is the voteItem id
 * value is the number of votes
 */
typealias TotalVotes = HashMap<String, VoteCounts>

enum class VoteStatus(val value: String) {
    Active("active"),
    Deleted("deleted")
}