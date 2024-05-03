package com.example.kt_mobile_front.requests

import android.content.Context
import android.net.Uri
import com.example.kt_mobile_front.data.CreateLotData
import com.example.kt_mobile_front.data.LotData
import com.example.kt_mobile_front.data.ShortLotData
import com.example.kt_mobile_front.data.SignInData
import com.example.kt_mobile_front.data.SignInResponseData
import com.example.kt_mobile_front.data.SignUpUserData
import com.example.kt_mobile_front.data.UserData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import kotlinx.serialization.encodeToString
import java.util.UUID

var token = ""
val client = HttpClient(CIO) {
    install(ContentNegotiation) {
        json()
    }
}

private const val SIGNIN_URL = "http://90.156.225.87:8080/auth"

suspend fun postSignIn(
    signInData: SignInData
): SignInResponseData {
    val response = client.post(SIGNIN_URL) {
        contentType(Json)
        setBody(signInData)
    }.body<SignInResponseData>()
    token = "Bearer ${response.access_token}"
    return response
}

private const val SIGNUP_URL = "http://90.156.225.87:8080/register"
var id = ""
suspend fun postSignUp(
    signUpUserData: SignUpUserData,
    town: String
): String {
    val response = client.submitFormWithBinaryData(
        url = SIGNUP_URL,
        formData = formData {
            append("user_json", kotlinx.serialization.json.Json.encodeToString(signUpUserData))
            append("town", town)
        }
    ).body<String>()
    id = response
    return response
}

private const val SIGNUP_PHOTO_URL = "http://90.156.225.87:8080/user/"
suspend fun postPhotoSignUp(
    photo: Uri,
    id: String,
    context: Context
) {
    val idLot = client.submitFormWithBinaryData(
        url = SIGNUP_PHOTO_URL + id + "/photos",
        formData = formData {
            append(
                "photos",
                context.contentResolver.openInputStream(photo)!!.use { it.readBytes() },
                Headers.build {
                    append(HttpHeaders.ContentType, "image/png")
                    append(
                        HttpHeaders.ContentDisposition,
                        "filename=\"${UUID.randomUUID()}.png\""
                    )
                })


        }
    ).body<String>()
}

private const val ITEM_URL = "http://90.156.225.87:8080/items/"

suspend fun getAllItems(): List<ShortLotData> {
    return client.get(ITEM_URL).body()
}
suspend fun getItemById(id: String): LotData {
    return client.get(ITEM_URL + id).body()
}
suspend fun postAddLot(
    createLotData: CreateLotData,
    cat: String,
    cond: String,
    uris: List<Uri>,
    context: Context
) {
    val idLot = client.submitFormWithBinaryData(
        url = ITEM_URL,
        formData = formData{
            append("lot_json", kotlinx.serialization.json.Json.encodeToString(createLotData))
            append("cat", cat)
            append("cond", cond)
        }
    ) {
        headers {
            append(HttpHeaders.Authorization, token)
        }
    }.body<String>()
    postPhotosLot(uris, idLot, context)
}

suspend fun postPhotosLot(
    uris: List<Uri>,
    id: String,
    context: Context
) {
    val url = ITEM_URL + id.drop(1).dropLast(1) + "/photos"
    return client.submitFormWithBinaryData(
        url = url,
        formData = formData {
            uris.forEach { uri ->
                append(
                    "photos",
                    context.contentResolver.openInputStream(uri)!!.use { it.readBytes() },
                    Headers.build {
                        append(HttpHeaders.ContentType, "image/png")
                        append(
                            HttpHeaders.ContentDisposition,
                            "filename=\"${UUID.randomUUID()}.png\""
                        )
                    }
                )
            }
        }
    ) {
        headers {
            append(HttpHeaders.Authorization, token)
        }
    }.body()
}

private const val USER_URL = "http://90.156.225.87:8080/user/"

suspend fun getUser(id: String): UserData{
    return client.get(USER_URL + id).body()
}

suspend fun getUser(): UserData{
    return client.get(USER_URL){
        headers {
            append(HttpHeaders.Authorization, token)
        }
    }.body()
}