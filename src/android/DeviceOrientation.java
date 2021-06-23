
package com.qarma.cordova;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.webkit.WebView;

import androidx.core.content.FileProvider;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CordovaPlugin;


public class DeviceOrientation extends CordovaPlugin {

    private OrientationEventListener mOrientationEventListener;
    private CordovaWebView cordovaWebView;
    private String mOrientation = "unknown";

    private static final String ORIENTATION_PORTRAIT_NORMAL = "portrait-primary";
    private static final String ORIENTATION_PORTRAIT_INVERTED = "portrait-secondary";
    private static final String ORIENTATION_LANDSCAPE_NORMAL = "landscape-primary";
    private static final String ORIENTATION_LANDSCAPE_INVERTED = "landscape-secondary";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject options = args.optJSONObject(0);

        if (action.equals("startAccelerometerUpdates")) {
            this.startAccelerometerUpdates(callbackContext);
        }
        else if (action.equals("stopAccelerometerUpdates")) {
            this.stopAccelerometerUpdates(callbackContext);
        }
        else {
            return false;
        }

        return true;
    }

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        cordovaWebView = webView;
    }

    private void startAccelerometerUpdates(CallbackContext callbackContext) {
        Activity activity = this.cordova.getActivity();
        Context context = activity.getApplicationContext();
        if (mOrientationEventListener == null) {
            mOrientationEventListener = new OrientationEventListener(this.cordova.getActivity(), SensorManager.SENSOR_DELAY_NORMAL) {

                @Override
                public void onOrientationChanged(int orientation) {

                    // determine our orientation based on sensor response
                    String lastOrientation = mOrientation;

                    if (orientation >= 315 || orientation < 45 && orientation > -1) {
                        if (mOrientation != ORIENTATION_PORTRAIT_NORMAL) {
                            mOrientation = ORIENTATION_PORTRAIT_NORMAL;
                        }
                    }
                    else if (orientation < 315 && orientation >= 225) {
                        if (mOrientation != ORIENTATION_LANDSCAPE_NORMAL) {
                            mOrientation = ORIENTATION_LANDSCAPE_NORMAL;
                        }
                    }
                    else if (orientation < 225 && orientation >= 135) {
                        if (mOrientation != ORIENTATION_PORTRAIT_INVERTED) {
                            mOrientation = ORIENTATION_PORTRAIT_INVERTED;
                        }
                    }
                    else if(orientation < 135 && orientation >= 45) { // orientation <135 && orientation > 45
                        if (mOrientation != ORIENTATION_LANDSCAPE_INVERTED) {
                            mOrientation = ORIENTATION_LANDSCAPE_INVERTED;
                        }
                    } else {
                        mOrientation = lastOrientation;
                    }

                    if (lastOrientation != mOrientation) {
                        cordovaWebView.sendJavascript("cordova.fireDocumentEvent('orientationupdate', {orientation:'" + mOrientation + "'}, true);");
                    }
                }
            };
        }
        if (mOrientationEventListener.canDetectOrientation()) {
            mOrientationEventListener.enable();
        }
        PluginResult result;
        result = new PluginResult(PluginResult.Status.OK);
        callbackContext.sendPluginResult(result);
    }

    private void stopAccelerometerUpdates(CallbackContext callbackContext) {
        PluginResult result;
        result = new PluginResult(PluginResult.Status.OK);
        callbackContext.sendPluginResult(result);
    }
}
