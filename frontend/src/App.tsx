import React from 'react';
import './styles/App.css';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import UserListPage from './components/UserListPage';
import StatisticsPage from './components/StatisticsPage';
import Menu from './components/Menu';

const App: React.FC = () => {
    return (
        <Router>
            <Menu />
            <Routes>
                <Route path="/" element={<Navigate to="/search" />} /> {/* Redirect to SearchPage as default */}
                <Route path="/search" element={<UserListPage />} />
                <Route path="/statistics" element={<StatisticsPage />} />
            </Routes>
        </Router>
    );
};

export default App;