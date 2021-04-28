#import <Cordova/CDVPlugin.h>

@interface DeviceOrientation : CDVPlugin {
	NSString* callbackId;
}

@property (nonatomic, copy) NSString* callbackId;

- (void)getOrientation:(CDVInvokedUrlCommand*)command;

@end
