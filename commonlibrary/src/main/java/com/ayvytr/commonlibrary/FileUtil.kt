package com.ayvytr.commonlibrary

import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.support.annotation.RequiresApi

/**
 * @author admin
 */
object FileUtil {

    val sdFreeSpace: Long
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        get() {
            val stat = StatFs(Environment.getExternalStorageDirectory().absolutePath)
            val blockSize = stat.blockSizeLong
            val availableBlocks = stat.availableBlocksLong
            return availableBlocks * blockSize
        }
}
