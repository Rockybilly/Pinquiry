import React, { useEffect, useState } from "react";
import {
  Button,
  Col,
  Form,
  InputNumber,
  Radio,
  Row,
  Select,
  Table,
} from "antd";

import { Empty, Spin } from "antd";
import { Link, useNavigate } from "react-router-dom";
import ReactCountryFlag from "react-country-flag";
import Column from "antd/es/table/Column";

import { AreaChartCardFull } from "../../Components/AreaChartCardFull";
import { getUserMonitors, removeMonitor } from "../../Services/backendComm";
const { Option } = Select;

export function Monitors() {
  const [contentLoading, setContentLoading] = useState(true);
  const [monitors, setMonitors] = useState([]);
  const [monitorCount, setMonitorCount] = useState(0);
  const [pageSize, setPageSize] = useState(10);

  const [form] = Form.useForm();
  const navigate = useNavigate();

  useEffect(() => {
    async function doWork() {
      setContentLoading(true);
      let result = await getUserMonitors({ page_size: 10, page_no: 0 });
      setMonitors(result.monitors);
      setMonitorCount(result.totalMonitorSize);
      setContentLoading(false);
    }
    doWork();
  }, []);

  if (!contentLoading && monitorCount === 0) {
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

  function onQuery(values) {
    async function doWork() {
      setContentLoading(true);
      let result = await getUserMonitors(values).then();
      setMonitors(result.monitors);
      setMonitorCount(result.totalMonitorSize);
      setContentLoading(false);
    }
    doWork();
  }

  function onPageSizeUpdate(e) {
    setPageSize(e.target.value);
    form.setFieldValue(["page_no"], undefined);
  }

  return (
    <Spin spinning={contentLoading}>
      <Form
        form={form}
        initialValues={{ page_size: 10, page_no: 0 }}
        onFinish={onQuery}
        layout="inline"
      >
        <Form.Item label="Monitors per page" name="page_size">
          <Radio.Group onChange={onPageSizeUpdate}>
            <Radio.Button value={10}>10</Radio.Button>
            <Radio.Button value={50}>50</Radio.Button>
            <Radio.Button value={100}>100</Radio.Button>
          </Radio.Group>
        </Form.Item>

        <Form.Item
          label="Page"
          name="page_no"
          rules={[
            {
              required: true,
              message: "Select page!",
            },
          ]}
        >
          <Select placeholder="Select page">
            {range(0, monitorCount, pageSize).map((v, i) => {
              let left = v;
              let right = v + pageSize;
              return (
                <Option key={i} value={i}>
                  {left + " - " + (right > monitorCount ? monitorCount : right)}
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

      <Table pagination={false} dataSource={monitors}>
        <Column
          title="Monitor Name"
          dataIndex="name"
          key="name"
          render={(_, record) => (
            <span style={{ fontSize: "1rem" }}>{record.name}</span>
          )}
        />

        <Column
          title="Location"
          dataIndex="location"
          key="location"
          render={(_, record) => (
            <ReactCountryFlag
              svg
              style={{ width: "3rem", height: "3rem" }}
              countryCode={record.location}
            />
          )}
        />
        <Column
          title="Monitor Type"
          dataIndex="type"
          key="type"
          render={(_, record) => record.type}
        />
        <Column
          title="Status"
          dataIndex="online"
          key="online"
          render={(_, record) => (
            <span
              style={{
                fontSize: "2rem",
                fontWeight: "600",
                color: record.online ? "green" : "red",
              }}
            >
              {record.online ? "Online" : "Offline"}
            </span>
          )}
        />
        <Column
          title="Avg Response Time Ms (Last 50 Requests)"
          dataIndex="responseAvgMsLast50"
          key="responseAvgMsLast50"
          render={(_, record) => (
            <span style={{ fontSize: "2rem" }}>
              {record.responseTimes.length <= 1
                ? "No Data"
                : (
                    record.responseTimes.reduce(
                      (r, c) => r + c.responseTime,
                      0
                    ) / record.responseTimes.length
                  ).toFixed(1) + " ms"}
            </span>
          )}
        />

        <Column
          title="Response Times (Last 50 requests)"
          dataIndex="responseTimes"
          key="responseTimes"
          render={(_, record) => (
            <AreaChartCardFull
              data={record.responseTimes}
              dataKey="responseTime"
              width={"10vw"}
              height={"10vh"}
            />
          )}
        />

        <Column
          title="Incident Count (Last Hour)"
          dataIndex="incidentCountLastHour"
          key="incidentCountLastHour"
          render={(_, record) => (
            <span style={{ fontSize: "2rem" }}>
              {record.incidentCountLastHour}
            </span>
          )}
        />

        <Column
          title="Action"
          key="action"
          render={(_, record) => (
            <>
              <Button
                style={{
                  fontWeight: "bold",
                }}
                onClick={() => navigate("/monitor-detail/" + record.id)}
              >
                See Details
              </Button>
            </>
          )}
        />
      </Table>
    </Spin>
  );
}

export default Monitors;
