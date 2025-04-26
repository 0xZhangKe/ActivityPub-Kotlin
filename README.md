## ActivityPub-Kotlin

Kotlin version of the ActivityPub protocol client SDK, used to communicate with the ActivityPub
server.
Support Kotlin Multiplatform.

## Usage

```kotlin
    val client = ActivityPubClient(
        baseUrl = "${baseUrl}/",
        engine = httpClientEngine,
        json = globalJson,
        tokenProvider = tokenProvider,
        onAuthorizeFailed = {

        },
    )
    client.searchRepo.query()
```
### Add Dependency
```kts
implementation("io.github.0xzhangke:activity-pub-client:0.1.0")
```