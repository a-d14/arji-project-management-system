import { NavLink, Outlet, useLocation, useMatch } from "react-router-dom";

const HomePage : React.FC = () => {

    // const location = useLocation();

    const isRootPath = useMatch("/");

    // console.log(location.state);

    return (
        <div className="home-page">
            <nav className="sidebar">
                <div className="user-details">
                    <img src="" alt="profile picture" />
                    <h3>User Name</h3>
                </div>
                <ul className="menu">
                    <li><NavLink to="">Home</NavLink></li>
                    <li><NavLink to="project-list">Projects</NavLink></li>
                    <li><NavLink to="ticket-list">Tickets</NavLink></li>
                </ul>
                <NavLink to="/auth">Logout</NavLink>
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
            {/* <h1>Hello {location.state.username}</h1> */}
        </div>
    )
}

export default HomePage;