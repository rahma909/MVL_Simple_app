package com.example.app

sealed class MainIntent {
    object AddNumber:MainIntent()
    object ZeroNumber:MainIntent()
}