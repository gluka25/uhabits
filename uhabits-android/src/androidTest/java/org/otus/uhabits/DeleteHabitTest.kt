package org.otus.uhabits;

import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.isoron.uhabits.activities.habits.list.ListHabitsActivity
import org.junit.Rule
import org.junit.Test

class DeleteHabitTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(ListHabitsActivity::class.java)

    @Test
    fun deleteHabit() {
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
        ListHabitsScreen{
            habit{
                isVisible()
                isDisplayed()
                click()
            }
        }
        ViewHabitScreen{
            menu{
                click()
            }
            deleteButton{
                click()
            }
            yesDeleteButton{
                click()
            }
        }
        ListHabitsScreen{
            emptyList{
                isVisible()
                isDisplayed()
            }
        }

    }
}
