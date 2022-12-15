package cn.houkyo.wini.utils

import android.graphics.Color

object ColorUtils {
    // color转换不可靠，加一个默认值
    val defaultReturnColor = Color.argb(50, 0, 0, 0)

    fun colorToHex(color: Int): String {
        var originalColor = Color.valueOf(defaultReturnColor)
        try {
            originalColor = Color.valueOf(color)
        } catch (e: Throwable) {
            // 颜色转换失败
        }
        val alpha = (originalColor.alpha() * 255).toInt()
        val red = (originalColor.red() * 255).toInt()
        val green = (originalColor.green() * 255).toInt()
        val blue = (originalColor.blue() * 255).toInt()
        val alphaHex = if (alpha <= 15) {
            '0' + alpha.toString()
        } else {
            alpha.toString(16)
        }
        val redHex = if (red <= 15) {
            '0' + red.toString()
        } else {
            red.toString(16)
        }
        val greenHex = if (green <= 15) {
            '0' + green.toString()
        } else {
            green.toString(16)
        }
        val blueHex = if (blue <= 15) {
            '0' + blue.toString()
        } else {
            blue.toString(16)
        }
        return "#$alphaHex$redHex$greenHex$blueHex".uppercase()
    }

    fun hexToColor(hexString: String): Int {
        try {
            return Color.parseColor(hexString)
        } catch (e: Throwable) {
            return defaultReturnColor
        }
    }
}