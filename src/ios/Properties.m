// The MIT License (MIT)
// 
// Copyright (c) 2025 Oleksandr Sovenko
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

#import "DetectScreenshot.h"
#import <Cordova/CDV.h>

@implementation screenshot

- (void)pluginInitialize {
	if ([[[UIDevice currentDevice] systemVersion] floatValue] >= 7.0) {
		NSOperationQueue *mainQueue = [NSOperationQueue mainQueue];
			[[NSNotificationCenter defaultCenter] addObserverForName:UIApplicationUserDidTakeScreenshotNotification
				object:nil
				queue:mainQueue
				usingBlock:^(NSNotification *note) {
					if ([self.webView respondsToSelector:@selector(stringByEvaluatingJavaScriptFromString:)]) {
						// UIWebView
						[self.webView performSelectorOnMainThread:@selector(stringByEvaluatingJavaScriptFromString:) withObject:@"cordova.fireDocumentEvent('screenshot');" waitUntilDone:NO];
					} else if ([self.webView respondsToSelector:@selector(evaluateJavaScript:completionHandler:)]) {
						// WKWebView
						[self.webView performSelector:@selector(evaluateJavaScript:completionHandler:) withObject:@"cordova.fireDocumentEvent('screenshot');" withObject:nil];
					} else {
						// cordova lib version is > 4
						[self.commandDelegate evalJs:@"cordova.fireDocumentEvent('screenshot');" ];
					}
				}];
	}
}

@end
