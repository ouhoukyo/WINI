package cn.houkyo.wini.models

// 智能助理
data class PersonalAssistantModel(
    var background: BaseBlurBackgroundModel = BaseBlurBackgroundModel(
        true,
        80,
        "#1E000000"
    )
)
