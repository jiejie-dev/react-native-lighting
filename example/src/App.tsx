import React, { useEffect, useState } from 'react'
import { Button, View, Text, StyleSheet } from 'react-native'
import {
  turnLightOn,
  turnLightOff,
  toggle,
  isLightActive,
  lightEventEmitter,
} from 'react-native-lighting'

const App = () => {
  const [active, setActive] = useState(false)

  const showState = async () => {
    const state = await isLightActive()
    setActive(state)
  }

  useEffect(() => {
    showState()
  })

  return (
    <View style={styles.container}>
      <Button title='on' onPress={() => turnLightOn()} />
      <Button title='off' onPress={() => turnLightOff()} />
      <Button title='toggle' onPress={() => toggle()} />
      <Text>{active ? '已打开' : '未打开'}</Text>
    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    display: 'flex',
    flex: 1,
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#FFFFFF',
  },
})

export default App
