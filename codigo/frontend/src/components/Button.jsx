import React from 'react';

const Button = ({ children, type = "button", variant = "default", onClick }) => {
  return (
    <button
      type={type}
      onClick={onClick}
      className={`btn ${variant}`} // Adiciona classe dinâmica
    >
      {children}
    </button>
  );
};

export default Button;
