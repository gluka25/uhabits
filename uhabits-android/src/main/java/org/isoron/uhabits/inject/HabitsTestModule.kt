package org.isoron.uhabits.inject

import dagger.Module
import dagger.Provides
import org.isoron.uhabits.core.AppScope
import org.isoron.uhabits.core.commands.CommandRunner
import org.isoron.uhabits.core.database.DatabaseOpener
import org.isoron.uhabits.core.io.Logging
import org.isoron.uhabits.core.models.HabitList
import org.isoron.uhabits.core.models.ModelFactory
import org.isoron.uhabits.core.models.sqlite.InMemoryHabitList
import org.isoron.uhabits.core.models.sqlite.InMemoryModelFactory
import org.isoron.uhabits.core.models.sqlite.SQLModelFactory
import org.isoron.uhabits.core.models.sqlite.SQLiteHabitList
import org.isoron.uhabits.core.preferences.Preferences
import org.isoron.uhabits.core.preferences.WidgetPreferences
import org.isoron.uhabits.core.reminders.ReminderScheduler
import org.isoron.uhabits.core.tasks.TaskRunner
import org.isoron.uhabits.core.ui.NotificationTray
import org.isoron.uhabits.database.AndroidDatabaseOpener
import org.isoron.uhabits.intents.IntentScheduler
import org.isoron.uhabits.io.AndroidLogging
import org.isoron.uhabits.notifications.AndroidNotificationTray
import org.isoron.uhabits.preferences.SharedPreferencesStorage

@Module
class HabitsTestModule {
    @Provides
    @AppScope
    fun getModelFactory(): ModelFactory {
        return InMemoryModelFactory()
    }

    @Provides
    @AppScope
    fun getHabitList(list: InMemoryHabitList): HabitList {
        return list
    }

    @Provides
    @AppScope
    fun getLogging(): Logging {
        return AndroidLogging()
    }

    @Provides
    @AppScope
    fun getWidgetPreferences(
        storage: SharedPreferencesStorage
    ): WidgetPreferences {
        return WidgetPreferences(storage)
    }

    @Provides
    @AppScope
    fun getPreferences(storage: SharedPreferencesStorage): Preferences {
        return Preferences(storage)
    }

    @Provides
    @AppScope
    fun getDatabaseOpener(opener: AndroidDatabaseOpener): DatabaseOpener {
        return opener
    }

    @Provides
    @AppScope
    fun getTray(
        taskRunner: TaskRunner,
        commandRunner: CommandRunner,
        preferences: Preferences,
        screen: AndroidNotificationTray
    ): NotificationTray {
        return NotificationTray(taskRunner, commandRunner, preferences, screen)
    }

    @Provides
    @AppScope
    fun getReminderScheduler(
        sys: IntentScheduler,
        commandRunner: CommandRunner,
        habitList: HabitList,
        widgetPreferences: WidgetPreferences
    ): ReminderScheduler {
        return ReminderScheduler(commandRunner, habitList, sys, widgetPreferences)
    }
}