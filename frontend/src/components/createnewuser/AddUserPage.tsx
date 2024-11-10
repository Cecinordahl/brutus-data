import React, { useState } from 'react';
import { addUser, uploadUserCsv } from '../../services/UserService';
import UserTable from '../common/UserTable';
import ManualEntryForm from './ManualEntryForm';
import CsvUploadForm from './CsvUploadForm';
import { validateUserData, validateCsvRow } from '../../utils/ValidationUtils';
import { User } from "../../models/User";

const AddUserPage: React.FC = () => {
    const [userData, setUserData] = useState({
        firstName: '',
        lastName: '',
        age: '',
        street: '',
        city: '',
        state: '',
        latitude: '',
        longitude: '',
        ccnumber: ''
    });
    const [uploadedUsers, setUploadedUsers] = useState<User[]>([]);
    const [csvFile, setCsvFile] = useState<File | null>(null);
    const [manualErrors, setManualErrors] = useState<string[]>([]);
    const [csvErrors, setCsvErrors] = useState<string[]>([]);

    const handleAddUser = async () => {
        const errors = validateUserData(userData);
        setManualErrors(errors);
        if (errors.length > 0) return;

        try {
            const newUser = await addUser(userData);
            setUploadedUsers([...uploadedUsers, newUser]);
            setUserData({
                firstName: '',
                lastName: '',
                age: '',
                street: '',
                city: '',
                state: '',
                latitude: '',
                longitude: '',
                ccnumber: ''
            });
            setManualErrors([]);
        } catch (error) {
            console.error('Failed to add user:', error);
        }
    };

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files) {
            setCsvFile(e.target.files[0]);
        }
    };

    const handleUploadCsv = async () => {
        if (!csvFile) return;

        try {
            const fileContent = await csvFile.text();
            const rows = fileContent.split('\n');
            const csvErrors: string[] = [];

            rows.forEach((row, index) => {
                const columns = row.split(',');
                csvErrors.push(...validateCsvRow(columns, index));
            });

            setCsvErrors(csvErrors);
            if (csvErrors.length > 0) return;

            const users = await uploadUserCsv(csvFile);
            setUploadedUsers(users);
            setCsvFile(null);
            setCsvErrors([]);
        } catch (error) {
            console.error('Failed to upload CSV:', error);
        }
    };

    return (
        <div>
            <h1>Add User</h1>
            <ManualEntryForm
                userData={userData}
                setUserData={setUserData}
                handleAddUser={handleAddUser}
                manualErrors={manualErrors}
            />
            <CsvUploadForm
                handleFileChange={handleFileChange}
                handleUploadCsv={handleUploadCsv}
                csvErrors={csvErrors}
            />
            <h2>Users added:</h2>
            <UserTable users={uploadedUsers} />
        </div>
    );
};

export default AddUserPage;
