package gr.fellow.fellow_traveller.ui.home.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.ConversationItemBinding
import gr.fellow.fellow_traveller.domain.chat.ChatMessage
import gr.fellow.fellow_traveller.ui.extensions.ConversationsDiffCallback
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.home.chat.models.Conversation
import gr.fellow.fellow_traveller.utils.currentTimeStamp
import gr.fellow.fellow_traveller.utils.getDateFromTimestamp


class ConversationsAdapter(

    private val onItemClickListener: (Conversation) -> Unit,
    private val myId: String,
    private val context: Context


) : ListAdapter<Conversation, ConversationsAdapter.ViewHolder>(ConversationsDiffCallback()) {

    private val DEFAULT = context.getString(R.string.DEFAULT)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ConversationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = currentList[position]

        with(holder) {
            //Binding all views
            binding.chatImage.loadImageFromUrl(currentItem.image)
            binding.chatName.text = currentItem.tripName
            binding.chatDescription.text = currentItem.description

            binding.chatDate.text = time(currentItem.timestamp, binding.chatDate.context)

            if (currentItem.seen) {
                binding.chatSeenIcon.visibility = View.INVISIBLE
                //If it's not seen, the text style will be bold
                binding.chatDescription.typeface = Typeface.DEFAULT
            } else {
                binding.chatSeenIcon.visibility = View.VISIBLE
                //If it's not seen, the text style will be normal
                binding.chatDescription.typeface = Typeface.DEFAULT_BOLD
            }
            if (currentItem.description == DEFAULT) {
                binding.chatDescription.text = ""
                //getLastMessage(currentItem.tripId, binding.chatDescription)
            }

            binding.root.setOnClickListener {
                onItemClickListener.invoke(currentItem)
            }
        }
    }


    //Set minutes ago
    private fun time(timestamp: Long, context: Context): String {
        val t = currentTimeStamp() - (timestamp / 1000)
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

    class ViewHolder(val binding: ConversationItemBinding) : RecyclerView.ViewHolder(binding.root)


    private fun getLastMessage(tripId: String, textView: TextView) {


        var theLastMessage = DEFAULT
        val reference = FirebaseDatabase.getInstance().getReference("Messages").child(tripId)
        var last: Query = reference.orderByKey().limitToLast(1)
        last.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                val item: ChatMessage? = dataSnapshot.getValue(ChatMessage::class.java)
                var senderId = item?.senderId.toString()


                if (senderId == myId) {
                    theLastMessage = item?.text.toString() //Later we can use You: "message"
                } else {
                    theLastMessage = item?.text.toString()
                }

                when (theLastMessage) {
                    DEFAULT -> textView.text = context.getString(R.string.no_messages)
                    else ->
                        if (theLastMessage.isNullOrEmpty())
                            textView.text = context.getString(R.string.no_messages)
                        else
                            textView.text = theLastMessage
                }
                theLastMessage = DEFAULT

            }

            override fun onCancelled(@NonNull databaseError: DatabaseError) {}
        })

    }
}

