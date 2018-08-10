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
import android.support.v7.widget.Toolbar
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.RelativeLayout
import com.google.zxing.Result
import com.google.zxing.ResultPoint
import com.sqsong.qrcodelib.camera.CameraManager
import com.sqsong.qrcodelib.camera.QRCodeDecodeCallback
import com.sqsong.qrcodelib.camera.QRCodeManager
import com.sqsong.qrcodelib.util.DensityUtil
import com.sqsong.qrcodelib.view.QRCodeScanView
import kz.sgq.colorassistant.R

class QRCodeScanActivity : AppCompatActivity(), QRCodeDecodeCallback {
    private var mSurfaceView: SurfaceView? = null
    private var mQRCodeScanView: QRCodeScanView? = null
    private var mSurfaceHolder: SurfaceHolder? = null
    private var mQRCodeManager: QRCodeManager? = null
    private var mToolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_qrcode_scan)
        initView()
        initEvent()
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

        if (mQRCodeScanView != null && cameraManager != null)
            mQRCodeScanView!!.setCameraManager(cameraManager)
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
    }

    private fun initView() {
        mToolbar = findViewById<View>(R.id.toolbar) as Toolbar
        mSurfaceView = findViewById<View>(R.id.surface_view) as SurfaceView
        mQRCodeScanView = findViewById<View>(R.id.qr_scan_view) as QRCodeScanView
    }

    private fun initEvent() {

        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mToolbar!!.setNavigationOnClickListener { finish() }

        val layoutParams = mToolbar!!.layoutParams as RelativeLayout.LayoutParams
        layoutParams.topMargin = DensityUtil.getStatusBarHeight(this)
        mSurfaceHolder = mSurfaceView!!.holder
        mQRCodeManager = QRCodeManager(applicationContext, this)
    }
}