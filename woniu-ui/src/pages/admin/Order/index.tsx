import { Form, Input } from 'antd'
import React, { useEffect, useRef } from 'react'
import { KeepAlive } from 'umi';

const Order = () => {

  const ref = useRef(null);

  useEffect(() => {
    // console.log("order 来了 ");
    return () => {
      // console.log("order 走了 ");
      // console.log(ref.current);
    }
  }, [])

  return (
    <div ref={ref}>
      <h1>订单</h1>
      <Form
      >
        <Form.Item label="Field A">
          <Input placeholder="input placeholder" />
        </Form.Item>
        <Form.Item label="Field B">
          <Input placeholder="input placeholder" />
        </Form.Item>
      </Form>
    </div>
  )
}

export default () => {
  return <KeepAlive>
    <Order></Order>
  </KeepAlive>
}
