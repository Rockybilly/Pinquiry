import React, { useEffect, useState } from "react";
import {
  AutoComplete,
  Button,
  Empty,
  Form,
  Radio,
  Row,
  Select,
  Spin,
  Table,
  Input,
  InputNumber,
  Modal,
  message,
} from "antd";
import ReactCountryFlag from "react-country-flag";
import {
  addService,
  deleteUserAdmin,
  getServices,
  getUserList,
  removeService,
} from "../../Services/backendComm";

const countries = require("country-data").countries;
const { Column } = Table;

const options = [];
const countryNameMap = {};
const countryCodeMap = {};

countries.all.forEach((country) => {
  if (
    country.status &&
    country.status !== "reserved" &&
    country.status !== "deleted"
  ) {
    options.push({
      value: country.name,
      label: (
        <span
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          {country.name + " (" + country.alpha2 + ")"}{" "}
          <span style={{ flex: "1" }} />
          <ReactCountryFlag
            style={{ height: "3vh", width: "3vh" }}
            countryCode={country.alpha2}
            svg
          />
        </span>
      ),
    });

    countryNameMap[country.name] = country.alpha2;
    countryCodeMap[country.alpha2] = country.name;
  }
});

export default function MonitorServices() {
  const [contentLoading, setContentLoading] = useState(true);
  const [services, setServices] = useState([]);
  const [form] = Form.useForm();
  const country = Form.useWatch("country", form);

  const [visible, setVisible] = useState(false);
  const [confirmLoading, setConfirmLoading] = useState(false);
  const [modalTarget, setModalTarget] = useState(-1);

  useEffect(() => {
    async function doWork() {
      setContentLoading(true);
      setServices(await getServices());
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
    removeService({ ip: modalTarget })
      .then(() => {
        setVisible(false);
        setConfirmLoading(false);

        async function doWork() {
          setContentLoading(true);
          setServices(await getServices());
          setContentLoading(false);
        }
        doWork();
      })
      .catch(function (error) {})
      .then(function () {
        setVisible(false);
        setConfirmLoading(false);
      });
  };

  const handleCancel = () => {
    setVisible(false);
  };

  function onFinish(values) {
    setContentLoading(true);
    addService({
      name: values.name,
      ip: values.ip,
      port: values.port,
      countryCode: countryNameMap[values.country],
    })
      .then(() => {
        setVisible(false);
        setConfirmLoading(false);

        async function doWork() {
          setContentLoading(true);
          setServices(await getServices());
          setContentLoading(false);
        }
        doWork();
      })
      .catch(function (error) {})
      .then(function () {
        setContentLoading(false);
      });
  }

  return (
    <Spin spinning={contentLoading}>
      <Modal
        title="Deleting Service Worker!"
        visible={visible}
        onOk={handleOk}
        confirmLoading={confirmLoading}
        onCancel={handleCancel}
      >
        <p>Are you sure you want to delete?</p>
      </Modal>
      <Row
        style={{
          alignItems: "center",
          justifyContent: "center",
          marginBottom: "3vh",
          fontWeight: "500",
          fontSize: "2rem",
        }}
      >
        Service Worker Operations
      </Row>
      <Table pagination={false} dataSource={services}>
        <Column
          title="Service Worker Name"
          dataIndex="name"
          key="name"
          render={(_, record) => <span>{record.name}</span>}
        />
        <Column
          title="Location"
          dataIndex="countryCode"
          key="countryCode"
          render={(_, record) => (
            <>
              <Row style={{ height: "80%" }}>
                {" "}
                <ReactCountryFlag
                  svg
                  style={{ width: "3rem", height: "3rem" }}
                  countryCode={record.countryCode}
                />
              </Row>
              <Row style={{ height: "20%" }}>
                {countryCodeMap[record.countryCode]}
              </Row>
            </>
          )}
        />
        <Column
          title="IP"
          dataIndex="ip"
          key="ip"
          render={(_, record) => <span>{record.ip}</span>}
        />
        <Column
          title="Port"
          dataIndex="port"
          key="port"
          render={(_, record) => <span>{record.port}</span>}
        />
        <Column
          title="Dedicated Monitor Count"
          dataIndex="monitorCount"
          key="monitorCount"
          render={(_, record) => <span>{record.monitorCount}</span>}
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
                onClick={() => showModal(record.ip)}
              >
                Delete Worker
              </Button>
            </>
          )}
        />
      </Table>

      <Row
        style={{
          alignItems: "center",
          justifyContent: "center",
          marginTop: "3vh",
        }}
      >
        <Form form={form} onFinish={onFinish} layout="inline">
          <Form.Item
            label="Service Worker Name"
            name="name"
            rules={[
              {
                required: true,
                message: "Missing Name!",
              },
            ]}
          >
            <Input placeholder="Enter name" />
          </Form.Item>
          <Form.Item
            name="country"
            label="Country"
            rules={[
              {
                required: true,
                message: "Country is required!",
              },
            ]}
          >
            <AutoComplete
              style={{ width: "10vw" }}
              filterOption={(input, option) =>
                option.value.toUpperCase().startsWith(input.toUpperCase())
              }
              options={options}
            ></AutoComplete>
          </Form.Item>
          <Form.Item style={{ width: "3vw" }}>
            <ReactCountryFlag
              style={{ width: "3vw", height: "2vw" }}
              countryCode={countryNameMap[country]}
              svg
            />
          </Form.Item>
          <Form.Item
            name="ip"
            rules={[
              {
                required: true,
                message: "Missing IP!",
              },
            ]}
          >
            <Input placeholder="Enter IP" />
          </Form.Item>
          <Form.Item
            label="Port"
            name="port"
            rules={[
              {
                required: true,
                message: "Port is required!",
              },
            ]}
          >
            <InputNumber placeholder="6363" />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit">
              Add Service
            </Button>
          </Form.Item>
        </Form>
      </Row>
    </Spin>
  );
}
