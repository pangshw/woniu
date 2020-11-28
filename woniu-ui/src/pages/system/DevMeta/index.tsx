import React, { useContext, useEffect, useState } from "react";
import { Button, Card, Col, Divider, Form, Input, Layout, message, Modal, PageHeader, Row, Space, Table } from 'antd';
import {
  DeleteOutlined,
  DownloadOutlined,
  EditOutlined,
  ExportOutlined,
  FundViewOutlined,
  ReloadOutlined,
  SearchOutlined,
  SettingOutlined
} from '@ant-design/icons';
import { deleteMetadata, genDownload, genLocal, listMetadata } from "./service";
import { authRoute, startDownload } from "@/utils/httpUtils";
import { fixContext, KeepAlive, useAliveController, withRouter } from 'umi';
import FormItem from 'antd/lib/form/FormItem';
import { LayoutContext } from 'antd/lib/layout/layout';

const TAG = "Metadata.List";
const Metadata = (props: any) => {

  const [form] = Form.useForm();
  const [data, setData] = useState([]);
  const [totalElements, setTotalElements] = useState(0);
  const [currentId, setCurrentId] = useState(0);//用户选择的当前行ID
  const { drop, dropScope, clear, getCachingNodes } = useAliveController();

  // console.log(TAG);
  // console.log(props);
  useEffect(() => {
    startSearch();
  }, [])

  const startSearch = () => {
    form.submit();
  }

  const onSearch = async (values: any) => {
    // console.log(values);
    const res = await listMetadata(values);
    // console.log(res);
    if (res.code === "0") {
      setData(res.data.content);
      setTotalElements(res.data.totalElements);
    }
  }

  const onDelete = async (id: number) => {
    Modal.confirm({
      title: '删除实体',
      content: '确定删除该实体吗？',
      okText: '确认',
      cancelText: '取消',
      onOk: async () => {
        const res = await deleteMetadata(id);
        // console.log(res);
        if (res.code === "0") {
          message.success("删除成功");
          startSearch();
        }
      },
    });
  }

  const onGenDownload = async (ids: any) => {
    const res = await genDownload(ids);
    if (res instanceof Blob) {
      // console.log('res instanceof Blob');
    } else {
      message.warning('Some error messages...', 5);
    }
    startDownload(res, "数据建模.zip");
  }

  const onGenLocal = async (ids: any) => {
    const res = await genLocal(ids);
    // console.log(res);
    if (res.code === "0") {
      message.success("代码生成成功，请查看本地目录");
    }
  }

  const columns = [
    {
      title: '实体',
      dataIndex: 'name',
      valueType: 'text',
    },
    {
      title: '表名称',
      dataIndex: 'tableName',
      valueType: 'text',
    },
    {
      title: '实体类名称',
      dataIndex: 'className',
      valueType: 'text',
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      valueType: 'text',
      width: 160,
    },
    {
      title: '修改时间',
      dataIndex: 'updateTime',
      valueType: 'text',
      width: 160,
    },
    {
      title: '操作',
      key: 'action',
      render: (_: string, record: any) => (
        <>
          <Button type="link"
            size="small"
            icon={<FundViewOutlined />}
            onClick={() => {
              dropScope('/system/devMeta/preview');
              props.history.push({ pathname: '/system/devMeta/preview', query: { id: record.id }, state: { title: '代码预览' } });
            }}>预览</Button>
          <Button type="link"
            size="small"
            icon={<EditOutlined />}
            onClick={() => {
              dropScope('/system/devMeta/edit').then(() => {
                props.history.push({ pathname: '/system/devMeta/edit', query: { id: record.id }, state: { title: '数据建模' } });
              });
            }}>编辑</Button>
          <Button type="link"
            size="small"
            icon={<DeleteOutlined />}
            onClick={() => {
              onDelete(record.id);
            }}>删除</Button>
          <Button type="link"
            size="small"
            icon={<DownloadOutlined />}
            onClick={() => {
              onGenDownload(Array.of(record.id));
            }}>生成</Button>
          <Button type="link"
            size="small"
            icon={<ExportOutlined />}
            onClick={() => {
              onGenLocal(Array.of(record.id));
            }}>导出本地</Button>
        </>
      ),
    },
  ];

  return <Layout>
    <PageHeader
      title="代码生成"
      backIcon={false}
      extra={
        <>
          <Button icon={<SearchOutlined />} size="small" shape="circle" />
          <Button icon={<ReloadOutlined />} size="small" shape="circle" onClick={() => {
            startSearch();
          }} />
          <Button icon={<SettingOutlined />} size="small" shape="circle" />
          <Button icon={<ExportOutlined />} size="small" shape="circle" />
          <Button type="primary"
            onClick={() => {
              dropScope('/system/devMeta/edit').then(() => {
                props.history.push({ pathname: '/system/devMeta/edit', state: { title: '数据建模' } });
              });
            }}>新增</Button>
        </>
      }
    >
      <Form layout="inline" form={form} onFinish={onSearch}>
        <Form.Item label="实体" name="name">
          <Input />
        </Form.Item>
        <Form.Item label="表名称" name="tableName">
          <Input />
        </Form.Item>
        <Form.Item label="实体类名称" name="className">
          <Input />
        </Form.Item>

        <Form.Item>
          <Button type="primary" icon={<SearchOutlined />} onClick={() => {
            startSearch();
          }}>搜索</Button>
        </Form.Item>
        <Form.Item>
          <Button icon={<ReloadOutlined />} onClick={() => {
            form.resetFields();
            startSearch();
          }}>重置</Button>
        </Form.Item>
      </Form>
    </PageHeader>
    <div className="page-items">
      <Table columns={columns} dataSource={data} rowKey="id" size="middle" bordered />
    </div>
  </Layout>;
};

export default withRouter((props: any) => {
  return <KeepAlive name="/system/devMeta">
    <Metadata {...props}></Metadata>
  </KeepAlive>
})
