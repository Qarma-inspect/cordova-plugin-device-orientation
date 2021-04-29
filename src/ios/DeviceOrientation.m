#import "DeviceOrientation.h"
#import <Cordova/CDV.h>

@implementation DeviceOrientation
@synthesize callbackId;

- (void)getOrientation:(CDVInvokedUrlCommand*)command {
	CDVPluginResult* pluginResult = nil;

    [[UIDevice currentDevice] beginGeneratingDeviceOrientationNotifications];
//    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(deviceOrientationDidChange) name:UIDeviceOrientationDidChangeNotification object:nil];

    UIDeviceOrientation orientation = [[UIDevice currentDevice] orientation];
    NSString* orientationStr;
    switch (orientation) {
        case 1:
            orientationStr = @"Portrait";
            break;
        case 2:
            orientationStr = @"Upside Down";
            break;
        case 3:
            orientationStr = @"Landscape Right";
            break;
        case 4:
            orientationStr = @"Landscape Left";
            break;
        case 5:
            orientationStr = @"Camera Facing Down";
            break;
        case 6:
            orientationStr = @"Camera Facing Up";
            break;
        default:
            orientationStr = @"Unknown";
            break;
    }
    
    NSMutableDictionary* returnInfo = [NSMutableDictionary dictionaryWithCapacity:4];
    [returnInfo setObject:orientationStr forKey:@"orientation"];

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:returnInfo];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

@end
