import React, { useState } from 'react';


const Tooltip = ({ message }) => {
  const [visible, setVisible] = useState(false);

  return (
    <div 
      className="tooltip-container" 
      onMouseEnter={() => setVisible(true)} 
      onMouseLeave={() => setVisible(false)}
    >
      <i className="fas fa-info-circle tooltip-icon"></i>
      {visible && <div className="tooltip-message">{message}</div>}
    </div>
  );
};

export default Tooltip;