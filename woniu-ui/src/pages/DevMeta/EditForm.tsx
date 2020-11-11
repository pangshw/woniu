import React, {useEffect, useState} from "react";
import {Button, Col, Form, Input, InputNumber, message, Modal, Row, Select, Table} from "antd";
import {getMetadata, saveMetadata} from "@/pages/DevMeta/service";
import {v4 as uuid} from "uuid";

const TAG = "Metadata.EditForm";
const EditForm = (props: any) => {

  console.log(TAG);
  console.log(props);

  const [data, setData] = useState({}); //主单
  const [items, setItems] = useState([]); //明细行
  const [scroll, setScroll] = useState({}); //表格的控制

  const {Option} = Select;

  const calcScroll = () => {
    let h = document.body.clientHeight
      - document.getElementsByClassName("ant-modal-header")[0].clientHeight
      - document.getElementsByClassName("ant-modal-footer")[0].clientHeight
      - document.getElementsByClassName("ant-table-thead")[0].clientHeight
      - 100;
    var elements = document.getElementsByClassName('other');
    Array.prototype.forEach.call(elements, function (element) {
      h -= element.clientHeight;
    });
    setScroll({y: h});
  };

  useEffect(() => {
    console.log(`${TAG} useEffect`);
    if (!props.visible) return;
    if (props.id) {
      (async () => {
        const res = await getMetadata(props.id);  //useState中的id会滞后，要使用传过来的ID
        console.log(res);
        if (res.code === "0") {
          form.setFieldsValue(res.data);
          setData(res.data);
          //添加明细行的_uid,与新增行保持一致
          let ds = [...res.data.columns];
          ds.map((item, key) => {
            item._uid = uuid();
          });
          setItems(ds);
          //计算滚动条
          calcScroll();
        }
      })();
    } else {
      form.resetFields();
      setItems([]);
      calcScroll();
    }
  }, [props.visible]);

  const onSave = async (values: any) => {
    console.log(`${TAG} onSave`);
    console.log(values);
    // console.log(data);

    let model = data;
    for (var i in values) {     //主单字段
      model[i] = values[i];
    }
    items.map((item, index) => {  //明细顺序号
      item.seqNo = index;
    });
    model.columns = items;
    console.log(model);

    const res = await saveMetadata(model);
    console.log(res);
    if (res.code === "0") {
      message.success("保存成功");
      props.close();
      props.refresh();
    }
  }

  const onCellChange = (value: any, record: any) => {
    console.log(`${TAG} onCellChange`);
    console.log(value);
    for (var i in value) {
      record[i] = value[i]; //这一句是必须的，不然状态无法更改
      setItems(items.map((item, key) => item._uid == record._uid ? {...item, [i]: value[i]} : item)); //新行可能无ID，新增加_uid
    }
  };

  const onNewItem = () => {
    let ds = [...items];
    ds.push({_uid: uuid()});
    setItems(ds);
  };

  const [form] = Form.useForm();

  const columns = [
    {
      title: '名称',
      dataIndex: 'name',
      render: (text: string, record: any) => <Input value={text}
                                                    onChange={(e) => onCellChange({name: e.target.value}, record)}/>,
    },
    {
      title: '字段列名',
      dataIndex: 'tableField',
      render: (text: string, record: any) => <Input value={text}
                                                    onChange={(e) => onCellChange({tableField: e.target.value}, record)}/>,
    },
    {
      title: '数据类型',
      dataIndex: 'dataType',
      width: 120,
      render: (text: string, record: any) =>
        <Select defaultValue="String" style={{width: 100}}>
          <Option value="String">文本</Option>
          <Option value="Text">大文本</Option>
          <Option value="Integer">整数</Option>
          <Option value="Long">长整数</Option>
          <Option value="Date">日期</Option>
          <Option value="DateTime">日期时间</Option>
          <Option value="Time">时间</Option>
          <Option value="Decimal">小数</Option>
        </Select>,
    },
    {
      title: '长度',
      dataIndex: 'size',
      width: 100,
      render: (text: string, record: any) => <InputNumber value={text}
                                                          onChange={(value: number) => onCellChange({size: value}, record)}/>,
    },
    {
      title: '精度',
      dataIndex: 'scale',
      width: 100,
      render: (text: string, record: any) => <InputNumber value={text}
                                                          onChange={(value: number) => onCellChange({scale: value}, record)}/>,
    },
    {
      title: 'java类型',
      dataIndex: 'javaType',
      width: 120,
      render: (text: string, record: any) =>
        <Select defaultValue="String" style={{width: 100}}>
          <Option value="String">String</Option>
          <Option value="Integer">Integer</Option>
          <Option value="Long">Long</Option>
          <Option value="Date">Date</Option>
          <Option value="Decimal">BigDecimal</Option>
        </Select>,
    },
    {
      title: 'java属性',
      dataIndex: 'javaField',
      render: (text: string, record: any) => <Input value={text}
                                                    onChange={(e) => onCellChange({javaField: e.target.value}, record)}/>,
    },
  ];

  return <Modal
    title='数据建模'
    visible={props.visible}
    width='100%'
    centered
    maskClosable={false}
    onOk={() => {
      form.validateFields().then(() => {
        form.submit();
      });
    }}
    onCancel={() => {
      props.close();
    }}
  >
    <Form form={form} onFinish={onSave}>
      <Row justify="start" gutter={16} className="other">
        <Col span={8}>
          <Form.Item label="实体" name="name" rules={[{required: true, message: "请输入实体名称"}]}>
            <Input/>
          </Form.Item>
        </Col>
        <Col span={8}>
          <Form.Item label="表名称" name="tableName" rules={[{required: true, message: "请输入表名称"}]}>
            <Input/>
          </Form.Item>
        </Col>
        <Col span={8}>
          <Form.Item label="实体类名称" name="className" rules={[{required: true, message: "请输入实体类名称"}]}>
            <Input/>
          </Form.Item>
        </Col>
        <Col span={8}>
          <Form.Item label="生成包路径" name="packageName"
                     rules={[{required: true, message: "请输入生成包路径"}]}
                     required tooltip="生成在哪个java包下">
            <Input/>
          </Form.Item>
        </Col>
      </Row>
      <Row className="other">
        <Button type="primary" onClick={onNewItem}>插入字段</Button>
      </Row>
      <Table columns={columns}
             dataSource={items}
             rowKey="_uid"
             pagination={false}
             scroll={scroll}
             size="small"></Table>
    </Form>
  </Modal>;
}

export default EditForm;
