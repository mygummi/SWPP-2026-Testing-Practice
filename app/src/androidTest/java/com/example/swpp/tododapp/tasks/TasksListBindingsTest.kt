package com.example.swpp.tododapp.tasks

import android.view.View
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.swpp.tododapp.data.Task
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TasksListBindingsTest {
    @Test
    fun setDueDateLabel_completedTaskWithoutDueDate_showsClear() = onMain {
        // Given
        val textView = TextView(context).apply { visibility = View.VISIBLE }
        val task = Task(isCompleted = true, dueDate = null)

        // When
        setDueDateLabel(textView, task)

        // Then
        assertEquals("CLEAR", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_activeTaskDueToday_showsDDay() = onMain {
        // Given
        val textView = TextView(context).apply { visibility = View.GONE }
        val task = Task(isCompleted = false, dueDate = LocalDate.now())

        // When
        setDueDateLabel(textView, task)

        // Then
        assertEquals("D-Day", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_activeTaskDueInThreeDays_showsDMinusThree() = onMain {
        // Given
        val textView = TextView(context).apply { visibility = View.GONE }
        val task = Task(isCompleted = false, dueDate = LocalDate.now().plusDays(3))

        // When
        setDueDateLabel(textView, task)

        // Then
        assertEquals("D-3", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_activeTaskWithoutDueDate_hidesStaleLabel() = onMain {
        // Given
        val textView = TextView(context).apply {
            text = "CLEAR"
            visibility = View.VISIBLE
        }
        val task = Task(isCompleted = false, dueDate = null)

        // When
        setDueDateLabel(textView, task)

        // Then
        assertEquals(View.GONE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_activeTaskDueTomorrow_showsDMinusOne() = onMain {
        // Given
        val textView = TextView(context).apply { visibility = View.GONE }
        val task = Task(isCompleted = false, dueDate = LocalDate.now().plusDays(1))

        // When
        setDueDateLabel(textView, task)

        // Then
        assertEquals("D-1", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_nullTask_hidesStaleLabel() = onMain {
        // Given
        val textView = TextView(context).apply {
            text = "KEEP"
            visibility = View.VISIBLE
        }

        // When
        setDueDateLabel(textView, null)

        // Then
        assertEquals(View.GONE, textView.visibility)
    }

    private val context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    private fun onMain(test: () -> Unit) {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(test)
    }
}
