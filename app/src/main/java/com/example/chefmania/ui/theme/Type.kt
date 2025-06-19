package com.example.ui.theme

import androidx.compose.material3.TextField
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.chefmania.R


val agbalumo = FontFamily(
    Font(R.font.agbalumo_regular)
)

val crimsonTextBold = FontFamily(
    Font(R.font.crimsontext_bold)
)

val crimsonText = FontFamily(
    Font(R.font.crimsontext_regular)
)

val crimsonTextItalic = FontFamily(
    Font(R.font.crimsontext_italic)
)

// Default Material 3 typography values
val baseline = Typography()

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = agbalumo,
        fontWeight = FontWeight.Normal,
        fontSize = 84.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = crimsonTextBold,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp
    ),
    labelMedium = TextStyle(
        fontFamily = crimsonTextItalic,
        fontWeight = FontWeight.Thin,
        fontSize = 24.sp
    ),
)

