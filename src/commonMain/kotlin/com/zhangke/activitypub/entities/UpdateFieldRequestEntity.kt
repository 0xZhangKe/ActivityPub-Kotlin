package com.zhangke.activitypub.entities

import kotlinx.serialization.Serializable

@Serializable
data class UpdateFieldRequestEntity(
    val name: String,
    val value: String,
)
