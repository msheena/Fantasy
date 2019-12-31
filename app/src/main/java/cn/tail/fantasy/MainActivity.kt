package cn.tail.fantasy

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.tail.fantasy.material.SuspensionAdapter
import cn.tail.fantasy.pop.AnglePopUpWindow
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {
    var height=0
    var currentPos=0
    lateinit var suspensionTitle:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        suspensionTitle=findViewById<TextView>(R.id.tv_suspension_bar)
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerview)
        val layoutManager=LinearLayoutManager(this)
        recyclerView.layoutManager=layoutManager
        val adapter=SuspensionAdapter()
        recyclerView.adapter=adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                height=suspensionTitle.height
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val view=layoutManager.findViewByPosition(currentPos+1)
                if (view!=null){
                    if(view.top<=height){
                        suspensionTitle.y= (-(height-view.top)).toFloat()
                    }else{
                        suspensionTitle.y=0f
                    }
                }

                if (currentPos!=layoutManager.findFirstVisibleItemPosition()){
                    currentPos=layoutManager.findFirstVisibleItemPosition()
                    updateSuspensionBar()
                }


            }
        })


    }

    fun updateSuspensionBar(){
        suspensionTitle.setText("item: $currentPos")
    }


}
