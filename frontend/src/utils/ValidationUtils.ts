export const validateUserData = (userData: any) => {
    const errorMessages: string[] = [];

    if (!userData.firstName.trim()) errorMessages.push("First Name is required.");
    if (!userData.lastName.trim()) errorMessages.push("Last Name is required.");
    if (!userData.age || isNaN(Number(userData.age)) || Number(userData.age) <= 0) {
        errorMessages.push("Age must be a valid, positive number.");
    }
    if (!userData.street.trim()) errorMessages.push("Street is required.");
    if (!userData.city.trim()) errorMessages.push("City is required.");
    if (!/^[a-zA-Z]{2}$/.test(userData.state.trim()) || !userData.state.trim()) {
        errorMessages.push("State must be exactly two letters.");
    }
    if (!userData.latitude.trim() || isNaN(Number(userData.latitude)) || Number(userData.latitude) < -90 || Number(userData.latitude) > 90) {
        errorMessages.push("Latitude must be between -90 and 90.");
    }
    if (!userData.longitude.trim() || isNaN(Number(userData.longitude)) || Number(userData.longitude) < -180 || Number(userData.longitude) > 180) {
        errorMessages.push("Longitude must be between -180 and 180.");
    }
    if (!/^\d{15,16}$/.test(userData.ccnumber)) {
        errorMessages.push("Credit card number must be 15-16 digits.");
    }

    return errorMessages;
};

export const validateCsvRow = (columns: string[], index: number) => {
    const errorMessages: string[] = [];
    if (columns.length !== 9) {
        errorMessages.push(`Row ${index + 1} does not have the required 9 fields.`);
        return errorMessages;
    }

    const [, , , , , , latitude, longitude, ccnumber] = columns;

    if (isNaN(Number(latitude)) || Number(latitude) < -90 || Number(latitude) > 90) {
        errorMessages.push(`Row ${index + 1}: Latitude must be between -90 and 90.`);
    }
    if (isNaN(Number(longitude)) || Number(longitude) < -180 || Number(longitude) > 180) {
        errorMessages.push(`Row ${index + 1}: Longitude must be between -180 and 180.`);
    }

    const sanitizedCcNumber = ccnumber.replace(/\D/g, '');
    if (!/^\d{15,16}$/.test(sanitizedCcNumber)) {
        errorMessages.push(`Row ${index + 1}: Credit card number must be 15-16 digits.`);
    }

    return errorMessages;
};