package com.wehby.githubstarredrepos

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.net.URL

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.wehby.githubstarredrepos", appContext.packageName)
    }

    @Test
    fun formatUrl() {
        val url = URL("https", "api.github.com", "/asdf/jjjj/contributors")
        assertEquals("https://api.github.com/asdf/jjjj/contributors", url.toString())
    }

    @Test
    fun formatRepoUrl() {
        val url = URL("https", "api.github.com", "/search/repositories?q=stars:>0&per_page=100")
        assertEquals("https://api.github.com/asdf/jjjj/contributors", url.toString())
    }
}