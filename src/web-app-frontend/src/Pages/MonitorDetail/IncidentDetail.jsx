import { Button, Collapse, DatePicker, message } from "antd";
import React, { useState } from "react";
import { getIncidentsDetails } from "../../Services/backendComm";
import moment from "moment";

const { Panel } = Collapse;
const { RangePicker } = DatePicker;

function IncidentPanelContent({ incident }) {
  const type = incident.type;

  if (type === "ping") {
    return "Incident Error String: " + incident.errorString;
  }
  if (type === "http") {
    return (
      <>
        <text
          style={{
            fontSize: "1rem",
            textShadow: "0 0 6px rgba(0,0,0,0.4)",
            paddingRight: "1rem",
            paddingLeft: "2rem",
          }}
        >
          Incident Error String:
        </text>
        {incident.debugInfo.errorString}
        <p></p>
        <text
          style={{
            fontSize: "1rem",
            textShadow: "0 0 6px rgba(0,0,0,0.4)",
            paddingRight: "1rem",
            paddingLeft: "2rem",
          }}
        >
          Response Headers:
        </text>
        {incident.debugInfo.response_headers &&
        Object.entries(incident.debugInfo.response_headers).length > 0
          ? Object.entries(incident.debugInfo.response_headers).map(
              ([key, value]) => (
                <div style={{ paddingTop: "1rem", paddingLeft: "4rem" }}>
                  {key}:{value}
                </div>
              )
            )
          : "None available"}
      </>
    );
  }
  if (type === "content") {
    return (
      <>
        Content results are grouped according to their response bodies.
        {incident.groups && incident.groups.length > 0
          ? incident.groups.map((group_obj, index) => (
              <div style={{ paddingTop: "2rem" }}>
                <text
                  style={{
                    fontSize: "1rem",
                    textShadow: "0 0 6px rgba(0,0,0,0.4)",

                    paddingRight: "1rem",
                    paddingLeft: "2rem",
                    textDecorationLine: "underline",
                  }}
                >
                  Group: {index + 1}
                </text>

                {group_obj.results && group_obj.results.length > 0
                  ? group_obj.results.map((result, r_index) => (
                      <div style={{ paddingBottom: "1.5rem" }}>
                        <text
                          style={{
                            fontSize: "1rem",
                            textShadow: "0 0 6px rgba(0,0,0,0.4)",
                            paddingRight: "1rem",
                            paddingLeft: "5rem",

                            textDecorationLine: "underline",
                          }}
                        >
                          Content Result: {r_index + 1}
                        </text>
                        <p
                          style={{
                            fontSize: "1rem",
                            textShadow: "0 0 6px rgba(0,0,0,0.4)",
                            paddingRight: "1rem",
                            paddingLeft: "5rem",
                          }}
                        >
                          URL: {result.url}
                        </p>
                        <p
                          style={{
                            fontSize: "1rem",
                            textShadow: "0 0 6px rgba(0,0,0,0.4)",
                            paddingRight: "1rem",
                            paddingLeft: "5rem",
                          }}
                        >
                          IP: {result.ip}
                        </p>
                        <p
                          style={{
                            fontSize: "1rem",
                            textShadow: "0 0 6px rgba(0,0,0,0.4)",
                            paddingRight: "1rem",
                            paddingLeft: "5rem",
                          }}
                        >
                          Response Time: {result.responseTime}
                        </p>
                        <p
                          style={{
                            fontSize: "1rem",
                            textShadow: "0 0 6px rgba(0,0,0,0.4)",
                            paddingRight: "1rem",
                            paddingLeft: "5rem",
                          }}
                        >
                          Status Code: {result.httpstatusCode}
                        </p>

                        <text
                          style={{
                            fontSize: "1rem",
                            textShadow: "0 0 6px rgba(0,0,0,0.4)",
                            paddingRight: "1rem",
                            paddingLeft: "5rem",
                            textDecorationLine: "underline",
                          }}
                        >
                          Response Headers
                        </text>

                        {result.debugInfo &&
                        result.debugInfo.response_headers &&
                        Object.entries(result.debugInfo.response_headers)
                          .length > 0
                          ? Object.entries(
                              result.debugInfo.response_headers
                            ).map(([key, value]) => (
                              <div
                                style={{
                                  paddingLeft: "3rem",
                                }}
                              >
                                <text
                                  style={{
                                    fontSize: "1rem",
                                    textShadow: "0 0 6px rgba(0,0,0,0.4)",
                                    paddingRight: "1rem",
                                    paddingLeft: "5rem",
                                  }}
                                >
                                  {key}
                                </text>
                                {value}
                              </div>
                            ))
                          : "None Available"}
                      </div>
                    ))
                  : "None Available"}
              </div>
            ))
          : "None available"}
      </>
    );
  } else {
    console.log("oh no");
  }
}

export default function IncidentDetail({ monId }) {
  const [dates, setDates] = useState([moment().subtract(1, "hours"), moment()]);
  const [value, setValue] = useState(null);
  const [incidents, setIncidents] = useState([]);
  const [loading, setLoading] = useState(false);

  function disabledDate(current) {
    if (!dates) {
      return false;
    }
    const tooLate =
      Math.abs(dates[0] && current.diff(dates[0], "minutes")) > 60;
    const tooEarly =
      Math.abs(dates[1] && dates[1].diff(current, "minutes")) > 60;

    return (
      !!tooEarly ||
      !!tooLate ||
      (current && current.valueOf() > moment().valueOf())
    );
  }

  const disabledRangeTime = (current, type) => {
    if (!dates) {
      return false;
    }

    if (type === "start") {
      if (!dates[1]) {
        return false;
      }

      return {
        disabledHours: () => {
          const result = [];
          for (let i = 0; i < 24; i++) {
            if (Math.abs(dates[1].hours() - i) > 1) {
              result.push(i);
            }
          }
          return result;
        },
        disabledMinutes: (hour) => {
          const result = [];

          for (let i = 0; i < 60; i++) {
            let minutes = hour * 60 + i;

            if (
              Math.abs(dates[1].hours() * 60 + dates[1].minutes() - minutes) >
              60
            ) {
              result.push(i);
            }
          }

          return result;
        },
      };
    } else if (type === "end") {
      if (!dates[0]) {
        return false;
      }
      return {
        disabledHours: () => {
          const result = [];
          for (let i = 0; i < 24; i++) {
            if (Math.abs(dates[0].hours() - i) > 1) {
              result.push(i);
            }
          }
          return result;
        },
        disabledMinutes: (hour) => {
          const result = [];

          for (let i = 0; i < 60; i++) {
            let minutes = hour * 60 + i;

            if (
              Math.abs(dates[0].hours() * 60 + dates[0].minutes() - minutes) >
              60
            ) {
              result.push(i);
            }
          }

          return result;
        },
      };
    }
    return {};
  };

  const onOpenChange = (open) => {
    if (open) {
      if (!dates) {
        setDates([null, null]);
      } else {
        setDates(dates);
      }
    }
    console.log(dates);
  };

  const onClick = () => {
    if (!dates || !dates[0] || !dates[1]) {
      message.error(
        "Please select beginning and end dates for incident listing!"
      );
    } else {
      setLoading(true);

      getIncidentsDetails({
        id: monId,
        begin: dates[0].valueOf(),
        end: dates[1].valueOf(),
      })
        .then(function (response) {
          setIncidents(response);
          if (response.length === 0) {
            message.info("No incidents found in the selected time span.");
          }
        })
        .catch(function (error) {})
        .then(function () {
          setLoading(false);
        });
    }
  };

  const onChange = (key) => {
    console.log(key);
  };
  return (
    <div
      style={{
        alignItems: "center",
        justifyContent: "center",
        textAlign: "center",
        paddingTop: "7vh",
      }}
    >
      <RangePicker
        value={dates || value}
        disabledDate={disabledDate}
        disabledTime={disabledRangeTime}
        onCalendarChange={(val) => setDates(val)}
        onChange={(val) => setValue(val)}
        showTime={{}}
        onOpenChange={onOpenChange}
        format="YYYY-MM-DD HH:mm"
      />
      <Button type="primary" onClick={onClick} loading={loading}>
        Query Incidents
      </Button>

      {incidents && (
        <Collapse
          style={{
            alignItems: "left",
            justifyContent: "left",
            textAlign: "left",
          }}
        >
          {incidents.map((incident, index) => (
            <Panel
              header={
                <text style={{ fontSize: "1.5rem" }}>
                  Incident at {"  "}
                  {moment(incident.timestamp).format("YYYY/MM/DD HH:mm:ss")}
                </text>
              }
              key={index}
            >
              {IncidentPanelContent({ incident })}
            </Panel>
          ))}
        </Collapse>
      )}
    </div>
  );

  //return <>Incident detail</>;
}
