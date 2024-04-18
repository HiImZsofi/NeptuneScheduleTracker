import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './registration.css'
import { useEffect, useState } from "react";
import {validateEmail} from "../utils/validation";

function Registration(){
    const [user, setUser] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState({
        value: "",
        isTouched: false,
    });
    const [confPassword, setConfPassword] = useState({
        value: "",
        isTouched: false,
    });

    const PasswordErrorMessage = () => {
        return (
            <p className="FieldError">A jelszó nem lehet üres</p>
        );
    };

    const PasswordValidationError = () => {
        return (
            <p className="FieldError">A két jelszó nem egyezik!</p>
        )
    }

    const getIsFormValid = () => {
        return (firstName && validateEmail(email) && password.value.length > 0 && confPassword.value.length > 0 && password.value === confPassword.value);
    };

    const clearForm = () => {
        setFirstName("");
        setLastName("");
        setEmail("");
        setPassword({
            value: "",
            isTouched: false,
        });
        setConfPassword({
            value: "",
            isTouched: false,
        });
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        const userData = {
            firstName: firstName,
            lastName: lastName,
            email: email,
            password: password.value,
        };
        console.log("UserData:", userData);
        fetch('http://localhost:8080/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userData),
        })
            .then(response => response.json())
            .then(data => {
                console.log('User created:', data);
            })
            .catch(error => console.error('Error creating user:', error));
        clearForm();
    };

    return(
        <div className="Registration">
            <form onSubmit={handleSubmit}>
                <fieldset>
                    <h2 className="Title">Sign Up</h2>
                    <div className="Field">
                        <label>
                            First name <sup>*</sup>
                        </label>
                        <input
                            value={firstName}
                            onChange={(e) => {
                                setFirstName(e.target.value);
                            }}
                            placeholder="First name"
                        />
                    </div>
                    <div className="Field">
                        <label>Last name</label>
                        <input
                            value={lastName}
                            onChange={(e) => {
                                setLastName(e.target.value);
                            }}
                            placeholder="Last name"
                        />
                    </div>
                    <div className="Field">
                        <label>
                            Email address <sup>*</sup>
                        </label>
                        <input
                            value={email}
                            onChange={(e) => {
                                setEmail(e.target.value);
                            }}
                            placeholder="Email address"
                        />
                    </div>
                    <div className="Field">
                        <label>
                            Password <sup>*</sup>
                        </label>
                        <input
                            value={password.value}
                            type="password"
                            onChange={(e) => {
                                setPassword({ ...password, value: e.target.value });
                            }}
                            onBlur={() => {
                                setPassword({ ...password, isTouched: true });
                            }}
                            placeholder="Password"
                        />
                        {password.isTouched && password.value.length === 0 ? (
                            <PasswordErrorMessage />
                        ) : null}
                    </div>
                    <div className="Field">
                        <label>
                            Confirm Password <sup>*</sup>
                        </label>
                        <input
                            value={confPassword.value}
                            type="password"
                            onChange={(e) => {
                                setConfPassword({...confPassword, value: e.target.value});
                            }}
                            onBlur={() => {
                                setConfPassword({...confPassword, isTouched: true});
                            }}
                            placeholder="Password confirmation"
                        />
                        {confPassword.isTouched && confPassword.value.length === 0 ? (
                            <PasswordErrorMessage />
                        ) : null}
                        {confPassword.value !== password.value && confPassword.value.length !== 0 ? (
                            <PasswordValidationError />
                        ) : null}
                    </div>
                    <button type="submit" disabled={!getIsFormValid()}>
                        Create account
                    </button>
                </fieldset>
            </form>
        </div>
    );
}

export default Registration;