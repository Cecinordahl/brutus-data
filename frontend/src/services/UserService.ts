import axios from 'axios';

const API_URL = 'http://localhost:8080/api/users';

export const getAllUsers = async () => {
    return await axios.get(API_URL);
};

export const getUserById = async (id: number) => {
    return await axios.get(`${API_URL}/${id}`);
};

export const createUser = async (user: any) => {
    return await axios.post(API_URL, user);
};

export const updateUser = async (id: number, user: any) => {
    return await axios.patch(`${API_URL}/${id}`, user);
};

export const deleteUser = async (id: number) => {
    return await axios.delete(`${API_URL}/${id}`);
};
