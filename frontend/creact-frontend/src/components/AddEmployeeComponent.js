import React, { useState,  useEffect } from 'react'
import EmployeeService from '../Services/EmployeeService'
import {Link, useNavigate, useParams} from 'react-router-dom'

const AddEmployeeComponent = () => {

    const [firstname, setFirstname] = useState('')
    const [lastname, setLastname] = useState('')
    const [email, setEmail] = useState('')
    const navigate = useNavigate();
    const {id} = new useParams();

    const saveOrUpdateEmployee=(e) =>{
        e.preventDefault()  

        const employee = {firstname, lastname, email}

        if(id){
            EmployeeService.updateEmployees(id, employee).then((res)=>{
                navigate('/employees');
            }).catch(err =>{console.log(err)})
        }
        else{   
            EmployeeService.createEmployee(employee).then((res)=>{
                console.log(res.data)
                navigate('/employees');
            }).catch(err => {console.log(err)})
        }
    }

    useEffect(() => {
        EmployeeService.getEmployeeByID(id).then((res)=>{
            setFirstname(res.data.firstname)
            setLastname(res.data.lastname)
            setEmail(res.data.email)
        }).catch(err => {
            console.log(err)
        })
    }, [])

    const changeTitle =()=>{
        if(id){
            return <h2 className='text-center'>Update Employee</h2>
        }
        else{
            return <h2 className='text-center'>Add Employee</h2>
        }
    }

    return (
        <div>
            <div className='container' style={{paddingTop:"100px"}}>
                <div className='row' style={{marginBottom: "120px"}}>
                    <div className='card col-md-6 offset-md-3 border-2 border-secondary pt-3'>
                        {changeTitle()}
                        <div className='crad-body'>
                            <form className='p-3'>
                                <div className='form-group mb-2'>
                                    <label className='form-label' style={{ paddingRight: "10px", 
                                    fontWeight: "bold" }}> First Name : </label>
                                    <input type='text' placeholder='Enter your First Name' name='firstname' 
                                    value={firstname} className='form-control border border-secondary ' 
                                    onChange={(e) => setFirstname(e.target.value)} />
                                </div>
                                <div className='form-group mb-2'>
                                    <label className='form-label' style={{ paddingRight: "10px", 
                                    fontWeight: "bold" }}> Last Name : </label>
                                    <input type='text' placeholder='Enter your Last Name' name='lastname' 
                                    value={lastname} className='form-control border border-secondary ' 
                                    onChange={(e) => setLastname(e.target.value)} />
                                </div>
                                <div className='form-group mb-2'>
                                    <label className='form-label' style={{ paddingRight: "10px", 
                                    fontWeight: "bold" }}> Email ID : </label>
                                    <input type='email' placeholder='Enter your Email ID' name='email' 
                                    value={email} className='form-control border border-secondary ' 
                                    onChange={(e) => setEmail(e.target.value)} />
                                </div>
                                <br></br>
                                <button className='btn btn-success' onClick={(e)=>saveOrUpdateEmployee(e)}>
                                    Save Employee</button>
                                <Link to='/employees' className='btn btn-danger' 
                                style={{marginLeft:"15px"}}>Cancel</Link>
                                <br></br>
                                <br></br>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default AddEmployeeComponent
