package progetto.cinema.cinestock

object MovieRepository {
    private val movies = listOf(
        Movie(1, "Pinocchio", "Pinocchio, un burattino di legno intagliato da Geppetto e trasformato in una marionetta vivente", 10.0, R.drawable.pinocchio),
        Movie(2, "Madagascar", "Quattro amici animali vivono tutti insieme in uno zoo a New York e si troveranno a vivere una nuova avventura", 10.0, R.drawable.madagascar),
        Movie(3, "Aristogatti", "Una nobile famiglia di gatti conosceranno la gigantesca città di Parigi, bellissima quanto pericolosa", 10.0, R.drawable.aristogatti),
        Movie(4, "Avatar", "Un ex-marine entrerà, con l'aiuto di scienziati, nel mondo di 'Avatar' e la sua vita cambierà per sempre", 15.0, R.drawable.avatar),
        Movie(5, "Joker", "Arthr Fleck per guadagnarsi da vivere fa pubblicità per strada travestito da clown. Un giorno, però, le cose cambiarono", 15.0, R.drawable.joker),
        Movie(6, "Napoleon", "Ricostruzione della vita e vicende di Napoleone Bonaparte, dalla sua rapida ascesa fino alla sua caduta", 15.0, R.drawable.napoleon),
        Movie(7, "Piccole Donne", "Quattro sorelle, tutte determinate nel realizzare i propri sogni ma, sullo sfondo, la Guerra Civile Americana", 15.0, R.drawable.piccoledonne),
        Movie(8, "Ratatouille", "Remy ha un grande talento nonostante la sua natura: la cucina. Riuscirà a realizzare il suo sogno?", 10.0, R.drawable.ratatouille),
        Movie(9, "Terminator", "Nel futuro i robot hanno preso il sopravvento. Un cyborg dalle sembianze umane deve fermarli", 15.0, R.drawable.terminator),
        Movie(10, "Zootropolis", "Judy Hoops vuole diventare una poliziotta nella grande Zootropolis, ci riuscirà?", 10.0, R.drawable.zootropolis)
        // Aggiungi altri film se necessario
    )

    fun getMovies() = movies

    fun getMovieById(id: Int) = movies.find { it.id == id } ?: throw Exception("Film non trovato")
}
