//
//  Lighting.h
//  RNLighting
//
//  Created by jj on 2021/3/5.
//  Copyright © 2021 jerloo. All rights reserved.
//

#define Lighting_h


#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>

@interface RNLighting : NSObject <RCTBridgeModule>
- (void) turnLightOn;
- (void) turnLightOff;
- (void) toggle;
- (void) isLightActive: (RCTResponseSenderBlock)successCallback;
@end
