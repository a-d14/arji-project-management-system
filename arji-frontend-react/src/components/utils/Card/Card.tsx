import styles from './Card.module.css';

import React, { ReactNode } from "react";

const Card : React.FC<{children: ReactNode}> = ({children}) => {
    return (
        <div className={styles.card}>
            {children}
        </div>
    )
}

export default Card;
