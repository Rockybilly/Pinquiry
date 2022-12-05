import {
  Bar,
  BarChart,
  CartesianGrid,
  Cell,
  Legend,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
  LineChart,
  Line,
} from "recharts";
import { Button, Form, Radio, Row, Select, Typography } from "antd";
import React, { useEffect, useState } from "react";
import {
  getMonIncidentCountRange,
  getMonResponseTimeRange,
} from "../../Services/backendComm";

export default function ResponseTimeGraph({ monId }) {
  const [form] = Form.useForm();
  const [dataRange, setDataRange] = useState("Last Hour");
  const [dataRangeDesc, setDataRangeDesc] = useState("Every Result");
  const [graphData, setGraphData] = useState([]);
  const [contentLoading, setContentLoading] = useState(true);

  useEffect(() => {
    query();
  }, []);

  const onChange = (e) => {
    console.log("radio checked", e.target.value);
    setDataRange(e.target.value);

    if (e.target.value === "Last Hour") {
      setDataRangeDesc("Every Result");
    } else if (e.target.value === "Last Day") {
      setDataRangeDesc("Result Average By Minute");
    } else if (e.target.value === "Last Week") {
      setDataRangeDesc("Result Average By 5 Minutes");
    } else {
      console.log("Unexpected radio button event");
    }
  };

  function query() {
    setContentLoading(true);
    let now = Date.now();
    let begin = now;
    let end = now;
    let interval = 0;

    if (dataRange === "Last Week") {
      begin -= 7 * 24 * 60 * 60 * 1000;
      interval = 5 * 60 * 1000;
    } else if (dataRange === "Last Day") {
      begin -= 24 * 60 * 60 * 1000;
      interval = 60 * 1000;
    } else if (dataRange === "Last Hour") {
      begin -= 60 * 60 * 1000;
      interval = 0;
    } else {
      console.log("Unexpected query button event");
    }

    getMonResponseTimeRange({ id: monId, begin, end, interval })
      .then(function (value) {
        setGraphData(value);
      })
      .catch(function (error) {
        console.log(error);
      })
      .then(function () {
        setContentLoading(false);
      });
  }

  return (
    <>
      <Row
        style={{
          alignItems: "center",
          justifyContent: "center",
          textAlign: "center",
          height: "15vh",
          fontSize: "3rem",
          textShadow: "0 0 10px rgba(0,0,0,0.4)",
          marginTop: "10vh",
        }}
      >
        Response Time Graph
      </Row>
      <Row
        style={{
          alignItems: "center",
          justifyContent: "center",
          textAlign: "center",
          height: "15vh",
        }}
      >
        {" "}
        <Form
          form={form}
          initialValues={{
            dataRange: "Last Hour",
          }}
          layout="inline"
          style={{
            alignItems: "center",
            justifyContent: "center",
            textAlign: "center",
          }}
          onFinish={query}
        >
          <Form.Item
            label="Data Range"
            name="dataRange"
            rules={[{ required: true, message: "Please input data range!" }]}
          >
            <Radio.Group onChange={onChange}>
              <Radio.Button value="Last Week">Last Week</Radio.Button>
              <Radio.Button value="Last Day">Last Day</Radio.Button>
              <Radio.Button defaultChecked={true} value="Last Hour">
                Last Hour
              </Radio.Button>
            </Radio.Group>
          </Form.Item>

          <Form.Item>
            <Button type="primary" htmlType="submit">
              Query
            </Button>
          </Form.Item>
        </Form>
        <Typography>
          <pre>{dataRangeDesc}</pre>
        </Typography>
      </Row>

      <Row
        style={{
          alignItems: "center",
          justifyContent: "center",
          textAlign: "center",
          width: "100%",
          height: "40vh",
          paddingLeft: "5vw",
          paddingRight: "5vw",
        }}
      >
        <ResponsiveContainer>
          <LineChart data={graphData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis dataKey="responseTime" />
            <Tooltip />
            <Legend />
            <Line unit=" ms" dataKey="responseTime" name="Response Time" />
          </LineChart>
        </ResponsiveContainer>
      </Row>
    </>
  );
}
