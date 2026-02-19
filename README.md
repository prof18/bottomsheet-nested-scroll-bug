# ModalBottomSheet Nested Scroll Jitter / Overscroll Bug

## Summary

`ModalBottomSheet` (Material3) exhibits jitter and overscroll when the sheet content contains a scrollable child (`LazyColumn` inside a `Scaffold`). When scrolling rapidly and the list reaches its boundary, it starts to briefly drag toward dismiss and spring back.

## Video

https://github.com/user-attachments/assets/PLACEHOLDER

## Trigger Conditions

The bug appears under **either** of the following conditions:

1. **Compose BOM `2025.08.01`** + `material3-window-size-class-android:1.4.0`
2. **Compose BOM `2026.02.00`** (no extra dependency needed)

Without `material3-window-size-class-android`, BOM `2025.08.01` alone does **not** reproduce the issue.

## Steps to Reproduce

1. Build and run the app on a physical device or emulator
2. **Rapidly scroll the list upward (fling up)** — the sheet jitters/bounces when the list reaches the top
