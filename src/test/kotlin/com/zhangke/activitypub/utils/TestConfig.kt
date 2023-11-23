package com.zhangke.activitypub.utils

import com.google.gson.JsonObject
import com.zhangke.activitypub.ActivityPubApplication
import com.zhangke.activitypub.entities.ActivityPubTokenEntity
import java.io.File

object TestConfig {

    private val configFile = File("src/test/TestConfig.json")

    fun readToken(): ActivityPubTokenEntity? {
        val json = testGlobalGson.fromJson(configFile.readText(), JsonObject::class.java)
        val tokenPrimitive = json.getAsJsonPrimitive("token")?.asString
        if (tokenPrimitive.isNullOrEmpty()){
            return null
        }
        return ActivityPubTokenEntity(
            accessToken = tokenPrimitive,
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