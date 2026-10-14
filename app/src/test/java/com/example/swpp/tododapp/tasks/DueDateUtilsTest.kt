package com.example.swpp.tododapp.tasks

import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

class DueDateUtilsTest {
    private val today = LocalDate.of(2026, 10, 14)

    @Test
    fun dueDateLabel_completedTask_returnsClear() {
        // Given
        val dueDate = LocalDate.of(2026, 10, 15)

        // When
        val result = dueDateLabel(dueDate, today, true)

        // Then
        assertEquals("CLEAR", result)
    }

    @Test
    fun dueDateLabel_activeTaskWithoutDueDate_returnsEmptyString() {
        // Given
        val dueDate: LocalDate? = null

        // When
        val result = dueDateLabel(dueDate, today, false)

        // Then
        assertEquals("", result)
    }

    @Test
    fun dueDateLabel_activeTaskDueToday_returnsDDay() {
        // Given
        val dueDate = LocalDate.of(2026, 10, 14)

        // When
        val result = dueDateLabel(dueDate, today, false)

        // Then
        assertEquals("D-Day", result)
    }

    @Test
    fun dueDateLabel_activeTaskDueInThreeDays_returnsDMinusThree() {
        // Given
        val dueDate = LocalDate.of(2026, 10, 17)

        // When
        val result = dueDateLabel(dueDate, today, false)

        // Then
        assertEquals("D-3", result)
    }

    @Test
    fun dueDateLabel_activeTaskOverdueByThreeDays_returnsDPlusThree() {
        // Given
        val dueDate = LocalDate.of(2026, 10, 11)

        // When
        val result = dueDateLabel(dueDate, today, false)

        // Then
        assertEquals("D+3", result)
    }

    @Test
    fun dueDateLabel_activeTaskDueYesterday_returnsDPlusOne() {
        // Given
        val dueDate = LocalDate.of(2026, 10, 13)

        // When
        val result = dueDateLabel(dueDate, today, false)

        // Then
        assertEquals("D+1", result)
    }

    @Test
    fun dueDateLabel_activeTaskDueTomorrow_returnsDMinusOne() {
        // Given
        val dueDate = LocalDate.of(2026, 10, 15)

        // When
        val result = dueDateLabel(dueDate, today, false)

        // Then
        assertEquals("D-1", result)
    }

    @Test
    fun dueDateLabel_nextMonthDueTomorrow_returnsDMinusOne() {
        // Given
        val today = LocalDate.of(2026, 10, 31)
        val dueDate = LocalDate.of(2026, 11, 1)

        // When
        val result = dueDateLabel(dueDate, today, false)

        // Then
        assertEquals("D-1", result)
    }

    @Test
    fun dueDateLabel_nextMonthDueInFourDays_returnsDMinusFour() {
        // Given
        val today = LocalDate.of(2026, 10, 30)
        val dueDate = LocalDate.of(2026, 11, 3)

        // When
        val result = dueDateLabel(dueDate, today, false)

        // Then
        assertEquals("D-4", result)
    }

    @Test
    fun dueDateLabel_nextMonthSameDayNumber_returnsDMinusThirtyOne() {
        // Given
        val dueDate = LocalDate.of(2026, 11, 14)

        // When
        val result = dueDateLabel(dueDate, today, false)

        // Then
        assertEquals("D-31", result)
    }
}
