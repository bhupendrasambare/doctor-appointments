# Doctor appoitments system

## Swagger URL : http://localhost:9000/swagger-ui/index.html

### Api to get all Doctors
```bash
curl -X 'GET' \
  'http://localhost:9000/api/v1/doctor/get' \
  -H 'accept: */*'
```
Sample Response
``` json
[
  {
    "doctorId": 1,
    "doctorName": "DR. John Doe",
    "hospitalId": 1,
    "hospitalName": "City Hospital",
    "hospitalAddress": "Line 12, Civil lines, Medical square, Nagpur Maharashtra"
  },
  {
    "doctorId": 2,
    "doctorName": "DR. Sanjay bansal",
    "hospitalId": 2,
    "hospitalName": "Government Hospital",
    "hospitalAddress": "Line 12, Government lines, Medical square, Nagpur Maharashtra"
  }
]
```


### Api to fetch doctors by Name | Days | Time
``` bash
curl -X 'POST' \
  'http://localhost:9000/api/v1/doctor/fetch' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "doctorName": "Dr.",
  "days": [1],
  "time": "13:00"
}'
```
Sample Response
``` json
[
  {
    "doctorId": 1,
    "doctorName": "DR. John Doe",
    "hospitalId": 1,
    "hospitalName": "City Hospital",
    "hospitalAddress": "Line 12, Civil lines, Medical square, Nagpur Maharashtra"
  },
  {
    "doctorId": 2,
    "doctorName": "DR. Sanjay bansal",
    "hospitalId": 2,
    "hospitalName": "Government Hospital",
    "hospitalAddress": "Line 12, Government lines, Medical square, Nagpur Maharashtra"
  }
]
```

### Get doctors appoitment by doctor ID
``` bash
curl -X 'GET' \
  'http://localhost:9000/api/v1/doctor/get/1' \
  -H 'accept: */*'
```
Sample Response
``` json
{
  "code": "200",
  "status": "SUCCESS",
  "message": "Doctor appointments found",
  "data": {
    "doctorId": 1,
    "doctorName": "DR. John Doe",
    "hospitalId": 1,
    "hospitalName": "City Hospital",
    "hospitalAddress": "Line 12, Civil lines, Medical square, Nagpur Maharashtra",
    "appointments": [
      {
        "timeId": 1,
        "days": 1,
        "openStatus": true,
        "startTime": "09:00:00",
        "endTime": "16:00:00"
      },
      {
        "timeId": 2,
        "days": 2,
        "openStatus": false,
        "startTime": null,
        "endTime": null
      },
      {
        "timeId": 3,
        "days": 3,
        "openStatus": true,
        "startTime": "09:00:00",
        "endTime": "17:00:00"
      },
      {
        "timeId": 4,
        "days": 4,
        "openStatus": true,
        "startTime": "09:00:00",
        "endTime": "18:00:00"
      },
      {
        "timeId": 5,
        "days": 5,
        "openStatus": false,
        "startTime": null,
        "endTime": null
      },
      {
        "timeId": 6,
        "days": 6,
        "openStatus": false,
        "startTime": null,
        "endTime": null
      },
      {
        "timeId": 7,
        "days": 7,
        "openStatus": false,
        "startTime": null,
        "endTime": null
      }
    ]
  }
}
```

### Delete doctors appoitment by doctor ID
``` bash
curl -X 'DELETE' \
  'http://localhost:9000/api/v1/doctor/delete-availability/4' \
  -H 'accept: */*'
```
Sample Response
``` json
{
  "code": "200",
  "status": "SUCCESS",
  "message": "Doctor appointments deleted",
  "data": null
}
```

### Update doctors appoitment by doctor ID
``` bash
curl -X 'POST' \
  'http://localhost:9000/api/v1/doctor/update-availability/4' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '[
  {
    "days": 1,
    "openStatus": true,
    "startTime": "09:00",
    "endTime": "18:00"
  }
]'
```
Sample Response
``` json
{
  "code": "200",
  "status": "SUCCESS",
  "message": "Doctors appointment updated successfully",
  "data": null
}
```