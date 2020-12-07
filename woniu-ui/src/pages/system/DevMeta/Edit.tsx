import React, { useEffect, useState } from "react";
import { Button, Col, Form, Input, InputNumber, Layout, message, Modal, PageHeader, Row, Select, Space, Table } from "antd";
import { getMetadata, saveMetadata } from "./service";
import { v4 as uuid } from "uuid";
import { KeepAlive, withRouter } from 'umi';
import { PlusOutlined, MinusOutlined, ArrowUpOutlined, ArrowDownOutlined } from '@ant-design/icons';
import { Content } from 'antd/lib/layout/layout';
import { EDIT_FORM_ITEM_LAYOUT, EDIT_FORM_ROW_LAYOUT, FORM_COL_3_LAYOUT, FORM_COL_4_LAYOUT } from '@/utils/constants';
import { tableScrollHeight } from '@/utils/common';

const TAG = "Metadata.EditForm";
const EditForm = (props: any) => {

  // console.log(TAG);
  // console.log(props);

  const [data, setData] = useState({}); //主单
  const [items, setItems] = useState<any[]>([]); //明细行

  const [form] = Form.useForm();
  const { Option } = Select;

  useEffect(() => {
    console.log(`${TAG} useEffect`);
    if (props.location.query["id"]) {
      (async () => {
        const res = await getMetadata(props.location.query["id"]);  //useState中的id会滞后，要使用传过来的ID
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
        }
      })();
    } else {
      form.resetFields();
      setItems([]);
    }
  }, []);

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
      // props.close();
      // props.refresh();
    }
  }

  const onCellChange = (value: any, record: any) => {
    console.log(`${TAG} onCellChange`);
    console.log(value);
    for (var i in value) {
      record[i] = value[i]; //这一句是必须的，不然状态无法更改
      setItems(items.map((item, key) => item._uid == record._uid ? { ...item, [i]: value[i] } : item)); //新行可能无ID，新增加_uid
    }
  };

  const onNewItem = () => {
    let ds = [...items];
    ds.push({ _uid: uuid() });
    setItems(ds);
  };

  const columns = [
    {
      title: '名称',
      dataIndex: 'name',
      render: (text: string, record: any) => <Input value={text}
        onChange={(e) => onCellChange({ name: e.target.value }, record)} />,
    },
    {
      title: '字段列名',
      dataIndex: 'tableField',
      render: (text: string, record: any) => <Input value={text}
        onChange={(e) => onCellChange({ tableField: e.target.value }, record)} />,
    },
    {
      title: '数据类型',
      dataIndex: 'dataType',
      width: 120,
      render: (text: string, record: any) =>
        <Select defaultValue="String" style={{ width: 100 }}>
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
      render: (text: any, record: any) => <InputNumber value={text}
        onChange={(value: number) => onCellChange({ size: value }, record)} />,
    },
    {
      title: '精度',
      dataIndex: 'scale',
      width: 100,
      render: (text: any, record: any) => <InputNumber value={text}
        onChange={(value: number) => onCellChange({ scale: value }, record)} />,
    },
    {
      title: 'java类型',
      dataIndex: 'javaType',
      width: 120,
      render: (text: string, record: any) =>
        <Select defaultValue="String" style={{ width: 100 }}>
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
        onChange={(e) => onCellChange({ javaField: e.target.value }, record)} />,
    },
  ];

  return <Layout className="page-workspace">
    <PageHeader title='数据建模'
      extra={
        <>
          <Button type="primary"
            onClick={() => {
              form.validateFields().then(() => {
                form.submit();
              });
            }}>保存</Button>
        </>
      }>
    </PageHeader>
    <Content className="page-content">
      <Form form={form} onFinish={onSave} {...EDIT_FORM_ITEM_LAYOUT}>
        <Row {...EDIT_FORM_ROW_LAYOUT}>
          <Col {...FORM_COL_4_LAYOUT}>
            <Form.Item label="实体" name="name" rules={[{ required: true, message: "请输入实体名称" }]}>
              <Input />
            </Form.Item>
          </Col>
          <Col {...FORM_COL_4_LAYOUT}>
            <Form.Item label="表名称" name="tableName" rules={[{ required: true, message: "请输入表名称" }]}>
              <Input />
            </Form.Item>
          </Col>
          <Col {...FORM_COL_4_LAYOUT}>
            <Form.Item label="实体类名称" name="className" rules={[{ required: true, message: "请输入实体类名称" }]}>
              <Input />
            </Form.Item>
          </Col>
          <Col {...FORM_COL_4_LAYOUT}>
            <Form.Item label="生成包路径" name="packageName"
              rules={[{ required: true, message: "请输入生成包路径" }]}
              required tooltip="生成在哪个java包下">
              <Input />
            </Form.Item>
          </Col>
        </Row>
      </Form>
      <Row justify="space-between">
        <Col>
          <h1>字段信息</h1>
        </Col>

        <Col>
          <Space size="small" direction="horizontal">
            <Button icon={<PlusOutlined />} size="small" onClick={onNewItem} shape="circle" />
            <Button icon={<MinusOutlined />} size="small" onClick={onNewItem} shape="circle" />
            <Button icon={<ArrowUpOutlined />} size="small" onClick={onNewItem} shape="circle" />
            <Button icon={<ArrowDownOutlined />} size="small" onClick={onNewItem} shape="circle" />
          </Space>
        </Col>
      </Row>
      <Table bordered columns={columns}
        dataSource={items}
        rowKey="_uid"
        pagination={false}
        scroll={{ y: tableScrollHeight() }}
        size="small"></Table>
    </Content>
  </Layout>;
}

export default withRouter((props: any) => {
  return <KeepAlive name="/system/devMeta/edit">
    <EditForm {...props}></EditForm>
  </KeepAlive>
})
