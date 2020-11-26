import { Breadcrumb, Button, Card, Col, Divider, Layout, List, Row, Space, Tabs, Tag } from 'antd'
import React, { useEffect, useState } from 'react'
import { AppstoreOutlined, CaretRightOutlined } from '@ant-design/icons'
import menu from './menu'
import { withRouter } from 'umi'
import './index.less'

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
    <Layout style={{ padding: 8 }}>
      <Tabs style={{ marginBottom: 16, marginLeft: 16 }}>
        {
          rootNode && rootNode.children ?
            rootNode.children.map((item: any) => {
              // console.log(item);
              return <Tabs.TabPane tab={item.title} key={item.title}></Tabs.TabPane>
            })
            : <></>
        }
      </Tabs>
      {
        rootNode && rootNode.children ?
          rootNode.children.map((item: any) => {
            // console.log(item);
            return <AppItem {...props} node={item} key={item.title}></AppItem>
          })
          : <></>
      }
    </Layout >
  )
}

const AppItem = (props: any) => {
  return (
    <>
      <Row gutter={16}>
        <Col />
        <Col style={{ display: 'flex', alignItems: 'center', paddingRight: 0 }}><div style={{ backgroundColor: '#1890ff', width: 2, height: '80%' }}></div></Col>
        <Col style={{ color: '#1890ff' }} >{props.node.title}</Col>
        <Col flex="auto" style={{ display: 'flex', alignItems: 'center' }}><div style={{ flexGrow: 1, borderTop: '1px dotted #1890ff' }}></div></Col>
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
                style={{ padding: 4, display: "flex", justifyContent: 'flex-start' }}
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
