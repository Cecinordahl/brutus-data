import React, { useEffect, useState } from 'react';
import AgeDistributionChart from './AgeDistributionChart';
import { fetchStatistics } from '../services/UserService';
import '../styles/StatisticsPage.css';
import {Statistics} from "../models/Statistics";

const StatisticsPage: React.FC = () => {
    const [statistics, setStatistics] = useState<Statistics>({
        totalUsers: 0,
        averageAge: 0,
        mostCommonCity: '',
        ageDistribution: {},
    });

    useEffect(() => {
        const loadStatistics = async () => {
            const data = await fetchStatistics();
            setStatistics(data);
        };
        loadStatistics();
    }, []);

    return (
        <div >
            <div className="statistics-page-component">
                <h1>Statistics</h1>
                <p><strong>Total Users:</strong> {statistics.totalUsers}</p>
                <p><strong>Average Age:</strong> {statistics.averageAge}</p>
                <p><strong>Most Common City:</strong> {statistics.mostCommonCity}</p>
            </div>

            <AgeDistributionChart
                ageDistribution={statistics.ageDistribution}
                totalUsers={statistics.totalUsers}
            />
        </div>
    );
};

export default StatisticsPage;
