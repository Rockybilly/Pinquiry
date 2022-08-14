import { Button, Form, Input, InputNumber, Row, Select, Space } from "antd";
import React from "react";
import { MinusCircleOutlined, PlusOutlined } from "@ant-design/icons";
const { Option } = Select;
export default function HttpForm({ form }) {
  const onProtocolChange = (value) => {
    switch (value) {
      case "HTTP":
        form.setFieldsValue({
          port: 80,
        });
        return;

      case "HTTPS":
        form.setFieldsValue({
          port: 443,
        });
        return;
    }
  };

  return (
    <>
      <Form.Item
        label="Protocol"
        name="protocol"
        required={true}
        rules={[
          {
            required: true,
            message: "Protocol is required!",
          },
        ]}
      >
        <Select
          style={{ width: "30%" }}
          placeholder="Select protocol"
          onChange={onProtocolChange}
        >
          <Option value="HTTP">HTTP</Option>
          <Option value="HTTPS">HTTPS</Option>
        </Select>
      </Form.Item>
      <Form.Item
        style={{ width: "80%" }}
        label="Server IP/Domain"
        name="http_server"
        required={true}
        rules={[
          {
            required: true,
            message: "Server IP/Domain is required!",
          },
        ]}
      >
        <Input placeholder="127.0.0.1 or example.com" />
      </Form.Item>
      <Form.Item
        label="Port"
        name="port"
        required={true}
        rules={[
          {
            required: true,
            message: "Port is required!",
          },
        ]}
      >
        <InputNumber placeholder="80" />
      </Form.Item>
      <Form.Item
        rules={[
          {
            required: true,
            message: "Use at least '/'",
          },
        ]}
        style={{ width: "50%" }}
        label="URI"
        name="uri"
      >
        <Input placeholder="/index.html" />
      </Form.Item>
      <div
        style={{
          borderStyle: "double",
          padding: "5%",
          marginBottom: "5%",
          textAlign: "center",
        }}
      >
        <span style={{ position: "relative", bottom: "15%" }}>
          Request headers
        </span>
        <Form.List name="request_headers">
          {(fields, { add, remove }) => (
            <>
              {fields.map(({ key, name }) => (
                <Space key={key}>
                  <Form.Item
                    name={[name, "header"]}
                    rules={[
                      {
                        required: true,
                        message: "Missing header name!",
                      },
                    ]}
                  >
                    <Input placeholder="Header" />
                  </Form.Item>
                  <Form.Item
                    name={[name, "value"]}
                    rules={[
                      {
                        required: true,
                        message: "Missing value!",
                      },
                    ]}
                  >
                    <Input placeholder="Value" />
                  </Form.Item>
                  <Form.Item>
                    <MinusCircleOutlined onClick={() => remove(name)} />
                  </Form.Item>
                </Space>
              ))}
              <Form.Item>
                <Button
                  type="dashed"
                  onClick={() => add()}
                  block
                  icon={<PlusOutlined />}
                >
                  Add header
                </Button>
              </Form.Item>
            </>
          )}
        </Form.List>
      </div>

      <div
        style={{
          borderStyle: "double",
          padding: "5%",
          marginBottom: "5%",
          textAlign: "center",
        }}
      >
        <span style={{ position: "relative", bottom: "15%" }}>
          Success Codes
        </span>

        <Form.List name="success_codes">
          {(fields, { add, remove }) => (
            <>
              {fields.map(({ code, name }) => (
                <Row>
                  <Space key={code}>
                    <Form.Item
                      name={[name, "code"]}
                      rules={[
                        {
                          required: true,
                          message: "Missing code value!",
                        },
                      ]}
                    >
                      <InputNumber placeholder="Code" />
                    </Form.Item>
                    <Form.Item>
                      <MinusCircleOutlined onClick={() => remove(name)} />
                    </Form.Item>
                  </Space>
                </Row>
              ))}
              <Form.Item>
                <Button
                  type="dashed"
                  onClick={() => add()}
                  block
                  icon={<PlusOutlined />}
                >
                  Add Success Code
                </Button>
              </Form.Item>
            </>
          )}
        </Form.List>
      </div>

      <div
        style={{
          borderStyle: "double",
          padding: "5%",
          marginBottom: "5%",
          textAlign: "center",
        }}
      >
        <span style={{ position: "relative", bottom: "15%" }}>
          Expected Response Headers
        </span>
        <Form.List name="success_headers">
          {(fields, { add, remove }) => (
            <>
              {fields.map(({ key, name }) => (
                <Space key={key}>
                  <Form.Item
                    name={[name, "header"]}
                    rules={[
                      {
                        required: true,
                        message: "Missing header name!",
                      },
                    ]}
                  >
                    <Input placeholder="Header" />
                  </Form.Item>
                  <Form.Item
                    name={[name, "value"]}
                    rules={[
                      {
                        required: true,
                        message: "Missing value!",
                      },
                    ]}
                  >
                    <Input placeholder="Value" />
                  </Form.Item>
                  <Form.Item>
                    <MinusCircleOutlined onClick={() => remove(name)} />
                  </Form.Item>
                </Space>
              ))}
              <Form.Item>
                <Button
                  type="dashed"
                  onClick={() => add()}
                  block
                  icon={<PlusOutlined />}
                >
                  Add header
                </Button>
              </Form.Item>
            </>
          )}
        </Form.List>
      </div>
      <Form.Item
        tooltip="A string to be searched in the body of the request. Optional."
        name="search_string"
        label="Search String"
      >
        <Input placeholder="Enter search string" />
      </Form.Item>
    </>
  );
}
