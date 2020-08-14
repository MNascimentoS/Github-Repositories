package br.com.mateus.githubrepositories.ui


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import br.com.mateus.githubrepositories.R
import br.com.mateus.githubrepositories.utils.waitId
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner ::class)
class RepositoryActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(RepositoryActivity::class.java)

    @Test
    fun repositoryActivityTest() {

        onView(isRoot()).perform(waitId(R.id.cardView, 20000))

        val recyclerView = onView(
            allOf(
                withId(R.id.repositoryRCL),
                childAtPosition(
                    withId(R.id.navHostFG),
                    0
                )
            )
        )

        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        pressBack()

        val recyclerView2 = onView(
            allOf(
                withId(R.id.repositoryRCL),
                childAtPosition(
                    withId(R.id.navHostFG),
                    0
                )
            )
        )
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(2, click()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
