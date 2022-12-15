package cn.houkyo.wini.models

// 通知
data class NotificationBlurModel(
    var enable: Boolean = true,
    var cornerRadius: Int = 48,
    var blurRadius: Int = 60,
    var blurBackgroundAlpha: Int = 170,
    var defaultBackgroundAlpha: Int = 200
)
