package hu.bme.caffshare

import android.os.Bundle
import co.zsmb.rainbowcake.navigation.SimpleNavActivity
import hu.bme.caffshare.ui.cafflist.CaffListFragment

class MainActivity : SimpleNavActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            navigator.add(CaffListFragment())
        }
    }

}
