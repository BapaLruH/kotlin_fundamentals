package ru.movie.app.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.movie.app.databinding.CellActorBinding
import ru.movie.app.ui.model.Actor

class ActorsAdapter(private val actors: MutableList<Actor> = mutableListOf()) :
    RecyclerView.Adapter<ActorsAdapter.ActorHolder>() {

    class ActorHolder(val binding: CellActorBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorHolder {
        return ActorHolder(
            CellActorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActorHolder, position: Int) = holder.run {
        val context = holder.itemView.context
        val actor = actors[position]
        Glide.with(context)
            .load(actor.picture)
            .into(holder.binding.ivActorPhoto)
        holder.binding.tvActorName.text = actor.name
    }

    override fun getItemCount(): Int {
        return actors.size
    }

    fun update(list: List<Actor>) {
        actors.clear()
        actors.addAll(list)
        notifyDataSetChanged()
    }
}