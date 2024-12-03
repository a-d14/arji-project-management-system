import styles from './Form.module.css';

import React, { ReactNode } from "react";

const Form : React.FC<{children: ReactNode}> = ({children}) => {
    return (
        <form className={styles.form}>
            {children}
        </form>
    )
}

export default Form;
