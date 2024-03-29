package com.zhangke.activitypub.api

import com.zhangke.activitypub.utils.newAndroidClient
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Created by ZhangKe on 2022/12/2.
 */

class AppsApiTest {

    @Test
    fun testRegister() = runBlocking {
        val activityPubClient = newAndroidClient()
        try {
            AppsRepo(activityPubClient.retrofit).registerApplication(
                clientName = "utopia",
                redirectUris = listOf("utopia://oauth.utopia"),
                scopes = AppsRepo.AppScopes.ALL,
                website = "https://0xzhangke.github.io/"
            ).onFailure {
                println("onError:$it")
                assert(false)
            }.onSuccess {
                println("onSuccess:$it")
                assert(true)
            }
            assert(true)
        } catch (e: Throwable) {
            e.printStackTrace()
            assert(false)
        }
    }

    @Test
    fun testRegisterIllegalRequest() {
        runBlocking {
//            try {
//                activityPub.oauthRepo.registerApplication(
//                    clientName = "utopia",
//                    redirectUris = emptyList(),
//                    scopes = RegisterRepo.AppScopes.ALL,
//                    website = "https://0xzhangke.github.io/"
//                ).onFailure {
//                    println("onError:$it")
//                    assert(false)
//                }.onSuccess {
//                    println("onSuccess:$it")
//                    assert(true)
//                }
//                assert(true)
//            } catch (e: Throwable) {
//                e.printStackTrace()
//                assert(false)
//            }
        }
    }

    @Test
    fun testRegisterIOError() {
        runBlocking {
//            try {
//                activityPub.oauthRepo.registerApplication(
//                    clientName = "utopia",
//                    redirectUris = listOf("https://0xzhangke.github.io/"),
//                    scopes = RegisterRepo.AppScopes.ALL,
//                    website = "https://0xzhangke.github.io/"
//                ).onFailure {
//                    println("onError:$it")
//                    assert(false)
//                }.onSuccess {
//                    println("onSuccess:$it")
//                    assert(true)
//                }
//                assert(true)
//            } catch (e: Throwable) {
//                e.printStackTrace()
//                assert(false)
//            }
        }
    }
}
