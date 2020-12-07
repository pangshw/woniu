import React, { createContext, useEffect, useMemo, useRef, useState } from 'react';
import { Layout, Menu, Tabs } from 'antd';
import menu from '../pages/system/Console/menu'
import { Content } from 'antd/lib/layout/layout';
import { useAliveController } from 'umi';

const index = (props: any) => {
  // console.log(props);
  const { Header, Content, Footer } = Layout;
  const NAV_URL = "/system/console";

  const { drop, dropScope, clear, getCachingNodes } = useAliveController()
  const [tabs, setTabs] = useState<any[]>([]);
  const [activeKey, setActiveKey] = useState("");

  useEffect(() => {

  }, [])

  useEffect(() => {
    //自动计算工作区高度
    document.getElementById("content").style.height = (document.body.clientHeight - 80) + "px";

    // console.log(getCachingNodes());
    let thisKey = props.location.pathname;
    let title = props.location.pathname === "/" ? "首页" :
      props.location.state && props.location.state.title ? props.location.state.title : "";
    let findKey = false;
    let ls = [...tabs];
    if (tabs.length == 0) {
      ls.push({ id: "/", title: "首页" });
    }
    ls.map((item) => {
      if (item.id == thisKey) {
        findKey = true;
        item.title = title;
      }
    });
    //没有标题的不显示标签页
    if (!findKey && title) {
      ls.push({ id: thisKey, title: title, query: props.location.query, state: props.location.state });
    }
    setTabs(ls);
    setActiveKey(thisKey);
  }, [props.location])

  const onTabChange = (tabKey: string) => {
    routeTo(tabKey);
  }

  const routeTo = (tabKey: string) => {
    tabs.map((item) => {
      if (item.id === tabKey) {
        props.history.push({ pathname: tabKey, query: item.query, state: item.state });
        setActiveKey(tabKey);
      }
    });
  }

  const onTabRemove = (targetKey: any, action: any) => {
    console.log('onTabRemove ' + targetKey);

    let removeIndex = 0;
    let newActiveKey = activeKey;
    tabs.map((item, index) => {
      if (item.id === targetKey) {
        removeIndex = index;
      }
    });
    //最后一个
    if (removeIndex == tabs.length - 1) {
      newActiveKey = tabs[tabs.length - 2].id;
    } else {
      newActiveKey = tabs[removeIndex + 1].id;
    }
    // 删除多标签页缓存
    // 如果关闭激活中的 KeepAlive Tab，需要先离开当前路由
    // 触发 KeepAlive unactivated 后再进行 drop
    if (props.location.pathname === targetKey) {
      console.log('props.location.pathname === targetKey ' + targetKey);
      const unlisten = props.history.listen(() => {
        unlisten()
        setTimeout(() => {
          dropScope(targetKey);
        }, 60)
      })
    } else {
      dropScope(targetKey)
    }

    setTabs(tabs.filter((item) => item.id != targetKey));
    routeTo(newActiveKey);
  }

  const onMenuClick = (menuItem: any) => {
    // console.log(menuItem);
    menu.routes.map((item) => {
      if (item.title == menuItem.key) {
        props.history.push({ pathname: item.path, state: { title: item.title } });
      }
    })
  }

  return (
    <Layout className="layout">
      <Header style={{ paddingLeft: 32 }}>
        <div className="logo" style={{ float: 'left', color: "white", fontSize: 18 }}>
          <img src={require('@/assets/logo.png')} style={{ width: 32, height: 32 }}></img>
          <span style={{ margin: '0 8px' }}>蜗牛365</span>
        </div>

        <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['1']} onClick={onMenuClick}>
          {
            menu.routes.map((item) => {
              return <Menu.Item key={item.title} > {item.title} </Menu.Item>
            })
          }
        </Menu>
      </Header>
      <Tabs
        hideAdd
        type="editable-card"
        activeKey={activeKey}
        onChange={onTabChange}
        onEdit={onTabRemove}
        style={{ paddingLeft: 16, paddingRight: 8, background: "white" }}
      >
        {
          tabs.map((item) => {
            return <Tabs.TabPane key={item.id} tab={item.title} closable={item.id != "/"} />
          })
        }
      </Tabs>
      <Content id="content">
        {props.children}
      </Content>
    </Layout>
  )
}

export default index
