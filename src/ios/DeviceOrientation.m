#import "DeviceOrientation.h"
#import <Cordova/CDV.h>

@implementation DeviceOrientation
@synthesize callbackId;

- (void)getOrientation:(CDVInvokedUrlCommand*)command {
	CDVPluginResult* pluginResult = nil;

    [[UIDevice currentDevice] beginGeneratingDeviceOrientationNotifications];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(deviceOrientationDidChange) name:UIDeviceOrientationDidChangeNotification object:nil];

    UIDeviceOrientation orientation = [[UIDevice currentDevice] orientation];
    NSMutableDictionary* returnInfo = [NSMutableDictionary dictionaryWithCapacity:1];
    [returnInfo setObject:orientation forKey:@"orientation"];
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:returnInfo];
    [self.commandDelegate sendPluginResult:result callbackId:callbackId];
}

@end
