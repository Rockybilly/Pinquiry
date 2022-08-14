import React, { useEffect, useState } from "react";
import { deleteUserAdmin, getUserList } from "../../Services/backendComm";
import { Button, Modal, Space, Spin, Table, Tag } from "antd";
import Column from "antd/es/table/Column";

export default function UserOperations() {
  const [contentLoading, setContentLoading] = useState(true);
  const [users, setUsers] = useState([]);

  const [visible, setVisible] = useState(false);

  const [confirmLoading, setConfirmLoading] = useState(false);

  const [modalTarget, setModalTarget] = useState(-1);

  useEffect(() => {
    async function doWork() {
      setContentLoading(true);
      setUsers(await getUserList());
      setContentLoading(false);
    }
    doWork();
  }, []);

  const showModal = (value) => {
    setVisible(true);

    setModalTarget(value);
  };

  const handleOk = () => {
    setConfirmLoading(true);

    deleteUserAdmin({ user_id: modalTarget }).then(() => {
      setVisible(false);
      setConfirmLoading(false);

      async function doWork() {
        setContentLoading(true);
        setUsers(await getUserList());
        setContentLoading(false);
      }
      doWork();
    });
  };

  const handleCancel = () => {
    setVisible(false);
  };

  if (contentLoading) {
    return (
      <Spin>
        <div style={{ width: "80vw", height: "95vh" }}></div>
      </Spin>
    );
  }

  return (
    <>
      <Modal
        title="Deleting user!"
        visible={visible}
        onOk={handleOk}
        confirmLoading={confirmLoading}
        onCancel={handleCancel}
      >
        <p>Are you sure you want to delete?</p>
      </Modal>
      <Table dataSource={users}>
        <Column title="Username" dataIndex="username" key="username" />

        <Column
          title="Monitor Count"
          dataIndex="numberOfMonitors"
          key="numberOfMonitors"
        />
        <Column
          title="Action"
          key="action"
          render={(_, record) => (
            <>
              <Button
                type="primary"
                style={{
                  color: "#1F3D20",
                  fontWeight: "bold",
                  backgroundColor: "#FF6060",
                }}
                onClick={() => showModal(record.user_id)}
              >
                Delete User
              </Button>
            </>
          )}
        />
      </Table>
    </>
  );
}
