
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
import android.content.Intent;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.webkit.WebView;

import androidx.core.content.FileProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CordovaPlugin;


public class DeviceOrientation extends CordovaPlugin {

    private OrientationEventListener mOrientationEventListener;
    private WebView webView;
    private int mOrientation =  -1;

    private static final int ORIENTATION_PORTRAIT_NORMAL =  1;
    private static final int ORIENTATION_PORTRAIT_INVERTED =  2;
    private static final int ORIENTATION_LANDSCAPE_NORMAL =  3;
    private static final int ORIENTATION_LANDSCAPE_INVERTED =  4;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject options = args.optJSONObject(0);

        if (action.equals("startAccelerometerUpdates")) {
            this.startAccelerometerUpdates(callbackContext);
        }
        else if (action.equals("getOrientation")) {
            this.getOrientation(callbackContext);
        }
        else if (action.equals("stopAccelerometerUpdates")) {
            this.stopAccelerometerUpdates(callbackContext);
        }
        else {
            return false;
        }

        return true;
    }

    /**
     * Sets up an intent to capture audio.  Result handled by onActivityResult()
     */
    private void startAccelerometerUpdates(CallbackContext callbackContext) {
        webView = new WebView(this.cordova.getActivity());
        Activity activity = this.cordova.getActivity();
        if (mOrientationEventListener == null) {
            mOrientationEventListener = new OrientationEventListener(this.cordova.getActivity(), SensorManager.SENSOR_DELAY_NORMAL) {

                @Override
                public void onOrientationChanged(int orientation) {

                    // determine our orientation based on sensor response
                    int lastOrientation = mOrientation;

                    if (orientation >= 315 || orientation < 45) {
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
                    else { // orientation <135 && orientation > 45
                        if (mOrientation != ORIENTATION_LANDSCAPE_INVERTED) {
                            mOrientation = ORIENTATION_LANDSCAPE_INVERTED;
                        }
                    }
                    if (lastOrientation != mOrientation) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                webView.evaluateJavascript("cordova.fireDocumentEvent('orientationupdate', null, true);", null);
                            }
                        });
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


    /**
     * Sets up an intent to capture audio.  Result handled by onActivityResult()
     */
    private void getOrientation(CallbackContext callbackContext) {
        PluginResult result;
        result = new PluginResult(PluginResult.Status.OK, mOrientation);
        callbackContext.sendPluginResult(result);
    }

    /**
     * Sets up an intent to capture audio.  Result handled by onActivityResult()
     */
    private void stopAccelerometerUpdates(CallbackContext callbackContext) {
        PluginResult result;
        result = new PluginResult(PluginResult.Status.OK);
        callbackContext.sendPluginResult(result);
    }
}
