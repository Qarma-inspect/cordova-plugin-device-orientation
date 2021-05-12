import CoreMotion

@objc(DeviceOrientation) class DeviceOrientation : CDVPlugin {
    var motionManager: CMMotionManager!;
    var timer: Timer!
    var accelerometerData: CMAccelerometerData!

    @objc func update() {
        accelerometerData = motionManager.accelerometerData;
        self.commandDelegate!.evalJs("cordova.fireDocumentEvent('orientationupdate', null, true);");
    }

    @objc(startUpdates:) func startUpdates(command: CDVInvokedUrlCommand) {
        motionManager = CMMotionManager();
        motionManager.startAccelerometerUpdates();
        timer = Timer.scheduledTimer(timeInterval: 0.1, target: self, selector:  #selector(update), userInfo: nil, repeats: true);

        let pluginResult = CDVPluginResult(
            status: CDVCommandStatus_OK
        )
        self.commandDelegate!.send(
            pluginResult, 
            callbackId: command.callbackId
        )
    }

    @objc(getOrientation:) func getOrientation(command: CDVInvokedUrlCommand) {
        var pluginResultData = CDVPluginResult(
            status: CDVCommandStatus_ERROR
        )
        
        accelerometerData = motionManager.accelerometerData
        
        
        if accelerometerData != nil {
            let returnInfo = [
                "x": accelerometerData.acceleration.x,
                "y": accelerometerData.acceleration.y,
                "z": accelerometerData.acceleration.z,
            ] as [AnyHashable : Any];

            pluginResultData = CDVPluginResult(
                status: CDVCommandStatus_OK,
                messageAs: returnInfo
            )
        }

        self.commandDelegate!.send(
            pluginResultData,
            callbackId: command.callbackId
        )
    }
}
