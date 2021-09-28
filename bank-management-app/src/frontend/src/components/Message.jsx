import React from "react";

const Message = ({ messageStyle, children }) => {
  return <div className={messageStyle}>{children}</div>;
};

export default Message;
