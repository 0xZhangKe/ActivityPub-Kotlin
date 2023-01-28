package com.zhangke.activitypub.api

import com.zhangke.activitypub.utils.newCmxClient
import com.zhangke.activitypub.utils.newMastodonClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

/**
 * Created by ZhangKe on 2022/12/13.
 */
class TimelinesRepoTest {

    private val activityPub = newCmxClient()

    @Test
    fun testLocalTimelines() {
        runBlocking {
            activityPub.timelinesRepo.localTimelines()
                .onFailure {
                    println(it.stackTraceToString())
                }
                .onSuccess {
                    println(it)
                }
        }
    }

    @Test
    fun testPublicTimelines() {
        runBlocking {
            activityPub.timelinesRepo.localTimelines()
                .onFailure {
                    println(it.stackTraceToString())
                }
                .onSuccess {
                    println(it)
                }
        }
    }

    @Test
    fun testHomeTimeline() {
        runBlocking {
            activityPub.timelinesRepo.homeTimeline(
                maxId = "",
                minId = "",
                sinceId = "",
                limit = 30
            ).onFailure {
                it.printStackTrace()
            }.onSuccess {
                println(it)
            }
        }
    }
}