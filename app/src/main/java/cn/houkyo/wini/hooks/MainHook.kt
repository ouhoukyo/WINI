package cn.houkyo.wini.hooks

import cn.houkyo.wini.BuildConfig
import cn.houkyo.wini.hooks.blur.*
import cn.houkyo.wini.models.ConfigModel
import cn.houkyo.wini.utils.HookUtils
import cn.houkyo.wini.utils.Storage
import de.robv.android.xposed.*
import de.robv.android.xposed.callbacks.XC_LoadPackage

class MainHook : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        val otherHooks = OtherHooks(lpparam.classLoader)
        when (lpparam.packageName) {
            // 系统桌面
            "com.miui.home" -> {
                val config = getConfig()
                val miuiHomeHooks = MiuiHome(lpparam.classLoader, config)
                if (config.miuiHome.enableShortcutBackgroundBlur) {
                    miuiHomeHooks.addBlurEffectToShortcutLayer()
                }

                // 以下针对系统桌面模糊的功能部分与 MiuiHome 模块重合，且BUG未修复，故酷安公开版本未上线
                // 部分功能与 MiuiHome 重合，代码没有参考MiuiHome，不需要遵守其开源协议
                otherHooks.deviceLevelHook()
                miuiHomeHooks.addBlurEffectToFolderIcon()
                miuiHomeHooks.addBlurEffectToAlphaIcon()
                miuiHomeHooks.hideBlurIconWhenEnterRecents()

            }
            // 系统界面
            "com.android.systemui" -> {
                val config = getConfig()
                val systemUIHooks = SystemUI(lpparam.classLoader, config)
                if (config.systemUI.notification.enable) {
                    systemUIHooks.addBlurEffectToNotificationView()
                }
                if (config.systemUI.quickSetting.hideMiPlayEntry) {
                    systemUIHooks.hideControlsPlugin()
                }
                if (config.systemUI.quickSetting.controlDetailBackgroundAlpha != 255) {
                    systemUIHooks.setQSControlDetailBackgroundAlpha()
                }
                systemUIHooks.enableBlurForMTK()
                systemUIHooks.addBlurEffectToLockScreen()
            }
            // 个人助理 负一屏
            "com.miui.personalassistant" -> {
                val config = getConfig()
                if (config.personalAssistant.background.enable) {
                    val personalAssistantHooks = PersonalAssistant(lpparam.classLoader, config)
                    personalAssistantHooks.addBlurEffectToPersonalAssistant()
                }
            }
            // 安全中心
            "com.miui.securitycenter" -> {
                val config = getConfig()
                if (config.securityCenter.dockBackground.enable) {
                    val securityCenterHooks = SecurityCenter(lpparam.classLoader, config)
                    securityCenterHooks.addBlurEffectToDock()
                }
            }
            BuildConfig.APPLICATION_ID -> {
                getConfig(true)
                otherHooks.enableModule()
            }
            else -> {
                return
            }
        }

    }

    private fun getConfig(showLog: Boolean = false): ConfigModel {
        val xSharedPreferences =
            XSharedPreferences(BuildConfig.APPLICATION_ID, Storage.DATA_FILENAME)
        xSharedPreferences.makeWorldReadable()
        val configJsonString = xSharedPreferences.getString(Storage.CONFIG_JSON, "")
        if (configJsonString != null && configJsonString != "") {
            if (showLog) {
                HookUtils.log(configJsonString)
            }
            return Storage.getConfig(configJsonString)
        }
        return ConfigModel()
    }
}