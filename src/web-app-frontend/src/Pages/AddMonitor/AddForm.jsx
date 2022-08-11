import { Button, Form, Input, InputNumber, Radio, Select, Space } from "antd";
import React, { useState } from "react";
import PingForm from "./PingForm";
import HttpForm from "./HttpForm";
import ContentForm from "./ContentForm";
import ReactCountryFlag from "react-country-flag";
import { MinusCircleOutlined } from "@ant-design/icons";
const { Option } = Select;

export function AddForm() {
  const [form] = Form.useForm();

  const onFinish = (values) => {
    console.log(values);
  };

  const monitorServices = [
    {
      serviceId: "abc",
      serviceIp: "127.0.0.1",
      countryName: "United States",
      countryCode: "US",
    },
    {
      serviceId: "abc2",
      serviceIp: "127.0.0.2",
      countryName: "United Kingdom",
      countryCode: "GB",
    },
  ];

  return (
    <Form
      form={form}
      style={{ width: "40%", margin: "auto" }}
      initialValues={{
        type: "Ping",
      }}
      onFinish={onFinish}
    >
      <Form.Item
        label="Monitor Server"
        name="monitor_server"
        required={true}
        rules={[
          {
            required: true,
            message: "Monitor service is required!",
          },
        ]}
      >
        <Select placeholder="Select service">
          {monitorServices.map((d, index) => (
            <Option key={index} value={d.serviceId}>
              <ReactCountryFlag countryCode={d.countryCode} />{" "}
              {d.countryName + "(" + d.serviceIp + ")"}
            </Option>
          ))}
        </Select>
      </Form.Item>
      <Form.Item label="Monitor Type" name="type">
        <Radio.Group value="Ping">
          <Radio.Button value="Ping">Ping</Radio.Button>
          <Radio.Button value="HTTP">HTTP</Radio.Button>
          <Radio.Button value="Content">Content</Radio.Button>
        </Radio.Group>
      </Form.Item>
      <Form.Item
        noStyle
        shouldUpdate={(prevValues, currentValues) =>
          prevValues.type !== currentValues.type
        }
      >
        {({ getFieldValue }) => {
          switch (getFieldValue("type")) {
            case "Ping":
              return <PingForm form={form} />;
            case "HTTP":
              return <HttpForm form={form} />;
            case "Content":
              return <ContentForm form={form} />;
          }
        }}
      </Form.Item>
      <Form.Item
        label="Timeout (s)"
        name="timeout"
        required={true}
        rules={[
          {
            required: true,
            message: "Timeout is required!",
          },
        ]}
      >
        <InputNumber placeholder="2" />
      </Form.Item>
      <Form.Item
        label="Request Interval (s)"
        name="interval"
        required={true}
        rules={[
          {
            required: true,
            message: "Interval is required!",
          },
        ]}
      >
        <InputNumber placeholder="10" />
      </Form.Item>
      <Form.Item>
        <Button type="primary" htmlType="submit">
          Submit
        </Button>
      </Form.Item>
    </Form>
  );
}
