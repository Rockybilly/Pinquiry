import { ResponsiveContainer } from "recharts";
import { Col, Row } from "antd";
import { BasicCard } from "./BasicCard";

//
//Montserrat

export function BasicTextCard({
  header,
  text,
  footer,
  backgroundColor,
  textColor,
  size,
}) {
  return (
    <BasicCard
      backgroundColor={backgroundColor}
      size={size}
      style={{ textShadow: "rgba(0,0,0,.8) 0 0 100px" }}
    >
      <Col
        style={{
          height: size,
          width: size,
        }}
      >
        <Row
          style={{
            height: "30%",
            width: "100%",
            fontFamily: "Montserrat",
            color: textColor,
            fontSize: "calc(" + size + "/" + 9.6 + ")",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <span style={{ textShadow: "rgba(0,0,0,0.8) 0 0 1px" }}>
            {header}
          </span>
        </Row>
        <Row
          style={{
            height: "60%",
            width: "100%",
            fontFamily: "Montserrat",
            color: textColor,
            fontSize:
              "calc(" +
              size +
              "/" +
              1.8 +
              " - " +
              String(text).length * 0.7 +
              "vh)",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <div
            style={{
              position: "relative",
              bottom: "calc(40% - " + String(text).length * 0.4 + "vh)",
              textShadow: "rgba(0,0,0,.8) 0 0 5px",
            }}
          >
            {text}
          </div>
        </Row>
        <Row
          style={{
            height: "10%",
            width: "100%",
            fontFamily: "Montserrat",
            color: textColor,
            fontSize: "calc(" + size + "/" + 13 + ")",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <div
            style={{
              position: "relative",
              top: "-50%",
              textShadow: "rgba(0,0,0,.8) 0 0 2px",
            }}
          >
            {footer}{" "}
          </div>
        </Row>
      </Col>
    </BasicCard>
  );
}
