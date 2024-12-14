import { useState } from "react";
import AuthenticationForm from "../components/AuthenticationForm/AuthenticationForm";
import Card from "../components/utils/Card/Card";
import { login } from '../http.js';

import { useNavigate, useSearchParams } from "react-router-dom";
import ErrorDialog from "../components/utils/Dialogs/ErrorDialog.js";

const LoginPage : React.FC = () => {

    const [searchParams] = useSearchParams();
    const isRegister = searchParams.get('mode') === 'register';

    const [error, setError] = useState(null);

    const navigate = useNavigate();

    async function onSubmit(e) {
        e.preventDefault();

        const fd = new FormData(e.target);
        
        const formDataObject = Object.fromEntries(fd.entries());

        let response;

        if(isRegister) {
            
        } else {
            response = await login(formDataObject);
        }

        if(!response.ok) {
            setError({
                message: 'Invalid Credentials'
            });
            e.target.reset();
            return;
        }

        const data = await response.json();

        navigate("/", {state : data});

        // console.log(data);
    }

    return (
        <div className="auth-page">
            <div className="auth-page__logo">
                <h1>arji.</h1>
            </div>
            <div className="auth-page__form">
                <Card>
                    {!isRegister ? <h2>Login</h2> : <h2>Register</h2>}
                    {error && <ErrorDialog content={error.message}/>}
                    <AuthenticationForm isRegister={isRegister} onSubmit={onSubmit}/>
                </Card>
            </div>
        </div>
    )
}

export default LoginPage;