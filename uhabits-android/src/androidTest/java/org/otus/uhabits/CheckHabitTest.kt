package org.otus.uhabits

import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.isoron.uhabits.activities.habits.list.ListHabitsActivity
import org.junit.Rule
import org.junit.Test

class CheckHabitTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(ListHabitsActivity::class.java)

    @Test
    fun ckeckHabit() {
        ListHabitsScreen {
            addHabitButton{
                isVisible()
                isDisplayed()
                click()
            }
            yesOrNO{
                isVisible()
                isClickable()
                click()
            }
        }
        CreateHabitScreen{
            nameInput{
                isVisible()
                typeText("Make tests")
            }
            saveButton{
                isClickable()
                click()
            }
        }
//        ListHabitsScreen{
//            habit{
//                isVisible()
//                isDisplayed()
//            }
//            firstCheck{
//                longClick()
//            }
//            habit{
//                click()
//            }
//        }
//        ViewHabitScreen{
//            percent{
//                isVisible()
//                isDisplayed()
//            }
//        }

    }
}