import { Button, Form, Input, InputNumber, Row, Select, Space } from "antd";
import React from "react";
import { MinusCircleOutlined, PlusOutlined } from "@ant-design/icons";
const { Option } = Select;

function InnerHeaderForm({ form, nameParent }) {
  return (
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
      <Form.List name={[nameParent, "request_headers"]}>
        {(fields, { add, remove }) => (
          <>
            {fields.map(({ key, name }) => (
              <Row>
                {" "}
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
              </Row>
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
  );
}

export default function ContentForm({ form }) {
  const onProtocolChange = (value, name) => {
    console.log(value, name);
    switch (value) {
      case "HTTP":
        form.setFieldValue(["contents", name, "port"], 80);
        return;

      case "HTTPS":
        form.setFieldValue(["contents", name, "port"], 443);
        return;
    }
  };

  return (
    <div
      style={{
        borderStyle: "double",
        padding: "5%",
        marginBottom: "5%",
        textAlign: "center",
        position: "relative",
        right: "22.5vw",
        minWidth: "73vw",
      }}
    >
      <Row style={{ justifyContent: "center", alignItems: "center" }}>
        <span style={{ position: "relative", bottom: "15%" }}>
          Content Connection Details
        </span>
      </Row>

      <Form.List name="contents">
        {(fields, { add, remove }) => (
          <>
            {fields.map(({ key, name }) => (
              <Space key={key}>
                <Form.Item
                  label="Protocol"
                  name={[name, "protocol"]}
                  required={true}
                  rules={[
                    {
                      required: true,
                      message: "Protocol is required!",
                    },
                  ]}
                >
                  <Select
                    placeholder="Select protocol"
                    onChange={(value) => {
                      onProtocolChange(value, name);
                    }}
                  >
                    <Option value="HTTP">HTTP</Option>
                    <Option value="HTTPS">HTTPS</Option>
                  </Select>
                </Form.Item>
                <Form.Item
                  label="Server IP/Domain"
                  name={[name, "http_server"]}
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
                  name={[name, "port"]}
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
                  label="URI"
                  name={[name, "uri"]}
                  rules={[
                    {
                      required: true,
                      message: "Use at least '/'",
                    },
                  ]}
                >
                  <Input placeholder="/index.html" />
                </Form.Item>
                <Form.Item>
                  <MinusCircleOutlined onClick={() => remove(name)} />
                </Form.Item>
                <InnerHeaderForm form={form} nameParent={name} />
              </Space>
            ))}
            <Form.Item>
              <Button
                type="dashed"
                onClick={() => add()}
                block
                icon={<PlusOutlined />}
              >
                Add content
              </Button>
            </Form.Item>
          </>
        )}
      </Form.List>
    </div>
  );
}
