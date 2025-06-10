import React from "react";
import "../styles/components/Label.css";

const Label = ({ htmlFor, children }) => {
  return (
    <label htmlFor={htmlFor} className="label-component">
      {children}
    </label>
  );
};

export default Label;
