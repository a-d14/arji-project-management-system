import { Link } from "react-router-dom";

const AuthenticationForm : React.FC<{isRegister : boolean}> = ({ isRegister }) => {

    return (
        <form method="post">
            <div className="form-controls">
                <label>Enter email</label>
                <input type="email" required />
            </div>
            <div className="form-controls">
                <label>Enter password</label>
                <input type="password" required />
            </div>
            <button>{isRegister ? 'Register' : 'Login'}</button>
            <Link to={isRegister ? '/auth' : '?mode=register'}>
                {isRegister ? 'Login' : 'Register New User'}
            </Link>
        </form>
    )

}

export default AuthenticationForm;