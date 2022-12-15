package cn.houkyo.wini.models

// 总配置
data class ConfigModel(
    var versionCode: Int = 0,
    var systemUI: SystemUIModel = SystemUIModel(),
    var personalAssistant: PersonalAssistantModel = PersonalAssistantModel(),
    var securityCenter: SecurityCenterModel = SecurityCenterModel(),
    var miuiHome: MiuiHomeModel = MiuiHomeModel()
)
