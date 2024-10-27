interface HttpStatusCode {
    error: boolean;
    clientError4xx: boolean;     // Changed from '4xxClientError'
    serverError5xx: boolean;     // Changed from '5xxServerError'
    informational1xx: boolean;    // Changed from '1xxInformational'
    successful2xx: boolean;       // Changed from '2xxSuccessful'
}