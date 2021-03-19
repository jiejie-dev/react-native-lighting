package com.jerloo.reactnativelighting

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Camera
import android.hardware.camera2.CameraManager
import android.os.Build
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule


class RNLightingModule(val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    override fun getName() = "RNLighting"

    private var manager: CameraManager? = null
    private var mCamera: Camera? = null
    private var active: Boolean = false;

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            manager = reactContext.getSystemService(Context.CAMERA_SERVICE) as CameraManager
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                manager?.registerTorchCallback(object : TorchCallback() {
//                    override fun onTorchModeChanged(cameraId: String, enabled: Boolean) {
//                        handler.post {
//                            active = enabled;
//                        }
//                    }
//                }, null)
//            };
        }
    }

    override fun getConstants(): MutableMap<String, Any> {
        return hashMapOf("count" to 1)
    }

    private fun isFlashSupported(): Boolean {
        val pm: PackageManager = reactContext.packageManager
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    private fun isFlashActive(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return active;
        } else {
            return if (mCamera != null) {
                mCamera?.parameters?.flashMode.equals(Camera.Parameters.FLASH_MODE_TORCH)
            } else false
        }
    }

    @ReactMethod
    fun isLightActive(successCallback: Callback) {
        successCallback.invoke(isFlashActive())
    }

    @ReactMethod
    fun turnLightOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                manager!!.setTorchMode("0", true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            val packageManager: PackageManager = reactContext.packageManager
            val features = packageManager.systemAvailableFeatures
            for (featureInfo in features) {
                if (PackageManager.FEATURE_CAMERA_FLASH == featureInfo.name) { // 判断设备是否支持闪光灯
                    if (null == mCamera) {
                        mCamera = Camera.open()
                    }
                    val parameters = mCamera?.getParameters()
                    parameters?.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH)
                    mCamera?.setParameters(parameters)
                    mCamera?.startPreview()
                }
            }
        }
        active = true;
        sendEvent(reactContext, "onLightTurnedOn")
    }

    @ReactMethod
    fun turnLightOff() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            try {
                manager?.setTorchMode("0", false)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            if (mCamera != null) {
                mCamera!!.stopPreview()
                mCamera!!.release()
                mCamera = null
            }
        }
        active = false;
        sendEvent(reactContext, "onLightTurnedOff")
    }

    @ReactMethod
    fun toggle() {
        if (isFlashActive()) {
            turnLightOff()
        } else {
            turnLightOn()
        }
    }

    private fun sendEvent(reactContext: ReactContext, eventName: String) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                .emit(eventName, "")
    }
}
