import axios from 'axios';

const USERS_URL = 'http://localhost:8080/api/users';
const STATS_URL = 'http://localhost:8080/api/stats';

// Fetch paginated list of users with search parameters
export const fetchUsers = async (limit: number, offset: number, searchParams: any) => {
    try {
        const response = await getUsers(limit, offset, searchParams);
        return response.data; // Return data for direct usage
    } catch (error) {
        console.error('Error fetching users:', error);
        return []; // Return empty array or handle as needed
    }
};

export const getUsers = async (limit: number, offset: number, searchParams: any = {}) => {
    const { firstName, lastName, city, minAge, maxAge } = searchParams;
    return await axios.get(USERS_URL + '/search', {
        params: {limit, offset, firstName, lastName, city, minAge, maxAge},
    });
};

// Fetch overall statistics about the user dataset
export const fetchStatistics = async () => {
    try {
        const response = await getUserStatistics();
        return response.data; // Return data for direct usage
    } catch (error) {
        console.error("Failed to fetch statistics:", error);
        return {}; // Return empty object or handle as needed
    }
};

export const getUserStatistics = async () => {
    return await axios.get(STATS_URL);
};

export const getUserById = async (id: number) => {
    return await axios.get(`${USERS_URL}/${id}`);
};

export const createUser = async (user: any) => {
    return await axios.post(USERS_URL, user);
};

export const updateUser = async (id: number, user: any) => {
    return await axios.patch(`${USERS_URL}/${id}`, user);
};

export const deleteUser = async (id: number) => {
    return await axios.delete(`${USERS_URL}/${id}`);
};
