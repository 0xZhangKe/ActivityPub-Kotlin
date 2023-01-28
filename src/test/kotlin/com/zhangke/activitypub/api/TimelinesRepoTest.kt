package com.zhangke.activitypub.api

import com.zhangke.activitypub.utils.newMastodonClient
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Created by ZhangKe on 2022/12/13.
 */
class TimelinesRepoTest {

    private val activityPub = newMastodonClient()

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
}