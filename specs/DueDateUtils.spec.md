# Spec: `DueDateUtils.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `dueDateLabel(dueDate: LocalDate?, today: LocalDate, isCompleted: Boolean): String`

**Intent**: Return the task's D-day label based on its completion state and calendar-day distance from today.

**Pre-conditions**
- `today` is a non-null `LocalDate`.

**Post-conditions**
- Success cases
  - For a completed task, return `"CLEAR"` regardless of due date; with due date `2026-10-15`, today `2026-10-14`, and `isCompleted = true`, return `"CLEAR"`. `[Unit]`
    - Tip: Given due date `2026-10-15`, today `2026-10-14`, and `isCompleted = true` / When `dueDateLabel` is called / Then the result is `"CLEAR"`.
  - For an active task with no due date, return the empty string; with `dueDate = null`, today `2026-10-14`, and `isCompleted = false`, return `""`. `[Unit]`
    - Tip: Given `dueDate = null`, today `2026-10-14`, and `isCompleted = false` / When `dueDateLabel` is called / Then the result is `""`.
  - For an active task due today, return `"D-Day"`; with due date and today both `2026-10-14`, return `"D-Day"`. `[Unit]`
    - Tip: Given due date and today both `2026-10-14` and `isCompleted = false` / When `dueDateLabel` is called / Then the result is `"D-Day"`.
  - For an active task due in the future, return `"D-N"` where N is the number of calendar days; with due date `2026-10-17` and today `2026-10-14`, return `"D-3"`. `[Unit]`
    - Tip: Given due date `2026-10-17`, today `2026-10-14`, and `isCompleted = false` / When `dueDateLabel` is called / Then the result is `"D-3"`.
  - For an active overdue task, return `"D+N"` where N is the number of calendar days overdue; with due date `2026-10-11` and today `2026-10-14`, return `"D+3"`. `[Unit]`
    - Tip: Given due date `2026-10-11`, today `2026-10-14`, and `isCompleted = false` / When `dueDateLabel` is called / Then the result is `"D+3"`.
- Error cases
  - None;
- Edge cases
  - One day before today, return `"D+1"`; with due date `2026-10-13` and today `2026-10-14`, return `"D+1"`. `[Unit]`
    - Tip: Given due date `2026-10-13`, today `2026-10-14`, and `isCompleted = false` / When `dueDateLabel` is called / Then the result is `"D+1"`.
  - One day after today, return `"D-1"`; with due date `2026-10-15` and today `2026-10-14`, return `"D-1"`. `[Unit]`
    - Tip: Given due date `2026-10-15`, today `2026-10-14`, and `isCompleted = false` / When `dueDateLabel` is called / Then the result is `"D-1"`.
  - Across a month boundary, count calendar days; with due date `2026-11-01` and today `2026-10-31`, return `"D-1"`. `[Unit]`
    - Tip: Given due date `2026-11-01`, today `2026-10-31`, and `isCompleted = false` / When `dueDateLabel` is called / Then the result is `"D-1"`.
  - Across a month boundary with a multi-day difference, count the total calendar days; with due date `2026-11-03` and today `2026-10-30`, return `"D-4"`. `[Unit]`
    - Tip: Given due date `2026-11-03`, today `2026-10-30`, and `isCompleted = false` / When `dueDateLabel` is called / Then the result is `"D-4"`.
  - Across a month boundary with the same day-of-month, count the total calendar days; with due date `2026-11-14` and today `2026-10-14`, return `"D-31"`. `[Unit]`
    - Tip: Given due date `2026-11-14`, today `2026-10-14`, and `isCompleted = false` / When `dueDateLabel` is called / Then the result is `"D-31"`.
