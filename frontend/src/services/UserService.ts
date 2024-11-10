import axios from 'axios';

const USERS_URL = 'http://localhost:8080/api/users';
const STATS_URL = 'http://localhost:8080/api/stats';

// Fetch paginated list of users with search parameters
export const fetchUsers = async (limit: number, offset: number, searchParams: any) => {
    try {
        const response = await getUsers(limit, offset, searchParams);
        return {
            data: response.data.data,
            total: response.data.total
        };
    } catch (error) {
        console.error('Error fetching users:', error);
        return { data: [], total: 0 }; // Return empty data and zero total in case of error
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

// Add a single user
export const addUser = async (userData: any) => {
    try {
        const response = await axios.post(USERS_URL, userData);
        return response.data;
    } catch (error) {
        console.error('Error adding user:', error);
        throw error;
    }
};

// Upload a CSV file for bulk user creation
export const uploadUserCsv = async (file: File) => {
    const formData = new FormData();
    formData.append('file', file);

    try {
        const response = await axios.post(`${USERS_URL}/upload`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
        return response.data;
    } catch (error) {
        console.error('Error uploading CSV:', error);
        throw error;
    }
};