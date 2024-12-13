import React from "react";

import FormControlComponent from "./FormControlComponent";
import FormSubmitButton from "./FormSubmitButton";


interface FormComponent extends React.FC<{children : React.ReactNode | JSX.Element | JSX.Element[], onSubmit : React.FormEventHandler}> {
    FormControls : typeof FormControlComponent;
    FormButton: typeof FormSubmitButton;
}

const Form : FormComponent = ({children, onSubmit}) => {
    return (
        <form onSubmit={onSubmit}>
            {children}
        </form>
    )
}

Form.FormControls = FormControlComponent;
Form.FormButton = FormSubmitButton;

export default Form;