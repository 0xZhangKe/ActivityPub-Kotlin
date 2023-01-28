package com.zhangke.activitypub.utils

import com.google.gson.JsonObject
import com.zhangke.activitypub.ActivityPubApplication
import com.zhangke.activitypub.entry.ActivityPubToken
import java.io.File

object TestConfig {

    private val configFile = File("src/test/TestConfig.json")

    fun readToken(): ActivityPubToken {
        val json = testGlobalGson.fromJson<JsonObject>(configFile.readText(), JsonObject::class.java)
        val token = json.getAsJsonPrimitive("token").asString
        return ActivityPubToken(
            accessToken = token,
            tokenType = "",
            scope = "",
            createdAt = ""
        )
    }

    fun readActivityPubApplication(): ActivityPubApplication {
        val applicationJson = testGlobalGson.fromJson(configFile.readText(), JsonObject::class.java)
        return testGlobalGson.fromJson(
            applicationJson.getAsJsonObject("cmx"),
            ActivityPubApplication::class.java
        )
    }
}