/*
 * Copyright 2018 Vlad Weber-Pflaumer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kz.sgq.colorassistant.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.SurfaceHolder
import com.google.zxing.Result
import com.google.zxing.ResultPoint
import com.sqsong.qrcodelib.camera.CameraManager
import com.sqsong.qrcodelib.camera.QRCodeDecodeCallback
import com.sqsong.qrcodelib.camera.QRCodeManager
import com.sqsong.qrcodelib.view.QRCodeScanView
import kotlinx.android.synthetic.main.activity_qrcode_scan.*
import kz.sgq.colorassistant.R
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper

class QRCodeScanActivity : AppCompatActivity(), QRCodeDecodeCallback {
    private var mQRCodeScanView: QRCodeScanView? = null
    private var mSurfaceHolder: SurfaceHolder? = null
    private var mQRCodeManager: QRCodeManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        val helper = SwipeBackActivityHelper(this)

        helper.onActivityCreate()
        setContentView(R.layout.activity_qrcode_scan)
        helper.swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
        initEvent()
        helper.onPostCreate()
    }

    override fun onResume() {
        super.onResume()

        mQRCodeManager!!.onResume(mSurfaceHolder!!)
    }

    override fun onPause() {
        super.onPause()

        mQRCodeManager!!.onPause(mSurfaceHolder)
    }

    override fun cameraManagerInitFinish(cameraManager: CameraManager?) {

        if (qr_scan_view != null && cameraManager != null)
            qr_scan_view!!.setCameraManager(cameraManager)
    }

    override fun foundPossibleResultPoint(point: ResultPoint) {

        if (mQRCodeScanView != null)
            mQRCodeScanView!!.addPossibleResultPoint(point)
    }

    override fun onDecodeSuccess(result: Result) {
        val intent = Intent()

        intent.putExtra("scan_result", result.text)
        setResult(Activity.RESULT_OK, intent)
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun initEvent() {
        bar.title = ""

        setSupportActionBar(bar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        bar!!.setNavigationOnClickListener {

            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        mSurfaceHolder = surface_view!!.holder
        mQRCodeManager = QRCodeManager(applicationContext, this)
    }
}