import {
  Button,
  Form,
  Input,
  InputNumber,
  message,
  Radio,
  Select,
  Space,
  Spin,
} from "antd";
import React, { useEffect, useState } from "react";
import PingForm from "./PingForm";
import HttpForm from "./HttpForm";
import ContentForm from "./ContentForm";
import ReactCountryFlag from "react-country-flag";
import { MinusCircleOutlined } from "@ant-design/icons";
import { addMonitor, getServices } from "../../Services/backendComm";
const { Option } = Select;

export function AddForm() {
  const [form] = Form.useForm();
  const [monitorServices, setMonitorServices] = useState([]);
  const [contentLoading, setContentLoading] = useState(true);
  const [addLoading, setAddLoading] = useState(false);

  useEffect(() => {
    async function doWork() {
      setContentLoading(true);
      setMonitorServices(await getServices());
      setContentLoading(false);
    }
    doWork();
  }, []);

  const onFinish = (values) => {
    console.log(values);
    setAddLoading(true);
    addMonitor(values)
      .then(function (response) {})
      .catch(function (error) {})
      .then(function () {
        setAddLoading(false);
      });
  };

  return (
    <Spin spinning={addLoading}>
      <Form
        form={form}
        style={{ width: "40%", margin: "auto" }}
        initialValues={{
          type: "Ping",
        }}
        onFinish={onFinish}
      >
        <Form.Item
          label="Monitoring Location"
          name="monitor_location"
          required={true}
          rules={[
            {
              required: true,
              message: "Monitor location is required!",
            },
          ]}
        >
          {contentLoading ? (
            <Spin spinning={contentLoading} />
          ) : (
            <Select placeholder="Select location">
              {monitorServices.map((d, index) => (
                <Option key={index} value={d.countryCode}>
                  <ReactCountryFlag
                    style={{
                      width: "1.3vw",
                      height: "1.3vw",
                    }}
                    countryCode={d.countryCode}
                    svg
                  />
                  <span style={{ marginLeft: "0.6vw" }}>{d.name}</span>
                </Option>
              ))}
            </Select>
          )}
        </Form.Item>

        <Form.Item
          label="Monitor Name"
          name="name"
          rules={[
            {
              required: true,
              message: "Missing monitor name!",
            },
          ]}
        >
          <Input placeholder="origin_http_monitor_uk" />
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
          tooltip="Request will be considered failed after this timeout value is exceeded. In seconds."
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
          tooltip="The requests will be sent with this interval. In seconds."
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
    </Spin>
  );
}
