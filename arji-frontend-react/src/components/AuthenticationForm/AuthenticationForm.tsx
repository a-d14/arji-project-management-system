import { Link } from "react-router-dom";
import Form from "../utils/Form/Form";

const AuthenticationForm : React.FC<{isRegister : boolean}> = ({ isRegister }) => {

    return (
        <Form>
            <Form.FormControls>
                <label>Enter email</label>
                <input type="email" required />
            </Form.FormControls>
            <Form.FormControls>
                <label>Enter password</label>
                <input type="password" required />
            </Form.FormControls>
            <div className="">
                <button>{isRegister ? 'Register' : 'Login'}</button>
                <Link to={isRegister ? '/auth' : '?mode=register'}>
                    {isRegister ? 'Login' : 'Register New User'}
                </Link>
            </div>
        </Form>
    )

}

export default AuthenticationForm;