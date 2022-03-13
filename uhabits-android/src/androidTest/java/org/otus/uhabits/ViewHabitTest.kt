package org.otus.uhabits

import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.isoron.uhabits.activities.habits.list.ListHabitsActivity
import org.junit.Rule
import org.junit.Test

class ViewHabitTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(ListHabitsActivity::class.java)

    @Test
    fun viewHabit() {
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
            questionInput{
                isVisible()
                typeText("question?")
            }
            notesInput{
                isVisible()
                typeText("note test")
            }
            saveButton{
                isClickable()
                click()
            }
        }
        ListHabitsScreen{
            habit{
                isVisible()
                click()
            }
        }
        ViewHabitScreen{
            notes{
                hasText("note test")
            }
            question{
                hasText("question?")
            }
            chart{
                isVisible()
            }
        }
    }
}