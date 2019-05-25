package com.angelvargas.cvapp.presenter

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.angelvargas.cvapp.CvApplication
import com.angelvargas.cvapp.R
import com.angelvargas.cvapp.assertionutil.CustomAssertions.Companion.hasItemCount
import com.angelvargas.cvapp.assertionutil.CustomMatchers.Companion.withItemCount
import com.angelvargas.cvapp.di.DaggerMockAppComponent
import com.angelvargas.cvapp.di.MockAppModule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java, true, false)


    @Before
    fun setupMainActivity() {

        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as CvApplication

        val testComponent = DaggerMockAppComponent.builder()
            .mockAppModule(MockAppModule())
            .build()
        app.appComponent = testComponent
        activityRule.launchActivity(Intent())
    }

    @Test
    fun countPrograms() {
        onView(withId(R.id.rv_resume_info))
            .check(matches(withItemCount(7)))
    }

    @Test
    fun countProgramsWithViewAssertion() {
        onView(withId(R.id.rv_resume_info))
            .check(hasItemCount(7))
    }

}