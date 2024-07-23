package progetto.cinema.cinestock.ui.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import progetto.cinema.cinestock.models.movie.TMDbMovie
import progetto.cinema.cinestock.repository.MovieRepository

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MovieRepository = MovieRepository(application)
    private val _movies = MutableLiveData<List<TMDbMovie>>()
    val movies: LiveData<List<TMDbMovie>> = _movies

    fun fetchTrendingMovies(apiKey: String) = viewModelScope.launch {
        val movieList = repository.getTrendingMovies(apiKey)
        _movies.postValue(movieList)
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MovieViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
