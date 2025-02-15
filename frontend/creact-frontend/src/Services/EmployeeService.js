import axios from "axios";

const EMPLOYEE_BASE_REST_API = "http://localhost:8080/api/v1/employees";

class EmployeeService{
    getAllEmployees(){
        return axios.get(EMPLOYEE_BASE_REST_API)
    }

    createEmployee(employee){
        return axios.post(EMPLOYEE_BASE_REST_API, employee)
    }

    getEmployeeByID(id){
        return axios.get(EMPLOYEE_BASE_REST_API + '/'+id)
    }

    updateEmployees(id, employee){
        return axios.put(EMPLOYEE_BASE_REST_API+'/'+id, employee)
    }

    deleteEmployee(id){
        return axios(EMPLOYEE_BASE_REST_API+'/'+id)
    }
}

export default new EmployeeService();