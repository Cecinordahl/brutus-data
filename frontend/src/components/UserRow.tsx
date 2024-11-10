import React from 'react';
import { User } from '../models/User';

interface UserRowProps {
    user: User;
}

const formatCcNumber = (ccnumber: string): string => {
        return `**** **** **** ${ccnumber.slice(-4)}`;
};

const UserRow: React.FC<UserRowProps> = ({ user }) => (
    <tr>
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
);

export default UserRow;