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
  console.log("calc(" + size + "/" + 3 + ")");
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
            fontSize: "calc(" + size + "/" + 10 + ")",
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
            fontSize: "calc(" + size + "/" + 1.8 + ")",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <div style={{ position: "relative", top: "-40%" }}>{text}</div>
        </Row>
        <Row
          style={{
            height: "10%",
            width: "100%",
            fontFamily: "Montserrat",
            color: textColor,
            fontSize: "calc(" + size + "/" + 17 + ")",
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
