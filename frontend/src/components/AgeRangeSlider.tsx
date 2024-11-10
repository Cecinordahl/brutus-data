import React from 'react';
import Slider from '@mui/material/Slider';
import '../styles/AgeRangeSlider.css';

interface AgeRangeSliderProps {
    ageRange: number[];
    onChange: (newValue: number[]) => void;
}

const AgeRangeSlider: React.FC<AgeRangeSliderProps> = ({ ageRange, onChange }) => {
    const handleChange = (event: Event, newValue: number | number[]) => {
        onChange(newValue as number[]);
    };

    return (
        <div className="age-range-slider">
            <p>Current Age Range: {ageRange[0]} - {ageRange[1]}</p>
            <Slider
                value={ageRange}
                onChange={handleChange}
                valueLabelDisplay="auto"
                min={16}
                max={75}
                marks
            />
        </div>
    );
};

export default AgeRangeSlider;