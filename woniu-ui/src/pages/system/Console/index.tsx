import { Breadcrumb, Button, Card, Col, Divider, Layout, List, PageHeader, Row, Space, Tabs, Tag } from 'antd'
import React, { useEffect, useState } from 'react'
import { AppstoreOutlined, CaretRightOutlined } from '@ant-design/icons'
import menu from './menu'
import { withRouter } from 'umi'
import './index.less'
import { Content } from 'antd/lib/layout/layout'

const index = (props: any) => {

  // const TAG = "Navigation";
  // console.log(TAG);
  // console.log(props);

  const [rootNode, setRootNode] = useState<any>({});

  useEffect(() => {
    let title = props.location.pathname === "/" ? "首页" :
      props.location.state && props.location.state.title ? props.location.state.title : props.location.pathname;

    menu.routes.map((item) => {
      if (item.title == title) {
        setRootNode(item);
        // console.log(item);
      }
    })
  }, [props])

  return (
    <Layout className="page-workspace">
      <PageHeader title="">
        <Tabs style={{ marginBottom: 16, marginLeft: 8 }}>
          {
            rootNode && rootNode.children ?
              rootNode.children.map((item: any) => {
                // console.log(item);
                return <Tabs.TabPane tab={item.title} key={item.title}></Tabs.TabPane>
              })
              : <></>
          }
        </Tabs>
      </PageHeader>
      <Content className="page-content">
        {
          rootNode && rootNode.children ?
            rootNode.children.map((item: any) => {
              // console.log(item);
              return <AppItem {...props} node={item} key={item.title}></AppItem>
            })
            : <></>
        }
      </Content>
    </Layout >
  )
}

const AppItem = (props: any) => {
  return (
    <>
      <Row gutter={16}>
        <Col />
        <Col style={{ display: 'flex', alignItems: 'center', paddingLeft: 0, paddingRight: 0 }}>
          <div className="app-item-bar"></div>
        </Col>
        <Col className="app-item-title" >{props.node.title}</Col>
        <Col flex="auto" className="app-item-ext-line-flex"><div className="app-item-ext-line"></div></Col>
      </Row>
      <Row gutter={16}>
        {props.node.children.map((item: any) => {
          return <MenuGroup {...props} node={item} key={item.title}></MenuGroup>
        })}
      </Row>
    </>
  )
}

const MenuGroup = (props: any) => {
  return (
    <>
      <Col className="gutter-row" span={6}>
        <Card title={props.node.title} bordered={false} size="small">
          <List
            size="small"
            bordered={false}
            split={false}
          >
            {props.node.children.map((item: any) => {
              return <List.Item
                className="nav-menu"
                key={item.title}
                style={{ padding: 2, display: "flex", justifyContent: 'flex-start' }}
                onClick={() => {
                  props.history.push({ pathname: item.path, state: { title: item.title } });
                }}>
                <img src={require('@/assets/menuTitle.png')} style={{ marginRight: 8 }} />
                {item.title}
              </List.Item>
            })}
          </List>
        </Card>
      </Col>
    </>
  )
}

export default withRouter(index)
