package progetto.cinema.cinestock.remote.remotemodels.search

import com.squareup.moshi.Json
//import android.os.Parcelable
//import kotlinx.parcelize.Parcelize


data class FilmSearchResponseRemoteModel(
    val searchTerm: String? = null,
    val data: FilmData? = null,
    val mainSearch: MainSearch? = null,
    @Json(name = "edges") val searchRemoteModelList: List<Entity>? = null
)


data class FilmData(
    val mainSearch: MainSearch? = null
)


data class MainSearch(
    val edges: List<Edge>? = null
)


data class Edge(
    val node: Node? = null
)


data class Node(
    val entity: Entity? = null
)

data class Entity(
    val typeName: String? = null,
    val id: String? = null,
    val titleText: TitleText? = null,
    val text: String? = null,
    val originalTitleText: TitleText? = null,
    val releaseYear: YearRange? = null,
    val releaseDate: ReleaseDate? = null,
    val titleType: TitleType? = null,
    val primaryImage: Image? = null,
    val principalCredits: List<PrincipalCredit>? = null,
    val url: String? = null,
)

data class TitleText(
    val text: String? = null,
    val isOriginalTitle: Boolean? = null
)


data class YearRange(
    val year: Int? = null,
    val endYear: Int? = null
)


data class ReleaseDate(
    val month: Int? = null,
    val day: Int? = null,
    val year: Int? = null,
    val country: Country? = null,
    val restriction: String? = null,
    val attributes: List<String>? = null,
    val displayableProperty: DisplayableProperty? = null
)

data class Country(
    val id: String? = null
)

data class DisplayableProperty(
    val qualifiersInMarkdownList: List<String>? = null
)

data class TitleType(
    val id: String? = null,
    val text: String? = null,
    val categories: List<Category>? = null,
    val canHaveEpisodes: Boolean? = null,
    val isEpisode: Boolean? = null,
    val isSeries: Boolean? = null,
    val displayableProperty: DisplayableProperty? = null
)

data class Category(
    val id: String? = null,
    val text: String? = null,
    val value: String? = null
)

data class Image(
    val id: String? = null,
    val url: String? = null,
    val height: Int? = null,
    val width: Int? = null
)

data class PrincipalCredit(
    val credits: List<Credit>? = null
)


data class Credit(
    val name: Name? = null
)


data class Name(
    val typeName: String? = null,
    val id: String? = null,
    val nameText: NameText? = null,
    val primaryImage: Image? = null
)


data class NameText(
    val text: String? = null
)
