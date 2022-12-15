## WINI
一个Xposed模块，为基于**Android 12**的MIUI13增加一些模糊效果。

### 注意
此开源版本与酷安发布的版本不一致，不包含参数调节界面，开发者需要自己开发调节界面。    

当前模块配置JSON示例：
```json
{
  "miuiHome": {
    "enableShortcutBackgroundBlur": true,
    "shortcutMenuBackgroundAlpha": 255
  },
  "personalAssistant": {
    "background": {
      "backgroundColor": "#1E000000",
      "blurRadius": 80,
      "enable": true
    }
  },
  "securityCenter": {
    "dockBackground": {
      "backgroundColor": "#3CFFFFFF",
      "blurRadius": 60,
      "enable": true
    }
  },
  "systemUI": {
    "notification": {
      "blurBackgroundAlpha": 170,
      "blurRadius": 60,
      "cornerRadius": 48,
      "defaultBackgroundAlpha": 200,
      "enable": true
    },
    "quickSetting": {
      "controlDetailBackgroundAlpha": 200,
      "hideMiPlayEntry": true
    }
  },
  "versionCode": 0
}
```

### License
本项目基于[MIT](https://github.com/ouhoukyo/WINI/blob/master/LICENSE)协议开源