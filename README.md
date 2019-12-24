# Fantasy
随便写写的乱七八糟的东西(#^.^#)

## AnglePopUpWindow
带有三角尖尖的popupView,可根据剩余空间自动显示在anchorView的上方或者下方
## 使用方法
所有属性支持xml中设置，也支持java代码设置
```
<cn.tail.fantasy.pop.AngleLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:id="@+id/angle_pop_container"
    app:angleWidth="9dp"
    app:angleHeight="4.5dp"
    app:paintColor="#000"
    app:radius="4dp"
    >
</cn.tail.fantasy.pop.AngleLayout>
```

```
val view=LayoutInflater.from(this).inflate(R.layout.test_menu, null);
val testPop = AnglePopUpWindow(this, 300, 300)
testPop.inflateView(view);
testPop.setAngleColor(Color.CYAN)
testPop.setAngleDimensions(15f,9f)
testPop.showAsDropDown(btnTest)
```
