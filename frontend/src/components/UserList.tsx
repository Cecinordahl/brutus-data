import React, { useEffect, useState } from 'react';
import { getAllUsers } from '../services/UserService';

interface User {
    id: number;
    name: string;
    age: number;
    address: string;
    ccnumber: string;
}

const UserList: React.FC = () => {
    const [users, setUsers] = useState<User[]>([]);

    useEffect(() => {
        getAllUsers().then(response => setUsers(response.data));
    }, []);

    return (
        <div>
            <h2>User List</h2>
            <ul>
                {users.map(user => (
                    <li key={user.id}>
                        {user.name} - {user.age} - {user.address}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default UserList;
