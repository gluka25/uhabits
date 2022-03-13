package org.isoron.uhabits.core.otus

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.isoron.uhabits.core.database.Repository
import org.isoron.uhabits.core.models.Habit
import org.isoron.uhabits.core.models.HabitMatcher
import org.isoron.uhabits.core.models.ScoreList
import org.isoron.uhabits.core.models.StreakList
import org.isoron.uhabits.core.models.sqlite.SQLModelFactory
import org.isoron.uhabits.core.models.sqlite.SQLiteEntryList
import org.isoron.uhabits.core.models.sqlite.SQLiteHabitList
import org.isoron.uhabits.core.models.sqlite.records.HabitRecord
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GetFilteredTest {
    private val repository = mockk<Repository<HabitRecord>>()
    private val sqlmf = mockk<SQLModelFactory>()
    private val sqlel = mockk<SQLiteEntryList>()

    @Test
    fun getFilteredTest() {
        val habitRecord = HabitRecord()
        habitRecord.id = 66600L
        habitRecord.name = "habitName"
        habitRecord.description = "habitDescription"
        habitRecord.question = "habitQuestion"
        habitRecord.freqNum = 1
        habitRecord.freqDen = 1
        habitRecord.color = 0
        habitRecord.archived = 1
        habitRecord.type = 0
        habitRecord.targetType = 0
        habitRecord.targetValue = 13.0
        habitRecord.unit = "habitUnit"
        habitRecord.position = 13
        habitRecord.uuid = ""

        val hab = Habit(
            scores = ScoreList(),
            streaks = StreakList(),
            originalEntries = sqlel,
            computedEntries = sqlel,
        )

        habitRecord.copyTo(hab)

        val listHabits = mutableListOf(habitRecord)
        every{ repository.findAll("order by position") } returns listHabits
        every{ sqlmf.buildHabitListRepository() } returns repository
        every{ sqlmf.buildHabit() } returns hab
        every { sqlel.habitId = any() } answers {nothing}
        every {repository.executeAsTransaction(any())} answers {nothing}

        val habitList = SQLiteHabitList(sqlmf)
        val habits = habitList.getFiltered(HabitMatcher(isArchivedAllowed = true))

        verify { repository.findAll("order by position")  }

        assertFalse { habits.isEmpty }
        for (habit in habits) {
            assertEquals(habit.name, habitRecord.name )
            assertEquals(habit.id, habitRecord.id )
        }

    }


    @Test
    fun getEmptyFilteredTest() {
        val habitRecord = HabitRecord()
        habitRecord.id = 66600L
        habitRecord.name = "habitName"
        habitRecord.description = "habitDescription"
        habitRecord.question = "habitQuestion"
        habitRecord.freqNum = 1
        habitRecord.freqDen = 1
        habitRecord.color = 0
        habitRecord.archived = 1
        habitRecord.type = 0
        habitRecord.targetType = 0
        habitRecord.targetValue = 13.0
        habitRecord.unit = "habitUnit"
        habitRecord.position = 13
        habitRecord.uuid = ""

        val hab = Habit(
            scores = ScoreList(),
            streaks = StreakList(),
            originalEntries = sqlel,
            computedEntries = sqlel,
        )

        habitRecord.copyTo(hab)

        val listHabits = mutableListOf(habitRecord)
        every{ repository.findAll("order by position") } returns listHabits
        every{ sqlmf.buildHabitListRepository() } returns repository
        every{ sqlmf.buildHabit() } returns hab
        every { sqlel.habitId = any() } answers {nothing}
        every {repository.executeAsTransaction(any())} answers {nothing}

        val habitList = SQLiteHabitList(sqlmf)
        val habits = habitList.getFiltered(HabitMatcher(isArchivedAllowed = false))

        verify { repository.findAll("order by position")  }

        assertTrue { habits.isEmpty }
    }
}