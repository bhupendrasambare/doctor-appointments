export interface DoctorDto {
    doctorId: number;
    doctorName?: string;
    hospitalId?: number;
    hospitalName?: string;
    hospitalAddress?: string;
}

export interface DoctorDetails{
    doctorId: number,
    doctorName: string,
    hospitalId: number
    hospitalName: string,
    hospitalAddress: string,
    appointments: DoctorAvailiablity[]
}

export interface DoctorAvailiablity {
    timeId: number;
    days: number;
    openStatus?: boolean;
    startTime?: string;
    endTime?: string;
}

export interface ErrorResponse {
    code: string;        // Error code (e.g., "405")
    status: string;      // Status of the response (e.g., "FAIL")
    message: string;     // Description of the error (e.g., "Invalid time for Tuesday")
    data: null;          // Data related to the error, which is null in this case
}