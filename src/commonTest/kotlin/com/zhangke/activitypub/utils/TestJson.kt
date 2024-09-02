package com.zhangke.activitypub.utils

import kotlinx.serialization.json.Json

val testGlobalJson = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
}