package com.angelvargas.cvapp.presenter

import android.content.Intent
import android.support.design.widget.CollapsingToolbarLayout
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.angelvargas.cvapp.CvApplication
import com.angelvargas.cvapp.R
import com.angelvargas.cvapp.assertionutil.CustomAssertions.Companion.hasItemCount
import com.angelvargas.cvapp.assertionutil.CustomMatchers.Companion.atPosition
import com.angelvargas.cvapp.assertionutil.CustomMatchers.Companion.withItemCount
import com.angelvargas.cvapp.di.DaggerMockAppComponent
import com.angelvargas.cvapp.di.MockAppModule
import com.angelvargas.cvapp.domain.models.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.core.Is.`is`
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
            .mockAppModule(MockAppModule(ResumeData(createBasicsData(), createWorkDataList(), createSkillDataList())))
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

    @Test
    fun checHeaderTitle() {
        matchToolbarTitle(BASICS_NAME)
    }

    @Test
    fun checkDescriptionContent() {
        onView(withId(R.id.tv_profile_description))
            .check(matches(withText(BASICS_SUMMARY)))
    }

    @Test
    fun testTitleSkillsList() {
        onView(withId(R.id.rv_resume_info))
            .check(matches(atPosition(0, withText("Skills"))))
    }

    @Test
    fun testElementSkillsList() {
        onView(withId(R.id.rv_resume_info))
            .check(matches(atPosition(1, hasDescendant(withText(SKILL_NAME)))))
    }

    private fun createSkillDataList(): List<SkillsData> {
        return listOf(
            SkillsData(
                SKILL_NAME,
                SKILL_LEVEL,
                SKILL_KEYWORDS
            )
        )
    }

    private fun matchToolbarTitle(
        title: CharSequence
    ): ViewInteraction {
        return onView(isAssignableFrom(CollapsingToolbarLayout::class.java))
            .check(matches(withToolbarTitle(`is`(title))))
    }

    private fun withToolbarTitle(
        textMatcher: Matcher<CharSequence>
    ): Matcher<Any> {
        return object : BoundedMatcher<Any, CollapsingToolbarLayout>(CollapsingToolbarLayout::class.java) {
            public override fun matchesSafely(toolbar: CollapsingToolbarLayout): Boolean {
                return textMatcher.matches(toolbar.title)
            }

            override fun describeTo(description: Description) {
                description.appendText("with toolbar title: ")
                textMatcher.describeTo(description)
            }
        }
    }

    private fun createWorkDataList(): List<WorkData> {
        return listOf(
            WorkData(
                WORK_COMPANY,
                WORK_POSITION,
                WORK_URL_IMAGE,
                WORK_START_DATE,
                WORK_END_DATE,
                WORK_SUMMARY,
                WORK_HIGHLIGHTS
            ), WorkData(
                WORK_COMPANY,
                WORK_POSITION,
                WORK_URL_IMAGE,
                WORK_START_DATE,
                WORK_END_DATE,
                WORK_SUMMARY,
                WORK_HIGHLIGHTS
            ),
            WorkData(
                WORK_COMPANY,
                WORK_POSITION,
                WORK_URL_IMAGE,
                WORK_START_DATE,
                WORK_END_DATE,
                WORK_SUMMARY,
                WORK_HIGHLIGHTS
            ),
            WorkData(
                WORK_COMPANY,
                WORK_POSITION,
                WORK_URL_IMAGE,
                WORK_START_DATE,
                WORK_END_DATE,
                WORK_SUMMARY,
                WORK_HIGHLIGHTS
            )
        )
    }

    private fun createProfilesDataList(): List<ProfileData> {
        return listOf(
            ProfileData(
                PROFILE_NETWORK,
                PROFILE_USER_NAME,
                PROFILE_URL
            )
        )
    }

    private fun createBasicsData(): BasicsData {
        return BasicsData(
            BASICS_NAME,
            BASICS_LABEL,
            BASICS_PICTURE,
            BASICS_EMAIL,
            BASICS_SUMMARY,
            createProfilesDataList())
    }

    companion object {
        const val PROFILE_NETWORK = "profileNetwork"
        const val PROFILE_USER_NAME = "profileUserName"
        const val PROFILE_URL = "profileUrl"
        const val WORK_COMPANY = "workCompany"
        const val WORK_POSITION = "workPosition"
        const val WORK_URL_IMAGE = "workUrlImage"
        const val WORK_START_DATE = "workStartDate"
        const val WORK_END_DATE = "workEndDate"
        const val WORK_SUMMARY = "workSummary"
        val WORK_HIGHLIGHTS = listOf("highlight")
        const val SKILL_NAME = "skillName"
        const val SKILL_LEVEL = "skillLevel"
        val SKILL_KEYWORDS = listOf("android", "kotlin")
        const val BASICS_NAME = "name"
        const val BASICS_LABEL = "label"
        const val BASICS_PICTURE = "picture"
        const val BASICS_EMAIL = "email"
        const val BASICS_SUMMARY = "summary"
    }
}