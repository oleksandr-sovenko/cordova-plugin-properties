# cordova-plugin-detect-screenshot-ng
Detects screenshots on Cordova for Android and iOS. This is a fork of the official plugin https://github.com/restocks/cordova-plugin-detect-screenshot

## Installation
`cordova plugin add https://github.com/EstPress/cordova-plugin-detect-screenshot-ng`

## Usage
When a screenshot occurs, a 'screenshot' event is triggered. Simply listen for this event in your javascript.

    document.addEventListener("screenshot", function() {
        window.alert("Screenshot");
    }, false);
    
## License 
The MIT License (MIT)

Copyright (c) 2017 EstPress

Copyright (c) 2016 Restocks

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
