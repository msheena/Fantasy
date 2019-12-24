package cn.tail.fantasy

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import cn.tail.fantasy.pop.AnglePopUpWindow

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        var testLayout=findViewById<AngelLayout>(R.id.test_angel)
//        testLayout.setAnchorView()
        var btnTest=findViewById<Button>(R.id.test_button)
        val function: (View) -> Unit = {
            val view=LayoutInflater.from(this).inflate(R.layout.test_menu, null);
            val testPop = AnglePopUpWindow(this, 300, 300)
            testPop.inflateView(view);
            testPop.setAngleColor(Color.CYAN)
            testPop.setAngleDimensions(15f,9f)
            testPop.showAsDropDown(btnTest)
//            testPop.showAtLocation(btnTest,Gravity.TOP,0,0)
        }
        btnTest.setOnClickListener(function)
    }

//    @ClickBehavior("click")
//    public fun click(){
//
//    }
}
