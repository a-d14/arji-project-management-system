import { useLocation } from "react-router-dom";

const HomePage : React.FC = () => {

    const location = useLocation();

    // console.log(location.state);

    return (
        <>
            <h1>Hello {location.state.username}</h1>
        </>
    )
}

export default HomePage;