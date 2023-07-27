package com.zhangke.activitypub.api

import com.zhangke.activitypub.utils.newCmxClient
import kotlinx.coroutines.runBlocking
import org.junit.Test

class InstanceRepoTest {

    private val instanceRepo = newCmxClient().instanceRepo

    @Test
    fun shouldResultInstance() {
        runBlocking {
            val instance = instanceRepo.getInstanceInformation().getOrThrow()
            println(instance)
        }
    }

    @Test
    fun shouldResultAnnouncement() {
        runBlocking {
            val announcement = instanceRepo.getAnnouncement().getOrThrow()
            println(announcement)
        }
    }

    @Test
    fun shouldResultTagsWhenAuth() {
        runBlocking {
            val tags = instanceRepo.getTrendsTags(limit = 20, offset = 0).getOrThrow()
            println(tags)
        }
    }
}
