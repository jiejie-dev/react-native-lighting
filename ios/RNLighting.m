//
//  RNLighting.m
//  RNLighting
//
//  Copyright © 2021 jerloo. All rights reserved.
//

#import <React/RCTBridgeModule.h>
#import "Lighting.h"
#import <AVFoundation/AVFoundation.h>

@implementation RNLighting

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(isLightActive:(RCTResponseSenderBlock)successCallback)
{
    AVCaptureDevice *device = [AVCaptureDevice defaultDeviceWithMediaType:AVMediaTypeVideo];
    if ([device hasTorch] && [device hasFlash]) {
        successCallback(@[[NSNull null],@true]);
    } else {
        successCallback(@[[NSNull null],@false]);
    }
}

RCT_EXPORT_METHOD(toggle)
{
    AVCaptureDevice *device = [AVCaptureDevice defaultDeviceWithMediaType:AVMediaTypeVideo];
    if ([device hasTorch] && [device hasFlash]) {
        [self turnLightOff];
    }else{
        [self turnLightOn];
    }
}

RCT_EXPORT_METHOD(turnLightOn)
{
    [self doFlashWithLevel:1.0];
}

RCT_EXPORT_METHOD(turnLightOff)
{
    AVCaptureDevice *device = [AVCaptureDevice defaultDeviceWithMediaType:AVMediaTypeVideo];
    if ([device hasTorch] && [device hasFlash]){
        [device lockForConfiguration:nil];
        [device setTorchMode:AVCaptureTorchModeOff];
        [device unlockForConfiguration];
    }
}

- (void)doFlashWithLevel:(float)level
{
    AVCaptureDevice *device = [AVCaptureDevice defaultDeviceWithMediaType:AVMediaTypeVideo];
    if ([device hasTorch] && [device hasFlash]){
        [device lockForConfiguration:nil];
        NSError *error = nil;
        float acceptedLevel = (level < AVCaptureMaxAvailableTorchLevel ? level : AVCaptureMaxAvailableTorchLevel);
        NSLog(@"FLash level: %f", acceptedLevel);
        [device setTorchModeOnWithLevel:acceptedLevel error:&error];
        [device unlockForConfiguration];
    }
}

- (void)doFakeFlashWithLevel:(float)level
{
    float acceptedLevel = (level < AVCaptureMaxAvailableTorchLevel ? level : AVCaptureMaxAvailableTorchLevel);
}

@end
