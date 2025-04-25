package com.zhangke.activitypub.entities

import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubSuggestionEntry (
    val source: String,
    val account: ActivityPubAccountEntity,
){

    companion object{

        /**
         * This account was manually recommended by your administration team
         */
        const val SOURCE_STAFF = "staff"

        /**
         * You have interacted with this account previously
         */
        const val SOURCE_PAST_INTERACTIONS = "past_interactions"

        /**
         * This account has many reblogs, favourites, and active local followers within the last 30 days
         */
        const val SOURCE_GLOBAL = "global"
    }
}
