import { Notification } from "@vaadin/react-components";
import axios, { AxiosError } from "axios";
import { DoctorAvailiablity, ErrorResponse } from "Frontend/interfaces/dto"; // Assuming this interface exists and defines the fields appropriately
import React, { useState, useEffect } from 'react';
import { Form, Button, Row, Col, Container } from 'react-bootstrap';

interface UpdateAppointmentProps {
    appointments: DoctorAvailiablity[];
    doctorId: number;
    onCloseUpdate: () => void;
}

const UpdateAppointment: React.FC<UpdateAppointmentProps> = ({ appointments, doctorId,onCloseUpdate }) => {

    const [availability, setAvailability] = useState<DoctorAvailiablity[]>([]);

    useEffect(() => {
        if (appointments?.length > 0) {
            var updatedAppointments = appointments.map(appointment => ({
                ...appointment,
                startTime: appointment.openStatus ? appointment.startTime : undefined, // Use undefined instead of null
                endTime: appointment.openStatus ? appointment.endTime : undefined,     // Use undefined instead of null
            }));
            setAvailability(updatedAppointments);
        } else {
            var defaultdata: DoctorAvailiablity[] = Array.from({ length: 7 }, (_, index) => ({
                timeId: 0, // Ensure to provide a default value for timeId if it's required
                days: index + 1,
                openStatus: false,
                startTime: undefined, // Use undefined instead of null
                endTime: undefined,   // Use undefined instead of null
            }));
            setAvailability(defaultdata);
        }
    }, [appointments]);

    const handleCheckboxChange = (index: number) => {
        const updatedAvailability = [...availability];
        updatedAvailability[index].openStatus = !updatedAvailability[index].openStatus;
        if (!updatedAvailability[index].openStatus) {
            updatedAvailability[index].startTime = '09:00';
            updatedAvailability[index].endTime = '16:00';
        }
        setAvailability(updatedAvailability);
    };

    const handleTimeChange = (index: number, field: 'startTime' | 'endTime', value: string) => {
        const updatedAvailability = [...availability];
        updatedAvailability[index][field] = value;
        setAvailability(updatedAvailability);
    };

    const handleSubmit = async (e: React.FormEvent) => {
        onCloseUpdate();
        e.preventDefault();
        try {
            const response = await axios.post(`http://localhost:9000/api/v1/doctor/update-availability/${doctorId}`, availability, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
    
            Notification.show(response.data.message, {
                position: 'top-end',
                duration: 7000,
                theme: 'success',
            });
        } catch (error) {
            const axiosError = error as AxiosError;
    
            // Safely access the response data using the defined interface
            const errorResponse = axiosError.response?.data as ErrorResponse;
            const errorMessage = errorResponse?.message || 'An error occurred';
            Notification.show(errorMessage, {
                position: 'top-end',
                duration: 7000,
                theme: 'error',
            });
        }
    };

    return (
        <Container>
            <h3 className="my-4">Update Doctor Availability</h3>
            <Form onSubmit={handleSubmit}>
                {availability.map((day, index) => (
                    <Row className="mb-3" key={day.days}>
                        <Col xs={2} className="d-flex align-items-center">
                            <Form.Label>{['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][index]}</Form.Label>
                        </Col>
                        <Col xs={2}>
                            <Form.Check
                                type="checkbox"
                                label="Open"
                                checked={day.openStatus}
                                onChange={() => handleCheckboxChange(index)}
                            />
                        </Col>
                        <Col xs={4}>
                            <Form.Control
                                type="time"
                                disabled={!day.openStatus}
                                value={day.startTime}
                                onChange={(e) => handleTimeChange(index, 'startTime', e.target.value)}
                            />
                        </Col>
                        <Col xs={4}>
                            <Form.Control
                                type="time"
                                disabled={!day.openStatus}
                                value={day.endTime}
                                onChange={(e) => handleTimeChange(index, 'endTime', e.target.value)}
                            />
                        </Col>
                    </Row>
                ))}
                <Button variant="primary" type="submit" className="mt-4">
                    Update Availability
                </Button>
            </Form>
        </Container>
    );
}

export default UpdateAppointment;