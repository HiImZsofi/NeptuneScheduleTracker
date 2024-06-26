import './Registration.css'
import {useState} from "react";
import {validateEmail} from "../utils/validation";
import {Router, useNavigate} from "react-router-dom";

function Registration() {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [isEmailFree, setEmailFree] = useState(true);
    const [password, setPassword] = useState({
        value: "",
        isTouched: false,
    });
    const [confPassword, setConfPassword] = useState({
        value: "",
        isTouched: false,
    });
    const navigate = useNavigate();

    function SwitchToLogin(){
        navigate("/login");
    }

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

    const EmailIsNotFreeError = () => {
        return (
            <p className="FieldError">Ez az email cím már foglalt!</p>
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
            .then(response => {
                if (response.status === 409) {
                    setEmailFree(false);
                }
                return response.json();
            })
            .then(data => {
                setEmailFree(true);
                console.log('User created:', data);
            })
            .catch(error => console.error('Error creating user:', error));
        clearForm();
    };

    return (
        <div className="Registration">
            <form onSubmit={handleSubmit}>
                <fieldset>
                    <h2 className="Title">Regisztráció</h2>
                    <div className="Field">
                        <label>
                            Keresztnév <sup>*</sup>
                        </label>
                        <input
                            value={firstName}
                            onChange={(e) => {
                                setFirstName(e.target.value);
                            }}
                            placeholder="Keresztnév"
                        />
                    </div>
                    <div className="Field">
                        <label>Vezetéknév</label>
                        <input
                            value={lastName}
                            onChange={(e) => {
                                setLastName(e.target.value);
                            }}
                            placeholder="Vezetéknév"
                        />
                    </div>
                    <div className="Field">
                        <label>
                            Email cím <sup>*</sup>
                        </label>
                        <input
                            value={email}
                            onChange={(e) => {
                                setEmail(e.target.value);
                            }}
                            placeholder="Email cím"
                        />
                        {!isEmailFree ? (
                            <EmailIsNotFreeError/>
                        ) : null}

                    </div>
                    <div className="Field">
                        <label>
                            Jelszó <sup>*</sup>
                        </label>
                        <input
                            value={password.value}
                            type="password"
                            onChange={(e) => {
                                setPassword({...password, value: e.target.value});
                            }}
                            onBlur={() => {
                                setPassword({...password, isTouched: true});
                            }}
                            placeholder="Jelszó"
                        />
                        {password.isTouched && password.value.length === 0 ? (
                            <PasswordErrorMessage/>
                        ) : null}
                    </div>
                    <div className="Field">
                        <label>
                            Jelszó mégegyszer <sup>*</sup>
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
                            placeholder="Megerősítés"
                        />
                        {confPassword.isTouched && confPassword.value.length === 0 ? (
                            <PasswordErrorMessage/>
                        ) : null}
                        {confPassword.value !== password.value && confPassword.value.length !== 0 ? (
                            <PasswordValidationError/>
                        ) : null}
                    </div>
                    <button type="submit" disabled={!getIsFormValid()}>
                        Regisztráció
                    </button>
                    <button type="button" onClick={SwitchToLogin} className="Redirect">
                        Van már fiókod? Jelentkezz be!
                    </button>
                </fieldset>
            </form>
        </div>
    );
}

export default Registration;