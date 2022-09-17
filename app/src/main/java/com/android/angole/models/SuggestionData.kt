package com.android.angole.models

data class SuggestionData(
    val status: Boolean,
    val subCode: Int,
    val message: String,
    val items: List<SuggestionItems>?
)

data class SuggestionItems(
    val _id: String?,
    val posterLink: String?,
    val headLine: String?,
    val subtitle: String?
)

//{
//    "status": true,
//    "subCode": 200,
//    "message": "onboard data get successfully",
//    "error": "",
//    "items": [
//    {
//        "_id": "6322f098d8b62152fea0a2a4",
//        "posterLink": "https://angola-bucket.s3.amazonaws.com/posters/Thu_Sep_15_2022_1663234200365_ironman.jpg",
//        "headLine": "Unlimited entertainment,one low price.",
//        "subtitle": "All of Angola, starting at just $ 5.",
//        "__v": 0
//    },
//    {
//        "_id": "6322f3f6d8b62152fea0a2aa",
//        "posterLink": "https://angola-bucket.s3.ap-south-1.amazonaws.com/posters/Thu_Sep_15_2022_1663235062128_bullet-train-movie-poster-6974.jpg",
//        "headLine": "Download and watch offline",
//        "subtitle": "Always have something to watch offline.",
//        "__v": 0
//    },
//    {
//        "_id": "6322f6add8b62152fea0a2b2",
//        "posterLink": "https://angola-bucket.s3.ap-south-1.amazonaws.com/posters/Thu_Sep_15_2022_1663235756474_harry_potter.png",
//        "headLine": "No Pesky contracts",
//        "subtitle": "Join today, cancel anytime.",
//        "__v": 0
//    },
//    {
//        "_id": "6322f7d5d8b62152fea0a2b6",
//        "posterLink": "https://angola-bucket.s3.ap-south-1.amazonaws.com/posters/Thu_Sep_15_2022_1663236053374_moonlight.jpeg",
//        "headLine": "Watch everywhere",
//        "subtitle": "Stream on your phone",
//        "__v": 0
//    }
//    ]
//}