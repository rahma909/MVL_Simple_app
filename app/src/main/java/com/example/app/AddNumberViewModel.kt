package com.example.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class AddNumberViewModel:ViewModel (){

    // activity to viewModel
    val intentChannel=Channel<MainIntent>(Channel.UNLIMITED)

    //viewModel to activity
    private val _viewState= MutableStateFlow<MainViewState>(MainViewState.Idle)
    val state:StateFlow<MainViewState> get() = _viewState

    private var number=0


    init {
        processIntent()
    }
    //process
    private fun processIntent(){
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect{
                when(it){
                    is MainIntent.AddNumber ->addNumber()
                    is MainIntent.ZeroNumber-> zeroNumber()
                }
            }
        }

    }
    //reduce
    private fun addNumber(){
        viewModelScope.launch {
            _viewState.value=
                try {
                    MainViewState.Number(++ number )
                }catch (e:Exception){
                    MainViewState.Error(e.message!!)
                }
        }
    }
    private fun zeroNumber(){
        viewModelScope.launch {
            number=0
            _viewState.value=
            try {
                    MainViewState.ZeroNumber(0)
                }catch (e:Exception){
                    MainViewState.Error(e.message!!)
                }
        }
    }

}