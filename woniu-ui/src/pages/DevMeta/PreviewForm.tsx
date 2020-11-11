import {Modal, Tabs} from "antd";
import React, {useEffect, useState} from "react";
import {genPreview} from "@/pages/DevMeta/service";

const TAG = "Metadata.Priview";
const PriviewForm = (props: any) => {

  const {TabPane} = Tabs;
  const [panes, setPanes] = useState([]);

  useEffect(() => {
    console.log(`${TAG} useEffect`);
    if (!props.visible || !props.id) return;

    (async () => {
      const res = await genPreview(props.id);  //useState中的id会滞后，要使用传过来的ID
      console.log(res);
      if (res.code === "0") {
        setPanes(res.data);
      }
    })();
  }, [props.visible]);

  return <Modal
    title='数据建模'
    visible={props.visible}
    footer={null}
    width='100%'
    style={{top: 20}}
    onCancel={() => {
      props.close();
    }}>
    <Tabs>
      {panes.map(pane => (
        <TabPane tab={pane.title} key={pane.title}>
          <pre>{pane.content}</pre>
        </TabPane>
      ))}
    </Tabs>
  </Modal>;
}

export default PriviewForm;
