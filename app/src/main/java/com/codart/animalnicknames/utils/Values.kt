package com.codart.animalnicknames.utils

import java.util.*

object Values {
    const val PATH_BASE = "https://mydog.biz/api/rest/"
    const val PATH_GET_TOKEN ="oauth2/token/client_credentials"
    const val TOKEN_BASIC="Basic c2hvcHBpbmdfb2F1dGhfY2xpZW50OnNob3BwaW5nX29hdXRoX3NlY3JldA=="
    const val PATH_INPUT_CATEGORIES ="request"
    const val PATH_USER_DATA = "account"
    const val PATH_INPUT = "credit"
    const val PATH_FIREBASE_TOKEN = "firebase_token"
    const val PATH_LOCALIZATION = "account/setgeo"
    const val PATH_CONTACTS = "account/addcontact"
    const val BASIC_SKU = "1"
    const val PREMIUM_SKU = "premium_subscription"
    const val PLAY_STORE_SUBSCRIPTION_URL
            = "https://play.google.com/store/account/subscriptions"
    const val PLAY_STORE_SUBSCRIPTION_DEEPLINK_URL
            = "https://play.google.com/store/account/subscriptions?sku=%s&package=%s"

    val locale = if(Locale.getDefault().language == "ru" || Locale.getDefault().language == "ua"){
        "ru-ru"
    }
    else{
        "en-gb"
    }
}