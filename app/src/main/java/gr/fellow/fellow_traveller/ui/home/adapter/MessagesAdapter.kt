package gr.fellow.fellow_traveller.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.domain.chat.ChatMessage
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.extensions.setTextHtml
import gr.fellow.fellow_traveller.utils.currentTimeStamp
import gr.fellow.fellow_traveller.utils.getDateFromTimestamp

class MessagesAdapter(
    private val messagesList: MutableList<ChatMessage>,
    private val myId: String,

    ) : RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {

    private val MESSAGE_TOP = 0
    private val MESSAGE_MIDDLE = 1
    private val MESSAGE_ENTRY = 2

    private var viewTypeLayout = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == MESSAGE_TOP) {
            viewTypeLayout = 0
            val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item_top, parent, false)
            return ViewHolder(view)
        } else if (viewType == MESSAGE_MIDDLE) {
            viewTypeLayout = 1
            val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item_middle, parent, false)
            return ViewHolder(view)
        } else {
            viewTypeLayout = 2
            val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item_entry, parent, false)
            return ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = messagesList[position]

        holder.message.text = currentItem.text

        if (currentItem.senderId == myId && currentItem.messageType == 0)
            holder.message.setBackgroundResource(R.drawable.message_shape_sended)
        else if (currentItem.senderId != myId && currentItem.messageType == 0)
            holder.message.setBackgroundResource(R.drawable.message_shape_received)

        //if viewType is MESSAGE_TOP load all the details
        if (viewTypeLayout == 0) {
            holder.name.text = currentItem.senderName
            holder.image.loadImageFromUrl(currentItem.senderImage)
            holder.date.text = time(currentItem.timestamp, holder.date.context)
        }

        if (viewTypeLayout == 2)
            holder.message.setTextHtml(holder.message.context.getString(R.string.conversation_passenger_entry, currentItem.senderName))


    }


    //Set minutes ago
    private fun time(timestamp: Long, context: Context): String {
        val t = currentTimeStamp() - timestamp / 1000
        if (t <= 3600) {
            return if ((t / 60).toInt() == 1)
                context.getString(R.string.minute_ago)
            //"${(t / 60).toInt()} λεπτό"
            else
                context.getString(R.string.minutes_ago, ((t / 60).toInt()).toString())
            //"${(t / 60).toInt()} λεπτά"
        } else if (t <= 3600 * 24)
            return if ((t / 3600).toInt() == 1)
                context.getString(R.string.hour_ago)
            //"${(t / 3600).toInt()} ώρα"
            else
                context.getString(R.string.hours_ago, ((t / 3600).toInt()).toString())
        // "${(t / 3600).toInt()} ώρες"
        else if ((t / (3600 * 24)) <= 7)
            return if ((t / (3600 * 24)).toInt() == 1)
                context.getString(R.string.day_ago)
            //"${(t / (3600 * 24)).toInt()} μέρα"
            else
                context.getString(R.string.days_ago, ((t / (3600 * 24)).toInt()).toString())
        else
            return getDateFromTimestamp(timestamp, "d MMM yyyy")
        //  "${(t / (3600 * 24)).toInt()} μέρες"

        //return test
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val message: TextView = view.findViewById(R.id.message)
        val name: TextView = view.findViewById(R.id.sender_name)
        val date: TextView = view.findViewById(R.id.message_time)
        val image: ImageView = view.findViewById(R.id.sender_image)
    }

    override fun getItemViewType(position: Int): Int {

        var currNext = "-1"
        var currPrev = "-1"
        var current = messagesList[position].senderId
        //Initialize and check if we run out of the list
        if (position + 1 < messagesList.size) {
            currNext = messagesList[position + 1].senderId;
        }
        if (position - 1 > -1) {
            currPrev = messagesList[position - 1].senderId;
        }

        if (messagesList[position].messageType == 0) {
            if ((current == currPrev)) {
                if (messagesList[position - 1].messageType == 1)
                    return MESSAGE_TOP
                else
                    return MESSAGE_MIDDLE
            } else {
                return MESSAGE_TOP;
            }

        } else {
            return MESSAGE_ENTRY
        }
    }


    override fun getItemCount() = messagesList.size

}