import React from 'react';
import {UserFormData} from "../models/UserFormData";

interface ManualEntryFormProps {
    userData: UserFormData;
    setUserData: React.Dispatch<React.SetStateAction<UserFormData>>;
    handleAddUser: () => void;
    manualErrors: string[];
}

const ManualEntryForm: React.FC<ManualEntryFormProps> = ({ userData, setUserData, handleAddUser, manualErrors }) => {
    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setUserData(prevData => ({ ...prevData, [name]: value }));
    };

    return (
        <div>
            <h2>Manual Entry</h2>
            <input name="firstName" placeholder="First Name" value={userData.firstName} onChange={handleInputChange} />
            <input name="lastName" placeholder="Last Name" value={userData.lastName} onChange={handleInputChange} />
            <input name="age" placeholder="Age" value={userData.age} onChange={handleInputChange} type="number" />
            <input name="street" placeholder="Street" value={userData.street} onChange={handleInputChange} />
            <input name="city" placeholder="City" value={userData.city} onChange={handleInputChange} />
            <input name="state" placeholder="State" value={userData.state} onChange={handleInputChange} />
            <input name="latitude" placeholder="Latitude" value={userData.latitude} onChange={handleInputChange} />
            <input name="longitude" placeholder="Longitude" value={userData.longitude} onChange={handleInputChange} />
            <input name="ccnumber" placeholder="Credit Card" value={userData.ccnumber} onChange={handleInputChange} />
            <button onClick={handleAddUser}>Add User</button>
            {manualErrors.length > 0 && (
                <div style={{ color: 'red', marginTop: '10px' }}>
                    <h3>Validation Errors:</h3>
                    <ul>
                        {manualErrors.map((error, index) => (
                            <li key={index}>{error}</li>
                        ))}
                    </ul>
                </div>
            )}
        </div>
    );
};

export default ManualEntryForm;