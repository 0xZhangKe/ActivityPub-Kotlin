package com.zhangke.activitypub.api

import com.google.gson.JsonObject
import com.zhangke.activitypub.ActivityPubApplication
import com.zhangke.activitypub.utils.testGlobalGson
import java.io.File

val testApplication: ActivityPubApplication = getApplication()

private fun getApplication(): ActivityPubApplication {
    val file = File("src/test/TestConfig.json")
    val text = file.readText()
    val applicationJson = testGlobalGson.fromJson(text, JsonObject::class.java)
    return testGlobalGson.fromJson(applicationJson.getAsJsonObject("mastodon"), ActivityPubApplication::class.java)
}