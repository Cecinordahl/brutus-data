import React, { useEffect, useState } from 'react';
import { fetchUsers } from '../../services/UserService';
import { User } from '../../models/User';
import AgeRangeSlider from './AgeRangeSlider';
import '../../styles/UserListPage.css';
import UserTable from "../common/UserTable";

const UserListPage: React.FC = () => {
    const [users, setUsers] = useState<User[]>([]);
    const [offset, setOffset] = useState(0);
    const [limit] = useState(10);
    const [totalUsers, setTotalUsers] = useState(0);
    const [searchParams, setSearchParams] = useState({
        firstName: '',
        lastName: '',
        city: '',
        minAge: 16,
        maxAge: 75
    });

    const loadUsers = async () => {
        const { data, total } = await fetchUsers(limit, offset, searchParams);
        setUsers(data);
        setTotalUsers(total);
    };

    useEffect(() => {
        loadUsers();
    }, [offset, searchParams]); // Trigger search when offset or searchParams change

    const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setSearchParams(prev => ({ ...prev, [name]: value }));
        setOffset(0);
    };

    const handleAgeRangeChange = (newValue: number[]) => {
        setSearchParams(prev => ({
            ...prev,
            minAge: newValue[0],
            maxAge: newValue[1]
        }));
        setOffset(0);
    };

    const handleNextPage = () => {
        if (offset + limit < totalUsers) {
            setOffset(offset + limit);
        }
    };

    const handlePreviousPage = () => {
        if (offset - limit >= 0) {
            setOffset(offset - limit);
        }
    };

    // Calculate the current page number and total pages
    const currentPage = Math.floor(offset / limit) + 1;
    const totalPages = Math.ceil(totalUsers / limit);

    return (
        <div className="user-list-page-component">
            <h1>Search Users</h1>
            <p>Results update automatically</p>
            <div>
                <p>Search by first name, last name, or city:</p>
                <input
                    type="text"
                    placeholder="Search by first name"
                    name="firstName"
                    value={searchParams.firstName}
                    onChange={handleSearchChange}
                />
                <input
                    type="text"
                    placeholder="Search by last name"
                    name="lastName"
                    value={searchParams.lastName}
                    onChange={handleSearchChange}
                />
                <input
                    type="text"
                    placeholder="Search by city"
                    name="city"
                    value={searchParams.city}
                    onChange={handleSearchChange}
                />

                {/* Age Range Slider */}
                <div>
                    <p style={{marginBottom: 0}}>Filter by Age Range 16 - 75</p>
                    <AgeRangeSlider
                        ageRange={[searchParams.minAge, searchParams.maxAge]}
                        onChange={handleAgeRangeChange}
                    />
                </div>
            </div>

            {/* Pagination Controls */}
            <div className="pagination-controls">
                <p>Displaying 10 users per page</p>
                <button onClick={handlePreviousPage} disabled={offset === 0}>
                    Previous
                </button>
                <span>
                    Page {currentPage} of {totalPages}
                </span>
                <button onClick={handleNextPage} disabled={offset + limit >= totalUsers}>
                    Next
                </button>
            </div>

            {/* Display table */}
            <UserTable users={users} />
        </div>
    );
};

export default UserListPage;
