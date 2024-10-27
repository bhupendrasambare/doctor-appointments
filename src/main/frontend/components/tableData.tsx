// src/components/Table.tsx

import React, { useEffect, useState } from 'react';
import type {DoctorDetails, DoctorDto} from "Frontend/interfaces/dto"
import { ImCross } from 'react-icons/im';
import { FaHospital, FaPen } from 'react-icons/fa';
import { Button, Modal, Table } from 'react-bootstrap';
import axios from 'axios';
import { Notification } from '@vaadin/react-components';
import { FaUserDoctor } from 'react-icons/fa6';
import UpdateAppointment from 'Frontend/components/updateAppointment';

interface TableProps {
    doctors: DoctorDto[];
    onDeleteClick: (doctorId: number) => void;
}

const TableData: React.FC<TableProps> = ({ doctors, onDeleteClick }) => {

    const [showDetails, setShowDetails] = useState(false);
    const [showDetailUpdate, setShowDetailUpdate] = useState(false);
    const [doctorDetails,setDoctorDetails] = useState<DoctorDetails|null>(null)
    const [doctorList, setDoctorList] = useState<DoctorDto[]>([]);

    const checkDoctorDetails = (id:number|null|undefined,showUpdate:boolean)=>{
        if(showUpdate){
            setShowDetails(!showDetails);
        }
        if(id!=null && id!=undefined){
            axios.get('http://localhost:9000/api/v1/doctor/get/'+id)
                .then(response => {
                    setDoctorDetails(response.data.data);
            })
            .catch(error => {
                const notification = Notification.show(error.data.message, {
                    position: 'top-end',
                    duration: 7000,
                    theme: 'error',
                });
            });
        }
    }

    const showUpdateModal = (id:number|null) =>{
        if(id!=null){
            axios.get('http://localhost:9000/api/v1/doctor/get/'+id)
                .then(response => {
                    setDoctorDetails(response.data.data);
                    setShowDetailUpdate(!showDetailUpdate);
            })
            .catch(error => {
                const notification = Notification.show(error.data.message, {
                    position: 'top-end',
                    duration: 7000,
                    theme: 'error',
                });
            });
        }else{
            setShowDetailUpdate(!showDetailUpdate);
        }
    }

    const onCloseUpdate = () =>{
        setShowDetailUpdate(!showDetailUpdate);
    }

    return (
        <div>           
<div
      className="modal show"
      style={{ display: 'block', position: 'initial' }}
    >

        {/* Update details of the doctor availablity */}
        <Modal size='xl' show={showDetailUpdate} onHide={()=>setShowDetailUpdate(!showDetailUpdate)}>
            <Modal.Body>
                <h3 className="fs-4 my-2">
                    {doctorDetails?
                    <>Add appointment details</>:
                    <>Update appointment details</>}
                </h3>
                <hr/>
                <div className="py-4 container">
                    
                        <div className="my-3">
                            <div className="cotainer d-flex">
                                <div className="fs-4 fw-bold">Doctor</div>
                                <select style={{width:200}} className='form-control mx-3' 
                                onChange={(e)=>checkDoctorDetails(Number(e.target.value),false)}>
                                    {doctors != null && doctors.length > 0 && doctors.map((doctor) => (
                                        <option key={doctor.doctorName} value={doctor.doctorId}>
                                            {doctor.doctorName}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            {
                                doctorDetails && doctorDetails.doctorId &&
                                <UpdateAppointment appointments={doctorDetails.appointments} doctorId={doctorDetails.doctorId} onCloseUpdate={onCloseUpdate}  />
                            }
                        </div>
                        <div className="d-flex justify-content-end" onClick={()=>setShowDetailUpdate(!showDetailUpdate)}>
                            <Button className='btn btn-danger'>Close</Button>
                        </div>
                </div>
            </Modal.Body>
        </Modal>

        {/* Show details of the doctor availablity */}
        <Modal size='xl' show={showDetails} onHide={()=>setShowDetails(!showDetails)}>
            <Modal.Body>
                <h3 className="fs-4 my-2">
                    <div className="d-flex justify-content-between">
                        <div id="doctor-details">
                            <FaUserDoctor className='text-primary mx-2 fs-3'/>
                            <span className="fw-bold">{doctorDetails?.doctorName}</span>
                        </div>
                        <div id="hospital-details">
                            <FaHospital className='mx-2 text-success fs-3'/>
                            <span className="fw-bold fs-5">{doctorDetails?.hospitalName}</span>
                        </div>
                    </div>
                </h3>
                <div className="py-2 container">
                    <div className="container mt-4 mb-2">
                        <h3 className='fs-4 fw-bold'>Time Availability</h3>
                        <Table striped bordered hover responsive>
                            <thead>
                                <tr>
                                    <th>Day</th>
                                    <th>Status</th>
                                    <th>Start Time</th>
                                    <th>End Time</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    (doctorDetails?.appointments?.length!= null && doctorDetails.appointments.length>0)?
                                    <>
                                        {doctorDetails?.appointments.map((entry, index) => (
                                            <tr key={index}>
                                                <td>{['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'][entry.days]}</td>
                                                <td className='fs-6'>{entry.openStatus ? 
                                                    <><div className="btn btn-sm mx-2 btn-success rounded-pill">Open</div></>
                                                     : <><div className="btn btn-sm mx-2 btn-danger rounded-pill">Close</div></>}</td>
                                                <td>{entry.openStatus ? entry.startTime: "-"}</td>
                                                <td>{entry.openStatus ? entry.endTime: "-"}</td>
                                            </tr>
                                        ))}
                                    </>
                                    :
                                    <>
                                        <tr>
                                            <td colSpan={4} className="text-center">No data found</td>
                                        </tr>
                                    </>
                                }
                            </tbody>
                        </Table>
                        <div className="d-flex justify-content-end">
                            <Button onClick={()=>setShowDetails(!showDetails)} className='btn btn-danger'>Close</Button>
                        </div>
                    </div>
                </div>
            </Modal.Body>
        </Modal>

        </div>
            <div className="card">
                <div className="card-body">
                    <div className="d-flex justify-content-between">
                        <h5 className="card-title">Doctor List</h5>
                        <div className="card-title">
                            <div className="btn btn-success" onClick={()=>showUpdateModal(null)}>Add Appointment Time</div>
                        </div>
                    </div>
                    <table className="table table-striped">
                        <thead>
                            <tr className='fw-bold'>
                                <th>Doctor Name</th>
                                <th>Hospital Address</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {doctors.map((doctor) => (
                                <tr key={doctor.doctorId}>
                                    <td onClick={()=>checkDoctorDetails(doctor.doctorId,true)}>{doctor.doctorName}</td>
                                    <td>{doctor.hospitalAddress}</td>
                                    <td style={{width:150}}>
                                        <div className="d-flex">
                                            <FaPen className='text-primary ms-3' onClick={()=>showUpdateModal(doctor.doctorId)}/>
                                            <ImCross onClick={() => onDeleteClick(doctor.doctorId)} className='text-danger ms-3' />
                                        </div>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default TableData;
