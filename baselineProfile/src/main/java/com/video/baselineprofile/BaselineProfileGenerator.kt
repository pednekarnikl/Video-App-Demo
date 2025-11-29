package com.video.baselineprofile

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class BaselineProfileGenerator {

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generate() {
        rule.collect(
            packageName = InstrumentationRegistry.getArguments().getString("targetAppId")
                ?: throw Exception("targetAppId not passed as instrumentation runner arg"),

            includeInStartupProfile = true
        ) {

            pressHome()
            startActivityAndWait()


            waitForAsyncCall()
            scrollDown()
            goToDetailsScreen()

        }
    }
}

fun MacrobenchmarkScope.waitForAsyncCall(){
    device.wait(Until.hasObject(By.res("video_list")), 5000)
    val list = device.findObject(By.res("video_list"))
    list.wait(Until.hasObject(By.res("video_list")), 5000)
}


fun MacrobenchmarkScope.scrollDown() {
    val list = device.findObject(By.res("video_list"))
    // Set gesture margin to avoid triggering gesture navigation.
    list.setGestureMargin(device.displayWidth / 5)
    list.fling(Direction.DOWN)
    device.waitForIdle()
}

fun MacrobenchmarkScope.goToDetailsScreen() {
    val list = device.findObject(By.res("video_list"))
    val items = list.findObjects(By.res("video_item"))
    // Select snack from the list based on running iteration.
    val index = 3
    items[index].click()
    // Wait until the screen is gone = the detail is shown.
    device.wait(Until.gone(By.res("video_list")), 5_000)
}

/*fun MacrobenchmarkScope.allowPermissions() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val command = "pm grant $packageName ${Manifest.permission.POST_NOTIFICATIONS}"
        val output = device.executeShellCommand(command)
        Assert.assertEquals("", output)
    }
    val command = "pm grant $packageName ${Manifest.permission.ACCESS_FINE_LOCATION}"
    val output = device.executeShellCommand(command)
    Assert.assertEquals("", output)
}*/
