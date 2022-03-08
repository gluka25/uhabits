package org.otus.uhabits

import com.agoda.kakao.common.views.KView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import org.isoron.uhabits.R

object ViewHabitScreen : Screen<ViewHabitScreen>()  {
    val deleteButton = KButton{ withText("Delete") }
    val yesDeleteButton = KButton{withText("YES")}
    val menu = KButton{withContentDescription("More options")}
    val notes = KTextView{withId(R.id.habitNotes)}
    val question = KTextView{withId(R.id.questionLabel)}
    val chart = KView{ withId(R.id.frequencyChart)}
    val percent = KTextView{withText("5%")}
}