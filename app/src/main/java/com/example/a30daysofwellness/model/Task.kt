package com.example.a30daysofwellness.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Task(
    @StringRes val name: Int,
    @StringRes val description: Int,
    @DrawableRes val imageRes: Int,
    @StringRes val longDescription: Int
)
