import React from 'react'

const HeaderComponent = () => {
    return (
      <div style={{ width: '100%', margin: '0', padding: '0'}}>
        <header>
          <nav className='navbar navbar-expand-md navbar-dark bg-dark' style={{
            width: '100%', padding: '2px',height:"50px", paddingTop:"25px" }}>
            <div>
              <a href='#' className='navbar-brand' style={{
                color: '#ffffff',
                textDecoration: 'none',
                fontWeight: 'bold'
              }}>
                <h3 style={{ margin: '0', padding: '0' }}>Employee Management System</h3>
              </a>
            </div>
          </nav>
        </header>
      </div>
    )
  }

export default HeaderComponent
