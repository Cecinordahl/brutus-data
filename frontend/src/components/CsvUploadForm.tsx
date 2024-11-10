import React from 'react';

interface CsvUploadFormProps {
    handleFileChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    handleUploadCsv: () => void;
    csvErrors: string[];
}

const CsvUploadForm: React.FC<CsvUploadFormProps> = ({ handleFileChange, handleUploadCsv, csvErrors }) => (
    <div>
        <h2>Upload CSV</h2>
        <input type="file" onChange={handleFileChange} />
        <button onClick={handleUploadCsv}>Upload CSV</button>
        {csvErrors.length > 0 && (
            <div style={{ color: 'red', marginTop: '10px' }}>
                <h3>Validation Errors:</h3>
                <ul>
                    {csvErrors.map((error, index) => (
                        <li key={index}>{error}</li>
                    ))}
                </ul>
            </div>
        )}
    </div>
);

export default CsvUploadForm;