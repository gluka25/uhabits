package org.isoron.uhabits.core.models.sqlite

import org.isoron.uhabits.core.database.Database
import org.isoron.uhabits.core.models.*
import javax.inject.Inject

class InMemoryModelFactory
@Inject constructor(
)  : ModelFactory {
    override fun buildOriginalEntries() = EntryList()
    override fun buildComputedEntries() = EntryList()
    override fun buildHabitList() = InMemoryHabitList(this)
    override fun buildScoreList() = ScoreList()
    override fun buildStreakList() = StreakList()

    override fun buildHabitListRepository() = throw NotImplementedError()

    override fun buildRepetitionListRepository() = throw NotImplementedError()
}
