import React from 'react';
import { Pie } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';

ChartJS.register(ArcElement, Tooltip, Legend);

interface AgeDistributionChartProps {
    ageDistribution: Record<string, number>;
    totalUsers: number;
}

const AgeDistributionChart: React.FC<AgeDistributionChartProps> = ({ ageDistribution, totalUsers }) => {
    // Calculate percentage data for each age group
    const labels = Object.keys(ageDistribution);
    const dataValues = Object.values(ageDistribution).map(count => (count / totalUsers) * 100);

    const data = {
        labels,
        datasets: [
            {
                label: 'Age Distribution (%)',
                data: dataValues,
                backgroundColor: [
                    '#FF6384',
                    '#36A2EB',
                    '#FFCE56',
                    '#4BC0C0',
                    '#9966FF',
                    '#FF9F40'
                ],
                hoverBackgroundColor: [
                    '#FF6384',
                    '#36A2EB',
                    '#FFCE56',
                    '#4BC0C0',
                    '#9966FF',
                    '#FF9F40'
                ]
            }
        ]
    };

    return (
        <div style={{ width: '50%', margin: 'auto', textAlign: "center" }}>
            <h4>Age Distribution (%) :</h4>
            <Pie data={data} />
        </div>
    );
};

export default AgeDistributionChart;
