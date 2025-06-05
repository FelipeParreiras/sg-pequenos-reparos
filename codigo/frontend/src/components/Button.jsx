import React from 'react';

const Button = ({ children, type = "button", onClick }) => {
  return (
    <button
      type={type}
      onClick={onClick}
      className="form-button"
    >
      {children}
    </button>
  );
};

export default Button;
