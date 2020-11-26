import React, { Component, useRef } from 'react'
import { KeepAlive } from 'umi';

export class Welcome extends Component {

  constructor(props: any) {
    super(props)
    // console.log('constructor');
  }

  componentWillUnmount() {
    // console.log('componentWillUnmount');
  }

  render() {
    return (
      <div>
        <h1>欢迎</h1>
      </div>
    )
  }
}

export default () => {
  return <KeepAlive>
    <Welcome />
  </KeepAlive>
}

