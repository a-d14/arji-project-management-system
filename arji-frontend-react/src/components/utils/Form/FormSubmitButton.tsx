import styles from '../Buttons/Buttons.module.css';

const FormSubmitButton : React.FC<{children: React.ReactNode}> = ({children}) => {
    return <button type="submit" className={styles['submit']}>{children}</button>
}

export default FormSubmitButton;