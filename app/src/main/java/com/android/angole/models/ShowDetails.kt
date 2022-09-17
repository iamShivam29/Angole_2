package com.android.angole.models

data class ShowDetails(
    var subCode: Int?,
    var message: String?,
    var items: ShowItems?
)

data class ShowItems(
    val name: String?,
    val cover: String?,
    val stars: String?,
    val director: String?,
    val description: String?,
    val release_date: String?,
    val genre: String?,
    val episode_run_time: String?,
    val youtube_trailer: String?,
    val seriesInfo: List<SeriesInfo>?,
    var isFavourie: Boolean?
)

data class SeriesInfo(
    val season_element: SeasonElement?,
    val episodes: List<Episodes>?
)

data class SeasonElement(
    val name: String?,
    val air_date: String?,
    val overview: String?,
    val cover_big: String?,
    val cover: String?,
    val episode_count: String?,
    val season_number: String?,
    val id: String?
)

data class Episodes(
    val id: String?,
    val episode_num: String?,
    val title: String?,
    val info: Info?,
    val season: Int?,
    val playableUrl: String?
)

data class Info(
    val release_date: String?,
    val plot: String?,
    val duration: String?,
    val movie_image: String?,
    val rating: Float?,
    val season: String?,
    val cover_big: String?
)


//"name": "Spidey e Seus Amigos Espetaculares (2021)",
//"cover": "http://195.201.198.179:32400/photo/:/transcode?width=300&height=450&minSize=1&quality=100&upscale=1&url=/library/metadata/10399/thumb/1658915190&X-Plex-Token=t_KxfzuYPa4wnbiou_Bn",
//"stars": "Benjamin Valic, Lily Sanfelippo, Jakari Fraser, JP Karliak, Melanie Minichino",
//"director": "",
//"description": "Peter Parker e seus amigos Miles Morales, Gwen, Hulk, Pantera Negra e a Sra. Marvel trabalham juntos para derrotar as forças do mal e aprender que o trabalho em equipe é a melhor maneira de salvar o dia.",
//"release_date": "2021-07-17",
//"genre": "Animação, Action, Adventure",
//"episode_run_time": "11",
//"youtube_trailer": "",
//"seriesInfo": [
//{
//    "season_element": {
//    "name": "Season 1",
//    "air_date": "2021-08-06",
//    "overview": "Peter Parker e seus amigos Miles Morales, Gwen, Hulk, Pantera Negra e a Sra. Marvel trabalham juntos para derrotar as forças do mal e aprender que o trabalho em equipe é a melhor maneira de salvar o dia.",
//    "cover_big": "http://195.201.198.179:32400/photo/:/transcode?width=300&height=450&minSize=1&quality=100&upscale=1&url=/library/metadata/10400/thumb/1658915212&X-Plex-Token=t_KxfzuYPa4wnbiou_Bn",
//    "cover": "http://195.201.198.179:32400/photo/:/transcode?width=300&height=450&minSize=1&quality=100&upscale=1&url=/library/metadata/10400/thumb/1658915212&X-Plex-Token=t_KxfzuYPa4wnbiou_Bn",
//    "episode_count": "19",
//    "season_number": "1",
//    "id": "10400"
//},
//    "episodes": [
//    {
//        "id": "93977",
//        "episode_num": "1",
//        "title": "Spidey e Seus Amigos Espetaculares - S01E01 - Spidey Elevado a Três",
//        "info": {
//            "release_date": "2021-08-06",
//            "plot": "Spidey lembra que Spin e Aranha-Fantasma precisam trabalhar como uma equipe para rastrear Rhino.",
//            "duration": "00:24:05",
//            "movie_image": "http://195.201.198.179:32400/photo/:/transcode?width=450&height=253&minSize=1&quality=100&upscale=1&url=/library/metadata/10401/thumb/1658915222&X-Plex-Token=t_KxfzuYPa4wnbiou_Bn",
//            "rating": 1,
//            "season": "1",
//            "cover_big": "http://195.201.198.179:32400/photo/:/transcode?width=450&height=253&minSize=1&quality=100&upscale=1&url=/library/metadata/10401/thumb/1658915222&X-Plex-Token=t_KxfzuYPa4wnbiou_Bn"
//        },
//        "subtitles": [],
//        "season": 1,
//        "playableUrl": "http://88.99.244.62/series/fbengui/fbengui1991/93977.mp4"
//    },
