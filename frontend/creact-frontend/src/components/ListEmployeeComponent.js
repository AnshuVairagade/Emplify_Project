import React, {useState,useEffect} from 'react'
import EmployeeService from '../Services/EmployeeService'
import {Link} from "react-router-dom";

const ListEmployeeComponent = () => {

    const [employees, setEmployees] = useState([])

    const displayAllEmployees =()=>{
        EmployeeService.getAllEmployees().then((Response)=>{
            setEmployees(Response.data)
        }).catch(err => {console.log(err)})
    }

    useEffect(() => {
        displayAllEmployees()
    }, [])
    

    const deleteEmployee=(id)=>{
        EmployeeService.deleteEmployee(id).then((res)=>{
            displayAllEmployees();
        }).catch((err)=>{console.log(err)})
    }

    return (
        <div className='container'>
            <h2 className='text-center'>List Employees</h2>
            <Link to="/add-employee" className='btn btn-primary mb-2'>Add Employee</Link>
            <table className='table table-bordered table-striped'>
                <thead className='text-center'>
                    <th>Employee Id</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email ID</th>
                    <th>Actions</th>
                </thead>
                <tbody>{
                    employees.map(
                    employee => 
                    <tr key = {employee.id}>
                        <td className='text-center'>{employee.id}</td>
                        <td className='text-center'>{employee.firstname}</td>
                        <td className='text-center'>{employee.lastname}</td>
                        <td className='text-center'>{employee.email}</td>
                        <td className='text-center'>
                            <Link to ={`/edit-employee/${employee.id }`} style={{marginRight:"25px"}} 
                            className='btn btn-info'>Update</Link>
                            <button onClick={()=> deleteEmployee(employee.id)} className='btn btn-danger'>Delete</button>
                        </td>
                    </tr>
                    )
                    }
                </tbody>
            </table>
        </div>
    )
}

export default ListEmployeeComponent
