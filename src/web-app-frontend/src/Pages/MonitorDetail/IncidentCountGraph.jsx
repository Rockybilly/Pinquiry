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
} from "recharts";
import {
  Button,
  Form,
  message,
  Radio,
  Row,
  Select,
  Spin,
  Typography,
} from "antd";
import React, { useEffect, useState } from "react";
import {
  getDashboardStatistics,
  getMonIncidentCountRange,
} from "../../Services/backendComm";

export default function IncidentCountGraph({ monId }) {
  const [form] = Form.useForm();
  const [dataInterval, setDataInterval] = useState("hour");
  const [dataIntervalDesc, setDataIntervalDesc] = useState("3 Days");
  const [graphData, setGraphData] = useState([]);
  const [contentLoading, setContentLoading] = useState(true);

  useEffect(() => {
    query();
  }, []);

  const onChange = (e) => {
    console.log("radio checked", e.target.value);
    setDataInterval(e.target.value);

    if (e.target.value === "week") {
      setDataIntervalDesc("3 Months");
    } else if (e.target.value === "day") {
      setDataIntervalDesc("A Month");
    } else if (e.target.value === "hour") {
      setDataIntervalDesc("3 Days");
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

    if (dataInterval === "week") {
      begin -= 3 * 30 * 24 * 60 * 60 * 1000;
      interval = 7 * 24 * 60 * 60 * 1000;
    } else if (dataInterval === "day") {
      begin -= 30 * 24 * 60 * 60 * 1000;
      interval = 24 * 60 * 60 * 1000;
    } else if (dataInterval === "hour") {
      begin -= 3 * 24 * 60 * 60 * 1000;
      interval = 60 * 60 * 1000;
    } else {
      console.log("Unexpected query button event");
    }

    getMonIncidentCountRange({ id: monId, begin, end, interval })
      .then(function (value) {
        console.log(value);
        let avgIncident = 0;
        value.forEach((d) => {
          avgIncident += d.count / value.length;
        });

        let greenRatio = 0.6;
        let redRatio = 0.4;

        let dataConverted = [];
        value.forEach((d) => {
          let date = new Date(0);

          date.setUTCSeconds(parseInt(d.timestamp) / 1000);

          let diffRatio = (avgIncident - d.count) / avgIncident;
          let red = Math.min(200, Math.max(0, 255 * (redRatio - diffRatio)));
          let green = Math.min(
            255,
            Math.max(0, 255 * (greenRatio + diffRatio))
          );

          let name = "";
          if (dataInterval === "week") {
            const month = date.toLocaleString("default", { month: "short" });
            name = month + " " + date.getDate();
          } else if (dataInterval === "day") {
            const month = date.toLocaleString("default", { month: "short" });
            name = month + " " + date.getDate();
          } else if (dataInterval === "hour") {
            name = date.getHours() + ":" + date.getMinutes();
          }

          dataConverted.push({
            name: name,
            incidentCount: d.count,
            cellProps: { fill: "rgb(" + red + "," + green + ",0)" },
          });

          setGraphData(dataConverted);
        });
      })
      .catch(function (error) {
        console.log(error);
      })
      .then(function () {
        setContentLoading(false);
      });
  }

  return (
    <Spin spinning={contentLoading}>
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
        Incident Count Graph
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
          initialValues={{ dataInterval: "hour" }}
          layout="inline"
          style={{
            alignItems: "center",
            justifyContent: "center",
            textAlign: "center",
          }}
          onFinish={query}
        >
          <Form.Item
            label="Data Interval"
            name="dataInterval"
            rules={[{ required: true, message: "Please input data interval!" }]}
          >
            <Radio.Group onChange={onChange}>
              <Radio.Button value="week">Weekly</Radio.Button>
              <Radio.Button value="day">Daily</Radio.Button>
              <Radio.Button value="hour">Hourly</Radio.Button>
            </Radio.Group>
          </Form.Item>
          <Form.Item>
            {" "}
            <Typography>
              <pre>Data of: {dataIntervalDesc}</pre>
            </Typography>
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit">
              Query
            </Button>
          </Form.Item>
        </Form>
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
          <BarChart data={graphData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis dataKey="incidentCount" />
            <Tooltip />
            <Legend />
            <Bar dataKey="incidentCount" name="Incident Count">
              {graphData.map((entry, index) => (
                <Cell {...entry.cellProps} />
              ))}
            </Bar>
          </BarChart>
        </ResponsiveContainer>
      </Row>
    </Spin>
  );
}
