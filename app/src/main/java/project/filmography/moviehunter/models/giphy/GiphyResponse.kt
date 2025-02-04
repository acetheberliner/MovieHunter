package project.filmography.moviehunter.models.giphy

// Risposta delle GIF
data class GiphyResponse(
    val data: List<GiphyGif> // Lista delle GIFs
)

// Modello per ogni singola GIF
data class GiphyGif(
    val id: String, // ID della GIF
    val images: GiphyImages // Immagini della GIF
)

// Dettagli dell'immagine della GIF
data class GiphyImages(
    val original: GiphyImage // GIF in formato grande
)

data class GiphyImage(
    val url: String // URL della GIF
)
