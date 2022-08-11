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
  console.log("70% " + String(text).length * 10 + "vh");
  return (
    <BasicCard backgroundColor={backgroundColor} size={size}>
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
          {header}
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
          <div style={{ position: "relative", top: "-50%" }}>{footer} </div>
        </Row>
      </Col>
    </BasicCard>
  );
}
