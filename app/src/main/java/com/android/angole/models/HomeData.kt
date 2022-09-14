package com.android.angole.models

data class HomeData(
    val subCode: Int,
    val message: String,
    val items: List<HomeItems>
)

data class HomeItems(
    val categoryName: String,
    val categoryId: String,
    val categoryItems: List<CategoryItems>
)

data class CategoryItems(
    val id: Int,
    val name: String,
    val genre: String,
    val cover: String,
    val backdrop_path: List<String>,
    val type: String
)

//"categoryName": "Amazon Series",
//"categoryId": "95cc527ef834",
//"categoryItems": [
//{
//    "name": "CSI: Investigação Criminal (2000)",
//    "genre": "Crime, Drama, Mistério",
//    "id": 113,
//    "cover": "http://195.201.198.179:32400/photo/:/transcode?width=300&height=450&minSize=1&quality=100&upscale=1&url=/library/metadata/551/thumb/1658892565&X-Plex-Token=sVGpC9PEaZqDEMPBrgho",
//    "backdrop_path": [
//    "http://195.201.198.179:80/images/Jf-fAFvnMeFoXhKl48hyeijkRu_E4gzBDF2Qh43SfHW1DIZtwd1a9yPTdhJBnwyx8Q3iFbztUP-wy0rH-8vBEVtcEfwagCUY637gSsAoKOstSEYI09tCStc_hqacl2WH_-tQdjvf5VcEm0hSpMfxQKuW2PwNGXJM7ldGgXBUn_D0DnlfOmgA5z-k9iwc7-v1TNulsUHu_SrMEoELxAMQourl2j48GeEnjpM-vuF_PvY.jpeg"
//    ],
//    "type": "SHOWS"
//},