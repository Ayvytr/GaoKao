package com.ayvytr.commonlibrary

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented com.ayvytr.commonlibrary.test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under com.ayvytr.commonlibrary.test.
        val appContext = InstrumentationRegistry.getTargetContext()

        assertEquals("com.ayvytr.commonlibrary.com.ayvytr.commonlibrary.test", appContext.packageName)
    }
}
