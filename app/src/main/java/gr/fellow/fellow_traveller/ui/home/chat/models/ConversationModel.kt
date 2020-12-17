package gr.fellow.fellow_traveller.ui.home.chat.models

class ConversationModel : Comparable<Any?> {
    var tripId = 0
    var tripName: String? = null
    var description: String? = null
    var date: Long = 0
    var isSeen = false

    constructor(aId: Int, aUserName: String?, aDescription: String?, aDate: Long, aSeen: Boolean) {
        tripId = aId
        tripName = aUserName
        description = aDescription
        date = aDate
        isSeen = aSeen
    }

    constructor() {}

    override fun compareTo(other: Any?): Int {
        TODO("Not yet implemented")
    }


}