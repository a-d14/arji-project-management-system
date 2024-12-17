import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { NavLink, Outlet, useMatch, useNavigate } from "react-router-dom";

import { authActions } from '../store/index.js';

const HomePage : React.FC = () => {

    // const location = useLocation();

    const isRootPath = useMatch("/");
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const loggedInUser:string = useSelector(state => state.auth);
    
    useEffect(() => {
        if (loggedInUser.username === '') {
            navigate("/auth");
        }
    }, [loggedInUser, navigate]);

    function logout() {
        dispatch(authActions.logout());
        navigate("/auth");
    }

    return (
        loggedInUser.username !== '' && 
            (<div className="home-page">
                <nav className="sidebar">
                    <div className="user-details">
                        <img src="" alt="profile picture" />
                        <h3>{loggedInUser.username}</h3>
                    </div>
                    <ul className="menu">
                        <li><NavLink to="">Home</NavLink></li>
                        <li><NavLink to="projects">Projects</NavLink></li>
                        <li><NavLink to="tickets">Tickets</NavLink></li>
                    </ul>
                    <button onClick={logout}>Logout</button>
                </nav>
                <div className="page-content">
                    {isRootPath ? 
                        <div>
                            <h1>DashBoard</h1>
                            <div>
                                P
                            </div>
                        </div> : <Outlet />}
                </div>
            </div>)
    )
}

export default HomePage;