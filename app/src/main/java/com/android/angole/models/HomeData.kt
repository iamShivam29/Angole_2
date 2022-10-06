package com.android.angole.models

data class HomeData(
    val subCode: Int,
    val message: String,
    var items:HomeItems?
)

data class HomeItems(
    val banners: List<CategoryItems>?,
    val categoryData: List<CategoryData>?
)

data class BannersData(
    val id: Int?,
    val name: String?,
    val genre: String?,
    val cover: String?,
    val backdrop_path: List<String>?,
    val type: String?
)

data class CategoryData(
    val categoryName: String?,
    val categoryId: String?,
    val categoryType: String?,
    var categoryItems: List<CategoryItems>?
)

data class CategoryItems(
    val id: Int?,
    val name: String?,
    val genre: String?,
    val playableUrl: String?,
    val cover: String?,
    val backdrop_path: List<String>?,
    val type: String?
)

//"banners": [
//{
//    "name": "Cowboy Bebop (1998)",
//    "genre": "Animação, Action, Adventure",
//    "backdrop_path": [],
//    "type": "SHOWS"
//},