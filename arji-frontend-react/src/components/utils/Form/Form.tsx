import React from "react";

import styles from './Form.module.css';
import FormControlComponent from "./FormControlComponent";

interface FormComponent extends React.FC<{children : React.ReactNode | JSX.Element | JSX.Element[]}> {
    FormControls : typeof FormControlComponent;
}

const Form : FormComponent = ({children}) => {
    return (
        <form method="post" className={styles['form-container']}>
            {children}
        </form>
    )
}

Form.FormControls = FormControlComponent;

export default Form;