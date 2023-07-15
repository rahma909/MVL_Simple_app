package com.example.app


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var numberTv:TextView
    lateinit var addNumberBtn:Button
    lateinit var zeronNumberBtn:Button
private val viewModel: AddNumberViewModel by lazy {
    androidx.lifecycle.ViewModelProvider(this,
        defaultViewModelProviderFactory).get(AddNumberViewModel::class.java)
}

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        numberTv=findViewById(R.id.number_textView)
        addNumberBtn=findViewById(R.id.add_number_button)
        zeronNumberBtn=findViewById(R.id.zero_button)



        render()
            //send
        addNumberBtn.setOnClickListener{
            lifecycleScope.launch {
                viewModel.intentChannel.send(MainIntent.AddNumber)
            }
        }
        zeronNumberBtn.setOnClickListener{
            lifecycleScope.launch {
                viewModel.intentChannel.send(MainIntent.ZeroNumber)
            }
        }

    }

    //render
    private fun render(){
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect{
                when(it){
                    is MainViewState.Idle ->numberTv.text="ابدأ"
                    is MainViewState.Number ->numberTv.text=it.number.toString()
                    is MainViewState.Error -> numberTv.text= it.error
                    is MainViewState.ZeroNumber->numberTv.text=it.zeroNumber.toString()
                }

            }
        }
    }
}