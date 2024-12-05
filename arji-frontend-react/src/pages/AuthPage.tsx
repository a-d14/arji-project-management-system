import AuthenticationForm from "../components/AuthenticationForm/AuthenticationForm";
import Card from "../components/utils/Card/Card";

import { useSearchParams } from "react-router-dom";

const LoginPage : React.FC = () => {

    const [searchParams] = useSearchParams();
    const isRegister = searchParams.get('mode') === 'register';

    return (
        <div className="auth-page">
            <div className="auth-page__logo">
                <h1>arji.</h1>
            </div>
            <div className="auth-page__form">
                <Card>
                    {!isRegister ? <h2>Login</h2> : <h2>Register</h2>}
                    <AuthenticationForm isRegister={isRegister} />
                </Card>
            </div>
        </div>
    )
}

export default LoginPage;