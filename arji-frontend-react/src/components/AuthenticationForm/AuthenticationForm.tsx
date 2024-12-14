import { Link } from "react-router-dom";
import Form from "../utils/Form/Form";
import FormSubmitButton from "../utils/Form/FormSubmitButton";

import styles from './AuthenticationForm.module.css';

const AuthenticationForm : React.FC<{isRegister : boolean}> = ({ isRegister, onSubmit }) => {

    return (
        <Form onSubmit={onSubmit}>
            {isRegister && <Form.FormControls>
                <label>Enter email</label>
                <input name="email" type="email" required />
            </Form.FormControls>}
            <Form.FormControls>
                <label>Enter username</label>
                <input name="username" type="text" required />
            </Form.FormControls>
            <Form.FormControls>
                <label>Enter password</label>
                <input name="password" type="password" required />
            </Form.FormControls>
            <div className={styles['buttons']}>
                <FormSubmitButton>{isRegister ? 'Register' : 'Login'}</FormSubmitButton>
                <Link to={isRegister ? '/auth' : '?mode=register'}>
                    {isRegister ? 'Login' : 'Register New User'}
                </Link>
            </div>
        </Form>
    )

}

export default AuthenticationForm;