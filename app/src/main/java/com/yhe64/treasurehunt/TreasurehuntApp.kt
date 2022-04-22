package com.yhe64.treasurehunt

import android.app.Application

class TreasurehuntApp: Application() {

    companion object{
        private lateinit var instance: TreasurehuntApp

        const val SHOW_MESSAGE_AT_START = "show_message_at_start"
        const val CARD_COLOR = "card_color"
        const val SHOW_POINT_SCORE = "show_point_score"
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }
}