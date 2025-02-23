package com.oleksandrsovenko;

import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginResult;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Properties extends CordovaPlugin {
    private static final String TAG = "Properties";

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    @Override
    public boolean execute(final String action, final CordovaArgs args, final CallbackContext callbackContext) {
        if (action.equals("Get")) {
            try {
                final String name = args.getString(0);

                cordova.getThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Process process = Runtime.getRuntime().exec("getprop " + name);
                            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String value = reader.readLine();
                            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, value));
                        } catch (IOException e) {
                            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "Error reading property"));
                            LOG.e(TAG, "Error executing getprop", e);
                        }
                    }
                });
                return true;
            } catch (JSONException e) {
                LOG.e(TAG, "Invalid string argument, use f.i. 'ro.miui.ui.version.code'");
                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "Invalid argument"));
            }
        }
        return false;
    }
}
