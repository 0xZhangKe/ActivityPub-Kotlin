package com.zhangke.activitypub.utils

import com.zhangke.activitypub.entities.ActivityPubTokenEntity
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.bearerAuth
import io.ktor.util.AttributeKey
import io.ktor.utils.io.KtorDsl

class AuthorizationPlugin private constructor(
  private val tokenProvider: (suspend () -> ActivityPubTokenEntity?)?,
) {
  @KtorDsl
  class Config(internal var tokenProvider: (suspend () -> ActivityPubTokenEntity?)?)

  @KtorDsl
  companion object Plugin : HttpClientPlugin<Config, AuthorizationPlugin> {

    override val key: AttributeKey<AuthorizationPlugin>
      get() = AttributeKey("AuthorizationPlugin")

    override fun install(plugin: AuthorizationPlugin, scope: HttpClient) {
      plugin.setupRequestAuthorization(scope)
    }

    override fun prepare(block: Config.() -> Unit): AuthorizationPlugin {
      val config = Config(null).apply(block)
      return AuthorizationPlugin(config.tokenProvider)
    }
  }

  private fun setupRequestAuthorization(client: HttpClient) {
    client.requestPipeline.intercept(HttpRequestPipeline.State) {
      if (tokenProvider != null) {
        val token = tokenProvider.invoke()
        if (token != null) {
          context.bearerAuth(token.accessToken)
        }
      }
    }
  }
}
