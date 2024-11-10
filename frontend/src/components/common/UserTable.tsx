import React from 'react';

interface User {
    id?: number;
    firstName: string;
    lastName: string;
    age: number;
    street: string;
    city: string;
    state: string;
    latitude: number;
    longitude: number;
    ccnumber: string;
}

interface UserTableProps {
    users: User[];
}

const formatCcNumber = (ccnumber: string): string => {
    return `**** **** **** ${ccnumber.slice(-4)}`;
};

const UserTable: React.FC<UserTableProps> = ({ users }) => {
    return (
        <table>
            <thead>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Age</th>
                <th>Street</th>
                <th>City</th>
                <th>State</th>
                <th>Latitude</th>
                <th>Longitude</th>
                <th>Credit Card</th>
            </tr>
            </thead>
            <tbody>
            {users.map((user, index) => (
                <tr key={user.id || index}>
                    <td>{user.firstName}</td>
                    <td>{user.lastName}</td>
                    <td>{user.age}</td>
                    <td>{user.street}</td>
                    <td>{user.city}</td>
                    <td>{user.state}</td>
                    <td>{user.latitude.toFixed(4)}</td>
                    <td>{user.longitude.toFixed(4)}</td>
                    <td>{formatCcNumber(user.ccnumber)}</td>
                </tr>
            ))}
            </tbody>
        </table>
    );
};

export default UserTable;