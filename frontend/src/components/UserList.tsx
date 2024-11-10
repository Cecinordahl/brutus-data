import React, { useEffect, useState } from 'react';
import { getUsers } from '../services/UserService';
import { User } from '../models/User';
import UserRow from './UserRow';
import AgeRangeSlider from './AgeRangeSlider'; // Import the AgeRangeSlider component
import '../styles/UserList.css';

const UserList: React.FC = () => {
    const [users, setUsers] = useState<User[]>([]);
    const [offset, setOffset] = useState(0);
    const [limit] = useState(10);
    const [searchParams, setSearchParams] = useState({
        firstName: '',
        lastName: '',
        city: '',
        minAge: 18,  // Default minimum age
        maxAge: 100  // Default maximum age
    });

    const fetchUsers = async () => {
        try {
            const response = await getUsers(limit, offset, searchParams);
            setUsers(response.data);
        } catch (error) {
            console.error('Error fetching users:', error);
        }
    };

    useEffect(() => {
        fetchUsers();
    }, [searchParams]); // Trigger search when searchParams change

    const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setSearchParams(prev => ({ ...prev, [name]: value }));
        setOffset(0); // Reset offset for new search
    };

    const handleAgeRangeChange = (newValue: number[]) => {
        setSearchParams(prev => ({
            ...prev,
            minAge: newValue[0],
            maxAge: newValue[1]
        }));
        setOffset(0); // Reset offset for new search
    };

    return (
        <div>
            <h2>User List</h2>
            <div>
                <p>Search by First Name, Last Name, or City (results update automatically):</p>
                <input
                    type="text"
                    placeholder="Search by First Name"
                    name="firstName"
                    value={searchParams.firstName}
                    onChange={handleSearchChange}
                />
                <input
                    type="text"
                    placeholder="Search by Last Name"
                    name="lastName"
                    value={searchParams.lastName}
                    onChange={handleSearchChange}
                />
                <input
                    type="text"
                    placeholder="Search by City"
                    name="city"
                    value={searchParams.city}
                    onChange={handleSearchChange}
                />

                {/* Age Range Slider */}
                <div>
                    <p>Filter by Age Range: 16 - 75</p>
                    <AgeRangeSlider
                        ageRange={[searchParams.minAge, searchParams.maxAge]}
                        onChange={handleAgeRangeChange}
                    />
                </div>
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
        </div>
    );
};

export default UserList;
