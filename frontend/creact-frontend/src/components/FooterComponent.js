import React from 'react';

const FooterComponent = () => {
  return (
    <footer style={{ 
      marginTop: '20px', 
      padding: '10px', 
      backgroundColor: '#333', // Dark background
      textAlign: 'center', 
      fontSize: '14px', 
      color: '#fff', // Light text color for contrast
    }}>
      Owner of Application <a href="https://www.linkedin.com/in/anshu-vairagade" 
        target="_blank" rel="noopener noreferrer" style={{ color: '#fff', textDecoration: 'none' }}>
        @Anshu Vairagade
      </a>
    </footer>
  );
};

export default FooterComponent;