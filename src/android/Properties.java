package com.oleksandrsovenko;

import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginResult;
import org.json.JSONException;
import org.json.JSONObject;

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
        if (action.equals("GetAll")) {
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Process process = Runtime.getRuntime().exec("getprop");
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        StringBuilder properties = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            properties.append(line).append("\n");
                        }
                        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, properties.toString()));
                        LOG.e(TAG, "getprop all properties retrieved");
                    } catch (IOException e) {
                        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "Error retrieving properties"));
                        LOG.e(TAG, "Error executing getprop", e);
                    }
                }
            });

            return true;
        } else if (action.equals("Get")) {
            try {
                final String name = args.getString(0);
                LOG.e(TAG, "getprop " + name);

                cordova.getThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Process process = Runtime.getRuntime().exec("getprop " + name);
                            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String value = reader.readLine();
                            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, value));
                            LOG.e(TAG, "getprop " + name + ", value = " + value);
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
        } else if (action.equals("Set")) {
            try {
                final String name = args.getString(0);
                final String value = args.getString(1);
                LOG.e(TAG, "setprop " + name + " " + value);

                cordova.getThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Process process = Runtime.getRuntime().exec("setprop " + name + " " + value);
                            process.waitFor();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String result = reader.readLine();
                            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, result));
                            LOG.e(TAG, "setprop " + name + " = " + value);
                        } catch (IOException | InterruptedException e) {
                            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "Error setting property"));
                            LOG.e(TAG, "Error executing setprop", e);
                        }
                    }
                });
                return true;
            } catch (JSONException e) {
                LOG.e(TAG, "Invalid arguments, expected property name and value");
                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "Invalid arguments"));
            }
        }

        return false;
    }
}

// console.log(await Properties.Get('ro.miui.ui.version'))

