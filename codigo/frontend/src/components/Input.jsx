import React from 'react';

const Input = ({ label, name, value, onChange, type = "text", required = false }) => {
  return (
    <>
      <label>{label}:</label><br />
      <input
        type={type}
        name={name}
        value={value}
        onChange={onChange}
        required={required}
        className="form-input"
      />
    </>
  );
};

export default Input;
