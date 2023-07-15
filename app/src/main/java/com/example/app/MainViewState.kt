package com.example.app

sealed class MainViewState {
    //idle
    object Idle:MainViewState()
    //number
    data class Number(val number:Int):MainViewState()
    //error
    data class Error(val error:String):MainViewState()
    //zeroNumber
    data class ZeroNumber(val zeroNumber:Int):MainViewState()


}