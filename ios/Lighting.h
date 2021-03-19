//
//  Lighting.h
//  RNLighting
//
//  Created by jj on 2021/3/5.
//  Copyright © 2021 jerloo. All rights reserved.
//

#ifndef Lighting_h
#define Lighting_h


#import <Foundation/Foundation.h>

@interface Light : NSObject
- (void) turnLightOn;
- (void) turnLightOff;
- (void) toggle;
- (bool) isLightActive;
@end
