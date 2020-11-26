import { Card, Col, Layout, Row } from 'antd';
import React from 'react';
import styles from './index.less';

export default () => {
  return (
    <Layout>
      <Card bordered={false}>
        <Row gutter={16}>
          <Col span={12}>
            <Card title="蜗牛365后台管理系统" bordered={false}>
              蜗牛365是作者在根据多年软件项目研发实施经历，结合所见所闻开源的后台管理系统，适用于各种业务系统的后台管理，支持PC浏览器、移动端访问。功能正在逐步完善中...
          </Card>
          </Col>
          <Col span={4}>
            <Card title="后端" bordered={false}>
              spring boot<br />
            sring cloud<br />
            mybatis
          </Card>
          </Col>
          <Col span={4}>
            <Card title="前端" bordered={false}>
              react ant design<br />
              umi<br />
              react-activation
          </Card>
          </Col>
          <Col span={4}>
            <Card title="第三方软件" bordered={false}>
              数据库 mysql/oracle<br />
            缓存 redis<br />
            消息队列 kafaka
          </Card>
          </Col>
        </Row>
      </Card>
    </Layout>
  );
}
