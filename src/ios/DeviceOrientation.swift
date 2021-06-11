import CoreMotion

@objc(DeviceOrientation) class DeviceOrientation : CDVPlugin {
    var motionManager: CMMotionManager!;
    var timer: Timer!
    var accelerometerData: CMAccelerometerData!
    var lastOrientation: String = "";

    @objc func update() {
        let newOrientation: String = getOrientation();
        self.commandDelegate!.evalJs("cordova.fireDocumentEvent('orientationupdate', {orientation:'" + newOrientation + "'}, true);");
    }

    @objc(startAccelerometerUpdates:) func startAccelerometerUpdates(command: CDVInvokedUrlCommand) {
        motionManager = CMMotionManager();
        motionManager.startAccelerometerUpdates();
        timer = Timer.scheduledTimer(timeInterval: 0.2, target: self, selector:  #selector(update), userInfo: nil, repeats: true);

        let pluginResult = CDVPluginResult(
            status: CDVCommandStatus_OK
        )
        self.commandDelegate!.send(
            pluginResult, 
            callbackId: command.callbackId
        )
    }

    @objc(stopAccelerometerUpdates:) func stopAccelerometerUpdates(command: CDVInvokedUrlCommand) {
        motionManager.stopAccelerometerUpdates();
        let pluginResult = CDVPluginResult(
            status: CDVCommandStatus_OK
        )
        self.commandDelegate!.send(
            pluginResult, 
            callbackId: command.callbackId
        )
    }

    func getOrientation() -> String {
        accelerometerData = motionManager.accelerometerData
        
        if accelerometerData != nil {
            let x = accelerometerData.acceleration.x;
            let y = accelerometerData.acceleration.y;
            if x >= 0.33 {
                lastOrientation = "landscape-secondary";
            } else if x <= -0.33 {
                lastOrientation = "landscape-primary";
            } else if y <= -0.33 {
                lastOrientation = "portrait-primary";
            } else if y >= 0.33 {
                lastOrientation = "portrait-secondary";
            }
            if lastOrientation.isEmpty {
                lastOrientation = "portrait-primary";
            }
            return lastOrientation;
        }
        return "portrait-primary"
    }
}
