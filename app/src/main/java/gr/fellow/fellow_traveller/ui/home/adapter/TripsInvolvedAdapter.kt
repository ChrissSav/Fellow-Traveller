package gr.fellow.fellow_traveller.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.TripInvolvedItemLayoutBinding
import gr.fellow.fellow_traveller.databinding.TripInvolvedItemLayoutSecondsBinding
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.ui.extensions.TripInvolvedDiffCallback
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import kotlin.random.Random.Default.nextInt

class TripsInvolvedAdapter(
    private val drawable: Int = R.drawable.background_stroke_radius_27_green,
    private val onTripClickListener: (TripInvolved) -> Unit


) : ListAdapter<TripInvolved, TripsInvolvedAdapter.ViewHolder>(TripInvolvedDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TripInvolvedItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {
            val currentTrip = currentList[position]
            //binding.background.background = ContextCompat.getDrawable(binding.background.context, drawable)
            //= binding.background.context.resources.getColorStateList(color)
            binding.from.text = currentTrip.destFrom.title
            binding.to.text = currentTrip.destTo.title
            binding.name.text = currentTrip.creatorUser.fullName
            binding.date.text = currentTrip.date
            binding.time.text = currentTrip.time
            binding.price.text = binding.price.context.getString(R.string.price, currentTrip.price.toString())
            binding.picture.loadImageFromUrl(currentTrip.creatorUser.picture)
            binding.pet.visibility = if (currentTrip.hasPet) View.VISIBLE else View.INVISIBLE


            val cities = mutableListOf("")
            cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fstelios-kontoulis-CfdKapTCfEQ-unsplash-min.jpg?alt=media&token=ce76d3a4-c790-4a35-9d93-67f64124f7c9")
            cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fmaxime-vermeil-max-vrm-0yU_eHPC_Ic-unsplash-min.jpg?alt=media&token=882c83e3-0e36-46d8-b91c-2bc5512f1b19")
            cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fphilip-jahn-PYkpulrIMG0-unsplash-min.jpg?alt=media&token=ff65c766-1570-40c2-af38-08ae033a86f3")
            cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fstavrialena-gontzou-tsc0SC2LXxc-unsplash-min.jpg?alt=media&token=cf987a6d-5d22-47cf-b2b2-10da27f8d844")
            cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fmax-van-den-oetelaar-0ta9IhdKFgI-unsplash-min.jpg?alt=media&token=047466af-8656-45bd-9c72-411ce60d80e9")
            cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fmatt-artz-eQQI_HzT9RE-unsplash-min.jpg?alt=media&token=2395bc63-c538-42e1-8270-f2c700a4f367")
            cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fmarkus-winkler-gVnlbGa4LXk-unsplash-min.jpg?alt=media&token=8166a8ea-7893-408b-ba94-0c2bba475817")
            cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fmarkus-winkler-gQqECU0YiMw-unsplash-min.jpg?alt=media&token=3e3a2506-ec10-4ee6-ab5b-5c86dd70d17e")
            cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fjoshua-rondeau-KWUyjtSFNc0-unsplash-min.jpg?alt=media&token=79774f8b-c575-406a-b0d3-2b9a85dcab85")
            cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fjason-blackeye-OUOMSXvkZH4-unsplash-min.jpg?alt=media&token=980db0b1-8bad-43b1-9d14-b68dd220c257")
            cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fjason-blackeye-K1uLEiqTQEA-unsplash-min.jpg?alt=media&token=720a71ff-c0aa-42d8-aa9d-7d3ac2465605")
            cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Ffleur-XMwPBhnJf7g-unsplash-min.jpg?alt=media&token=94319be4-766e-450f-ae54-d1d3149da3ca")
            cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fdichatz-8OjTCSjlQic-unsplash-min.jpg?alt=media&token=fad25a5e-4b6c-404a-ac0c-ed4846e18455")
            cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fdespina-galani-o6p3eBm4Da4-unsplash-min.jpg?alt=media&token=1b1e91bd-710c-47f0-a6d5-3204701d7d50")
            cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Falex-antoniadis-em7-Tgt22mg-unsplash-min.jpg?alt=media&token=7ed06873-5e39-4b13-8315-8cd25328b3e4")


            binding.cityImage.loadImageFromUrl(cities.get(nextInt(14)))
            //binding.rate.text = currentTrip.creatorUser.rate.toString()
            //binding.review.text = currentTrip.creatorUser.reviews.toString()
            /*  binding.seats.text = currentTrip.seatsStatus
              binding.bags.text = currentTrip.bags
              binding.pet.text = if (currentTrip.hasPet) binding.pet.resources.getString(R.string.allowed) else binding.pet.resources.getString(R.string.not_allowed)*/
            binding.root.setOnClickListener {
                onTripClickListener.invoke(currentTrip)
            }
        }
    }

    class ViewHolder(val binding: TripInvolvedItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}

