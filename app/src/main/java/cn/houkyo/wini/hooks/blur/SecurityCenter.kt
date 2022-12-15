package cn.houkyo.wini.hooks.blur

import android.view.View
import cn.houkyo.wini.models.ConfigModel
import cn.houkyo.wini.utils.ColorUtils
import cn.houkyo.wini.utils.HookUtils
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge

class SecurityCenter(private val classLoader: ClassLoader, config: ConfigModel) {
    val blurRadius = config.securityCenter.dockBackground.blurRadius
    val backgroundColor =
        ColorUtils.hexToColor(config.securityCenter.dockBackground.backgroundColor)

    fun addBlurEffectToDock() {
        val TurboLayoutClass = HookUtils.getClass(
            "com.miui.gamebooster.windowmanager.newbox.TurboLayout",
            classLoader
        ) ?: return
        var NewboxClass:Class<*>? = null
        TurboLayoutClass.methods.forEach {
            if(it.name == "getDockLayout"){
                NewboxClass = it.returnType
            }
        }
        if(NewboxClass == null){
            HookUtils.log("Dock Layout is NOT found!")
            return
        }

        XposedBridge.hookAllConstructors(NewboxClass, object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam) {
                val view = param.thisObject as View
                view.addOnAttachStateChangeListener(
                    object :
                        View.OnAttachStateChangeListener {
                        override fun onViewAttachedToWindow(view: View) {
                            // 已有背景 避免重复添加
                            if (view.background != null) {
                                if (HookUtils.isBlurDrawable(view.background)) {
                                    return;
                                }
                            }
                            view.background =
                                HookUtils.createBlurDrawable(
                                    view,
                                    blurRadius,
                                    40,
                                    backgroundColor
                                )
                        }

                        override fun onViewDetachedFromWindow(view: View) {
                            view.background = null
                        }
                    })
            }
        })
    }
}