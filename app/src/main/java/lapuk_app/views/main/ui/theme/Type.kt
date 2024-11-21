package lapuk_app.views.main.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(

    /*       SUMMARY OF SPACINGS        */
    //======= (all units in .sp) =======//
    /* Body (L) --> 16, double          */
    /* Body (M) --> 14, x1.8            */
    /* Body (S) --> 12, x1.8            */
    //=====    =====     =====     =====//
    /* Title (L) = 60, 28sp LH (bold)   */
    /* Title (M) = 48, 28sp LH (bold)   */
    /* Title (S) = 36, 1.5x (bold)      */
    //=====    =====     =====     =====//
    /* Label (L) --> 24 (bold)          */
    /* Label (M) --> 20 (bold)          */ // [currently unusable; breaks bottom navbar]
    /* Label (S) --> 16 (bold)          */

    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp * 2.0,
        letterSpacing = 0.5.sp
    ),

    bodyMedium = TextStyle( // default labels, body text
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 14.sp * 1.4,
        letterSpacing = 0.3.sp
    ),

    bodySmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 12.sp * 1.8,
        letterSpacing = 0.2.sp
    ),

    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 60.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    titleSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 36.sp * 1.5,
        letterSpacing = 0.sp
    ),

    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),

//    labelMedium = TextStyle(
//        fontFamily = FontFamily.SansSerif,
//        fontWeight = FontWeight.Bold,
//        fontSize = 20.sp,
//        letterSpacing = 0.sp
//    ),

    labelSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        letterSpacing = 0.sp
    )
)