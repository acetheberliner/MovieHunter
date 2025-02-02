package project.filmography.moviehunter.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import project.filmography.moviehunter.models.movie.TMDbMovie
import project.filmography.moviehunter.repository.MovieRepository

// ViewModel per la gestione dei film
class MovieViewModel(application: Application) : AndroidViewModel(application) {
    // Repository per il recupero dei dati relativi ai film
    private val repository: MovieRepository = MovieRepository(application)

    // LiveData per i film di tendenza
    private val _movies = MutableLiveData<List<TMDbMovie>>()
    val movies: LiveData<List<TMDbMovie>> = _movies

    // LiveData per i risultati di ricerca dei film
    private val _searchResults = MutableLiveData<List<TMDbMovie>>()
    val searchResults: LiveData<List<TMDbMovie>> = _searchResults

    // LiveData per i dettagli di un film specifico
    private val _specificMovieDetails = MutableLiveData<TMDbMovie>()
    val movieDetails: LiveData<TMDbMovie> = _specificMovieDetails

    // Funzione per recuperare i film popolari
    fun fetchPopularMovies(apiKey: String) = viewModelScope.launch {
        val movieList = repository.getPopularMovies(apiKey)  // Chiamata al repository per ottenere i film popolari
        _movies.postValue(movieList)  // Aggiorna la LiveData con la lista di film
    }

    // Funzione per cercare film tramite una query
    fun searchMovies(apiKey: String, query: String) = viewModelScope.launch {
        val movieList = repository.searchMoviesByQuery(apiKey, query)  // Chiamata al repository per cercare film
        _searchResults.postValue(movieList)  // Aggiorna la LiveData con i risultati della ricerca
    }

    // Funzione per recuperare i dettagli di un film specifico
    fun fetchSpecificMovieDetails(apiKey: String, movieId: Int) = viewModelScope.launch {
        val movie = repository.getspecificMovieDetails(apiKey, movieId)  // Chiamata al repository per ottenere i dettagli
        _specificMovieDetails.postValue(movie)  // Aggiorna la LiveData con i dettagli del film
    }

    // Factory per creare il ViewModel con il parametro Application
    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MovieViewModel(application) as T  // Crea una nuova istanza del ViewModel
            }
            throw IllegalArgumentException("Unknown ViewModel class")  // Lancia un errore se il ViewModel non è riconosciuto
        }
    }
}
