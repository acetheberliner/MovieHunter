package progetto.cinema.cinestock.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import progetto.cinema.cinestock.remote.remotemodels.search.Entity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import progetto.cinema.cinestock.models.FilmModel
import progetto.cinema.cinestock.remote.FilmApi
import java.util.UUID

interface HomepageViewModelListener {
    fun onLocationListRetrieved(filmModelList: List<FilmModel>)
}

class HomepageViewModel: ViewModel(){
    private var listener: HomepageViewModelListener? = null

    fun setupListener(
        listener: HomepageViewModelListener?
    ){
        this.listener = listener
    }

    fun filmSearch(
        title: String,
        type: String? = null,
        first: Int? = null,
        country: String? = null,
        language: String? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val remoteModel = FilmApi.rootService.filmSearch(
                title = title, // la prima parte corrisponde alla chiave della variabile del metodo chiamato e la seconda parte corrisponde al campo in input del metodo
                type = type,
                first = first,
                country = country,
                language = language
            )
            val dataList = remoteModel.searchRemoteModelList.orEmpty().filter{
                it.titleType?.text == "Movie"
            }.map{
                it.toFilmModel()
            }
            // l'elemento che si è registrato al listener riceve la lista (dataList)
            listener?.onLocationListRetrieved(
                filmModelList = dataList
            )

        }
    }

}

private fun Entity.toFilmModel(): FilmModel {
    return FilmModel(
        identifier = this.id ?: UUID.randomUUID().toString(), // se l'id non è valorizzato viene creato un random UUID toString
        name = this.titleText?.text?: "", // titolo del film
        //description = this.regionNames?: "",
        //address = String.format("%s %s %s", this.hotelAddress?.street ?: "", this.hotelAddress?.city ?: "", this.hotelAddress?.province ?: ""),
        pictureUrl = this.url ?: "", // immagine/locandina
    )

}
