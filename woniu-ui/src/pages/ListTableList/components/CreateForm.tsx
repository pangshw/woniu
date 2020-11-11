import React, { useEffect } from 'react';
import { Modal } from 'antd';



interface CreateFormProps {
  modalVisible: boolean;
  onCancel: () => void;
}

const TAG = 'TableList.Create';

const CreateForm: React.FC<CreateFormProps> = (props) => {
  const { modalVisible, onCancel } = props;

  useEffect(() => {
    console.log(`${TAG} useEffect`);
  }, []);

  return (
    <Modal
      destroyOnClose
      title="新建规则"
      width={420}
      visible={modalVisible}
      onCancel={() => onCancel()}
      footer={null}
    >
      {props.children}
    </Modal>
  );
};

export default CreateForm;
