package com.gabo.moviesapp.other.adapters.rvAdapters

//class SimilarMoviesPagingSource(
//    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
//    private val movieId: Int
//) :
//    PagingSource<Int, MovieModel>() {
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
//        return try {
//            val page = params.key ?: 20
//            if (page != 20) {
//                delay(1500)
//            }
//            val response
//            = getSimilarMoviesUseCase(Pair(first = movieId, second = page))
//            var movies: List<MovieModel> = emptyList()
//
//            response.body()?.let {
//                movies = it.movieResults
//            }
//            response.errorBody()?.let {
//                d("ragacaerori",it.string())
//            }
//            return LoadResult.Page(
//                data = movies,
//                prevKey = if (page > 10) page - 1 else null,
//                nextKey = if (page < response.body()!!.totalPages) page + 1 else null
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//
//}
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gabo.moviesapp.data.models.genreModels.GenreModel
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.databinding.PopularMovieItemBinding
import com.gabo.moviesapp.other.adapters.diffCallback.MovieDiffCallback
import com.gabo.moviesapp.other.adapters.genresAdapter.GenresAdapter
import com.gabo.moviesapp.other.common.BASE_IMAGE_URL

class SimilarMoviesAdapter(private val click: (MovieModel) -> Unit) :
    RecyclerView.Adapter<SimilarMoviesAdapter.SimilarMoviesVH>() {
    private var genresList: List<GenreModel> = emptyList()
    private var list: List<MovieModel> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<MovieModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitGenresList(list: List<GenreModel>) {
        genresList = list
        notifyDataSetChanged()
    }

    inner class SimilarMoviesVH(private val binding: PopularMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: MovieModel, click: (MovieModel) -> Unit) {
            with(binding) {
                val genresAdapter = GenresAdapter()
                rvGenres.adapter = genresAdapter
                rvGenres.layoutManager = LinearLayoutManager(
                    rvGenres.context,
                    LinearLayoutManager.HORIZONTAL, false
                )
                val genresToFilter = genresList.toMutableList()
                val filtered = mutableListOf<GenreModel>()
                model.genreIds.forEach { genreId ->
                    genresToFilter.forEach { genreModel ->
                        if (genreId == genreModel.id) {
                            filtered.add(genreModel)
                        }
                    }
                }
                genresAdapter.submitList(filtered.toList())
                tvTitle.text = model.title
                tvRating.text = model.Rating.toString()
                Glide.with(ivPoster.context).load(BASE_IMAGE_URL + model.imageUrl)
                    .into(ivPoster)
                itemView.setOnClickListener { click(model) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMoviesVH {
        val binding =
            PopularMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimilarMoviesVH(binding)
    }

    override fun onBindViewHolder(holder: SimilarMoviesVH, position: Int) {
        val item = list[position]
        holder.bind(item, click)
    }

    override fun getItemCount() = list.size

}
