import './Login.css'
import {useState} from "react";

function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [emailError, setEmailError] = useState(false);
    const [passwordError, setPasswordError] = useState(false);

    const PasswordErrorMessage = () => {
        return (
            <p className="FieldError">A jelszó nem lehet üres</p>
        );
    };

    const EmailIsNotFoundError = () => {
        return (
            <p className="FieldError">Ezzel az email címmel nincs regisztrált felhasználó!</p>
        )
    }

    const clearForm = () => {
        setEmail("");
        setPassword("");
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        const loginData = {
            email: email,
            password: password,
        };
        fetch("http://localhost:8080/login", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginData),
        })
            .then(response => {
                if (response.status === 406) {
                    if (response.statusMessage === "email") {
                        setEmailError(true);
                    } else if (response.statusMessage === "password") {
                        setPasswordError(true);
                    }
                }
                return response.json();
            })
            .then(data => {
                console.log("User logged in", data);
            })
            .catch(error => console.error("Login failed"));
        clearForm();
    }

    return (
        <div className="Login">
            <form onSubmit={handleSubmit}>
                <fieldset>
                    <h2 className="Title">Bejelentkezés</h2>
                    <div className="Field">
                        <label>
                            Email cím
                        </label>
                        <input
                            value={email}
                            onChange={(e) => {
                                setEmail(e.target.value);
                            }}
                            placeholder="Email cím"
                        />
                        {emailError ? (
                            <EmailIsNotFoundError/>
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
                                setPassword(e.target.value);
                            }}
                            placeholder="Jelszó"
                        />
                        {passwordError ? (
                            <PasswordErrorMessage/>
                        ) : null}
                    </div>
                    <button type="submit">
                        Regisztráció
                    </button>
                </fieldset>
            </form>
        </div>
    );
}

export default Login;

