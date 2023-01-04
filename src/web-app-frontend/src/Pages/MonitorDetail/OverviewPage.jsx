import IncidentCountGraph from "./IncidentCountGraph";
import ResponseTimeGraph from "./ResponseTimeGraph";
import { getMonitorDetails } from "../../Services/backendComm";
import React, { useEffect, useState } from "react";
import { Row, Spin } from "antd";
import ReactCountryFlag from "react-country-flag";

function DetailsEntry({ header, value }) {
  return (
    <p>
      <text
        style={{
          fontSize: "1.2rem",
          textShadow: "0 0 5px rgba(0,0,0,0.4)",
          paddingRight: "1rem",
        }}
      >
        {header}
      </text>
      <text
        style={{
          fontSize: "1.0rem",
          textShadow: "0 0 5px rgba(0,0,0,0.4)",
        }}
      >
        {value}
      </text>
    </p>
  );
}

function Details({ details }) {
  console.log(details);
  if (details.type === "ping") {
    return (
      <div>
        <DetailsEntry
          header={"Monitor Type:"}
          value={details.type.toUpperCase()}
        />
        <DetailsEntry header={"Monitor Name:"} value={details.name} />
        <DetailsEntry
          header={"Ping Location:"}
          value={
            <>
              {" "}
              <ReactCountryFlag
                style={{
                  width: "3vw",
                  height: "3vw",
                  paddingRight: "1vw",
                }}
                countryCode={details.monitor_location}
                svg
              />
              ({details.monitor_location})
            </>
          }
        />
        <DetailsEntry header={"Ping IP:"} value={details.ping_ip} />

        <DetailsEntry
          header={"Ping Interval:"}
          value={details.interval_s + " seconds"}
        />
        <DetailsEntry
          header={"Timeout Limit:"}
          value={details.timeout_s + " seconds"}
        />
        <DetailsEntry
          header={"Incident Count for Event:"}
          value={details.acknowledgementThreshold + " incidents"}
        />
      </div>
    );
  } else if (details.type === "http") {
    return (
      <div>
        <DetailsEntry
          header={"Monitor Type:"}
          value={details.type.toUpperCase()}
        />
        <DetailsEntry header={"Monitor Name:"} value={details.name} />
        <DetailsEntry
          header={"Ping Location:"}
          value={
            <>
              {" "}
              <ReactCountryFlag
                style={{
                  width: "3vw",
                  height: "3vw",
                  paddingRight: "1vw",
                }}
                countryCode={details.monitor_location}
                svg
              />
              ({details.monitor_location})
            </>
          }
        />
        <DetailsEntry header={"Protocol:"} value={details.protocol} />
        <DetailsEntry header={"Server:"} value={details.server} />
        <DetailsEntry header={"URI:"} value={details.uri} />
        <DetailsEntry header={"Port:"} value={details.port} />
        <DetailsEntry
          header={"Success Codes:"}
          value={details.successCodes.map((val, index) => (
            <>
              {val}
              {index === details.successCodes.length - 1 ? "" : ", "}
            </>
          ))}
        />

        {details.requestHeaders &&
        Object.entries(details.requestHeaders).length > 0 ? (
          <DetailsEntry
            header={"Request Headers:"}
            value={Object.entries(details.requestHeaders).map(
              ([key, value]) => (
                <div style={{ paddingTop: "1rem", paddingLeft: "4rem" }}>
                  {key}: {value}
                </div>
              )
            )}
          />
        ) : (
          ""
        )}

        {details.responseHeaders &&
        Object.entries(details.responseHeaders).length > 0 ? (
          <DetailsEntry
            header={"Success Response Headers:"}
            value={Object.entries(details.responseHeaders).map(
              ([key, value]) => (
                <div style={{ paddingTop: "1rem", paddingLeft: "4rem" }}>
                  {key}:{value}
                </div>
              )
            )}
          />
        ) : (
          ""
        )}

        {details.searchString && details.searchString.length ? (
          <DetailsEntry
            header={"Search String:"}
            value={details.searchString}
          />
        ) : (
          ""
        )}

        <DetailsEntry
          header={"Request Interval:"}
          value={details.interval_s + " seconds"}
        />
        <DetailsEntry
          header={"Timeout Limit:"}
          value={details.timeout_s + " seconds"}
        />
        <DetailsEntry
          header={"Incident Count for Event:"}
          value={details.acknowledgementThreshold + " incidents"}
        />
      </div>
    );
  } else if (details.type === "content") {
    return (
      <div>
        <DetailsEntry
          header={"Monitor Type:"}
          value={details.type.toUpperCase()}
        />
        <DetailsEntry header={"Monitor Name:"} value={details.name} />
        <DetailsEntry
          header={"Ping Location:"}
          value={
            <>
              {" "}
              <ReactCountryFlag
                style={{
                  width: "3vw",
                  height: "3vw",
                  paddingRight: "1vw",
                }}
                countryCode={details.monitor_location}
                svg
              />
              ({details.monitor_location})
            </>
          }
        />

        <DetailsEntry header={"Content Locations:"} value={""} />
        <p />
        {details.content_locations.map((obj, index) => (
          <div>
            <DetailsEntry
              header={
                <text style={{ textDecorationLine: "underline" }}>
                  Location: {index + 1}
                </text>
              }
              value={""}
            />
            <div style={{ paddingLeft: "4rem" }}>
              <DetailsEntry header={"Protocol:"} value={obj.protocol} />
              <DetailsEntry header={"Server:"} value={obj.server} />
              <DetailsEntry header={"URI:"} value={obj.uri} />
              <DetailsEntry header={"Port:"} value={obj.port} />
              {obj.requestHeaders &&
              Object.entries(obj.requestHeaders).length > 0 ? (
                <DetailsEntry
                  header={"Request Headers:"}
                  value={Object.entries(obj.requestHeaders).map(
                    ([key, value]) => (
                      <div style={{ paddingTop: "1rem", paddingLeft: "4rem" }}>
                        {key}: {value}
                      </div>
                    )
                  )}
                />
              ) : (
                ""
              )}
            </div>
          </div>
        ))}
        <p />

        <DetailsEntry
          header={"Request Interval:"}
          value={details.interval_s + " seconds"}
        />
        <DetailsEntry
          header={"Timeout Limit:"}
          value={details.timeout_s + " seconds"}
        />
        <DetailsEntry
          header={"Incident Count for Event:"}
          value={details.acknowledgementThreshold + " incidents"}
        />
      </div>
    );
  } else {
    return <div>Unknown Error</div>;
  }
}

export default function OverviewPage({ monId }) {
  const [content, setContent] = useState({});
  const [contentLoading, setContentLoading] = useState(true);

  useEffect(() => {
    async function doWork() {
      setContentLoading(true);
      setContent(await getMonitorDetails({ id: monId }));
      setContentLoading(false);
    }

    doWork();
  }, []);

  return (
    <>
      <div
        style={{
          borderStyle: "double",
          borderColor: "#000000",
          padding: "2vh",
          marginTop: "3vh",
        }}
      >
        <Row
          style={{
            alignItems: "center",
            justifyContent: "center",
            textAlign: "center",
            height: "10vh",
            fontSize: "2.5rem",
            textShadow: "0 0 10px rgba(0,0,0,0.4)",
            marginTop: "1vh",
            textDecorationLine: "underline",
            padding: "2vh",
          }}
        >
          Monitor Details
        </Row>
        <Spin spinning={contentLoading}>
          <Details details={content} />
        </Spin>
      </div>

      <IncidentCountGraph monId={monId} />
      <ResponseTimeGraph monId={monId} />
    </>
  );
}
