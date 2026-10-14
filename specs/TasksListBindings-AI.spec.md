# Spec: `TasksListBindings.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `setDueDateLabel(textView: TextView, task: Task?)`

**Intent**: Show the due-date label for a task when the label has content. Leave the view unchanged when there is no task label to display.

**Pre-conditions**
- `textView` is a non-null `TextView`.

**Post-conditions**
- Success cases
  - For a completed task, set the view text to `"CLEAR"` and make the view visible, even when the task has no due date; with `Task(isCompleted = true, dueDate = null)`, text becomes `"CLEAR"` and visibility becomes `View.VISIBLE`. `[Integration]`
    - Tip: Given a visible `TextView` and `Task(isCompleted = true, dueDate = null)` / When `setDueDateLabel` runs / Then its text is `"CLEAR"` and its visibility is `View.VISIBLE`.
  - For an active task due today, set the text to `"D-Day"` and make the view visible; use due date `LocalDate.now()` at invocation and `isCompleted = false`. `[Integration]`
    - Tip: Given a `TextView` with `View.GONE` visibility and an active task whose due date is `LocalDate.now()` at invocation / When `setDueDateLabel` runs / Then its text is `"D-Day"` and its visibility is `View.VISIBLE`.
  - For an active task due three calendar days from invocation, show `"D-3"` and make the view visible; use due date `LocalDate.now().plusDays(3)`.  `[Integration]`
    - Tip: Given an active task with due date `LocalDate.now().plusDays(3)` at invocation / When `setDueDateLabel` runs / Then its text is `"D-3"` and its visibility is `View.VISIBLE`.
- Error cases
  - None; a null task is valid and produces no label. `[Integration]`
    - Tip: Given a `TextView` with text `"Keep"` and `View.VISIBLE` visibility and `task = null` / When `setDueDateLabel` runs / Then its text remains `"Keep"` and visibility remains `View.VISIBLE`.
- Edge cases
  - For an active task with no due date, leave both existing text and visibility unchanged; with `Task(isCompleted = false, dueDate = null)`, the view remains as initialized. `[Integration]`
    - Tip: Given a `TextView` with text `"Keep"` and `View.GONE` visibility and `Task(isCompleted = false, dueDate = null)` / When `setDueDateLabel` runs / Then its text remains `"Keep"` and visibility remains `View.GONE`.
  - At the one-day future boundary, show `"D-1"` and make the view visible; use due date `LocalDate.now().plusDays(1)` at invocation. `[Integration]`
    - Tip: Given a `TextView` with `View.GONE` visibility and an active task due `LocalDate.now().plusDays(1)` at invocation / When `setDueDateLabel` runs / Then its text is `"D-1"` and visibility is `View.VISIBLE`.
