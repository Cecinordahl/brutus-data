import React from 'react';
import { User } from '../models/User';
import { formatCcNumber } from '../utils/formatters';

interface UserRowProps {
    user: User;
}

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