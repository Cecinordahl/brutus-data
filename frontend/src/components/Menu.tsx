import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import '../styles/App.css';

const Menu: React.FC = () => {
    const navigate = useNavigate();
    const location = useLocation();

    return (
        <div className="menu-container">
            <button
                onClick={() => navigate('/search')}
                className={location.pathname === '/search' ? 'active' : ''}
            >
                Search
            </button>
            <button
                onClick={() => navigate('/statistics')}
                className={location.pathname === '/statistics' ? 'active' : ''}
            >
                Statistics
            </button>
        </div>
    );
};

export default Menu;
