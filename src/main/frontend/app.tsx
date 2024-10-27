import React, { useEffect, useState } from 'react';
import type {DoctorDto} from "Frontend/interfaces/dto"
import axios from 'axios';
import TableData from './components/tableData';
import { Notification } from '@vaadin/react-components';

interface SearchCriteria {
    doctorName: string;
    days: number[];
    time: string;
}

const App: React.FC = () => {
    const [doctors, setDoctors] = useState<DoctorDto[]>([]);
    const [showConfirmModal, setShowConfirmModal] = useState(false);
    const [selectedDoctorId, setSelectedDoctorId] = useState<number | null>(null);
    const [searchCriteria, setSearchCriteria] = useState<SearchCriteria>({
        doctorName: '',
        days: [],
        time: '',
    });

    const handleDayChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        const selectedDays = Array.from(event.target.selectedOptions, (option) => parseInt(option.value));
        setSearchCriteria({ ...searchCriteria, days: selectedDays });
    };

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;
        setSearchCriteria({ ...searchCriteria, [name]: value });
    };

    const searchDoctors = async () => {
        try {
            const response = await axios.post('http://localhost:9000/api/v1/doctor/fetch', searchCriteria, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            setDoctors(response.data);
        } catch (error) {
            console.error('Error fetching doctors:', error);
        }
    };

    const resetData = ()=>{
        axios.get('http://localhost:9000/api/v1/doctor/get')
            .then(response => {
                setDoctors(response.data);
        })
        .catch(error => {
            console.error("There was an error fetching the doctors!", error);
        });
    }

    const handleDeleteClick = (doctorId: number) => {
        setSelectedDoctorId(doctorId);
        setShowConfirmModal(true);
    };

    const handleDeleteAvailability = () => {
        if (selectedDoctorId) {
            axios.delete(`http://localhost:9000/api/v1/doctor/delete-availability/${selectedDoctorId}`)
                .then(response => {
                    const notification = Notification.show(response.data.message, {
                        position: 'top-end',
                        duration: 7000,
                        theme: 'success',
                    });
                    setShowConfirmModal(false);
                })
                .catch(error => {
                    const notification = Notification.show(error.data.message, {
                        position: 'top-end',
                        duration: 7000,
                        theme: 'error',
                    });
                });
        }
    };

    useEffect(() => {
        resetData();
    }, []);

    return (
        <div className="container mt-5">
            <div className="mb-4">
                <h2>Search Doctors</h2>
                <div className="d-flex flex-wrap justify-content-around">
                    <div className="form-group col-md-4 px-2">
                        <input
                            type="text"
                            name="doctorName"
                            className="form-control"
                            placeholder="Doctor Name"
                            value={searchCriteria.doctorName}
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="form-group col-md-4 px-2">
                        <select multiple className="form-control" onChange={handleDayChange}>
                            <option value="1">Monday</option>
                            <option value="2">Tuesday</option>
                            <option value="3">Wednesday</option>
                            <option value="4">Thursday</option>
                            <option value="5">Friday</option>
                            <option value="6">Saturday</option>
                            <option value="7">Sunday</option>
                        </select>
                    </div>
                    <div className="form-group col-md-4 px-2">
                        <input
                            type="time"
                            name="time"
                            className="form-control"
                            value={searchCriteria.time}
                            onChange={handleInputChange}
                            step="1" 
                            required 
                        />
                    </div>
                </div>
                <div className="d-flex justify-content-end w-100">
                    <button className="btn btn-success" onClick={searchDoctors}>
                        Search
                    </button>
                    <button className="btn btn-danger mx-2" onClick={resetData}>
                        Reset
                    </button>
                </div>
            </div>

            <TableData doctors={doctors} onDeleteClick={handleDeleteClick} />

            <div className={`modal ${showConfirmModal ? 'show' : ''}`} style={{ display: showConfirmModal ? 'block' : 'none' }}>
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header d-flex justify-content-between">
                            <h5 className="modal-title">Confirm Deletion</h5>
                            <button type="button" className="fs-4 btn " onClick={() => setShowConfirmModal(false)}>
                                &times;
                            </button>
                        </div>
                        <div className="modal-body">
                            <p>Are you sure you want to delete all appointments?</p>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" onClick={() => setShowConfirmModal(false)}>
                                Cancel
                            </button>
                            <button type="button" className="btn btn-danger" onClick={handleDeleteAvailability}>
                                Delete
                            </button>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    );
};

export default App;
