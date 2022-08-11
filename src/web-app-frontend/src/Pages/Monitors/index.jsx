import React, { useState } from "react";
import { Button, Col, Form, InputNumber, Radio, Row, Select } from "antd";

import { Empty, Spin } from "antd";
import { Link } from "react-router-dom";
import ReactCountryFlag from "react-country-flag";
import PingForm from "../AddMonitor/PingForm";
import HttpForm from "../AddMonitor/HttpForm";
import ContentForm from "../AddMonitor/ContentForm";
import { BasicTextCard } from "../../Components/BasicTextCard";
const { Option } = Select;

export function Monitors() {
  const [form] = Form.useForm();
  const [monitors, setMonitors] = useState([
    {
      monId: "1",
      monName: "Mon 1",
      monType: "ping",
      responseAvgMs: 10.2,
      interval: 15,
    },
    {
      monId: "2",
      monName: "Mon 2",
      monType: "ping",
      responseAvgMs: 5.88899,
      intervalS: 6,
    },
  ]);
  const [pageSize, setPageSize] = useState(10);

  if (monitors.length === 0) {
    return (
      <>
        <Empty
          imageStyle={{
            height: "30vh",
          }}
          description={<span style={{ fontSize: "2em" }}>No Monitors</span>}
        >
          <Button type="primary">
            <Link to={"/add-monitor"}>Create Now</Link>
          </Button>
        </Empty>
      </>
    );
  }

  function range(start, stop, step) {
    if (typeof stop == "undefined") {
      stop = start;
      start = 0;
    }

    if (typeof step == "undefined") {
      step = 1;
    }

    if ((step > 0 && start >= stop) || (step < 0 && start <= stop)) {
      return [];
    }

    const result = [];
    for (let i = start; step > 0 ? i < stop : i > stop; i += step) {
      result.push(i);
    }

    return result;
  }

  function onFinish(values) {
    console.log(values);
  }

  function onPageSizeUpdate(e) {
    setPageSize(e.target.value);
    form.setFieldValue(["page"], undefined);
  }

  return (
    <>
      <Form
        form={form}
        initialValues={{ pageSize: 10, page: 0 }}
        onFinish={onFinish}
        layout="inline"
      >
        <Form.Item label="Monitors per page" name="pageSize">
          <Radio.Group onChange={onPageSizeUpdate}>
            <Radio.Button value={10}>10</Radio.Button>
            <Radio.Button value={50}>50</Radio.Button>
            <Radio.Button value={100}>100</Radio.Button>
          </Radio.Group>
        </Form.Item>

        <Form.Item
          label="Page"
          name="page"
          rules={[
            {
              required: true,
              message: "Select page!",
            },
          ]}
        >
          <Select placeholder="Select protocol">
            {range(0, monitors.length, pageSize).map((v, i) => {
              let left = v;
              let right = v + pageSize;
              return (
                <Option key={i} value={i}>
                  {left +
                    " - " +
                    (right > monitors.length ? monitors.length : right)}
                </Option>
              );
            })}
          </Select>
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit">
            Query
          </Button>
        </Form.Item>
      </Form>
      <Row style={{ height: "10vh" }}></Row>
      {monitors.map((mon, ind) => {
        return (
          <Row
            style={{
              height: "20vh",
              backgroundColor: "#cfcfbf",
              boxShadow: "0px 0px 20px #000000",
              borderStyle: "solid",
              marginBottom: "3vh",
              alignItems: "center",
              paddingLeft: "2vw",
              paddingRight: "2vw",
              borderRadius: "45px",
            }}
          >
            {mon.monName}
            <Col flex={1} />
            <BasicTextCard
              header={"Average Response Time"}
              text={mon.responseAvgMs}
              footer="Milliseconds"
              backgroundColor="cyan"
              textColor="brown"
              size="15vh"
            />
          </Row>
        );
      })}
    </>
  );
}

export default Monitors;
