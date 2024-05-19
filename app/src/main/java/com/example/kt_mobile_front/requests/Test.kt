package com.example.kt_mobile_front.requests

import android.content.Context
import android.net.Uri
import com.example.kt_mobile_front.data.ChatData
import com.example.kt_mobile_front.data.CreateLotData
import com.example.kt_mobile_front.data.LotData
import com.example.kt_mobile_front.data.PasswordData
import com.example.kt_mobile_front.data.PutUserData
import com.example.kt_mobile_front.data.ShortChatData
import com.example.kt_mobile_front.data.ShortLotData
import com.example.kt_mobile_front.data.SignInData
import com.example.kt_mobile_front.data.SignInResponseData
import com.example.kt_mobile_front.data.SignUpUserData
import com.example.kt_mobile_front.data.UserData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.preparePut
import io.ktor.client.request.put
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.ContentType.Application.FormUrlEncoded
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.http.formUrlEncode
import io.ktor.http.headers
import io.ktor.http.parameters
import kotlinx.serialization.encodeToString
import java.util.UUID

var token = ""
val client = HttpClient(Android) {
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
    town: String,
    uris: List<Uri>,
    context: Context
): String {
    val response = client.submitFormWithBinaryData(
        url = Route.SIGNUP_URL,
        formData = formData {
            append("user_json", kotlinx.serialization.json.Json.encodeToString(signUpUserData))
            append("town", town)
        }
    ).body<String>()
    postPhotoSignUp(uris, response, context)
    return response
}


suspend fun postPhotoSignUp(
    uris: List<Uri>,
    id: String,
    context: Context,
) {
    val url = Route.USER_URL + id.drop(1).dropLast(1) + "/photos"
    return client.submitFormWithBinaryData(
        url = url,
        formData = formData {
            uris.forEach { uri ->
                append(
                    "photos",
                    context.contentResolver.openInputStream(uri)!!.use { it.readBytes() },
                    Headers.build {
                        append(HttpHeaders.ContentType, "image/jpeg")
                        append(
                            HttpHeaders.ContentDisposition,
                            "filename=\"${UUID.randomUUID()}.jpeg\""
                        )
                    }
                )
            }
        }
    ) { }.body()
}


suspend fun getAllItems(): List<ShortLotData> {
    return client.get(Route.ITEM_URL).body()
}

suspend fun getItemById(id: String): LotData {
    return client.get(Route.ITEM_URL + id).body()
}

suspend fun delItem(id: String) {
    client.delete(
        Route.ITEM_URL + id
    ) {
        headers {
            append(HttpHeaders.Authorization, token)
        }
    }
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
                        append(HttpHeaders.ContentType, "image/jpeg")
                        append(
                            HttpHeaders.ContentDisposition,
                            "filename=\"${UUID.randomUUID()}.jpeg\""
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

suspend fun getChats(): List<ShortChatData> {
    return client.get(Route.CHAT_URL) {
        headers {
            append(HttpHeaders.Authorization, token)
        }
    }.body()
}

suspend fun getChatById(id: String): ChatData {
    return client.get(Route.CHAT_URL + "/" + id) {
        headers {
            append(HttpHeaders.Authorization, token)
        }
    }.body()
}

suspend fun putPassword(
    old_pass: String,
    new_pass: String
) {
    client.submitFormWithBinaryData(
        Route.PASSWORD_URL,
        formData {
            append("old_pass", old_pass)
            append("new_pass", new_pass)
        }
    ) {
        headers {
            append(HttpHeaders.Authorization, token)
        }
    }
}

suspend fun postCreateChat(
    id: String
): ChatData {
    val url = Route.CHAT_URL + "/create/" + id
    return client.submitFormWithBinaryData(
        url,
        formData {

        }
    ) {
        headers {
            append(HttpHeaders.Authorization, token)
        }
    }.body<ChatData>()
}

suspend fun postMessage(
    id: String,
    msg: String
) {
    val url = Route.CHAT_URL + "/" + id
    client.submitFormWithBinaryData(
        url,
        formData {
            append("message", msg)
        }
    ) {
        headers {
            append(HttpHeaders.Authorization, token)
        }
    }
}

suspend fun putLot(
    id: String,
    createLotData: CreateLotData,
    cat: String,
    cond: String,
    uris: List<Uri>,
    context: Context
) {
    client.submitFormWithBinaryData(
        url = Route.ITEM_URL + "up/" + id,
        formData {
            append("lot_json", kotlinx.serialization.json.Json.encodeToString(createLotData))
            append("cat", cat)
            append("cond", cond)
            uris.forEach { uri ->
                append(
                    "photos",
                    context.contentResolver.openInputStream(uri)!!.use { it.readBytes() },
                    Headers.build {
                        append(HttpHeaders.ContentType, "image/jpeg")
                        append(
                            HttpHeaders.ContentDisposition,
                            "filename=\"${UUID.randomUUID()}.jpeg\""
                        )
                    }
                )
            }
        }
    ) {
        headers {
            append(HttpHeaders.Authorization, token)
        }
    }
}

suspend fun putUser(
    putUserData: PutUserData,
    town: String,
    uris: List<Uri>,
    context: Context
) {
    val url = Route.USER_URL + "up/"
    val response = client.submitFormWithBinaryData(
        url = url,
        formData = formData {
            append("user_json", kotlinx.serialization.json.Json.encodeToString(putUserData))
            append("town", town)
        }
    ){
        headers {
            append(HttpHeaders.Authorization, token)
        }
    }.body<String>()
    putPhotoUser(uris, response, context)
}

suspend fun putPhotoUser(
    uris: List<Uri>,
    id: String,
    context: Context,
) {
    val url = Route.USER_URL + id.drop(1).dropLast(1) + "/photos"
    return client.submitFormWithBinaryData(
        url = url,
        formData = formData {
            uris.forEach { uri ->
                append(
                    "photos",
                    context.contentResolver.openInputStream(uri)!!.use { it.readBytes() },
                    Headers.build {
                        append(HttpHeaders.ContentType, "image/jpeg")
                        append(
                            HttpHeaders.ContentDisposition,
                            "filename=\"${UUID.randomUUID()}.jpeg\""
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