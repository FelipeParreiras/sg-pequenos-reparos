import React from 'react';
import '../styles/components/Input.css';
import Label from './Label';

const Input = ({ label, name, value, onChange, type = "text", required = false }) => {
  return (
    <div className="input-container">
      <Label htmlFor={name} className="input-label">{label}:</Label>
      <input
        id={name}
        type={type}
        name={name}
        value={value}
        onChange={onChange}
        required={required}
        placeholder={label}
        className="input-field"
      />
    </div>
  );
};

export default Input;
