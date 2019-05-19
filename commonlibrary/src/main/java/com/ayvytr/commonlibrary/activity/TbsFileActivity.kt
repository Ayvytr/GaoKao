package com.ayvytr.commonlibrary.activity

import android.os.Bundle
import android.os.Environment
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.tencent.smtt.sdk.TbsReaderView
import kotlinx.android.synthetic.main.activity_tbs_file.*
import java.io.File


class TbsFileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.ayvytr.commonlibrary.R.layout.activity_tbs_file)
        initView()
    }

    private fun initView() {
        var mTbsReaderView = TbsReaderView(this,
            TbsReaderView.ReaderCallback { integer, o, o1 -> })
        cl_root.addView(mTbsReaderView, ConstraintLayout.LayoutParams(-1, -1))

        val bsReaderTempFile = File(Environment.getExternalStorageDirectory().absolutePath , "TbsReaderTemp")
        if (!bsReaderTempFile.exists()) {
            Log.d("print", "准备创建/TbsReaderTemp！！")
            val mkdir = bsReaderTempFile.mkdir()
            if (!mkdir) {
                Log.d("print", "创建/TbsReaderTemp失败！！！！！")
            }
        }
        val bundle = Bundle()
        bundle.putString("filePath", "/storage/emulated/0/0a/心理学原理.威廉_詹姆斯.pdf")
        bundle.putString("tempPath", bsReaderTempFile.absolutePath)

        mTbsReaderView.openFile(bundle)
    }
}
