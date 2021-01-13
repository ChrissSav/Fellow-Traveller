package gr.fellow.fellow_traveller.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.ConversationItemBinding
import gr.fellow.fellow_traveller.domain.chat.ChatMessage
import gr.fellow.fellow_traveller.service.NotificationJobService
import gr.fellow.fellow_traveller.ui.extensions.ConversationsDiffCallback
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.home.chat.models.Conversation
import gr.fellow.fellow_traveller.utils.currentTimeStamp
import gr.fellow.fellow_traveller.utils.getDateFromTimestamp


class ConversationsAdapter(

    private val onItemClickListener: (Conversation) -> Unit,
    private val myId: String


) : ListAdapter<Conversation, ConversationsAdapter.ViewHolder>(ConversationsDiffCallback()) {

    private lateinit var context: Context
    private var DEFAULT = "default"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ConversationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        context = binding.root.context
        DEFAULT = context.getString(R.string.DEFAULT)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = currentList[position]

        with(holder) {
            //Binding all views
            binding.chatImage.loadImageFromUrl(currentItem.image)
            binding.chatName.text = currentItem.tripName


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
//            if (currentItem.description == DEFAULT || currentItem.description == "" ) {
//                getLastMessage(currentItem.tripId, binding.chatDescription)
//            }else{
//                binding.chatDescription.text = currentItem.description
//            }
            getLastMessage(currentItem.tripId, binding.chatDescription)

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

        val reference = FirebaseDatabase.getInstance().getReference("Messages").child(tripId)
        var messageQuery: Query = reference.limitToLast(1)
        val messageChildEventListener = object : ChildEventListener {

            @SuppressLint("SetTextI18n")
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d(NotificationJobService.TAG, "onChildAdded:" + dataSnapshot.key!!)

                dataSnapshot.getValue(ChatMessage::class.java)?.let {
                    if (it.messageType == 0) {
                        if (myId == it.senderId)
                            textView.text = context.getString(R.string.from_you) + ": " + it.text
                        else
                            textView.text = it.senderName + ": " + it.text
                    } else if (it.messageType == 1) {
                        textView.text = context.getString(R.string.user_added_message)
                    } else if (it.messageType == 2) {
                        textView.text = context.getString(R.string.user_deleted_message)
                    } else if (it.messageType == 3) {
                        textView.text = context.getString(R.string.no_message)
                    } else {
                        textView.text = context.getString(R.string.trip_deleted_message)
                    }

                }

            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        messageQuery.addChildEventListener(messageChildEventListener)
    }
}

