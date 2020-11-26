import { Layout, Modal, PageHeader, Tabs } from "antd";
import React, { useEffect, useState } from "react";
import { genPreview } from "./service";

const TAG = "Metadata.Preview";
const Preview = (props: any) => {

  console.log('pppppppp');

  const { TabPane } = Tabs;
  const [panes, setPanes] = useState([]);

  useEffect(() => {
    console.log(`${TAG} useEffect`);
    // if (!props.visible || !props.id) return;
    console.log(props.location.query);
    if (!props.location.query["id"]) return;
    let id = props.location.query["id"];
    (async () => {
      const res = await genPreview(id);  //useState中的id会滞后，要使用传过来的ID
      console.log(res);
      if (res.code === "0") {
        setPanes(res.data);
      }
    })();
  }, []);

  return <PageHeader
    title='代码预览' style={{ overflowY: 'auto' }}
  >
    <Tabs>
      {panes.map(pane => (
        <TabPane tab={pane.title} key={pane.title}>
          <pre>{pane.content}</pre>
        </TabPane>
      ))}
    </Tabs>
  </PageHeader>;
}

export default Preview;
