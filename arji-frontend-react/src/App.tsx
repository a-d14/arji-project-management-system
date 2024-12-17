import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import './App.css'
import AddProject from './pages/AddProject';
import HomePage from './pages/HomePage';
import AuthPage from './pages/AuthPage';
import ProjectList from './pages/ProjectList';
import TicketList from './pages/TicketList';

const router = createBrowserRouter([
  {
    path: '',
    element: <HomePage />,
    children: [
      {
        path: 'project-list',
        element: <ProjectList />
      }, 
      {
        path: 'ticket-list',
        element: <TicketList />
      }
    ]
  },
  {
    path: '/auth',
    element: <AuthPage />
  },
  {
    path: '/projects/add-project',
    element: <AddProject />
  }
]);

function App() {
  return <RouterProvider router={router} />
}

export default App;
