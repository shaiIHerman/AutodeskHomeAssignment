package com.shai.autodesk.views.activities

import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.shai.autodesk.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @JvmField
    @Rule
    val testRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun init() {
        testRule.activity
            .supportFragmentManager.beginTransaction()
    }

    @Test
    fun initialViewsDisplayedProperly() {
        onView(withId(R.id.containerLay)).check(matches(isDisplayed()))

        onView(
            allOf(
                instanceOf(TextView::class.java),
                withParent(withResourceName("action_bar"))
            )
        )
            .check(matches(withText("NewsApp Candidate Assignment")))
    }
}