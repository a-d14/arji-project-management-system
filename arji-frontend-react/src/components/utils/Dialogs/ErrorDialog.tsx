import styles from './ErrorDialog.module.css';

const ErrorDialog : React.FC = function({content}) {
    return (
        <div className={styles['error']}>
            <h3>Error!</h3>
            <p>{content}</p>
        </div>
    )
}

export default ErrorDialog;