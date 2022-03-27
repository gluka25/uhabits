package org.isoron.uhabits.core.models.sqlite

import org.isoron.uhabits.core.models.Habit
import org.isoron.uhabits.core.models.HabitList
import org.isoron.uhabits.core.models.HabitMatcher
import org.isoron.uhabits.core.models.ModelFactory
import org.isoron.uhabits.core.models.memory.MemoryHabitList
import org.isoron.uhabits.core.utils.DateUtils
import java.util.*
import javax.inject.Inject

class InMemoryHabitList @Inject constructor(private val modelFactory: InMemoryModelFactory) : HabitList() {
    val list = LinkedList<Habit>()


    @get:Synchronized
    override var primaryOrder = Order.BY_POSITION
        set(value) {
            field = value
            comparator = getComposedComparatorByOrder(primaryOrder, secondaryOrder)
            resort()
        }

    @get:Synchronized
    override var secondaryOrder = Order.BY_NAME_ASC
        set(value) {
            field = value
            comparator = getComposedComparatorByOrder(primaryOrder, secondaryOrder)
            resort()
        }

    private var comparator: Comparator<Habit>? =
        getComposedComparatorByOrder(primaryOrder, secondaryOrder)
    private var parent: InMemoryHabitList? = null

//    constructor() : super()
//    constructor(
//        matcher: HabitMatcher,
//        comparator: Comparator<Habit>?,
//        parent: InMemoryHabitList
//    ) : super(matcher) {
//        this.parent = parent
//        this.comparator = comparator
//        primaryOrder = parent.primaryOrder
//        secondaryOrder = parent.secondaryOrder
//        parent.observable.addListener { loadFromParent() }
//        loadFromParent()
//    }

    @Synchronized
    @Throws(IllegalArgumentException::class)
    override fun add(habit: Habit) {
        list.addLast(habit)
        resort()
    }

    @Synchronized
    override fun getById(id: Long): Habit? {
        for (h in list) if (h.id == id) return h
        return null
    }

    @Synchronized
    override fun getByUUID(uuid: String?): Habit? {
        for (h in list) if (h.uuid == uuid) return h
        return null
    }

    @Synchronized
    override fun getByPosition(position: Int): Habit {
        return list[position]
    }

    @Synchronized
    override fun getFiltered(matcher: HabitMatcher?): HabitList {
        return InMemoryHabitList(modelFactory)
    }

    private fun getComposedComparatorByOrder(
        firstOrder: Order,
        secondOrder: Order?
    ): Comparator<Habit> {
        return Comparator { h1: Habit, h2: Habit ->
            val firstResult = getComparatorByOrder(firstOrder).compare(h1, h2)
            if (firstResult != 0 || secondOrder == null) {
                return@Comparator firstResult
            }
            getComparatorByOrder(secondOrder).compare(h1, h2)
        }
    }

    private fun getComparatorByOrder(order: Order): Comparator<Habit> {
        val nameComparatorAsc = Comparator<Habit> { habit1, habit2 ->
            habit1.name.compareTo(habit2.name)
        }
        val nameComparatorDesc =
            Comparator { h1: Habit, h2: Habit -> nameComparatorAsc.compare(h2, h1) }
        val colorComparatorAsc = Comparator<Habit> { (color1), (color2) ->
            color1.compareTo(color2)
        }
        val colorComparatorDesc =
            Comparator { h1: Habit, h2: Habit -> colorComparatorAsc.compare(h2, h1) }
        val scoreComparatorDesc =
            Comparator<Habit> { habit1, habit2 ->
                val today = DateUtils.getTodayWithOffset()
                habit1.scores[today].value.compareTo(habit2.scores[today].value)
            }
        val scoreComparatorAsc =
            Comparator { h1: Habit, h2: Habit -> scoreComparatorDesc.compare(h2, h1) }
        val positionComparator =
            Comparator<Habit> { habit1, habit2 -> habit1.position.compareTo(habit2.position) }
        val statusComparatorDesc = Comparator { h1: Habit, h2: Habit ->
            if (h1.isCompletedToday() != h2.isCompletedToday()) {
                return@Comparator if (h1.isCompletedToday()) -1 else 1
            }
            if (h1.isNumerical != h2.isNumerical) {
                return@Comparator if (h1.isNumerical) -1 else 1
            }
            val today = DateUtils.getTodayWithOffset()
            val v1 = h1.computedEntries.get(today).value
            val v2 = h2.computedEntries.get(today).value
            v2.compareTo(v1)
        }
        val statusComparatorAsc =
            Comparator { h1: Habit, h2: Habit -> statusComparatorDesc.compare(h2, h1) }
        return when {
            order === Order.BY_POSITION -> positionComparator
            order === Order.BY_NAME_ASC -> nameComparatorAsc
            order === Order.BY_NAME_DESC -> nameComparatorDesc
            order === Order.BY_COLOR_ASC -> colorComparatorAsc
            order === Order.BY_COLOR_DESC -> colorComparatorDesc
            order === Order.BY_SCORE_DESC -> scoreComparatorDesc
            order === Order.BY_SCORE_ASC -> scoreComparatorAsc
            order === Order.BY_STATUS_DESC -> statusComparatorDesc
            order === Order.BY_STATUS_ASC -> statusComparatorAsc
            else -> throw IllegalStateException()
        }
    }

    @Synchronized
    override fun indexOf(h: Habit): Int {
        return list.indexOf(h)
    }

    @Synchronized
    override fun iterator(): Iterator<Habit> {
        return ArrayList(list).iterator()
    }

    @Synchronized
    override fun remove(h: Habit) {
        list.remove(h)
    }

    @Synchronized
    override fun reorder(from: Habit, to: Habit) {
        val toPos = indexOf(to)
        list.remove(from)
        list.add(toPos, from)
        var position = 0
        for (h in list) h.position = position++
    }

    @Synchronized
    override fun size(): Int {
        return list.size
    }

    @Synchronized
    override fun update(habits: List<Habit>) {
        resort()
    }

    @Synchronized
    override fun resort() {
        list.sortWith(comparator!!)
    }

    @Synchronized
    private fun loadFromParent() {
        checkNotNull(parent)
        list.clear()
        for (h in parent!!) if (filter.matches(h)) list.add(h)
        resort()
    }

}

