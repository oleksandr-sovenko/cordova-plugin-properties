package com.oleksandrsovenko;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;

import android.app.Activity;
import android.app.ActivityManager;

import android.content.Context;

import android.os.Handler;

import java.util.List;

public class Properties extends CordovaPlugin {
    private static final String TAG = "Properties";

    // private static final String ACTION_HIDE = "hide";
    // private static final String ACTION_SHOW = "show";
    // private static final String ACTION_READY = "_ready";
    // private static final String ACTION_BACKGROUND_COLOR_BY_HEX_STRING = "backgroundColorByHexString";
    // private static final String ACTION_OVERLAYS_WEB_VIEW = "overlaysWebView";
    // private static final String ACTION_STYLE_DEFAULT = "styleDefault";
    // private static final String ACTION_STYLE_LIGHT_CONTENT = "styleLightContent";

    // private static final String STYLE_DEFAULT = "default";
    // private static final String STYLE_LIGHT_CONTENT = "lightcontent";

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
	}

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArry of arguments for the plugin.
     * @param callbackContext   The callback id used when calling back into JavaScript.
     * @return                  True if the action was valid, false otherwise.
     */
    @Override
    public boolean execute(final String action, final CordovaArgs args, final CallbackContext callbackContext) {
		if (action.equals("Get")) {
			try {
				String name = args.getString(0);
    			Process process = Runtime.getRuntime().exec("getprop " + name);
    			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    			String value = reader.readLine();
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, value));
				return true;
			} catch (IOException e) {
    			e.printStackTrace();
			}
		}

		return false
    }
}





