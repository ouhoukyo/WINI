package cn.houkyo.wini.hooks.blur

import android.view.View
import cn.houkyo.wini.models.ConfigModel
import cn.houkyo.wini.utils.ColorUtils
import cn.houkyo.wini.utils.HookUtils
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers

class PersonalAssistant(private val classLoader: ClassLoader, config: ConfigModel) {
    val blurRadius = config.personalAssistant.background.blurRadius
    val backgroundColor = ColorUtils.hexToColor(config.personalAssistant.background.backgroundColor)

    fun addBlurEffectToPersonalAssistant() {
        val AssistantOverlayWindowClass = HookUtils.getClass(
            "com.miui.personalassistant.core.overlay.AssistantOverlayWindow",
            classLoader
        ) ?: return

        XposedHelpers.findAndHookMethod(
            AssistantOverlayWindowClass,
            "a",
            Float::class.java,
            object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val scrollX = param.args[0] as Float
                    var assistContentView = HookUtils.getValueByField(param.thisObject, "r")
                    if (assistContentView == null
                        || !assistContentView.javaClass.name.contains("AssistContentView")
                    ) {
                        assistContentView = HookUtils.getValueByField(param.thisObject, "s")
                    }
                    if (assistContentView == null || !assistContentView.javaClass.name.contains("AssistContentView")) {
                        return
                    }
                    val mSpringLayout =
                        HookUtils.getValueByField(assistContentView, "mSpringLayout") as View
                    val targetView = mSpringLayout.parent as View
                    val blurRadius = scrollX * blurRadius
                    if (blurRadius in 1f..blurRadius + 1f) {
                        if (HookUtils.isBlurDrawable(targetView.background)) {
                            val blurDrawable = targetView.background
                            XposedHelpers.callMethod(
                                blurDrawable,
                                "setBlurRadius",
                                blurRadius.toInt()
                            )
                            XposedHelpers.callMethod(
                                blurDrawable,
                                "setColor",
                                backgroundColor
                            )
                        } else {
                            targetView.background =
                                HookUtils.createBlurDrawable(
                                    targetView,
                                    blurRadius.toInt(),
                                    0,
                                    backgroundColor
                                )
                        }
                    } else {
                        targetView.background = null
                    }
                }
            })
    }
}