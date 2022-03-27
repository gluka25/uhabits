package org.otus.di

import android.content.Context
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.isoron.uhabits.*
import org.isoron.uhabits.activities.habits.list.ListHabitsActivity
import org.isoron.uhabits.core.AppScope
import org.isoron.uhabits.core.models.*
import org.isoron.uhabits.core.preferences.Preferences
import org.isoron.uhabits.core.tasks.TaskRunner
import org.isoron.uhabits.inject.ActivityContextModule
import org.isoron.uhabits.inject.AppContextModule
import org.isoron.uhabits.inject.HabitsTestModule
import org.isoron.uhabits.utils.DatabaseUtils
import org.junit.Rule
import org.junit.Test
import org.otus.uhabits.CreateHabitScreen
import org.otus.uhabits.ListHabitsScreen
import org.otus.uhabits.ViewHabitScreen
import java.util.concurrent.CountDownLatch


class DeleteHabitTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(ListHabitsActivity::class.java)

//    @Rule
//    var mActivityRule: ActivityTestRule<MainActivity> =
//        DaggerActivityTestRule(MainActivity::class.java) { application, activity ->
//            mTestAppComponent = DaggerMainActivityEspressoTest_TestAppComponent.create()
//            (application as App).setTestComponent(mTestAppComponent)
//        }

//    @Component(modules = HabitsTestModule)
//    internal interface TestAppComponent : HabitsApplication {
//
//    }

    protected lateinit var habitList: HabitList
    protected lateinit var taskRunner: TaskRunner
    protected lateinit var fixtures: HabitFixtures
    protected lateinit var latch: CountDownLatch
    protected lateinit var appComponent: HabitsApplicationTestComponent
    protected lateinit var modelFactory: ModelFactory
    protected lateinit var component: HabitsActivityTestComponent
    private lateinit var device: UiDevice
    lateinit var prefs: Preferences

    @Test
    fun deleteHabit() {
        val targetContext: Context =
            InstrumentationRegistry.getInstrumentation().targetContext
        val context = targetContext.applicationContext
        val dbFile = DatabaseUtils.getDatabaseFile(context)
        appComponent = DaggerHabitsApplicationTestComponent
            .builder()
            .appContextModule(AppContextModule(context))
            //.habitsModule(HabitsModule(dbFile))
            .habitsTestModule(HabitsTestModule())
            .build()
        HabitsApplication.component = appComponent
//        prefs = appComponent.preferences
//        habitList = appComponent.habitList
//        taskRunner = appComponent.taskRunner
//        modelFactory = appComponent.modelFactory
//        prefs.clear()
//        fixtures = HabitFixtures(modelFactory, habitList)
//        fixtures.purgeHabits(appComponent.habitList)
//        fixtures.createEmptyHabit()
//        component = DaggerHabitsActivityTestComponent
//            .builder()
//            .activityContextModule(ActivityContextModule(targetContext))
//            .habitsApplicationComponent(appComponent)
//            .build()


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
//        ViewHabitScreen{
//            menu{
//                click()
//            }
//            deleteButton{
//                click()
//            }
//            yesDeleteButton{
//                click()
//            }
//        }
//        ListHabitsScreen{
//            emptyList{
//                isVisible()
//                isDisplayed()
//            }
//        }

    }
}

