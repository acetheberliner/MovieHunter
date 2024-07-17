package progetto.cinema.cinestock

object MovieRepository {
    private val movies = listOf(
        Movie(1, "Pinocchio", "Pinocchio, un burattino di legno intagliato da Geppetto e trasformato in una marionetta vivente", 10.0, R.drawable.pinocchio),
        Movie(2, "Madagascar", "Quattro amici animali vivono tutti insieme in uno zoo, ma si troveranno a vivere una nuova avventura", 10.0, R.drawable.madagascar)
        // Aggiungi altri film se necessario
    )

    fun getMovies() = movies

    fun getMovieById(id: Int) = movies.find { it.id == id } ?: throw Exception("Film non trovato")
}
