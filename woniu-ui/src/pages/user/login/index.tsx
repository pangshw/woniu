import logo from '@/assets/logo.svg';
import {Button, Col, Form, Input, Row} from 'antd';
import React from 'react';
import {history, Link} from 'umi';
import Footer from '@/components/Footer';

import styles from './index.less';
import {LockOutlined, UserOutlined} from '@ant-design/icons';
import {login} from '@/services/auth';

/**
 * 此方法会跳转到 redirect 参数所在的位置
 */
const goto = () => {
  const {query} = history.location;
  const {redirect} = query as { redirect: string };
  window.location.href = redirect || '/';
};

const Login: React.FC<{}> = () => {

  const [form] = Form.useForm();

  const onLogin = () => {
    form.validateFields().then((values: any) => {
      console.log(values);
      login(values).then((res) => {
        console.log(res);
        goto();
      });
    });
  };

  return (
    <div className={styles.container}>

      <div className={styles.content}>
        <div className={styles.top}>
          <div className={styles.header}>
            <Link to="/">
              <img alt="logo" className={styles.logo} src={logo}/>
              <span className={styles.title}>蜗牛365</span>
            </Link>
          </div>
          <div className={styles.desc}>蜗牛365 是中国最具影响力的后台管理系统</div>
        </div>

        <div className={styles.main}>
          <Form form={form} onFinish={onLogin}>
            <Row gutter={8}>
              <Col span={16} push={4}>
                <Form.Item name="userName" rules={[{required: true, message: "请输入用户名"}]}>
                  <Input placeholder="请输入用户名" prefix={<UserOutlined/>}></Input>
                </Form.Item>
              </Col>
            </Row>
            <Row gutter={8}>
              <Col span={16} push={4}>
                <Form.Item name="password" rules={[{required: true, message: "请输入密码"}]}>
                  <Input type="password" placeholder="请输入密码" prefix={<LockOutlined/>}></Input>
                </Form.Item>
              </Col>
            </Row>
            <Row justify="center">
              <Col></Col>
              <Form.Item>
                <Button type="primary" htmlType="submit" size="middle">登录</Button>
              </Form.Item>
            </Row>
          </Form>
        </div>
      </div>
      <Footer/>
    </div>
  );
};

export default Login;
