package com.android.angole.models

import java.time.Duration

data class MovieDetails(
    var subCode: Int,
    var message: String,
    var items: MovieItems?
)

data class MovieItems(
    var id: Int?,
    var name: String?,
    var genre: String?,
    var duration: String?,
    var description: String?,
    var director: String?,
    var stars: String?,
    var movie_image: String?,
    var cover_big: String?,
    var release_date: String?,
    var youtube_trailer: String?,
    var stream_link: String?,
    var isFavourite: Boolean?
)

//{
//    "status": true,
//    "subCode": 200,
//    "message": "Movies Details",
//    "error": "",
//    "items": {
//    "id": 9437,
//    "name": "Xuxa Só Para Baixinhos 3",
//    "genre": "",
//    "duration": "00:52:17",
//    "description": "Em clima country, Xuxa canta e dança músicas sobre animais e brincadeiras.",
//    "director": "Marlene Mattos",
//    "stars": "Xuxa, Sasha Meneghel",
//    "movie_image": "http://195.201.198.179:32400/photo/:/transcode?width=300&height=450&minSize=1&quality=100&upscale=1&url=/library/metadata/91657/thumb/1659030523&X-Plex-Token=sVGpC9PEaZqDEMPBrgho",
//    "cover_big": "http://195.201.198.179:32400/photo/:/transcode?width=300&height=450&minSize=1&quality=100&upscale=1&url=/library/metadata/91657/thumb/1659030523&X-Plex-Token=sVGpC9PEaZqDEMPBrgho",
//    "release_date": "2002-08-25",
//    "youtube_trailer": null,
//    "stream_link": "http://88.99.244.62/fbengui/fbengui1991/9437.mp4"
//}
//}
