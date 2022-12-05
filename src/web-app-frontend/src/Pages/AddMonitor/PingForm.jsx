import { Form, Input } from "antd";
import React from "react";

export default function PingForm({ form }) {
  return (
    <Form.Item
      label="Server IP"
      name="ping_ip"
      required={true}
      rules={[
        {
          required: true,
          message: "Server IP is required!",
        },
      ]}
    >
      <Input placeholder="127.0.0.1" />
    </Form.Item>
  );
}
