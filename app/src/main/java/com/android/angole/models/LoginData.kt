package com.android.angole.models

data class LoginData(
    var subCode: Int,
    var message: String,
    var items: LoginItems?
)

data class LoginItems(
    var token: String?,
    var isVerified: Boolean?,
    var reqId: String?
)

//"status": true,
//"subCode": 200,
//"message": "login successful",
//"error": "",
//"items": {
//    "token": "eyJhbGciOiJQUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJiODAzN2Y2NyIsInVzZXJuYW1lIjoidGVzdCIsInJvbGUiOjEsImlhdCI6MTY2MTQ5MDYwMywiZXhwIjoxNjY0MDgyNjAzfQ.CNNM36mwe02LHp7wKb3XHsome-CWRS6kMDXmjgCC2Q2r_VXQqnwNasvn0T5HL1k-MaC5_zRsvEvMFWz9DzjD8FGfiqvPj92BlUT-IKu6VD8WaMKQ5NUsxj-eSOZUmoGHE8TchWkGBU6Cq3mc0ZTo4SDbUfX4u94q_TuieyeVTQy5S28BUMHodNmlQkqSOlHoTiewLP8UCPrGOsxmzSulDcDEV0-yei59B5O2u4_I2ij9g37Sbr3zURHEVVFbz9MU9rpCoDipgdKR8lY4ElKqe0jylIZVptMhjFOoicniF7gUeUveKtcZX1cwhWf0WissNqDi_AuzlhNJhAzXxnxnw-hPO7xEtohJNiUia8ufnCCPdkxfr-SXlMaJPIhi6EaZ-OKye1BZox0nO4PZ7kiIPG2A6P1BuI6jW_sC0-S6cx_18pqDpN95haf0luMR4cgMMkQq9fOL2q-ZW23tJR6UFhDh-qXl1DqLOFcfIhpQXebzu6HokNgeHNMjdpC9OBXIpipGBA0k8jHuTCP63JvOq_EYtXPNW2hlxYNFQX5srhV0OUODcA-GYFXm7jDCEZoflGEeB85iRlRgwNiGYqnmAY1iITj5uwoC4HvB-5xpD6tGt_VH3t9xvjBOatjy8C7AZPxRVXoHAaTZV_0QQm2YCoE8ZoSr_Mq2d2ZWZ5aiqhM",
//    "isVerified": true,
//    "petAdded": true
//}