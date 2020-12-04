package ru.movie.app.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
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
        holder.binding.ivActorPhoto.background =
            ResourcesCompat.getDrawable(context.resources, actors[position].photo, context.theme)
        holder.binding.tvActorName.text = actors[position].name
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