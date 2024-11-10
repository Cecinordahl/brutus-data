import React, { useEffect, useState } from 'react';
import { getUsers } from '../services/UserService';
import { User } from '../models/User';
import UserRow from './UserRow';
import '../styles/UserList.css';

const UserList: React.FC = () => {
    const [users, setUsers] = useState<User[]>([]);
    const [offset, setOffset] = useState(0);
    const [limit] = useState(10);
    const [searchParams, setSearchParams] = useState({ firstName: '', lastName: '', city: '' });

    const fetchUsers = async () => {
        try {
            const response = await getUsers(limit, offset, searchParams);
            setUsers(response.data);
            setOffset(offset + limit);
        } catch (error) {
            console.error('Error fetching users:', error);
        }
    };

    useEffect(() => {
        fetchUsers();
    }, [searchParams]);

    const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setSearchParams(prev => ({ ...prev, [name]: value }));
        setOffset(0); // Reset offset for new search
    };

    const loadMoreUsers = () => {
        fetchUsers();
    };

    return (
        <div>
            <h2>User List</h2>
            {/* Search Form */}
            <div>
                <input
                    type="text"
                    placeholder="First Name"
                    name="firstName"
                    value={searchParams.firstName}
                    onChange={handleSearchChange}
                />
                <input
                    type="text"
                    placeholder="Last Name"
                    name="lastName"
                    value={searchParams.lastName}
                    onChange={handleSearchChange}
                />
                <input
                    type="text"
                    placeholder="City"
                    name="city"
                    value={searchParams.city}
                    onChange={handleSearchChange}
                />
                <button onClick={fetchUsers}>Search</button>
            </div>

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
                {users.map(user => (
                    <UserRow key={user.id} user={user} />
                ))}
                </tbody>
            </table>
            <button onClick={loadMoreUsers}>Load More</button>
        </div>
    );
};

export default UserList;
