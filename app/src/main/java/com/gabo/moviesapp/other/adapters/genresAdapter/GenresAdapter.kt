package com.gabo.moviesapp.other.adapters.genresAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabo.moviesapp.data.models.genreModels.GenreModel
import com.gabo.moviesapp.databinding.GenreItemBinding

class GenresAdapter: RecyclerView.Adapter<GenresAdapter.GenreVH>() {
    private var list: List<GenreModel> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<GenreModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class GenreVH(private val binding: GenreItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(model: GenreModel){
            binding.root.text = model.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreVH {
        val binding = GenreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreVH(binding)
    }

    override fun onBindViewHolder(holder: GenreVH, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
}