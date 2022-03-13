package org.otus.uhabits

import com.agoda.kakao.check.KCheckBox
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import org.hamcrest.Matchers.startsWith
import org.isoron.uhabits.R

object ListHabitsScreen : Screen<ListHabitsScreen>() {
    val addHabitButton = KButton{withId(R.id.actionCreateHabit)}
    val yesOrNO = KButton{withId(R.id.buttonYesNo)}
    val habit = KView{withText("Make tests")}
    val emptyList = KView{withText("You have no active habits")}
}
//    val checksasd = KRecyclerView({withId(R.id.list_item)}, )
//
    //ViewMatchers.hasDescendant(ViewMatchers.withText(habit)),
    //ViewMatchers.withClassName(endsWith("HabitCardView"))


//class KCheckmarkPanelView(){
//    val checkmarks = setOf<KButton>(
//        KButton{withIndex(0) {withMatcher(ViewMatchers.isAssignableFrom(Mater))} }
//    )
//}
