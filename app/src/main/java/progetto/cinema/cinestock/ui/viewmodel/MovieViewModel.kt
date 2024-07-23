package progetto.cinema.cinestock.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import progetto.cinema.cinestock.repository.MovieRepository

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MovieRepository
    val allMovies: LiveData<List<Movie>>

    init {
        val movieDao = MovieDatabase.getDatabase(application, viewModelScope).movieDao()
        repository = MovieRepository(movieDao, application)
        allMovies = repository.allMovies
    }

    fun insert(movie: Movie) = viewModelScope.launch {
        repository.insert(movie)
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
                return MovieViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
