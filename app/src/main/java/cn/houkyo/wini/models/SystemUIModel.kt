package cn.houkyo.wini.models

// 系统UI
data class SystemUIModel(
    var notification: NotificationBlurModel = NotificationBlurModel(),
    val quickSetting: QuickSettingModel = QuickSettingModel()
)
