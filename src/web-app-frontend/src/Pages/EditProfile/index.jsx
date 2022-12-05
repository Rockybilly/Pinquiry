import { Button, Form, Input, message, Spin } from "antd";
import { AddForm } from "../AddMonitor/AddForm";
import React, { useState } from "react";
import {
  deleteUser,
  deleteUserAdmin,
  getUserList,
  setEmail,
  updatePassword,
} from "../../Services/backendComm";
import { useNavigate } from "react-router-dom";

export default function EditProfile() {
  const [deletePending, setDeletePending] = useState(false);
  const [updatePending, setUpdatePending] = useState(false);
  const [emailPending, setEmailPending] = useState(false);

  let navigate = useNavigate();

  const onFinish = (values) => {
    console.log(values);
    setDeletePending(true);

    updatePassword({
      current_password: values.oldPassword,
      new_password: values.newPassword,
    })
      .then(function () {})
      .catch(function (error) {})
      .then(function () {
        setDeletePending(false);
      });
  };

  const onDeleteFinish = ({ password }) => {
    setDeletePending(true);

    deleteUser({ password: password })
      .then(function () {
        navigate("/");
      })
      .catch(function (error) {})
      .then(function () {
        setDeletePending(false);
      });
  };

  const onAddEmailFinish = ({ email }) => {
    setEmailPending(true);

    setEmail({ email })
      .then(function () {})
      .catch(function (error) {})
      .then(function () {
        setEmailPending(false);
      });
  };
  return (
    <div style={{ textAlign: "center" }}>
      <Spin spinning={updatePending}>
        <span
          style={{
            fontSize: "3vh",
            marginBottom: "5vh",
            display: "inline-block",
          }}
        >
          Change Password
        </span>
        <Form
          style={{ width: "30%", margin: "auto" }}
          onFinish={onFinish}
          autoComplete="off"
        >
          <Form.Item
            label="Old Password"
            name="oldPassword"
            rules={[
              { required: true, message: "Please input your old password!" },
            ]}
          >
            <Input.Password />
          </Form.Item>

          <Form.Item
            label="New Password"
            name="newPassword"
            rules={[
              { required: true, message: "Please input your new password!" },
            ]}
          >
            <Input.Password />
          </Form.Item>

          <Form.Item
            label="New Password Again"
            name="newPasswordAgain"
            dependencies={["newPassword"]}
            rules={[
              {
                required: true,
                message: "Please confirm your password!",
              },
              ({ getFieldValue }) => ({
                validator(_, value) {
                  if (!value || getFieldValue("newPassword") === value) {
                    return Promise.resolve();
                  }
                  return Promise.reject(
                    new Error(
                      "The two passwords that you entered do not match!"
                    )
                  );
                },
              }),
            ]}
          >
            <Input.Password />
          </Form.Item>

          <Form.Item>
            <Button type="primary" htmlType="submit">
              Submit
            </Button>
          </Form.Item>
        </Form>
      </Spin>

      <Spin spinning={emailPending}>
        <span
          style={{
            fontSize: "3vh",
            marginBottom: "5vh",
            marginTop: "5vh",
            display: "inline-block",
          }}
        >
          Add/Change Email To Profile
        </span>
        <Form
          style={{ width: "30%", margin: "auto", marginBottom: "5vh" }}
          onFinish={onAddEmailFinish}
          autoComplete="off"
        >
          <Form.Item
            label="Email"
            name="email"
            rules={[{ required: true, message: "Please input your Email" }]}
          >
            <Input />
          </Form.Item>

          <Form.Item>
            <Button type="primary" htmlType="submit">
              Submit
            </Button>
          </Form.Item>
        </Form>
      </Spin>
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
              marginBottom: "10vh",
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
            <Form.Item
              label="Password"
              name="password"
              rules={[
                {
                  required: true,
                  message: "Please confirm your password!",
                },
              ]}
            >
              <Input.Password />
            </Form.Item>
            <Button
              style={{
                color: "#1F3D20",
                fontWeight: "bold",
                backgroundColor: "#FF6060",
                width: "10vw",
                marginTop: "8vh",
              }}
              type="primary"
              htmlType="submit"
            >
              Delete Profile
            </Button>
          </Form>
        </div>
      </Spin>
    </div>
  );
}
