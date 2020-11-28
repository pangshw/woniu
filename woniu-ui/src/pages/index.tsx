import { Card, Col, Layout, Row } from 'antd';
import React from 'react';
import styles from './index.less';

export default () => {
  return (
    <Layout style={{ background: '#f0f2f5', padding: 8, height: '100%' }}>

      <Row gutter={8}>
        <Col xl={16} lg={24} md={24} sm={24} xs={24}>
          <Card title="蜗牛365后台管理系统" bordered={false}>
            蜗牛365是作者在根据多年软件项目研发实施经历，结合所见所闻开源的后台管理系统，适用于各种业务系统的后台管理，支持PC浏览器、移动端访问。功能正在逐步完善中...
          </Card>
          <Card style={{ marginTop: 8 }}>
            <Card.Grid
            >
              <img src={require('@/assets/wechat.jpg')} />
              <Card.Meta title="微信公众号 蜗牛365后台管理系统" />
            </Card.Grid>
            <Card.Grid>
              <img src={require('@/assets/qqun.png')} />
            </Card.Grid>
          </Card>
        </Col>
        <Col xl={8} lg={24} md={24} sm={24} xs={24}>
          <Card title="后端" bordered={false}>
            spring boot<br />
            sring cloud<br />
            mybatis
          </Card>
          <Card title="前端" bordered={false} style={{ marginTop: 8 }}>
            react ant design<br />
              umi<br />
              react-activation
          </Card>
          <Card title="第三方软件" bordered={false} style={{ marginTop: 8 }}>
            数据库 mysql/oracle<br />
            缓存 redis<br />
            消息队列 kafaka
          </Card>
        </Col>
      </Row>
    </Layout>
  );
}
