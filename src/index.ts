import { NativeEventEmitter, NativeModules } from 'react-native'

const { RNLighting } = NativeModules

export const lightEventEmitter = new NativeEventEmitter(RNLighting)
export const turnLightOn: () => void = RNLighting.turnLightOn
export const turnLightOff: () => void = RNLighting.turnLightOff
export const toggle: () => void = RNLighting.toggle
export const isLightActive = function () {
  return new Promise<boolean>((resolve, reject) => {
    RNLighting.isLightActive((state: boolean) => {
      return resolve(state)
    })
  })
}

export default RNLighting
