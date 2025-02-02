package project.filmography.moviehunter.models.signIn

// Modello per la risposta della creazione di una sessione ospite
data class GuestSessionResponse(
    val guest_session_id: String // ID della sessione ospite
)

// Modello per la risposta della creazione di un token di richiesta
data class TokenResponse(
    val request_token: String // Token di richiesta generato
)
