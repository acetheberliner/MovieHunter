package progetto.cinema.cinestock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieViewModel : ViewModel() {
    private val _allMovies = MutableLiveData<List<Movie>>().apply {
        value = MovieRepository.getMovies()
    }
    val allMovies: LiveData<List<Movie>> = _allMovies
}
