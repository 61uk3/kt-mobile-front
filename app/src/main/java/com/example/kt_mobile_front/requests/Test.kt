package com.example.kt_mobile_front.requests

import android.content.Context
import android.net.Uri
import com.example.kt_mobile_front.data.CreateLotData
import com.example.kt_mobile_front.data.LotData
import com.example.kt_mobile_front.data.ShortChatData
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

suspend fun postSignIn(
    signInData: SignInData
): SignInResponseData {
    val response = client.post(Route.SIGNIN_URL) {
        contentType(Json)
        setBody(signInData)
    }.body<SignInResponseData>()
    token = "Bearer ${response.access_token}"
    return response
}

suspend fun postSignUp(
    signUpUserData: SignUpUserData,
    town: String
): String {
    val response = client.submitFormWithBinaryData(
        url = Route.SIGNUP_URL,
        formData = formData {
            append("user_json", kotlinx.serialization.json.Json.encodeToString(signUpUserData))
            append("town", town)
        }
    ).body<String>()
    return response
}


suspend fun postPhotoSignUp(
    photo: Uri,
    id: String,
    context: Context
) {
    return client.submitFormWithBinaryData(
        url = Route.USER_URL + id + "/photos",
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
                }
            )
        }
    ).body()
}


suspend fun getAllItems(): List<ShortLotData> {
    return client.get(Route.ITEM_URL).body()
}

suspend fun getItemById(id: String): LotData {
    return client.get(Route.ITEM_URL + id).body()
}

suspend fun postAddLot(
    createLotData: CreateLotData,
    cat: String,
    cond: String,
    uris: List<Uri>,
    context: Context
) {
    val idLot = client.submitFormWithBinaryData(
        url = Route.ITEM_URL,
        formData = formData {
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
    val url = Route.ITEM_URL + id.drop(1).dropLast(1) + "/photos"
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

suspend fun getUser(id: String): UserData {
    return client.get(Route.USER_URL + id).body()
}

suspend fun getUser(): UserData {
    return client.get(Route.USER_URL) {
        headers {
            append(HttpHeaders.Authorization, token)
        }
    }.body()
}

suspend fun getChats(): List<ShortChatData>{
    return client.get(Route.CHAT_URL) {
        headers {
            append(HttpHeaders.Authorization, token)
        }
    }.body()
}