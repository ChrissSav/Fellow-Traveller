package gr.fellow.fellow_traveller.ui.home.chat.models

class MessageModel {
    var id = 0
    var text: String? = null
    var groupId = 0
    var timestamp: Long = 0
    var senderName: String? = null
    var senderImage: String? = null

    constructor(aId: Int, aText: String?, aName: String?, aGroupId: Int, aTimestamp: Long, aImage: String?) {
        id = aId
        text = aText
        senderName = aName
        groupId = aGroupId
        timestamp = aTimestamp
        senderImage = aImage
    }

    constructor() {}
}