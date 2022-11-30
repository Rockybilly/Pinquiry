import { DatePicker, Space } from "antd";
import React, { useState } from "react";
const { RangePicker } = DatePicker;

export default function IncidentDetail({ monId }) {
  const [dates, setDates] = useState(null);
  const [hackValue, setHackValue] = useState(null);
  const [value, setValue] = useState(null);

  const disabledDate = (current) => {
    if (!dates) {
      return false;
    }

    const tooLate = dates[0] && current.diff(dates[0], "days") > 7;
    const tooEarly = dates[1] && dates[1].diff(current, "days") > 7;
    return !!tooEarly || !!tooLate;
  };

  const onOpenChange = (open) => {
    if (open) {
      setHackValue([null, null]);
      setDates([null, null]);
    } else {
      setHackValue(null);
    }
  };

  /*return (
      <RangePicker
          value={hackValue || value}
          disabledDate={disabledDate}
          onCalendarChange={(val) => setDates(val)}
          onChange={(val) => setValue(val)}
          onOpenChange={onOpenChange}
      />
  );
};



  // ======================================
  const onChange = (value, dateString) => {
    console.log("Selected Time: ", value);
    console.log("Formatted Selected Time: ", dateString);
  };

  const onOk = (value) => {
    console.log("onOk: ", value);
  };*/

  return (
    <Space direction="vertical" size={12}>
      <DatePicker showTime />
      <RangePicker
        showTime={{
          format: "HH:mm",
        }}
        format="YYYY-MM-DD HH:mm"
      />
    </Space>
  );

  //return <>Incident detail</>;
}
