import { Button, Form, Input, message } from 'antd';
import React, { useContext } from 'react';
import Context from '@/layouts/index';
import { KeepAlive } from 'umi';

const Product = (props: any) => {

  // console.log(props);
  // const keep = useContext(Context);
  // console.log(keep);

  return (
    <div>
      <h1>产品</h1>
      <Form
      >
        <Form.Item label="编码">
          <Input placeholder="input placeholder" />
        </Form.Item>
        <Form.Item label="名称">
          <Input placeholder="input placeholder" />
        </Form.Item>
        <Button onClick={() => { message.info("demo"); }}>返回</Button>
      </Form>
    </div>
  )
}

export default () => {
  return <KeepAlive>
    <Product></Product>
  </KeepAlive>
}
