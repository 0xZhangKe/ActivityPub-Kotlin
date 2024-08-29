package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by ZhangKe on 2022/12/4.
 */
@Serializable
data class RegisterApplicationEntry(
    val id: String,
    val name: String,
    val website: String? = null,
    @SerialName("redirect_uri") val redirectUri: String,
    @SerialName("client_id") val clientId: String? = null,
    @SerialName("client_secret") val clientSecret: String? = null,
    @SerialName("vapid_key") val vapidKey: String? = null,
)
