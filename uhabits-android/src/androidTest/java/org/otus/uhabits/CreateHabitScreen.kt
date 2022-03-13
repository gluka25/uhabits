package org.otus.uhabits

import androidx.test.espresso.matcher.ViewMatchers.withId
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import org.isoron.uhabits.R

object CreateHabitScreen : Screen<CreateHabitScreen>()  {
    val nameInput = KEditText{ withId(R.id.nameInput)}
    val questionInput = KEditText{withId(R.id.questionInput)}
    val notesInput = KEditText{withId(R.id.notesInput)}
    val colorButton = KButton{ withId(R.id.colorButton)}
    val reminder = KButton{withId(R.id.reminderTimePicker)}
    val saveButton = KButton{withId(R.id.buttonSave)}
}