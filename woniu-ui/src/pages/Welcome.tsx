import React from 'react';
import {PageContainer} from '@ant-design/pro-layout';
import {Alert, Card, Col, Divider, Row, Typography} from 'antd';
import styles from './Welcome.less';

const CodePreview: React.FC<{}> = ({children}) => (
  <pre className={styles.pre}>
    <code>
      <Typography.Text copyable>{children}</Typography.Text>
    </code>
  </pre>
);

export default (): React.ReactNode => (
  <PageContainer>

    <Card bordered={false}>
      <Row gutter={16}>
        <Col span={12}>
          <Card title="蜗牛365后台管理系统" bordered={false}>
            蜗牛365是作者在根据多年软件项目研发实施经历，结合所见所闻开源的后台管理系统，适用于各种业务系统的后台管理，支持PC浏览器、移动端访问。功能正在逐步完善中...
          </Card>
        </Col>
        <Col span={4}>
          <Card title="后端" bordered={false}>
            spring boot<br/>
            sring cloud<br/>
            mybatis
          </Card>
        </Col>
        <Col span={4}>
          <Card title="前端" bordered={false}>
            react ant design
          </Card>
        </Col>
        <Col span={4}>
          <Card title="第三方软件" bordered={false}>
            数据库 mysql/oracle<br/>
            缓存 redis<br/>
            消息队列 kafaka
          </Card>
        </Col>
      </Row>
    </Card>

    <Divider></Divider>
    <Card>
      <Alert
        message="更快更强的重型组件，已经发布。"
        type="success"
        showIcon
        banner
        style={{
          margin: -12,
          marginBottom: 24,
        }}
      />
      <Typography.Text strong>
        高级表格{' '}
        <a
          href="https://procomponents.ant.design/components/table"
          rel="noopener noreferrer"
          target="__blank"
        >
          欢迎使用
        </a>
      </Typography.Text>
      <CodePreview>yarn add @ant-design/pro-table</CodePreview>
      <Typography.Text
        strong
        style={{
          marginBottom: 12,
        }}
      >
        高级布局{' '}
        <a
          href="https://procomponents.ant.design/components/layout"
          rel="noopener noreferrer"
          target="__blank"
        >
          欢迎使用
        </a>
      </Typography.Text>
      <CodePreview>yarn add @ant-design/pro-layout</CodePreview>
    </Card>
  </PageContainer>
);
