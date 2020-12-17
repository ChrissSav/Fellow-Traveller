package gr.fellow.fellow_traveller.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.ConversationItemBinding
import gr.fellow.fellow_traveller.ui.home.chat.models.Conversation
import gr.fellow.fellow_traveller.utils.currentTimeStamp
import gr.fellow.fellow_traveller.utils.getDateFromTimestamp

class ConversationsAdapter(
    private val conversationsList: MutableList<Conversation>,
    private val onItemClickListener: (Conversation) -> Unit

) : RecyclerView.Adapter<ConversationsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ConversationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = conversationsList[position]

        with(holder) {
            //Binding all views
            //binding.chatImage.loadImageFromUrl(currentItem.image)
            binding.chatName.text = currentItem.tripName
            binding.chatDescription.text = currentItem.description

            binding.chatDate.text = time(currentItem.date, binding.chatDate.context)

            if (currentItem.isSeen)
                binding.chatSeenIcon.visibility = View.INVISIBLE
            else
                binding.chatSeenIcon.visibility = View.VISIBLE

            binding.root.setOnClickListener {
                onItemClickListener.invoke(currentItem)
            }
        }
    }


    //Set minutes ago
    private fun time(timestamp: Long, context: Context): String {
        val t = currentTimeStamp() - timestamp
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

    override fun getItemCount() = conversationsList.size
}