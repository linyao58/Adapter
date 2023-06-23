package com.example.myadapter

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.permissionx.linyaodev.PermissionX
import com.pgyersdk.activity.FeedbackActivity
import com.pgyersdk.crash.PgyCrashManager
import com.pgyersdk.feedback.PgyFeedback
import com.pgyersdk.feedback.PgyFeedbackShakeManager
import com.tencent.bugly.crashreport.CrashReport
import kotlinx.android.synthetic.main.activity_main2.*
import java.io.IOException
import java.lang.Exception
import java.util.*

class MainActivity2 : AppCompatActivity() {

    var longitude: Double? = null
    var latitude: Double? = null
    var locationManager: LocationManager? = null

    val mLocationListener = object : LocationListener{
        override fun onLocationChanged(location: Location) {

//            获取当前纬度
            latitude = location.latitude

//            获取当前经度
            longitude = location.longitude

//            定义位置解析
            val geocoder = Geocoder(this@MainActivity2, Locale.getDefault())
            try {
                // 获取经纬度对于的位置
                // getFromLocation(纬度, 经度, 最多获取的位置数量)
                    if (latitude != null){
                        val addresses: List<Address> = geocoder.getFromLocation(latitude!!, longitude!!, 1)

                        //                得到第一个经纬度位置解析信息
                        val address = addresses[0]
                        Log.e("TAG", "onLocationChanged: $address")
                        // 获取到详细的当前位置
                        // Address里面还有很多方法你们可以自行实现去尝试。比如具体省的名称、市的名称...
                        val info = address.countryName + address.getAddressLine(0)
                        Log.e("TAG", "onLocationChanged: $info")
                        text.text = info
                    }

            }catch (e: IOException){
                Log.e("TAG", "onLocationChanged: ${e.printStackTrace()}")
            }

            locationManager?.removeUpdates(this)

        }

        override fun onProviderDisabled(provider: String) {
            Log.e("TAG", "onStatusChanged: $provider")
        }

        override fun onProviderEnabled(provider: String) {

            Log.e("TAG", "onStatusChanged: $provider")
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

            Log.e("TAG", "onStatusChanged: $provider")

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        PermissionX.request(this,
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.CHANGE_WIFI_STATE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE){allGranted, deniedList ->
            if (allGranted){
                getLocation()
            }
        }

        but.setOnClickListener {
            getLocation()
//            CrashReport.testJavaCrash()
//            showDialog()
        }

    }

    override fun onResume() {
        super.onResume()

        // 自定义摇一摇的灵敏度，默认为950，数值越小灵敏度越高。
        PgyFeedbackShakeManager.setShakingThreshold(1000)

//        以对话框的形式弹出，对话框只支持竖屏
        PgyFeedbackShakeManager.register(this)

        FeedbackActivity.setBarImmersive(true)
        PgyFeedbackShakeManager.register(this, false)

    }

    override fun onPause() {
        super.onPause()

        PgyCrashManager.unregister()

    }

    fun showDialog(){

        PgyFeedback.getInstance().showDialog(this)
        PgyFeedback.getInstance().showActivity(this)

    }

    fun getLocation(){

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1f, mLocationListener)

    }


}