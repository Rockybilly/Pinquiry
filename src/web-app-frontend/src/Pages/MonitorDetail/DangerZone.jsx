import React, { useState } from "react";
import { deleteUser, removeMonitor } from "../../Services/backendComm";
import { Button, Form, Input, Spin } from "antd";
import { useNavigate } from "react-router-dom";

export default function DangerZone({ monId }) {
  const [deletePending, setDeletePending] = useState(false);

  let navigate = useNavigate();

  const onDeleteFinish = () => {
    setDeletePending(true);

    removeMonitor(monId)
      .then(function () {
        navigate("/");
      })
      .catch(function (error) {})
      .then(function () {
        setDeletePending(false);
      });
  };

  return (
    <div style={{ textAlign: "center", paddingTop: "10vh" }}>
      {" "}
      <Spin spinning={deletePending}>
        {" "}
        <div
          style={{
            borderStyle: "double",
            borderColor: "#FF0000",
            padding: "2vh",
          }}
        >
          <span
            style={{
              fontSize: "5vh",
              color: "#FF0000",
              marginBottom: "2vh",
              display: "inline-block",
            }}
          >
            Danger Zone!
          </span>
          <Form
            style={{ width: "30%", margin: "auto" }}
            onFinish={onDeleteFinish}
            autoComplete="off"
            layout="horizontal"
          >
            <Button
              style={{
                color: "#1F3D20",
                fontWeight: "bold",
                backgroundColor: "#FF6060",
                width: "10vw",
                marginTop: "8vh",
                marginBottom: "8vh",
              }}
              type="primary"
              htmlType="submit"
            >
              Delete Monitor
            </Button>
          </Form>
        </div>
      </Spin>
    </div>
  );
}
