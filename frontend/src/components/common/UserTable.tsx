import React from 'react';
import {User} from "../../models/User";

interface UserTableProps {
    users: User[];
}

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
                    <td>{user.maskedCcnumber}</td>
                </tr>
            ))}
            </tbody>
        </table>
    );
};

export default UserTable;
