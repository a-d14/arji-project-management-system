import styles from './FormControlComponent.module.css';

const FormControlComponent : React.FC<{children: React.ReactNode}> = ({children}) => {
    return (
        <div className={styles['form-controls']}>
            {children}
        </div>
    )
}

export default FormControlComponent;